package paiso.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import paiso.model.Currency;

@Component
public class OpenExchangeRatesConfig {

	private String apiKey;
	private String apiBaseUrl;
	private String latestRateEndpoint;
	private String historicalRateEndpoint;
	
	@Autowired
	public OpenExchangeRatesConfig(Environment env) {
		apiKey = env.getProperty(AppConstants.OPEN_EXCHANGE_API_KEY);
		apiBaseUrl = env.getProperty(AppConstants.OPEN_EXCHANGE_API_BASE_URL);
		latestRateEndpoint = env.getProperty(AppConstants.OPEN_EXCHANGE_API_ENDPOINT_LATEST);
		historicalRateEndpoint = env.getProperty(AppConstants.OPEN_EXCHANGE_API_ENDPOINT_HISTORICAL);
	}
	
	public String latestRatesUrl(Currency currency) {
		String url =  apiBaseUrl + latestRateEndpoint + "?app_id=" + apiKey;
		
		if(currency != null)
			url += "&base=" + currency.name();
		
		return applyCurrencyFilter(url, currency);
	}
	
	public String historicRateUrl(Currency currency, LocalDate date) {
		String url =  apiBaseUrl + historicalRateEndpoint.replaceFirst("\\<date\\>", date.format(DateTimeFormatter.ISO_LOCAL_DATE)) + "?app_id=" + apiKey;
		
		if(currency != null)
			url += "&base=" + currency.name();
		
		return applyCurrencyFilter(url, currency);
	}
	
	private String applyCurrencyFilter(String url, Currency base) {
		
		if(base == null)
			return url;
		
		StringBuilder buff = new StringBuilder(url);
		buff.append("&symbols=");
		
		for(Currency currency : Currency.values()) {
			if(!currency.equals(base)) {
				buff.append(currency.name());
				buff.append(",");
			}
		}
		
		return buff.deleteCharAt(buff.length()-1).toString();
	}

}