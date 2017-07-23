package paiso.util;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

import paiso.model.Currency;

@RunWith(MockitoJUnitRunner.class)
public class OpenExchangeRatesConfigTest {

	OpenExchangeRatesConfig config;
	
	@Mock
	Environment env;
	
	@Before
	public void setup() {
		when(env.getProperty(AppConstants.OPEN_EXCHANGE_API_KEY)).thenReturn("123");
		when(env.getProperty(AppConstants.OPEN_EXCHANGE_API_BASE_URL)).thenReturn("http://localhost/api");
		when(env.getProperty(AppConstants.OPEN_EXCHANGE_API_ENDPOINT_LATEST)).thenReturn("/latest.json");
		when(env.getProperty(AppConstants.OPEN_EXCHANGE_API_ENDPOINT_HISTORICAL)).thenReturn("/historical/<date>.json");
		
		config = new OpenExchangeRatesConfig(env);
	}
	
	@Test
	public void latestRateUrl() {
		String url = config.latestRatesUrl(Currency.USD);
		assertThat(url).startsWith("http://localhost/api/latest");
		assertThat(url).contains("&base=USD");
	}
	
	@Test
	public void historicRateUrl() {
		String url = config.historicRateUrl(Currency.USD, LocalDate.of(2017, 1, 1));
		assertThat(url).startsWith("http://localhost/api/historical/2017-01-01.json");
		assertThat(url).contains("&base=USD");
	}
	
}
