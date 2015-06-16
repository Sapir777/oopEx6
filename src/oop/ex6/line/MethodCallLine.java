package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

/**
 * MethodCallLine - Line representing method call
 * @author sapir, tmrlvi
 */
public class MethodCallLine implements Line {
	// The varaiables in the method
	private List<String> variables;
	// The name of the method
	private String name;
	
	/**
	 * Constructor - creates new method
	 * @param name the name of the method
	 * @param variables the varaibles to call the method with
	 */
	public MethodCallLine(String name, List<String> variables){
		this.name = name;
		this.variables = variables;
	}

	@Override
	public List<String> getVariablesNames() {
		return new ArrayList<>();
	}

	@Override
	public List<String> getVariablesValues() {
		return variables;
	}

	@Override
	public List<Type> getVariableTypes() {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public LineVariant getLineType() {
		return LineVariant.METHOD_CALL;
	}
}
