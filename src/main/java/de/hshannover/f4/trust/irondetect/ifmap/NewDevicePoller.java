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
package de.hshannover.f4.trust.irondetect.ifmap;



import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import de.hshannover.f4.trust.ifmapj.exception.IfmapErrorResult;
import de.hshannover.f4.trust.ifmapj.exception.IfmapException;
import de.hshannover.f4.trust.ifmapj.identifier.Device;

public class NewDevicePoller implements Runnable {

	private Logger logger = Logger.getLogger(NewDevicePoller.class);
	
	private IfmapController controller;
	private BlockingQueue<Device> newDevices;
	private Set<String> devices;

	public NewDevicePoller(IfmapController controller) {
		this.controller = controller;
		this.newDevices = new LinkedBlockingQueue<Device>();
		this.devices = new HashSet<String>();
	}

	@Override
	public void run() {
		Device device = null;
		try {
			while (!Thread.currentThread().isInterrupted()) {
				device = this.newDevices.take();
				if (device != null && !this.devices.contains(device.getName())) {
					logger.trace("Found new smartphone '" + device + "'");
					this.controller.subscribeForSmartphone(device);
					this.devices.add(device.getName());
				}
			}
		} catch (IfmapErrorResult e) {
			e.printStackTrace();
		} catch (IfmapException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void put(Device device) throws InterruptedException {
		this.newDevices.put(device);
	}
}
