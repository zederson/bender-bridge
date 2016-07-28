package br.com.bender;

import java.util.List;

import br.com.bender.hardware.Bridge;
import br.com.bender.messages.MessageCallback;
import br.com.bender.messages.Publisher;
import br.com.bender.messages.Subscriber;

public class Application {

	public static void main(String[] args) {
		setUp();
		shutdown();
	}

	private static void setUp() {
		Bridge.start();
		setUpSubscriber();
	}

	private static void setUpSubscriber() {
		Configuration config = Configuration.getInstance();
		List<String> list = config.getValues("subscriber.queue");
		for (String queue : list) {
			new Subscriber(queue, new MessageCallback()).start();
		}
	}

	public static void shutdown() {
		try {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					System.out.println("Teh Mais !!!!!");
					Publisher.getInstance().stop();
					Bridge.stop();
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}