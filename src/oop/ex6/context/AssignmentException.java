package oop.ex6.context;

/**
 * AssignmentException - represents an error in assignment operation
 * @author sapir, tmrlvi
 */
public class AssignmentException extends InvalidOperationException {
	private static final long serialVersionUID = 1L;
	/**
	 * Description of the error
	 */
	public final String MESSAGE = "Assignment not possible";

}
