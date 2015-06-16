package oop.ex6.line;

/**
 * Represents exception in initializing new types
 * @author sapir, tmrlvi
 */
public class TypeException extends ParsingException {
	private static final long serialVersionUID = 1L;
	//Description
	public final String MESSAGE = "Unknown type";
	
	@Override
	public String getMessage(){
		return MESSAGE;
	}
}
