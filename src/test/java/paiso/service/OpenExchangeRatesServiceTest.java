package paiso.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import paiso.model.Currency;
import paiso.model.ExchangeRate;
import paiso.model.openexchange.Rates;
import paiso.util.OpenExchangeRatesConfig;
import paiso.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class OpenExchangeRatesServiceTest {

	@InjectMocks
	OpenExchangeRatesService service;

	@Mock
	OpenExchangeRatesConfig config;
	
	@Mock
	RestTemplate restTemplate;
	
	private static LocalDate today = LocalDate.now();
	private Rates pseudoRates = TestUtil.constructPseudoRates(Currency.USD);
	
	@Before
	public void setup() {
		when(config.latestRatesUrl(any())).thenReturn("/latest.json");
		when(config.historicRateUrl(any(), any(LocalDate.class))).thenReturn("/historical/2017-07-15.json");		
		when(restTemplate.getForObject(any(URI.class), Mockito.eq(Rates.class))).thenReturn(pseudoRates);
	}
	
	@Test
	public void determineApiUrl_shouldUseLatestUrlIfDateMissing() {
		assertThat(service.determineApiUrl(Currency.USD, null))
		.contains("/latest.json");
	}

	@Test
	public void determineApiUrl_shouldUseLatestUrlForToday() {
		assertThat(service.determineApiUrl(Currency.USD, today))
		.contains("/latest.json");
	}

	@Test
	public void determineApiUrl_shouldUseHistoricalUrlForPreviousDay() {
		assertThat(service.determineApiUrl(Currency.USD, today.minusDays(1)))
		.contains("/historical/");
	}

	@Test
	public void exchangeRate_shouldReturnNullIfBaseCurrencyMissing() {
		assertThat(service.exchangeRate(null, today)).isNull();
	}
	
	@Test
	public void exchangeRate_shouldReturnProvidedRates() {
		Currency baseCurrency = Currency.valueOf(pseudoRates.getBase());
		ExchangeRate rate = service.exchangeRate(baseCurrency, null);
		
		assertThat(rate.getBaseCurrency()).isEqualTo(baseCurrency);
		assertThat(rate.getLastUpdated()).isNotNull();
		assertThat(rate.getExchangeRate().keySet()).contains(TestUtil.allCurrenciesExcept(Currency.USD));
	}
}