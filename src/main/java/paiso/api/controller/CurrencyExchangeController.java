package paiso.api.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import paiso.exception.BadRequestDataException;
import paiso.model.Currency;
import paiso.model.ExchangeRate;
import paiso.service.IExchangeRateService;

@RestController
public class CurrencyExchangeController extends BaseController {
	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

	@Autowired
	@Qualifier("openexchangerates")
	IExchangeRateService exchangeService;
	
	@GetMapping("/exchange/{currencyCode}")
	public ExchangeRate exchangeRate(@PathVariable String currencyCode) {
		
		currencyCode = currencyCode.toUpperCase();
		validateCurrency(currencyCode);
		
		return exchangeService.exchangeRate(Currency.valueOf(currencyCode), null);
	}

	@GetMapping("/exchange/{date}/{currencyCode}")
	public ExchangeRate exchangeRate(@PathVariable String date, @PathVariable String currencyCode) {
		
		currencyCode = currencyCode.toUpperCase();
		
		if(!Currency.isValidCurrencyCode(currencyCode))
			throw new BadRequestDataException(message("error.invalid-currency-code"));
		
		Currency validCurrency = validateCurrency(currencyCode);
		LocalDate validDate = validateDate(date);
		
		return exchangeService.exchangeRate(validCurrency, validDate);
	}
	
	protected Currency validateCurrency(String currencyCode) throws BadRequestDataException {
		if(!Currency.isValidCurrencyCode(currencyCode))
			throw new BadRequestDataException(message("error.invalid-currency-code"));		
	
		return Currency.valueOf(currencyCode);
	}

	protected LocalDate validateDate(String date) throws BadRequestDataException {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.STRICT);
			ZonedDateTime utcDate = ZonedDateTime.of(LocalDate.parse(date, formatter), LocalTime.MIDNIGHT, ZoneOffset.UTC);
			ZonedDateTime localDateTime = utcDate.withZoneSameInstant(ZoneId.systemDefault());
			localDate = localDateTime.toLocalDate();
			
			if(localDate.isAfter(LocalDate.now()))
				throw new BadRequestDataException(message("error.future-date"));
					
		}catch(DateTimeParseException e) {
			throw new BadRequestDataException(message("error.invalid-yyyyMMdd-date"));
		}
		
		return localDate;
	}
	
}
