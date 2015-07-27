package me.toolkit.java.exception;

/**
 * 
 * Description: Exception of Telnet handle
 * @author wangdi0410@gmail.com
 */
public class TelnetException extends Exception {
    public TelnetException() {
	super();
    }

    public TelnetException( String message ) {
	super( message );
    }

    public TelnetException( String message, Throwable cause ) {
        super(message, cause);
    }
 
    public TelnetException(Throwable cause) {
        super(cause);
    }
    private static final long serialVersionUID = -5365630128856068164L;
}

