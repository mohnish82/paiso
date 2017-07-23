package paiso.api.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

public abstract class BaseController {

	@Autowired
	private Environment env;
	
	@Autowired
	private MessageSource messages;

	protected String property(String key) {
		return env.getProperty(key);
	}
	
	protected String message(String key) {
		return messages.getMessage(key, null, Locale.getDefault());
	}
}
