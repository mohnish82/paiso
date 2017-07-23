package paiso.web.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserAccountForm {

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email address")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

	public UserAccountForm() {
		super();
	}

	public UserAccountForm(String email, String password) {
		this();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
