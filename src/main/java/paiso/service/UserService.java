package paiso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import paiso.exception.UserAlreadyExistsException;
import paiso.model.RateInquiry;
import paiso.model.User;
import paiso.repository.IRateInquiryRepository;
import paiso.repository.IUserRepository;

@Component
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IRateInquiryRepository rateInquiryRepo;
    
	@Override
	public User registerNewUser(User user) throws UserAlreadyExistsException {
        User existingUser = userRepo.getByEmail(user.getEmail());
        
        if(existingUser != null)
        	throw new UserAlreadyExistsException("User (" + user.getEmail() +") already exists!");
        
        return userRepo.save(user);
	}

	@Override
	public User findUser(User user) {
		return StringUtils.isEmpty(user.getPassword())
				? userRepo.getByEmail(user.getEmail())
				: userRepo.getByEmailAndPassword(user.getEmail(), user.getPassword());
	}
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}

	public RateInquiry saveUserRateInquiry(RateInquiry inquiry) {
		return rateInquiryRepo.save(inquiry);
	}
	
	public List<RateInquiry> getRecentInquiries(User user) {
		return rateInquiryRepo.findTop10ByUserIdOrderByIdDesc(user.getId());
	}
}
