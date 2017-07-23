package paiso.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import paiso.model.User;
import paiso.model.UserProfile;
import paiso.service.IUserService;
import paiso.web.model.UserAccountForm;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserProfile userProfile;
	
	@Autowired
	IUserService userService;

	@PostMapping
	public String login(@Valid UserAccountForm form, Errors errors, Model model) {
		
		if(errors.hasErrors()) {
			return "index";
		}
		
		User user = userService.findUser(new User(form.getEmail(), form.getPassword()));
		
		if(user == null)
			return "redirect:/";
		
		userProfile.setUser(user);
		userProfile.addRecentInquiries(userService.getRecentInquiries());

		model.addAttribute("user", user);
		
		return "redirect:/home";
	}
	
}
