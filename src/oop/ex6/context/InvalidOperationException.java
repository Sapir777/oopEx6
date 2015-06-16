package oop.ex6.context;

import oop.ex6.main.SjavaException;

/**
 * InvalidOperationException - indicates invalid operation
 * @author sapir, tmrlvi
 */
public class InvalidOperationException extends SjavaException {
	private static final long serialVersionUID = 1L;
	// The reason for error
	public final String MESSAGE = "Operation not premitted";
	
	@Override
	public String getMessage(){
		return MESSAGE;
	}
	
}
