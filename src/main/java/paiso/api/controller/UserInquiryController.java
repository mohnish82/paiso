package paiso.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import paiso.exception.UserNotFoundException;
import paiso.model.RateInquiry;
import paiso.model.User;
import paiso.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserInquiryController extends BaseController {
	Logger logger = LoggerFactory.getLogger(UserInquiryController.class);

	@Autowired
	IUserService userService;
	
	@GetMapping("/{email}/recent-inquiries")
	public List<RateInquiry> recentInquiries(@PathVariable String email) {
		User user = userService.findUser(new User(email));
		
		if(user == null)
			throw new UserNotFoundException("No user found");
		
		return userService.getRecentInquiries();
	}
}
