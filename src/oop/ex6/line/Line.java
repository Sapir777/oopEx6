package oop.ex6.line;

import java.util.List;

import oop.ex6.types.Type;

public interface Line {
	//TODO: put the line type in each object
	public enum LineVariant {UNPECIFIED, TYPE_DECLARTION, METHOD_DECLERATION,
							 METHOD_CALL, CONDITION, ASSIGNMEND, RETURN, 
							 BRACES}
	public LineVariant type = LineVariant.UNPECIFIED;
	
	public List<String> getVariablesNames();
	public List<String> getVariablesValues();
	public List<Type> getVariableTypes();
	public String getName();

}
