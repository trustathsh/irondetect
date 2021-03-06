/*
 * #%L
 * =====================================================
 *   _____                _     ____  _   _       _   _
 *  |_   _|_ __ _   _ ___| |_  / __ \| | | | ___ | | | |
 *    | | | '__| | | / __| __|/ / _` | |_| |/ __|| |_| |
 *    | | | |  | |_| \__ \ |_| | (_| |  _  |\__ \|  _  |
 *    |_| |_|   \__,_|___/\__|\ \__,_|_| |_||___/|_| |_|
 *                             \____/
 * 
 * =====================================================
 * 
 * Hochschule Hannover
 * (University of Applied Sciences and Arts, Hannover)
 * Faculty IV, Dept. of Computer Science
 * Ricklinger Stadtweg 118, 30459 Hannover, Germany
 * 
 * Email: trust@f4-i.fh-hannover.de
 * Website: http://trust.f4.hs-hannover.de/
 * 
 * This file is part of irondetect, version 0.0.10, 
 * implemented by the Trust@HsH research group at the Hochschule Hannover.
 * %%
 * Copyright (C) 2010 - 2016 Trust@HsH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.Anomaly;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.ExtendedIdentifier;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.handler.AnomalyHandler;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.handler.ExtendedIdentifierHandler;
import de.hshannover.f4.trust.irondetect.policy.publisher.util.PolicyStrings;
import util.DomHelpers;

public class AnomalyHandlerTest extends AbstractHandlerTest {

	private Element mAnomalyElement;

	private Anomaly mAnomalyIdentfier;

	private ExtendedIdentifierHandler<?> mHandler;

	static {
		// register extended identifier PolicyHandler handler to ifmapJ
		Identifiers.registerIdentifierHandler(new AnomalyHandler());
	}

	public AnomalyHandlerTest() throws FileNotFoundException, ParseException {
		super();

		mHandler = new AnomalyHandler();
	}

	@Before
	public void setUp() throws Exception {
		ExtendedIdentifier identfier = PolicyDataManager.transformPolicyData(
				super.mPolicy.getRuleSet().get(0).getCondition().getConditionSet().get(1).getFirstElement());

		if (identfier instanceof Anomaly) {
			mAnomalyIdentfier = (Anomaly) identfier;

			mAnomalyElement = Identifiers.toElement(mAnomalyIdentfier, super.mDocumentBuilder.newDocument());
		}else{
			throw new RuntimeException("The transformed PolicyData is not the right ExtendedIdentifier type.");
		}
	}

	@Test
	public void TO_ELEMENT_expected_rightElement() throws MarshalException {
		Element policyElement = mHandler.toExtendedElement(mAnomalyIdentfier, super.mDocumentBuilder.newDocument());

		String administrativeDomain = policyElement.getAttribute(IfmapStrings.IDENTIFIER_ATTR_ADMIN_DOMAIN);
		String identityAdministrativeDomain = mAnomalyElement.getAttribute(IfmapStrings.IDENTIFIER_ATTR_ADMIN_DOMAIN);
		String identityOtherTypeDefinition = mAnomalyElement.getAttribute(IfmapStrings.IDENTITY_ATTR_OTHER_TYPE_DEF);
		String identityType = mAnomalyElement.getAttribute(IfmapStrings.IDENTITY_ATTR_TYPE);
		String identityName = mAnomalyElement.getAttribute(IfmapStrings.IDENTITY_ATTR_NAME);

		List<Element> children = DomHelpers.getChildElements(policyElement);
		List<Element> identityChildren = DomHelpers.getChildElements(mAnomalyElement);

		// assert extended Identity Element
		assertEquals(IfmapStrings.IDENTITY_EL_NAME, mAnomalyElement.getLocalName());
		assertEquals(IfmapStrings.OTHER_TYPE_EXTENDED_IDENTIFIER, identityOtherTypeDefinition);
		assertEquals(IdentityType.other.toString(), identityType);
		assertEquals(true, identityName.length() > 0);
		assertEquals(0, identityChildren.size());
		assertEquals("", identityAdministrativeDomain);

		// assert extended Element
		assertEquals(PolicyStrings.ANOMALY_EL_NAME, policyElement.getLocalName());
		assertEquals(2, children.size());
		assertEquals(PolicyStrings.ID_EL_NAME, children.get(0).getLocalName());
		assertEquals("TestAnomaly1", children.get(0).getTextContent());
		assertEquals(PolicyStrings.HINT_EXPRESSION_EL_NAME, children.get(1).getLocalName());
		assertEquals("TestHint1 > 0.5", children.get(1).getTextContent());
		assertEquals(PolicyStrings.DEFAULT_ADMINISTRATIVE_DOMAIN, administrativeDomain);
		assertEquals(PolicyStrings.POLICY_IDENTIFIER_NS_URI, policyElement.getNamespaceURI());
	}

	@Test
	public void FROM_ELEMENT_expected_rightExtendedIdentifier() throws UnmarshalException {
		IdentifierHandler<?> ih = Identifiers.getHandlerFor(mAnomalyElement);

		Identifier identifier = ih.fromElement(mAnomalyElement);

		assertEquals(Anomaly.class, identifier.getClass());
		assertEquals("TestAnomaly1", ((Anomaly) identifier).getID());
		assertEquals(1, ((Anomaly) identifier).getExpressions().size());
		assertEquals("TestHint1 > 0.5", ((Anomaly) identifier).getExpressions().get(0));
		assertEquals(PolicyStrings.DEFAULT_ADMINISTRATIVE_DOMAIN, ((Anomaly) identifier).getAdministrativeDomain());
	}
}
