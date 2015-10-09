package br.com.bender.messages;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import br.com.bender.hardware.sender.BridgeSernder;

public class MessageCallback implements MqttCallback {

	@Override
	public void connectionLost(Throwable e) {
		System.err.println(e.getMessage());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	@Override
	public void messageArrived(String topick, MqttMessage message) throws Exception {
		String payload = new String(message.getPayload());
		BridgeSernder.send(topick, payload);
	}
}