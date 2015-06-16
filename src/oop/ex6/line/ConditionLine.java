package oop.ex6.line;

import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;

/**
 * ConditionLine - Line represnting condition
 * @author sapir, tmrlvi
 */
public class ConditionLine implements Line {
	// the values the line contains
	List<String> values;
	
	/**
	 * Constructor
	 * @param values the values in the condition
	 */
	public ConditionLine(List<String> values) {
		this.values = values;
	}

	@Override
	public List<String> getVariablesNames() {
		return new ArrayList<>();
	}

	@Override
	public List<String> getVariablesValues() {
		return values;
	}

	@Override
	public List<Type> getVariableTypes() {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public LineVariant getLineType() {
		return LineVariant.CONDITION;
	}
}
