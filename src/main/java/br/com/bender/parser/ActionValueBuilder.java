package br.com.bender.parser;

import br.com.bender.hardware.ActionValue;

public class ActionValueBuilder {

	public static ActionValue build(String content) {
		if(isInvalid(content))
			return null;
		
		return new ActionValue(content);
	}

	private static boolean isInvalid(String content) {
		return content == null || content.isEmpty();
	}
}
