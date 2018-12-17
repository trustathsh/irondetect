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
 * Copyright (C) 2010 - 2018 Trust@HsH
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
package de.hshannover.f4.trust.irondetect.util.event;



import java.util.Calendar;
import java.util.Set;

public class TrainingData {
	
	private Set<String> featureIds;
	
	private Calendar startTime;
	
	private Calendar endTime;
	
	public Set<String> getFeatureIDs() {
		return this.featureIds;
	}
	
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	public Calendar getEndTime() {
		return this.endTime;
	}

	public void setFeatureIds(Set<String> featureIds) {
		this.featureIds = featureIds;
	}

	public void setStartTime(Calendar startTime) {
		if (this.startTime == null || startTime.before(this.startTime)) {
			this.startTime = startTime;
		}
	}

	public void setEndTime(Calendar endTime) {
		if (this.endTime == null || endTime.after(this.endTime)) {
			this.endTime = endTime;
		}
	}
}
