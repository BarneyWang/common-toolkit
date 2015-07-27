package me.toolkit.java.exception;

/**
 * Description: Exception of illegal param.
 * @author wangdi0410@gmail.com
 */
public class IllegalParamException extends Exception {
    public IllegalParamException() {
	super();
    }

    public IllegalParamException( String message ) {
	super( message );
    }

    public IllegalParamException( String message, Throwable cause ) {
        super(message, cause);
    }
 
    public IllegalParamException(Throwable cause) {
        super(cause);
    }
    private static final long serialVersionUID = -5365630128856068164L;
}

