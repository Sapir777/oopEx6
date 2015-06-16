package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

/**
 * MethodLine - Line representing deceleration of method
 * @author sapir, tmrlvi
 *
 */
public class MethodLine implements Line {
	// The name of the method
	String name;
	// The types of the method variables
	List<Type> variableType;
	// The names of the method variables
	List<String> variableNames;
	
	/**
	 * Constructor - creating new method deceleration line
	 * @param name The name of the method
	 * @param variableType The types of the method variables
	 * @param variableNames The names of the method variables
	 */
	public MethodLine(String name, List<Type> variableType, List<String> variableNames){
		this.name = name;
		this.variableType = variableType;
		for( Type type:variableType){
			type.setAssigned();
		}
		this.variableNames = variableNames;
	}

	@Override
	public List<String> getVariablesNames() {
		return variableNames;
	}

	@Override
	public List<String> getVariablesValues() {
		return new ArrayList<>();
	}

	@Override
	public List<Type> getVariableTypes() {
		return variableType;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public LineVariant getLineType() {
		return LineVariant.METHOD_DECLERATION;
	}

}
