package oop.ex6.context;

import java.util.HashMap;
import java.util.List;

import oop.ex6.line.Line;
import oop.ex6.types.Type;

/**
 * Context class - represents the scope of the code
 * @author sapir, tmrlvi
 */
public abstract class Context {
	// Holding the variables inside the scope
	private HashMap<String, Type> variables;
	
	/**
	 * Constructor - creates a new context
	 */
	public Context(){
		variables = new HashMap<>();
	}
	
	/**
	 * Get a variable that was declared locally
	 * @param name the name of variable to get
	 * @return object representing the variable in the scope
	 * @throws NameException the variable doesn't exist
	 */
	public Type getLocalVariable(String name) throws NameException{
		if (!hasLocalVariable(name)){
			throw new NameException();
		}
		return variables.get(name);
	}
	
	/**
	 * Check if a local variable exists
	 * @param name the name of the variable to search
	 * @return true if the variable exists
	 */
	public boolean hasLocalVariable(String name){
		return variables.containsKey(name);
	}
	
	/**
	 * Adds a local variable
	 * @param name the name of the new variable
	 * @param variable object representing the variable in the context
	 * @throws NameExistsException a variable of the same name already exists
	 */
	public void setLocalVariable(String name, Type variable) throws NameExistsException {
		if (hasLocalVariable(name)){
			throw new NameExistsException();
		}
		variables.put(name, variable);
	}
	
	/**
	 * Check if there exists a variable in this context (either locally or
	 * in parents)
	 * @param name the value to search for
	 * @return true if the value exists
	 */
	abstract public boolean hasVariable(String name);
	abstract public Type getVariable(String name) throws NameException;
	abstract public MethodContext getMethod(String name) throws NameException;
	
	/**
	 * Adds a new method. Throws exception if the context doens't support methods
	 * @param name The name of the method
	 * @param method the context of the method
	 * @throws InvalidOperationException 
	 */
	abstract public void setLocalMethod(String name, MethodContext method) throws InvalidOperationException;
	
	/**
	 * Returns the parent of the context, or null if not parent exists
	 * @return the parent of null of no parent available
	 */
	public Context getParent(){
		return null;
	}
	
	/**
	 * Handles a line in this context.
	 * @param line the line to handle
	 * @return the next context to parse
	 * @throws InvalidOperationException the operation requested is not valid in context
	 */
	public Context handleLine(Line line) throws InvalidOperationException {
		switch(line.getLineType()){
		//Decelerations are valid in any context
		case TYPE_DECLARTION:
			List<Type> types = line.getVariableTypes();
			List<String> names = line.getVariablesNames();
			List<String> values = line.getVariablesValues();
			for(int i = 0; i < names.size(); i++){
				if(hasLocalVariable(names.get(i))){
					throw new NameExistsException();
				}
				if(values.get(i).equals("") && types.get(i).isFinal()){
					throw new AssignmentException();
				}
				if (!values.get(i).equals("")){
					if (hasVariable(values.get(i))){
						Type value = getVariable(values.get(i));
						if (!value.isAssinged() || !types.get(i).isValid(value)){
							throw new InvalidOperationException();
						}
					} else {
						if (!types.get(i).isValid(values.get(i))){
							throw new AssignmentException();
						}
						
					}
					types.get(i).setAssigned();
				}
				setLocalVariable(names.get(i), types.get(i));
			}
			return this;
		default:
			break;
		}
		throw new InvalidOperationException();
	}
}
