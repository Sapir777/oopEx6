package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

/**
 * ReturnLine - Line representing the return.
 * @author sapir
 */
public class ReturnLine implements Line {
	// The name of the line
	private final static String RETURN = "return";
	
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
		return RETURN;
	}
	
	@Override
	public LineVariant getLineType() {
		return LineVariant.RETURN;
	}
}
