/**
 *  Smart Bulb Notifier/Flasher
 *
 *  Copyright 2015 Scott Gibson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Many thanks to Eric Roberts for his virtual switch creator, which served as the template for creating child switch devices!
 *
 */
definition(
    name: "Smart Bulb Blink",
    namespace: "sticks18",
    author: "Scott Gibson",
    description: "Creates a virtual momentary button that when turned on will trigger selected smart bulbs to blink or flash",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png"


preferences {
	section("Create Virtual Momentary Button as Trigger") {
		input "switchLabel", "text", title: "Momentary Button Label", required: true
	}
	section("Choose your Smart Bulbs")
	  input "bulbType1", "enum", title: "Choose a type of Smart Bulb", required true, multiple: true, options: ["GE Link", "Cree Connected", "Hue (via Hue Bridge", "Hue (via ST Hub)", "Osram Lightify"]
  }
  


def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
    def deviceId = app.id + "SimulatedSwitch"
    log.debug(deviceId)
    def existing = getChildDevice(deviceId)
    if (!existing) {
        def childDevice = addChildDevice("sticks18", "Smart Bulb Alert Momentary", deviceId, null, [label: switchLabel])
    }
}

def uninstalled() {
    removeChildDevices(getChildDevices())
}

private removeChildDevices(delete) {
    delete.each {
        deleteChildDevice(it.deviceNetworkId)
    }
}
