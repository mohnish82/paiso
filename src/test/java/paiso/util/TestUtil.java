package paiso.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import paiso.model.Currency;
import paiso.model.openexchange.Rates;

public class TestUtil {

	public static Rates constructPseudoRates(Currency baseCurrency) {
		Rates pseudoRates = new Rates();
		
		pseudoRates.setBase(baseCurrency.name());
		pseudoRates.setTimestamp((int)LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		
		Map<String, Double> rates = new HashMap<>(8);
		for(Currency currency : Currency.values()) {
			if(!currency.equals(baseCurrency))
				rates.put(currency.name(), Double.parseDouble("1.25"));
		}
		pseudoRates.setRates(rates);
		
		return pseudoRates;
	}
	
	public static Currency[] allCurrenciesExcept(Currency currency) {
		return Arrays.stream(Currency.values())
								.filter(curr -> !curr.equals(currency))
								.toArray(size -> new Currency[size]);
	}
	
}
