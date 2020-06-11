package logic.exception;

public class PaymentNotAcceptedException extends Exception {

	private static final long serialVersionUID = -8680252719337313170L;
	
	public PaymentNotAcceptedException(String message) {
		super(message);
	}

}
