package paiso.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import paiso.model.openexchange.Rates;

/**
 * Domain class to represent the exchange rate(s) for a currency. Maintains the time when these exchange rates were last refreshed.
 */
public class ExchangeRate extends BaseEntity {
	private static final long serialVersionUID = 1449949742943269360L;

	private Currency baseCurrency;
	private Map<Currency, String> exchangeRate;
	
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime lastUpdated;

	public ExchangeRate(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
		exchangeRate = new TreeMap<>();
	}

	public Map<Currency, String> getExchangeRate() {
		return exchangeRate;
	}

	public void addExchangeRate(Currency currency, String rate) {
		exchangeRate.put(currency, rate);
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public ZonedDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(ZonedDateTime timestamp) {
		this.lastUpdated = timestamp;
	}

	public String getRate(Currency currency) {
		return exchangeRate.get(currency);
	}
	
	public static ExchangeRate newInstance(Rates rates) {
		
		if(rates == null || !Currency.isValidCurrencyCode(rates.getBase()))
			return null;
		
		ExchangeRate _new = new ExchangeRate(Currency.valueOf(rates.getBase()));
		_new.lastUpdated = ZonedDateTime.ofInstant(
						Instant.ofEpochSecond(rates.getTimestamp()),
						ZoneOffset.UTC);
		
		for(Map.Entry<String, Double> rate : rates.getRates().entrySet()) {
			_new.exchangeRate.put(Currency.valueOf(rate.getKey()), rate.getValue().toString());
		}
		
		return _new;
	}
}
