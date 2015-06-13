package oop.ex6.context;

import java.util.HashMap;

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
		throw new InvalidOperationException();
	}
	abstract public boolean supportsCondtion();
	
	abstract public Context handleLine(Line line) throws NameException, InvalidOperationException, NameExistsException;
}
