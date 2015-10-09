package br.com.bender.messages;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import br.com.bender.Configuration;

public class Subscriber {

	private static final Configuration config = Configuration.getInstance();
	private String queue;
	private MqttClient client;
	private MqttCallback callback;

	public Subscriber(String queue, MqttCallback callback) {
		this.queue = queue;
		this.callback = callback;
	}

	public void start() {
		new Thread(() -> subscribe()).start();
	}

	private void subscribe() {
		try {
			getConnection().subscribe(queue, 1);
		} catch (MqttException e) {
			System.err.println(e.getMessage());
		}
	}

	private MqttClient getConnection() {
		if (client != null && client.isConnected())
			return client;

		try {
			MemoryPersistence persistence = new MemoryPersistence();
			String name = String.format("%s/%s", config.getName(), queue);
			client = new MqttClient(config.getUrlMqtt(), name, persistence);

			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			client.setCallback(callback);
			client.connect(options);

		} catch (MqttException me) {
			System.err.println(me.getMessage());
		}
		return client;
	}
}