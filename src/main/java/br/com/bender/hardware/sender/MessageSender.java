package br.com.bender.hardware.sender;

import java.util.HashMap;
import java.util.Map;

public class MessageSender {

	private static final Map<String, MessageSender> map = new HashMap<>();
	private String topick;

	public MessageSender(String topick) {
		this.topick = topick;
	}

	public String getTopick() {
		return topick;
	}

	public void setTopick(String topick) {
		this.topick = topick;
	}

	public String getValue(String payload) {
		return payload;
	}

	public static void add(String topick) {
		MessageSender message = new MessageSender(topick);
		getMap().put(topick, message);
	}

	public static String getValue(String topick, String payload) {
		MessageSender message = getMap().get(topick);
		if (message != null)
			return message.getValue(payload);
		return payload;
	}

	public static Map<String, MessageSender> getMap() {
		return map;
	}
}