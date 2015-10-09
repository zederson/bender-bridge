package br.com.bender.hardware;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.bender.Configuration;

public class ActionValue {

	private String content;
	private String value;
	private String name;
	private String queue;

	public ActionValue(String content) {
		this.content = content;
		buildValues();
	}

	private void buildValues() {
		String exp = "(^[\\w]*)\\[(.*?)\\]";
		Pattern pattern = Pattern.compile(exp);
		Matcher m = pattern.matcher(content);

		if (m.find()) {
			setName(m.group(1));
			setValue(m.group(2));
			buildQueue();
		}
	}

	public boolean isValid() {
		return (value != null && !value.isEmpty()) && hasQueue();
	}

	public boolean hasQueue() {
		return (getQueue() != null && !getQueue().isEmpty());
	}

	private void buildQueue() {
		Configuration config = Configuration.getInstance();
		setQueue(config.getValue(getName()));
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	@Override
	public String toString() {
		return "ActionValue [value=" + value + ", name=" + name + ", queue=" + queue + "]";
	}
}