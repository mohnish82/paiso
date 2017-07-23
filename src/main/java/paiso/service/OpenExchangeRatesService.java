package paiso.service;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import paiso.model.Currency;
import paiso.model.ExchangeRate;
import paiso.model.openexchange.Rates;
import paiso.util.OpenExchangeRatesConfig;

@Component
@Qualifier("openexchangerates")
public class OpenExchangeRatesService implements IExchangeRateService {

	@Autowired
	OpenExchangeRatesConfig config;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public ExchangeRate exchangeRate(Currency baseCurrency, LocalDate date) {
		
		if(baseCurrency == null)
			return null;
		
		String url = determineApiUrl(baseCurrency, date);
		Rates rates = restTemplate.getForObject(URI.create(url), Rates.class);
		
		return rates == null ? null : ExchangeRate.newInstance(rates);
	}
	
	protected String determineApiUrl(Currency baseCurrency, LocalDate date) {
		LocalDate today = LocalDate.now();
		
		return (date == null || date.isEqual(today))
				? config.latestRatesUrl(baseCurrency)
				: config.historicRateUrl(baseCurrency, date);
	}
	
}
