/**
 * 
 */
package de.fhhannover.inform.trust.irondetect.model;

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

import de.fhhannover.inform.trust.irondetect.gui.ResultLogger;
import de.fhhannover.inform.trust.irondetect.gui.ResultLoggerImpl;
import java.util.List;

import org.apache.log4j.Logger;

import de.fhhannover.inform.trust.irondetect.util.BooleanOperator;
import de.fhhannover.inform.trust.irondetect.util.Pair;

/**
 * @author jvieweg
 *
 */
public class Condition extends Evaluable{
	
	private static Logger logger = Logger.getLogger(Condition.class);
        private ResultLogger rlogger = ResultLoggerImpl.getInstance();

	private List<Pair<ConditionElement, BooleanOperator>> conditionSet;
	
	private Rule parent;

	
	
	/**
	 * @return the conditionSet
	 */
	public List<Pair<ConditionElement, BooleanOperator>> getConditionSet() {
		return conditionSet;
	}

	/**
	 * @param conditionSet the conditionSet to set
	 */
	public void setConditionSet(List<Pair<ConditionElement, BooleanOperator>> conditionSet) {
		this.conditionSet = conditionSet;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder condSetStr = new StringBuilder("conditionSet=");
		if(conditionSet != null) {
			for (Pair<ConditionElement, BooleanOperator> p : conditionSet) {
			condSetStr.append(p.toString());
			}
		}
		return "Condition [" + condSetStr + ", "
				+ super.toString() + "]";
	}

	@Override
	public boolean evaluate(String device) {
		logger.debug("evaluating condition " + this.getId());
		for (Pair<ConditionElement, BooleanOperator> p : this.conditionSet) {
			p.getFirstElement().setParent(this.parent);
		}
		boolean result = evaluateConditionSet(device);
		logger.debug("condition " + super.id + " evaluation returned " + result);
                rlogger.reportResultsToLogger(device, super.id, this.getClass().getSimpleName(), result);
		return result;

	}
	
	private boolean evaluateConditionSet(String device) {
		boolean result = false;
		if(getConditionSet().size() < 2) {
			return getConditionSet().get(0).getFirstElement().evaluate(device);
		}
		BooleanOperator op = getConditionSet().get(1).getSecondElement();
		switch (op) {
		case AND:
			for (int i = 0; i < getConditionSet().size(); i++) {
				result = getConditionSet().get(i).getFirstElement().evaluate(device);
				if(!result){
					return result;
				}
			}
			break;
		case OR:
			for (int i = 0; i < getConditionSet().size(); i++) {
				result = getConditionSet().get(i).getFirstElement().evaluate(device);
				if(result){
					return result;
				}
			}
			break;
		default:
			logger.error("Only AND/OR supported at this time!");
			break;
		}
		return result;
	}
	
	/**
	 * @param parent the parent rule to set
	 */
	public void setParent(Rule parent) {
		this.parent = parent;
	}

}