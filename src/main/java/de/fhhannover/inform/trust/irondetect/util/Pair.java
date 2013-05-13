/**
 * 
 */
package de.fhhannover.inform.trust.irondetect.util;

/*
 * #%L
 * ====================================================
 *   _____                _     ____  _____ _   _ _   _
 *  |_   _|_ __ _   _ ___| |_  / __ \|  ___| | | | | | |
 *    | | | '__| | | / __| __|/ / _` | |_  | |_| | |_| |
 *    | | | |  | |_| \__ \ |_| | (_| |  _| |  _  |  _  |
 *    |_| |_|   \__,_|___/\__|\ \__,_|_|   |_| |_|_| |_|
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
 * Website: http://trust.inform.fh-hannover.de/
 * 
 * This file is part of irongui, version 0.0.3, implemented by the Trust@FHH 
 * research group at the Hochschule Hannover, a program to visualize the content
 * of a MAP Server (MAPS), a crucial component within the TNC architecture.
 * 
 * The development was started within the bachelor
 * thesis of Tobias Ruhe at Hochschule Hannover (University of
 * Applied Sciences and Arts Hannover). irongui is now maintained
 * and extended within the ESUKOM research project. More information
 * can be found at the Trust@FHH website.
 * %%
 * Copyright (C) 2010 - 2013 Trust@FHH
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
 * @author jvieweg
 *
 */
public class Pair<A,B> {
	
	private A firstElement;
	private B secondElement;
	
	public Pair(A first, B second){
		this.firstElement = first;
		this.secondElement = second;
	}
	
	/**
	 * @return the firstElement
	 */
	public A getFirstElement() {
		return firstElement;
	}

	/**
	 * @param firstElement the firstElement to set
	 */
	public void setFirstElement(A firstElement) {
		this.firstElement = firstElement;
	}

	/**
	 * @return the secondElement
	 */
	public B getSecondElement() {
		return secondElement;
	}

	/**
	 * @param secondElement the secondElement to set
	 */
	public void setSecondElement(B secondElement) {
		this.secondElement = secondElement;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str;
		if(this.firstElement != null) {
			str = "firstElement=" + firstElement.toString() + ", ";
		} else {
			str = "firstElement=null,";
		}
		if(this.secondElement != null) {
			str += "secondElement=" + secondElement.toString();
		} else {
			str += "secondElement=null";
		}
		return "Pair [" + str + "]";
	}
	

}