package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

/**
 * BraceLine - Line representing ending braces
 * @author sapir, tmrlvi
 */
public class BraceLine implements Line {
	// The name of the line
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
	
	@Override
	public LineVariant getLineType() {
		return LineVariant.BRACES;
	}

}
