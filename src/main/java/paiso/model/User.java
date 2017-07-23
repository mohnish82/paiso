package paiso.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User extends BaseEntity {
	private static final long serialVersionUID = -8933527560501852201L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private String email;
	private String password;

	public User() {}

	public User(String email) {
		this();
		this.email = email;
	}
	
	public User(String email, String password) {
		this();
		this.email = email;
		this.password = password;
	}
	
	public User(Long id, String email, String password) {
		this();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
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

	@Override
	public String toString() {
		return "User [userId=" + id + ", email=" + email + "]";
	}

}
