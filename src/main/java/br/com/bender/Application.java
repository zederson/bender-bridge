package br.com.bender;

import br.com.bender.hardware.Bridge;
import br.com.bender.hardware.sender.MessageSender;
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
		registerSenders();
	}

	private static void setUpSubscriber() {
		Subscriber subs = new Subscriber("bender/socket/#", new MessageCallback());
		subs.start();
	}

	private static void registerSenders() {
		MessageSender.add("bender/socket/1", "1", "101");
	}

	public static void shutdown() {
		try {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					Publisher.getInstance().stop();
					Bridge.stop();
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}