package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

public class MethodCallLine implements Line {
	
	private List<String> variables;
	private String name;
	
	public MethodCallLine(String name, List<String> variables){
		this.name = name;
		this.variables = variables;
	}

	@Override
	public List<String> getVariablesNames() {
		return new ArrayList<String>();
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
}
