package paiso.filter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import paiso.model.Currency;
import paiso.model.ExchangeRate;
import paiso.model.RateInquiry;
import paiso.model.UserProfile;
import paiso.service.IUserService;

@Component
@Aspect
public class RateInquiryFilter {

	@Autowired
	IUserService userService;
	
	@Autowired
	UserProfile profile;
	
	@Pointcut("execution(* paiso.service.OpenExchangeRatesService.exchangeRate(paiso.model.Currency, *))"
			+ " && args(baseCurrency, date)")
	public void onRateInquiry(Currency baseCurrency, LocalDate date) {}

	@Around("onRateInquiry(baseCurrency, date)")
	public Object withUsdBaseCurrency(ProceedingJoinPoint pj, Currency baseCurrency, LocalDate date) throws Throwable {
	
		ExchangeRate usdRates = (ExchangeRate) pj.proceed(new Object[] {Currency.USD, date});
		
		ExchangeRate requestedRates = baseCurrency.equals(Currency.USD) 
										? usdRates
										: adaptUsdRatesToCurrency(baseCurrency, usdRates);

		try {
			saveRateInquiry(baseCurrency, date);
		}catch(Exception e) {
			e.printStackTrace();
			// do not error out if saving fails.
		}
		
		return requestedRates;
	}

	private ExchangeRate adaptUsdRatesToCurrency(Currency baseCurrency, ExchangeRate usdRates) {
		MathContext context = MathContext.DECIMAL128;
		
		String baseCurrencyRateStr = usdRates.getRate(baseCurrency);
		BigDecimal usdToCurrencyRatio = BigDecimal.ONE.divide(new BigDecimal(baseCurrencyRateStr), context);
		
		ExchangeRate baseCurrencyRate = new ExchangeRate(baseCurrency);
		
		for(Map.Entry<Currency, String> rate : usdRates.getExchangeRate().entrySet()) {
			if(!rate.getKey().equals(baseCurrency))
				baseCurrencyRate.addExchangeRate(rate.getKey(),
						new BigDecimal(rate.getValue()).multiply(usdToCurrencyRatio, context).setScale(5, RoundingMode.HALF_UP).toPlainString());
		}
		
		baseCurrencyRate.addExchangeRate(Currency.USD, usdToCurrencyRatio.setScale(5, RoundingMode.HALF_UP).toPlainString());
		
		return baseCurrencyRate;
	}
	
	//@AfterReturning("onRateInquiry(baseCurrency, date)")
	public void saveRateInquiry(Currency baseCurrency, LocalDate date) {
		
		RateInquiry inquiry = new RateInquiry(profile.getUser().getId());
		inquiry.setCurrency(baseCurrency);
		inquiry.setDate(date != null ? date : LocalDate.now());
		
		RateInquiry savedInquiry = userService.saveUserRateInquiry(inquiry);
		profile.addRecentInquiry(savedInquiry);
	}
	
}