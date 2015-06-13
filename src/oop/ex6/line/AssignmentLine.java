package oop.ex6.line;
import java.util.ArrayList;
import java.util.List;

import oop.ex6.types.Type;


public class AssignmentLine implements Line {
	private String name;
	private String value;
	
	public AssignmentLine(String name, String value){
		this.name = name;
		this.value = value;
	}

	@Override
	public List<String> getVariablesNames() {
		List<String> names = new ArrayList<>();
		names.add(name);
		return names;
	}

	@Override
	public List<String> getVariablesValues() {
		List<String> values = new ArrayList<>();
		values.add(value);
		return values;
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
		return LineVariant.ASSIGNMENT;
	}
	
	
	
}
