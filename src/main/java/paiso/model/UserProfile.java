package paiso.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserProfile extends BaseEntity {
	private static final long serialVersionUID = 5471237301520365079L;

	private User user;
	private LinkedList<RateInquiry> recentInquiries;
	
	@SuppressWarnings("all")
	public UserProfile() {
		recentInquiries = new LinkedList<>();
	}
	
	public UserProfile(User user) {
		this();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RateInquiry> getRecentInquiries() {
		return recentInquiries;
	}

	public void addRecentInquiry(RateInquiry recentInquiry) {
		recentInquiries.add(recentInquiry);

		if(recentInquiries.size() == 10)
			recentInquiries.removeFirst();
	}
	
	public void addRecentInquiries(List<RateInquiry> recentInquiries) {
		for(RateInquiry inquiry : recentInquiries)
			this.recentInquiries.add(inquiry);
	}
}
