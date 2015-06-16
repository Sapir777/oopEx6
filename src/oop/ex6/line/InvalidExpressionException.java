package oop.ex6.line;

/**
 * InvalidExpressionException - represents bad expression
 * @author sapir, tmrlbi
 *
 */
public class InvalidExpressionException extends ParsingException {
	private static final long serialVersionUID = 1L;
	// Description of the exception
	public final String MESSAGE = "Invalid expression";
	
	@Override
	public String getMessage(){
		return MESSAGE;
	}

}
