package paiso.web.model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAccountFormTest {
	
	static Validator validator;

	@BeforeClass
	public static void init() {
		validator = Validation.buildDefaultValidatorFactory().getValidator(); 
	}
	
	@Test
	public void noEmailError() {
		UserAccountForm form = new UserAccountForm(null, "pass");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "email");
		assertThat(errors.iterator().next().getMessage()).isEqualTo("Email is required");
	}

	@Test
	public void blankEmailError() {
		UserAccountForm form = new UserAccountForm("", "pass");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "email");
		assertThat(errors.iterator().next().getMessage()).isEqualTo("Email is required");
	}

	@Test
	public void justSpaceEmailError() {
		UserAccountForm form = new UserAccountForm(" ", "pass");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "email");
		assertThat(errors.iterator().next().getPropertyPath().toString()).isEqualTo("email");
		//TODO: Error message bouncing between required/invalid? Investigate. 
	}
	
	@Test
	public void invalidEmailError() {
		UserAccountForm form = new UserAccountForm("email", "pass");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "email");
		assertThat(errors.iterator().next().getMessage()).isEqualTo("Invalid email address");
	}

	@Test
	public void validEmail() {
		UserAccountForm form = new UserAccountForm("email@email.com", "pass");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "email");
		assertThat(errors).isEmpty();
	}
	
	@Test
	public void noPasswordError() {
		UserAccountForm form = new UserAccountForm("test@test.com", null);
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "password");
		assertThat(errors.iterator().next().getMessage()).isEqualTo("Password is required");
	}

	@Test
	public void blankPasswordError() {
		UserAccountForm form = new UserAccountForm("test@test.com", "");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "password");
		assertThat(errors.iterator().next().getMessage()).isEqualTo("Password is required");
	}

	@Test
	public void justSpacePasswordError() {
		UserAccountForm form = new UserAccountForm("test@test.com", "  ");
		Set<ConstraintViolation<UserAccountForm>> errors = validator.validateProperty(form, "password");
		assertThat(errors.iterator().next().getMessage()).isEqualTo("Password is required");
	}
	
}
