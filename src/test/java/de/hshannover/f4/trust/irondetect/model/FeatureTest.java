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
 * This file is part of irondetect, version 0.0.7, 
 * implemented by the Trust@HsH research group at the Hochschule Hannover.
 * %%
 * Copyright (C) 2010 - 2015 Trust@HsH
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
/**
 * 
 */
package de.hshannover.f4.trust.irondetect.model;



import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.hshannover.f4.trust.Category;
import de.hshannover.f4.trust.Feature;
import de.hshannover.f4.trust.FeatureType;

/**
 * @author bahellma
 *
 */
public class FeatureTest {

	String featureId;
	String normalizedId;
	String categoryId;
	String categoryInstance;
	String qualifiedFeatureId;
	
	private Feature f1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.featureId = "Name";
		this.normalizedId = "name";
		this.categoryId = "category1.subcategory1";
		this.categoryInstance = ":0";
		this.qualifiedFeatureId = "category1.subcategory1:0.feature1.name";
		
		this.f1 = new Feature(this.featureId, "0.0", new FeatureType(FeatureType.QUALIFIED), new Category(this.categoryId + this.categoryInstance + ".feature1"), null);
	}

	@Test
	public void testIds() {
		assertTrue(f1.getId().equals(this.normalizedId));
		assertTrue(f1.getQualifiedId().equals(this.qualifiedFeatureId));
		assertTrue(f1.getQualifiedIdWithoutInstance().contains(this.normalizedId));
	}

}
