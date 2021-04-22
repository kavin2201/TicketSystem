/**
 * 
 */
package org.ticketsystem.logger;

/**
 * @author Kavin
 *
 */
public class CustomException extends Exception {

	/**
	 * The constant serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CustomException() {
		super();
	}

	/**
	 * @param message
	 */
	public CustomException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CustomException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
