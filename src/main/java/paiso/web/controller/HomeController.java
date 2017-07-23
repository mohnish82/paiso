package paiso.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import paiso.model.UserProfile;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	UserProfile profile;
	
	@GetMapping
	public String showHome(Model model) {
		if(profile.getUser() == null)
			return "redirect:/";
		
		model.addAttribute("user", profile);
		return "home";
	}
	
}
