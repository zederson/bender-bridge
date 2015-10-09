package br.com.bender.hardware.sender;

import java.util.HashMap;
import java.util.Map;

public class MessageSender {

	private static final Map<String, MessageSender> map = new HashMap<>();

	private String topick;
	private String on;
	private String off;

	public MessageSender(String topick, String on, String off) {
		this.topick = topick;
		this.on = on;
		this.off = off;
	}

	public String getTopick() {
		return topick;
	}

	public void setTopick(String topick) {
		this.topick = topick;
	}

	public String getOn() {
		return on;
	}

	public void setOn(String on) {
		this.on = on;
	}

	public String getOff() {
		return off;
	}

	public void setOff(String off) {
		this.off = off;
	}

	public String getValue(String payload) {
		boolean inOut = Boolean.parseBoolean(payload);
		return inOut ? getOn() : getOff();
	}

	public static void add(String topick, String on, String off) {
		MessageSender message = new MessageSender(topick, on, off);
		getMap().put(topick, message);
	}

	public static String getValue(String topick, String payload) {
		MessageSender message = getMap().get(topick);
		if (message != null)
			return message.getValue(payload);
		return "";
	}

	public static Map<String, MessageSender> getMap() {
		return map;
	}
}