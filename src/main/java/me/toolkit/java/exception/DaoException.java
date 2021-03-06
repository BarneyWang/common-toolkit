package me.toolkit.java.exception;

/**
 * 
 * Description: Exception of DB handle
 * @author wangdi0410@gmail.com
 */
public class DaoException extends Exception {
    public DaoException() {
	super();
    }

    public DaoException( String message ) {
	super( message );
    }

    public DaoException( String message, Throwable cause ) {
        super(message, cause);
    }
 
    public DaoException(Throwable cause) {
        super(cause);
    }
    private static final long serialVersionUID = -5365630128856068164L;
}

