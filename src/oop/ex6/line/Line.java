package oop.ex6.line;

import java.util.List;

import oop.ex6.types.Type;

/**
 * Line interfaces - represents a line of code
 * @author sapir, tmrlvi
 *
 */
public interface Line {
	/**
	 * The types of lines available
	 * @author sapir, tmrlvi
	 */
	public enum LineVariant {UNPECIFIED, TYPE_DECLARTION, METHOD_DECLERATION,
							 METHOD_CALL, CONDITION, ASSIGNMENT, RETURN, 
							 BRACES}
	
	/**
	 * Get the variables used in the expression, or empty list
	 * @return list of variable names
	 */
	public List<String> getVariablesNames();
	/**
	 * Get the value used in the expression, or empty list
	 * @return list of value (corresponds to names)
	 */
	public List<String> getVariablesValues();
	/**
	 * Get the variable types used in the expression, or empty list
	 * @return list of types (corresponds to values)
	 */
	public List<Type> getVariableTypes();
	/**
	 * Get the name of the object referred by the line
	 * @return the name of the object referred by the line
	 */
	public String getName();
	/**
	 * Get the type of the line
	 * @return the type of the line
	 */
	public LineVariant getLineType();

}
