package paiso.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paiso.model.openexchange.Rates;
import paiso.util.TestUtil;

public class ExchangeRateTest {

	@Test
	public void shouldMapRatesCorrectly() {
		Rates pseudoRates = TestUtil.constructPseudoRates(Currency.USD);
		ExchangeRate rate = ExchangeRate.newInstance(pseudoRates);
		
		assertThat(rate.getBaseCurrency().name()).isEqualTo(pseudoRates.getBase());
		assertThat((int)rate.getLastUpdated().toEpochSecond()).isEqualTo(pseudoRates.getTimestamp());
		
		Currency[] otherCurrencies = TestUtil.allCurrenciesExcept(Currency.USD);
		
		assertThat(rate.getExchangeRate()).containsOnlyKeys(otherCurrencies);
		
		for(Currency currency : otherCurrencies) {
			assertThat(rate.getRate(currency)).isEqualTo(pseudoRates.getRates().get(currency.name()).toString());
		}
		
	}
	
}
