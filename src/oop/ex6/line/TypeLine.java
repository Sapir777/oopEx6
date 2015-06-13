package oop.ex6.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oop.ex6.types.Type;

public class TypeLine implements Line {
	HashMap<String, Type> declerations;
	HashMap<String, String> assignments;
	
	public TypeLine(){
		declerations = new HashMap<>();
		assignments = new HashMap<>();
	}
	
	public void addDecleration(String name, Type type){
		declerations.put(name, type);
	}
	
	public void addAssignment(String name, String value){
		if (value == null){
			value = "";
		}
		assignments.put(name, value);
	}

	@Override
	public List<String> getVariablesNames() {
		return new ArrayList<String>(declerations.keySet());
	}

	@Override
	public List<String> getVariablesValues() {
		return new ArrayList<String>(assignments.values());
	}

	@Override
	public List<Type> getVariableTypes() {
		return new ArrayList<Type>(declerations.values());
	}

	@Override
	public String getName() {
		List<Type> typeList = new ArrayList<Type>(declerations.values());
		return typeList.get(0).getName();
	}
	
	@Override
	public LineVariant getLineType() {
		return LineVariant.TYPE_DECLARTION;
	}
	
}
