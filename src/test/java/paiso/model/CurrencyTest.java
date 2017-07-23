package paiso.model;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

public class CurrencyTest {

	@Test
	public void nullCurrencyCode() {
		assertThat(Currency.isValidCurrencyCode(null)).isFalse();
	}

	@Test
	public void emptyCurrencyCode() {
		assertThat(Currency.isValidCurrencyCode("")).isFalse();
		assertThat(Currency.isValidCurrencyCode(" ")).isFalse();
	}
	
	@Test
	public void invalidCurrencyCode() {
		assertThat(Currency.isValidCurrencyCode("some-invalid-code")).isFalse();
	}

	@Test
	public void validCurrencyCode() {
		assertThat(Currency.isValidCurrencyCode(Currency.USD.name())).isTrue();
		assertThat(Currency.isValidCurrencyCode(Currency.AUD.name())).isTrue();
		assertThat(Currency.isValidCurrencyCode(Currency.CNY.name())).isTrue();
		assertThat(Currency.isValidCurrencyCode(Currency.EUR.name())).isTrue();
		assertThat(Currency.isValidCurrencyCode(Currency.GBP.name())).isTrue();
		assertThat(Currency.isValidCurrencyCode(Currency.JPY.name())).isTrue();
	}
	
}
