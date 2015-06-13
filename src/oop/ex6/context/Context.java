package oop.ex6.context;

import java.util.HashMap;
import java.util.List;

import oop.ex6.line.InvalidOperationException;
import oop.ex6.line.Line;
import oop.ex6.types.Type;

public abstract class Context {
	private HashMap<String, Type> variables;
	
	public Context(){
		variables = new HashMap<>();
	}
	
	public Type getLocalVariable(String name) throws NameException{
		if (!hasLocalVariable(name)){
			throw new NameException();
		}
		return variables.get(name);
	}
	
	public boolean hasLocalVariable(String name){
		return variables.containsKey(name);
	}
	
	public void setLocalVariable(String name, Type variable) throws NameExistsException {
		if (hasLocalVariable(name)){
			throw new NameExistsException();
		}
		variables.put(name, variable);
	}
	
	abstract public boolean hasVariable(String name);
	abstract public Type getVariable(String name) throws NameException;
	abstract public MethodContext getMethod(String name) throws NameException;
	/**
	 * Adds a new method. Throws exception if the context doens't support methods
	 * @param name
	 * @param method
	 * @throws InvalidOperationException 
	 */
	abstract public void setLocalMethod(String name, MethodContext method) throws InvalidOperationException;
	/**
	 * Returns the parent of the context, or null if not parent exists
	 * @return
	 */
	public Context getParent() throws InvalidOperationException{
		return null;
	}
	abstract public boolean supportsCondtion();
	
	public Context handleLine(Line line) throws NameException, InvalidOperationException, NameExistsException{
		switch(line.getLineType()){
		case TYPE_DECLARTION:
			List<Type> types = line.getVariableTypes();
			List<String> names = line.getVariablesNames();
			List<String> values = line.getVariablesValues();
			for(int i = 0; i < names.size(); i++){
				if(hasLocalVariable(names.get(i))){
					throw new InvalidOperationException();
				}
				if(values.get(i).equals("") && types.get(i).isFinal()){
					throw new InvalidOperationException();
				}
				if (!values.get(i).equals("")){
					if (hasVariable(values.get(i))){
						Type value = getVariable(values.get(i));
						if (!value.isAssinged() || !types.get(i).isValid(value)){
							throw new InvalidOperationException();
						}
					} else {
						if (!types.get(i).isValid(values.get(i))){
							throw new InvalidOperationException();
						}
						
					}
					types.get(i).setAssigned();
				}
				setLocalVariable(names.get(i), types.get(i));
			}
			return this;
		}
		throw new InvalidOperationException();
	}
}
