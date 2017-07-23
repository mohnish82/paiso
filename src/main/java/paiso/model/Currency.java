package paiso.model;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public enum Currency {
	
	AUD ("Australian Dollar"),
	CNY ("Chinese Yuan"),
	EUR ("Euro"),
	GBP ("British Pound"),
	JPY ("Japanese Yen"),
	USD ("US Dollar");
	
	private final String text;
	private static final Set<String> enumCodes = new HashSet<>();
	
	static {
		for(Currency currency : EnumSet.allOf(Currency.class)) {
			enumCodes.add(currency.name());
		}
	}
	
	private Currency(String text) {
		this.text = text;
	}
	
	public static boolean isValidCurrencyCode(String code) {
		return code != null 
				&& !code.trim().isEmpty()
				&& enumCodes.contains(code);
	}
	
	public String getText() {
		return this.text;
	}

}
