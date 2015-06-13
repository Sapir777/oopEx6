package oop.ex6.context;

import java.util.List;

import oop.ex6.line.InvalidOperationException;
import oop.ex6.line.Line;
import oop.ex6.types.Type;

public class MethodContext extends InnerContext{
	List<Type> variableTypes;
	
	private boolean hasReturn = false;
	
	public MethodContext(Context parent, List<String> variablesNames, List<Type> variableTypes) throws NameExistsException {
		super(parent);
		this.variableTypes = variableTypes;
		for (int i = 0; i < variablesNames.size(); i++){
			setLocalVariable(variablesNames.get(i), variableTypes.get(i));
		}
	}
	
	public List<Type> getTypes(){
		return variableTypes;
	}
	
	@Override
	public Context handleLine(Line line) throws NameException,
			InvalidOperationException, NameExistsException {
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

	
	
	/*public boolean checkVariables(List<Type> variables){
		if (variableTypes.size() != variables.size()){
			return false;
		}
		for (int i = 0; i < variables.size(); i++){
			if (!variableTypes.get(i).canAssign(variables.get(i))){
				return false;
			}
		}
		return true;
	}*/

}
