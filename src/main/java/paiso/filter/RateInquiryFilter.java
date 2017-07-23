package paiso.filter;

import java.time.LocalDate;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import paiso.model.Currency;
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
	
	@AfterReturning("onRateInquiry(baseCurrency, date)")
	public void saveRateInquiry(Currency baseCurrency, LocalDate date) {
		
		RateInquiry inquiry = new RateInquiry(profile.getUser().getId());
		inquiry.setCurrency(baseCurrency);
		inquiry.setDate(date != null ? date : LocalDate.now());
		
		RateInquiry savedInquiry = userService.saveUserRateInquiry(inquiry);
		profile.addRecentInquiry(savedInquiry);
	}
	
}