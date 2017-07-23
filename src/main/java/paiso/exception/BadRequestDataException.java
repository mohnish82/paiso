package paiso.exception;

public class BadRequestDataException extends RuntimeException {

	private static final long serialVersionUID = -803778417647930859L;

	public BadRequestDataException(String msg) {
		super(msg);
	}
	
}
