package paiso.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import paiso.exception.UserAlreadyExistsException;
import paiso.model.User;
import paiso.model.UserProfile;
import paiso.service.IUserService;
import paiso.web.model.UserAccountForm;

@Controller
@RequestMapping("/signup")
public class UserSignUpController {

	private static final String SIGNUP_PAGE = "user-signup";
	
	@Autowired
	UserProfile userProfile;
	
	@Autowired
	IUserService userService;
	
	@GetMapping
	public String showSignInView(Model model, HttpServletRequest request, HttpSession session) {

		if(!session.isNew())
			session.invalidate();
		
		model.addAttribute(new UserAccountForm());
		return SIGNUP_PAGE;
	}
	
	@PostMapping
	public String registerUser(@Valid UserAccountForm userAccountForm, Errors errors, Model model) {
		
		if(errors.hasErrors()) {
			model.addAttribute(userAccountForm);			
			return SIGNUP_PAGE;
		}
		
		User user = new User(userAccountForm.getEmail(), userAccountForm.getPassword());
		
		User registeredUser = null;
		try {
			registeredUser = userService.registerNewUser(user);
			registeredUser.setPassword(null);
		}catch(UserAlreadyExistsException e) {
			errors.reject("error.user-already-registered");
			return SIGNUP_PAGE;
		}
		
		userProfile.setUser(registeredUser);
		model.addAttribute("signUpSuccessful", Boolean.TRUE);
		
		return SIGNUP_PAGE;
	}

}
