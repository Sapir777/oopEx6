package oop.ex6.context;

/**
 * NameException - exception represents an name doesn't exists error
 * @author sapir, tmrlvi
 */
public class NameException extends InvalidOperationException {
	private static final long serialVersionUID = 1L;
	//Description of the error
	public final String MESSAGE = "Name doesnt exist";
	
	@Override
	public String getMessage(){
		return MESSAGE;
	}
}
