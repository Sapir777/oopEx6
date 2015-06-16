package oop.ex6.context;

/**
 * NameExistsException - represents error in which duplicate declaration occurs
 * @author sapir, tmrlvi
 *
 */
public class NameExistsException extends InvalidOperationException {
	private static final long serialVersionUID = 1L;
	//Description of the error
	public final String MESSAGE = "Duplicated declaration";
	
	@Override
	public String getMessage(){
		return MESSAGE;
	}
}
