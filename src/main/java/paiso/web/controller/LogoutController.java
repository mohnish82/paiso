package paiso.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import paiso.model.UserProfile;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@Autowired
	UserProfile userProfile;
	
	@GetMapping
	public String logout(HttpSession session) {
		session.invalidate();
		userProfile.setUser(null);
		
		return "redirect:/";
	}
	
}
