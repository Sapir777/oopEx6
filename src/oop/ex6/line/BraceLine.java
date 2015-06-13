package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

public class BraceLine implements Line {
	
	private final static String BRACE = "}";

	@Override
	public List<String> getVariablesNames() {
		return new ArrayList<>();
	}

	@Override
	public List<String> getVariablesValues() {
		return new ArrayList<>();
	}

	@Override
	public List<Type> getVariableTypes() {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return BRACE;
	}

}
