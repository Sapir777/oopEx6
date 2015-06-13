package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

public class MethodLine implements Line {
	String name;
	List<Type> variableType;
	List<String> variableNames;
	
	public MethodLine(String name, List<Type> variableType, List<String> variableNames){
		this.name = name;
		this.variableType = variableType;
		this.variableNames = variableNames;
	}

	@Override
	public List<String> getVariablesNames() {
		// TODO Auto-generated method stub
		return variableNames;
	}

	@Override
	public List<String> getVariablesValues() {
		// TODO Auto-generated method stub
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
