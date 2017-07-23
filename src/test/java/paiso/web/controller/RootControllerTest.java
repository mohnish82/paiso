package paiso.web.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RootControllerTest {

	RootController controller = new RootController();
	
	@Test
	public void showIndexPage() {
		Assertions.assertThat(controller.indexPage()).isEqualTo("index");
	}
	
}
