package br.com.bender.messages;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import br.com.bender.Configuration;
import br.com.bender.hardware.ActionValue;

public class Publisher {

	private static final int qos = 2;
	private static final Publisher instance = new Publisher();
	private static final Configuration config = Configuration.getInstance();
	private MqttClient client;

	private Publisher() {
	}

	public void start() {
		if (client != null && client.isConnected())
			return;

		try {
			MemoryPersistence persistence = new MemoryPersistence();
			this.client = new MqttClient(config.getUrlMqtt(), config.getName(), persistence);

			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			client.connect(options);
		} catch (MqttException me) {
			System.err.println(me.getMessage());
		}
	}

	public void stop() {
		try {
			this.client.disconnect();
		} catch (MqttException me) {
			System.out.println(me.getMessage());
		}
	}

	public void send(ActionValue value) {
		try {
			if (client == null || !client.isConnected())
				start();

			client.publish(value.getQueue(), buildMessage(value.getValue()));
		} catch (MqttException me) {
			System.out.println(me.getMessage());
		}
	}

	private MqttMessage buildMessage(String content) {
		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(qos);
		return message;
	}

	public MqttClient getClient() {
		return client;
	}

	public static Publisher getInstance() {
		return instance;
	}

	public static void publish(ActionValue value) {
		if (value == null || !value.isValid())
			return;
		getInstance().send(value);
	}
}
