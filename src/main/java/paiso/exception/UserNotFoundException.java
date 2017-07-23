package paiso.exception;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 854446648809794803L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
