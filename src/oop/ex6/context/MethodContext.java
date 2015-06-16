package oop.ex6.context;

import java.util.List;

import oop.ex6.line.Line;
import oop.ex6.types.Type;

/**
 * MethodContext - represents an interior of a method
 * @author sapir, tmrlvi
 */
public class MethodContext extends InnerContext{
	// The variable it has in initialization.
	List<Type> variableTypes;
	// Check if last line was return
	private boolean hasReturn = false;
	
	/**
	 * Constructor - creates new method
	 * @param parent the context it was declared in
	 * @param variablesNames the names of the variable
	 * @param variableTypes the types of the variables
	 * @throws NameExistsException a method in such name already exists
	 */
	public MethodContext(Context parent, List<String> variablesNames, List<Type> variableTypes) throws NameExistsException {
		super(parent);
		this.variableTypes = variableTypes;
		for (int i = 0; i < variablesNames.size(); i++){
			setLocalVariable(variablesNames.get(i), variableTypes.get(i));
		}
	}
	
	/**
	 * Returns the types that the method accepts
	 * @return list of types of the method
	 */
	public List<Type> getTypes(){
		return variableTypes;
	}
	
	@Override
	public Context handleLine(Line line) throws InvalidOperationException {
		switch(line.getLineType()){
		case RETURN:
			hasReturn = true;
			break;
		case BRACES:
			if(!hasReturn){
				throw new InvalidOperationException();
			}
		default:
			hasReturn = false;
		}
		return super.handleLine(line);
	}


}
