package com.ibc.ibchelper.error;

public final class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -2696389641743479049L;

	public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }
}
