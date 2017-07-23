package paiso.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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
import org.springframework.validation.Errors;

import static org.assertj.core.api.Assertions.assertThat;

import paiso.model.User;
import paiso.model.UserProfile;
import paiso.service.IUserService;
import paiso.web.model.UserAccountForm;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	@InjectMocks LoginController controller;
	@Mock IUserService userService;
	@Mock UserProfile userProfile;
	@Mock Model model;
	
	User pseudoUser;
	
	@Before
	public void setup() {
		pseudoUser = new User("test@test.com", "password");
		
		when(userService.findUser(any(User.class))).thenReturn(pseudoUser);
		when(userProfile.getUser()).thenReturn(pseudoUser);
	}
	
	
	@Test
	public void returnOnErrors() {
		Errors errors = mock(Errors.class);
		when(errors.hasErrors()).thenReturn(true);
		
		assertThat(controller.login(new UserAccountForm(), errors, model))
		.isEqualTo("index");
	}
	
	@Test
	public void goBackIfUserDoesNotExists() {
		Errors errors = mock(Errors.class);
		when(errors.hasErrors()).thenReturn(false);

		when(userService.findUser(any(User.class))).thenReturn(null);
		
		assertThat(controller.login(new UserAccountForm(), errors, model))
		.isEqualTo("redirect:/");
	}

	@Test
	public void goHomeIfUserExists() {
		Errors errors = mock(Errors.class);
		when(errors.hasErrors()).thenReturn(false);

		assertThat(controller.login(new UserAccountForm(), errors, model))
		.isEqualTo("redirect:/home");
	}

	@Test
	public void shouldSetUserInModelUponLogin() {
		Errors errors = mock(Errors.class);
		when(errors.hasErrors()).thenReturn(false);

		controller.login(new UserAccountForm(), errors, model);
		verify(model, times(1)).addAttribute("user", pseudoUser);
	}

	@Test
	public void shouldSetNewUserInProfileUponLogin() {
		Errors errors = mock(Errors.class);
		when(errors.hasErrors()).thenReturn(false);

		controller.login(new UserAccountForm(), errors, model);
		verify(model, times(1)).addAttribute("user", pseudoUser);
	}
	
}
