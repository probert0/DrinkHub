package logic.exception;

public class UserNotExistException extends Exception {
	
	private static final long serialVersionUID = -6926403925727893513L;

	public UserNotExistException(String message) {
		super(message);
	}

}
