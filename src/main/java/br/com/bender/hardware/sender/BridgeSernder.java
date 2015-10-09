package br.com.bender.hardware.sender;

import br.com.bender.hardware.Bridge;

public class BridgeSernder {

	public static void send(String topic, String payload) {
		String value = MessageSender.getValue(topic, payload);
		Bridge.send(value);
	}
}