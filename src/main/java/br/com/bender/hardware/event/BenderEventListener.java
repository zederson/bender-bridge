package br.com.bender.hardware.event;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.bender.hardware.ActionValue;
import br.com.bender.messages.Publisher;
import br.com.bender.parser.ActionValueBuilder;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class BenderEventListener implements SerialPortEventListener {
	private final BufferedReader input;

	public BenderEventListener(InputStream input) {
		this.input = new BufferedReader(new InputStreamReader(input));
	}

	public BufferedReader getInput() {
		return input;
	}

	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String content = input.readLine();
				ActionValue value = ActionValueBuilder.build(content);
				Publisher.publish(value);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}
}