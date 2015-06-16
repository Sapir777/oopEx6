package oop.ex6.line;

import oop.ex6.main.SjavaException;

/**
 * ParsingException - represents error in parsing the line
 * @author sapir, tmrlvi
 */
public class ParsingException extends SjavaException {
	private static final long serialVersionUID = 1L;
	// Description
	public final String MESSAGE = "Error in parsing";
	
	@Override
	public String getMessage(){
		return MESSAGE;
	}
}
