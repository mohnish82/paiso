package paiso.api.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import paiso.model.Currency;
import paiso.model.ExchangeRate;
import paiso.service.IExchangeRateService;
import paiso.service.IUserService;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyExchangeController.class)
public class CurrencyExchangeControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	MessageSource messages;
	
	@Autowired
	ObjectMapper jsonMapper;
	
	@MockBean
	@Qualifier("openexchangerates")
	IExchangeRateService exchangeService;
	
	ExchangeRate fixedRateResponse;
	
	@Before
	public void setup() {
		fixedRateResponse = new ExchangeRate(Currency.USD);
		fixedRateResponse.setLastUpdated(ZonedDateTime.now(ZoneOffset.UTC));
		fixedRateResponse.addExchangeRate(Currency.AUD, "1.25");
		fixedRateResponse.addExchangeRate(Currency.CNY, "1.26");
		fixedRateResponse.addExchangeRate(Currency.EUR, "1.27");
		fixedRateResponse.addExchangeRate(Currency.GBP, "1.28");
		fixedRateResponse.addExchangeRate(Currency.JPY, "1.29");
		
		when(exchangeService.exchangeRate(any(), any())).thenReturn(fixedRateResponse);
	}
	
	@Test
	public void shouldReturnOkResponseForUsd() throws Exception {
		mockMvc.perform(get("/exchange/USD"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(jsonMapper.writeValueAsString(fixedRateResponse)));
	}

	@Test
	public void shouldReturnOkResponseForPastDay() throws Exception {
		String yesterday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		mockMvc.perform(get("/exchange/" + yesterday + "/USD"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(jsonMapper.writeValueAsString(fixedRateResponse)));
	}
	
	@Test
	public void shouldReturnBadRequestErrorForUnsupportedCurrency() throws Exception {
		mockMvc.perform(get("/exchange/XYZ"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString(messages.getMessage("error.invalid-currency-code", null, Locale.getDefault()))));
	}

	@Test
	public void shouldReturnBadRequestErrorForFutureDate() throws Exception {
		ZonedDateTime tomorrow = ZonedDateTime.of(LocalDateTime.now().plusDays(2), ZoneId.systemDefault());
		String tomorrowUtcStr = tomorrow.withZoneSameInstant(ZoneOffset.UTC).toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
		
		mockMvc.perform(get("/exchange/" + tomorrowUtcStr + "/USD"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString(messages.getMessage("error.future-date", null, Locale.getDefault()))));
	}
	
	@Configuration
	@ComponentScan(basePackageClasses = { CurrencyExchangeController.class })
	public static class TestConf {
		@Bean public IUserService pseudoUserService() {
			return Mockito.mock(IUserService.class);
		}
	}
	
}
