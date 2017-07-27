package paiso.service;

import java.util.List;

import org.springframework.stereotype.Service;

import paiso.exception.UserAlreadyExistsException;
import paiso.model.RateInquiry;
import paiso.model.User;

@Service
public interface IUserService {

	User registerNewUser(User user) throws UserAlreadyExistsException;
	User findUser(User user);
	User saveUser(User user);
	
	List<RateInquiry> getRecentInquiries(User user);
	RateInquiry saveUserRateInquiry(RateInquiry inquiry);
}
