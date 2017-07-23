package paiso.web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

import paiso.model.User;
import paiso.model.UserProfile;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	@InjectMocks HomeController controller;
	@Mock UserProfile userProfile;
	@Mock Model model;
	
	User user = new User();
	
	@Before
	public void setup() {
		when(userProfile.getUser()).thenReturn(user);
	}
	
	@Test
	public void shouldRedirectToHomePage() {
		assertThat(controller.showHome(model)).isEqualTo("home");
		assertThat(verify(model, times(1)).addAttribute("user", userProfile));
	}
	
}