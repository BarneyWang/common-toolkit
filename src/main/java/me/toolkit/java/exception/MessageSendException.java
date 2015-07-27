package me.toolkit.java.exception;

/**
 * 
 * Description: Exception of SSH handle
 * @author wangdi0410@gmail.com
 */
public class MessageSendException extends Exception {
    public MessageSendException() {
	super();
    }

    public MessageSendException( String message ) {
	super( message );
    }

    public MessageSendException( String message, Throwable cause ) {
        super(message, cause);
    }
 
    public MessageSendException(Throwable cause) {
        super(cause);
    }
    private static final long serialVersionUID = -5365630128856068164L;
}

