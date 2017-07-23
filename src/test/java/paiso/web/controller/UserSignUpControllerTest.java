package paiso.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import static org.assertj.core.api.Assertions.assertThat;

import paiso.exception.UserAlreadyExistsException;
import paiso.model.User;
import paiso.model.UserProfile;
import paiso.service.IUserService;
import paiso.web.model.UserAccountForm;

@RunWith(MockitoJUnitRunner.class)
public class UserSignUpControllerTest {

	@InjectMocks UserSignUpController controller;
	
	@Mock IUserService userService;
	@Mock UserProfile userProfile;
	@Mock Model model;
	@Mock HttpServletRequest httpRequest;
	@Mock HttpSession httpSession;
	@Mock Errors errors;
	
	UserAccountForm userAccountForm;
	User pseudoRegisteredUser;
	
	@Before
	public void setup() {
		userAccountForm = new UserAccountForm("test@test.com", "password");
		pseudoRegisteredUser = new User(1L, "test@test.com", "password");
		
		when(httpRequest.getSession()).thenReturn(httpSession);
		when(httpSession.isNew()).thenReturn(false);
		when(errors.hasErrors()).thenReturn(false);
	}
	
	@Test
	public void shouldShowSignUpPage() {
		when(httpSession.isNew()).thenReturn(true);
		
		assertThat(controller.showSignInView(model, httpRequest, httpSession))
		.isEqualTo("user-signup");
	}

	@Test
	public void shouldInvalidateExistingSession() {
		controller.showSignInView(model, httpRequest, httpSession);
		verify(httpSession, times(1)).invalidate();
	}

	@Test
	public void shouldNotRegisterUserIfErrorsExist() {
		when(errors.hasErrors()).thenReturn(true);
		
		assertThat(controller.registerUser(userAccountForm, errors, model))
		.isEqualTo("user-signup");
	}

	@Test
	public void shouldSetFormOnModelIfErrorsExist() {
		when(errors.hasErrors()).thenReturn(true);
		
		controller.registerUser(userAccountForm, errors, model);
		verify(model, times(1)).addAttribute(userAccountForm);
	}

	@Test
	@SuppressWarnings("all")
	public void goBackWithErrorIfUserAlreadyRegistered() throws Exception {
		when(userService.registerNewUser(any())).thenThrow(UserAlreadyExistsException.class);
		
		assertThat(controller.registerUser(userAccountForm, errors, model)).isEqualTo("user-signup");
		verify(errors, times(1)).reject("error.user-already-registered") ;
	}

	@Test
	public void setRegisteredUserInProfile() throws Exception {
		when(userService.registerNewUser(any())).thenReturn(pseudoRegisteredUser);
		
		controller.registerUser(userAccountForm, errors, model);
		verify(userProfile, times(1)).setUser(pseudoRegisteredUser) ;
	}

	@Test
	public void passwordShouldNotBeStoredInProfile() throws Exception {
		when(userService.registerNewUser(any())).thenReturn(pseudoRegisteredUser);
		
		controller.registerUser(userAccountForm, errors, model);
		assertThat(pseudoRegisteredUser.getPassword()).isNull();
	}

	@Test
	public void setSuccessFlagOnModelIfUserRegisteredSuccessfully() throws Exception {
		when(userService.registerNewUser(any())).thenReturn(pseudoRegisteredUser);
		
		controller.registerUser(userAccountForm, errors, model);
		verify(model, times(1)).addAttribute("signUpSuccessful", Boolean.TRUE) ;
	}

	@Test
	public void shouldPassCorrectUserDetailsToService() throws Exception {
		when(userService.registerNewUser(any())).thenReturn(pseudoRegisteredUser);
		
		controller.registerUser(userAccountForm, errors, model);

		verify(userService).registerNewUser(argThat(new ArgumentMatcher<User>() {
			@Override
			public boolean matches(Object arg) {
				return arg.getClass().isAssignableFrom(User.class)
						&& "test@test.com".equals(((User)arg).getEmail())
						&& "password".equals(((User)arg).getPassword());
			}
		}));
	}
	
}
