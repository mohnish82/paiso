package paiso.exception;

public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 3710199626390484711L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
}
