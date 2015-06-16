package oop.ex6.context;
import java.util.List;

import oop.ex6.line.Line;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory.Types;

/**
 * Inner context - represents a context that is nested
 * @author sapir, tmrlvi
 */
public class InnerContext extends Context {
	// The context containing this one
	private Context parent;
	
	/**
	 * Constructor
	 * @param parent the context creating this context
	 */
	public InnerContext(Context parent) {
		super();
		this.parent = parent;
	}

	@Override
	public Context getParent() {
		return parent;
	};
	
	@Override
	public Type getVariable(String name) throws NameException {
		if (hasLocalVariable(name)) {
			return getLocalVariable(name);
		}
		return parent.getVariable(name);
	}

	@Override
	public MethodContext getMethod(String name) throws NameException {
		return parent.getMethod(name);
	}

	@Override
	public void setLocalMethod(String name, MethodContext method) throws InvalidOperationException {
		throw new InvalidOperationException();
	}
	
	/**
	 * handles lines that represent assignment. 
	 * @param line - the line to handle.
	 * @return the new Context (this one)
	 * @throws InvalidOperationException in case assignment is illegal or
	 *                                   variable doesn't exist
	 */
	private Context handleAssignmentLine(Line line) throws InvalidOperationException{
		List<String> names = line.getVariablesNames();
		List<String> values = line.getVariablesValues();
		for (int i = 0; i < names.size(); i++){
			Type type = getVariable(names.get(i));
			if (type.isFinal()){
				throw new AssignmentException();
			}
			if(hasVariable(values.get(i))){
				Type variable = getVariable(values.get(i));
				if (!type.isValid(variable)){
					throw new AssignmentException();
				}
			} else {
				if (!type.isValid(values.get(i))){
					throw new AssignmentException();
				}
			}
		}
		return this;
	}
	
	/**
	 * handles lines that represent condition. 
	 * @param line - the line to handle.
	 * @return the new Context (this condition)
	 * @throws InvalidOperationException trying to condition on not boolean, or
	 *                                   or varaible doesn't exist
	 */
	private Context handleConditiontLine(Line line) throws InvalidOperationException{
		List<String> values = line.getVariablesValues();
		for( String value: values){
			if(!hasVariable(value) && !Types.BOOLEAN.isValid(value)){
				throw new InvalidOperationException();
			}
			else if (hasLocalVariable(value)){
				Type variable = getVariable(value);
				if (!variable.isAssinged() || !Types.BOOLEAN.isValid(variable)){
					throw new InvalidOperationException();
				}
			}
		}
		return new InnerContext(this);
	}
	
	/**
	 * handles lines that represent method calling. 
	 * @param line - the line to handle.
	 * @return the new Context (this condition)
	 * @throws InvalidOperationException the name of the method doesn't exists
	 */
	private Context handleMethodCallLine(Line line) throws InvalidOperationException{
		String name = line.getName();
		List<String> values = line.getVariablesValues(); 
		MethodContext method = getMethod(name);
		List<Type> types = method.getTypes();
		for(int i = 0; i < types.size(); i++){
			if(hasVariable(values.get(i))){
				if (!types.get(i).isValid(getVariable(values.get(i)))){
					throw new InvalidOperationException();
				}
			}
			else{
				if (!types.get(i).isValid(values.get(i))){
					throw new InvalidOperationException();
				}
			}
		}
		return this;
	}

	@Override
	public Context handleLine(Line line) throws InvalidOperationException {
		switch(line.getLineType()){
		case BRACES:
			return parent;
		case ASSIGNMENT:
			return handleAssignmentLine(line);
		case CONDITION:
			return handleConditiontLine(line);
		case METHOD_CALL:
			return handleMethodCallLine(line);
		case RETURN:
			return this;
		default:
			break;
		}
		return super.handleLine(line);
	}

	@Override
	public boolean hasVariable(String name) {
		return hasLocalVariable(name) || parent.hasVariable(name);
	}

}
