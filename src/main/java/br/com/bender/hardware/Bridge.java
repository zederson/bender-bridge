package br.com.bender.hardware;

import java.io.IOException;
import java.io.OutputStream;

import br.com.bender.Configuration;
import br.com.bender.exception.PortNotFound;
import br.com.bender.hardware.event.BenderEventListener;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Bridge {

	private static final Bridge instance = new Bridge();

	private Configuration config = Configuration.getInstance();
	private SerialPort serialPort;
	private OutputStream output;

	private static Bridge getInstance() {
		return instance;
	}

	private SerialPort getSerialPort() {
		return serialPort;
	}

	private void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	private void initialize() {
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(config.getPort());

			if (portId == null)
				throw new PortNotFound();

			SerialPort serial = (SerialPort) portId.open(config.getName(), config.getTimeOut());

			serial.setSerialPortParams(config.getDataRate(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			serial.addEventListener(new BenderEventListener(serial.getInputStream()));
			serial.notifyOnDataAvailable(true);
			serial.notifyOnOutputEmpty(true);

			setOutput(serial.getOutputStream());

			setSerialPort(serial);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (getSerialPort() != null) {
			try {
				if (getOutput() != null)
					getOutput().close();
			} catch (IOException e) {
			}
			getSerialPort().removeEventListener();
			getSerialPort().close();
		}
	}

	public static void start() {
		getInstance().initialize();
	}

	public static void stop() {
		getInstance().close();
	}

	public static void send(String value) {
		try {
			getInstance().getOutput().write(value.getBytes());
		} catch (IOException e) {
			System.err.println("Deu Ruim ao enviar para o bender");
		}
	}

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}
}