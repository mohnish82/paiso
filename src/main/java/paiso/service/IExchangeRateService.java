package paiso.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import paiso.model.Currency;
import paiso.model.ExchangeRate;

@Service
public interface IExchangeRateService {

	ExchangeRate exchangeRate(Currency baseCurrency, LocalDate effectiveDate);	
	
}
