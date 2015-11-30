package de.hshannover.f4.trust.irondetect.policy.publisher.identifier.handler;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

import de.hshannover.f4.trust.ifmapj.binding.IfmapStrings;
import de.hshannover.f4.trust.ifmapj.exception.MarshalException;
import de.hshannover.f4.trust.ifmapj.exception.UnmarshalException;
import de.hshannover.f4.trust.ifmapj.identifier.Identifier;
import de.hshannover.f4.trust.ifmapj.identifier.IdentifierHandler;
import de.hshannover.f4.trust.ifmapj.identifier.Identifiers;
import de.hshannover.f4.trust.ifmapj.identifier.IdentityType;
import de.hshannover.f4.trust.irondetect.policy.parser.ParseException;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.handler.PolicyDataManager;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.ExtendedIdentifier;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.Rule;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.handler.ExtendedIdentifierHandler;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.handler.RuleHandler;
import de.hshannover.f4.trust.irondetect.policy.publisher.util.PolicyStrings;
import util.DomHelpers;

public class RuleHandlerTest extends AbstractHandlerTest {

	private Element mRuleElement;

	private Rule mRuleIdentfier;

	private ExtendedIdentifierHandler<?> mHandler;

	static {
		// register extended identifier PolicyHandler handler to ifmapJ
		Identifiers.registerIdentifierHandler(new RuleHandler());
	}

	public RuleHandlerTest() throws FileNotFoundException, ParseException {
		super();

		mHandler = new RuleHandler();
	}

	@Before
	public void setUp() throws Exception {
		if (super.mPolicy.getRuleSet().size() != 1) {
			throw new RuntimeException("Only for one Rule!");
		}

		ExtendedIdentifier identfier = PolicyDataManager.transformPolicyData(super.mPolicy.getRuleSet().get(0));

		if (identfier instanceof Rule) {
			mRuleIdentfier = (Rule) identfier;

			mRuleElement = Identifiers.toElement(mRuleIdentfier, super.mDocumentBuilder.newDocument());
		}else{
			throw new RuntimeException("The transformed PolicyData is not the right ExtendedIdentifier type.");
		}
	}

	@Test
	public void TO_ELEMENT_expected_rightElement() throws MarshalException {
		Element ruleElement = mHandler.toExtendedElement(mRuleIdentfier, super.mDocumentBuilder.newDocument());

		String administrativeDomain = ruleElement.getAttribute(IfmapStrings.IDENTIFIER_ATTR_ADMIN_DOMAIN);
		String identityAdministrativeDomain = mRuleElement.getAttribute(IfmapStrings.IDENTIFIER_ATTR_ADMIN_DOMAIN);
		String identityOtherTypeDefinition = mRuleElement.getAttribute(IfmapStrings.IDENTITY_ATTR_OTHER_TYPE_DEF);
		String identityType = mRuleElement.getAttribute(IfmapStrings.IDENTITY_ATTR_TYPE);
		String identityName = mRuleElement.getAttribute(IfmapStrings.IDENTITY_ATTR_NAME);

		List<Element> children = DomHelpers.getChildElements(ruleElement);
		List<Element> identityChildren = DomHelpers.getChildElements(mRuleElement);

		// assert extended Identity Element
		assertEquals(IfmapStrings.IDENTITY_EL_NAME, mRuleElement.getLocalName());
		assertEquals(IfmapStrings.OTHER_TYPE_EXTENDED_IDENTIFIER, identityOtherTypeDefinition);
		assertEquals(IdentityType.other.toString(), identityType);
		assertEquals(true, identityName.length() > 0);
		assertEquals(0, identityChildren.size());
		assertEquals("", identityAdministrativeDomain);

		// assert extended Element
		assertEquals(PolicyStrings.RULE_EL_NAME, ruleElement.getLocalName());
		assertEquals(1, children.size());
		assertEquals(PolicyStrings.ID_EL_NAME, children.get(0).getLocalName());
		assertEquals("TestRule1", children.get(0).getTextContent());
		assertEquals(PolicyStrings.DEFAULT_ADMINISTRATIVE_DOMAIN, administrativeDomain);
		assertEquals(PolicyStrings.POLICY_IDENTIFIER_NS_URI, ruleElement.getNamespaceURI());
	}

	@Test
	public void FROM_ELEMENT_expected_rightExtendedIdentifier() throws UnmarshalException {
		IdentifierHandler<?> ih = Identifiers.getHandlerFor(mRuleElement);

		Identifier identifier = ih.fromElement(mRuleElement);

		assertEquals(Rule.class, identifier.getClass());
		assertEquals("TestRule1", ((Rule) identifier).getID());
		assertEquals(PolicyStrings.DEFAULT_ADMINISTRATIVE_DOMAIN, ((Rule) identifier).getAdministrativeDomain());
	}
}
