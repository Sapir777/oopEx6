package oop.ex6.line;

import java.util.List;

import oop.ex6.types.Type;

public interface Line {
	public enum LineVariant {UNPECIFIED, TYPE_DECLARTION, METHOD_DECLERATION,
							 METHOD_CALL, CONDITION, ASSIGNMENT, RETURN, 
							 BRACES}
	
	public List<String> getVariablesNames();
	public List<String> getVariablesValues();
	public List<Type> getVariableTypes();
	public String getName();
	public LineVariant getLineType();

}
