package de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.handler;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.DomHelpers;
import de.hshannover.f4.trust.ifmapj.binding.IfmapStrings;
import de.hshannover.f4.trust.ifmapj.exception.MarshalException;
import de.hshannover.f4.trust.ifmapj.exception.UnmarshalException;
import de.hshannover.f4.trust.ifmapj.identifier.Identifier;
import de.hshannover.f4.trust.ifmapj.identifier.Identifiers.Helpers;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.ExtendedIdentifier;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.Rule;
import de.hshannover.f4.trust.irondetect.policy.publisher.util.PolicyStrings;

/**
 * An {@link RuleHandler} extends the ExtendedIdentifierHandler. It transforms an {@link Rule}-
 * {@link ExtendedIdentifier} to a XML {@link Element}.
 * 
 * @author Marcel Reichenbach
 */
public class RuleHandler extends ExtendedIdentifierHandler<Rule> {

	@Override
	public Element toExtendedElement(Identifier i, Document doc) throws MarshalException {
		Helpers.checkIdentifierType(i, this);

		Rule rule = (Rule) i;

		String id = rule.getID();

		if (id == null) {
			throw new MarshalException("No id set");
		}

		Element policyElement = doc.createElementNS(PolicyStrings.POLICY_IDENTIFIER_NS_URI,
				PolicyStrings.RULE_EL_NAME);
		Element idElement = doc.createElementNS(null, PolicyStrings.ID_EL_NAME);
		idElement.setTextContent(id);

		policyElement.appendChild(idElement);

		Helpers.addAdministrativeDomain(policyElement, rule);

		return policyElement;
	}

	@Override
	public Rule fromExtendedElement(Element element) throws UnmarshalException {

		Element child = null;

		String administrativeDomain = element.getAttribute(IfmapStrings.IDENTIFIER_ATTR_ADMIN_DOMAIN);
		List<Element> children = DomHelpers.getChildElements(element);

		if (children.size() != 1) {
			throw new UnmarshalException("Bad " + PolicyStrings.RULE_EL_NAME + " element? Has " + children.size()
					+ " child elements.");
		}

		child = children.get(0);

		if (!DomHelpers.elementMatches(child, PolicyStrings.ID_EL_NAME)) {
			throw new UnmarshalException("Unknown child element in " + PolicyStrings.RULE_EL_NAME + " element: "
					+ child.getLocalName());
		}

		String ruleId = child.getTextContent();

		if (ruleId == null || ruleId.length() == 0) {
			throw new UnmarshalException("No text content for ruleId found");
		}

		Rule rule = new Rule(ruleId, administrativeDomain);

		return rule;
	}

	@Override
	public Class<Rule> handles() {
		return Rule.class;
	}

}
