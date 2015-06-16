package oop.ex6.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oop.ex6.types.Type;

/**
 * TypeLine - line representing type
 * @author sapir, tmrlvi
 */
public class TypeLine implements Line {
	// Declerations - the types of the variable
	HashMap<String, Type> declerations;
	// The values of the varaiables
	HashMap<String, String> assignments;
	
	/**
	 * Constrictor - create empty type deleration
	 */
	public TypeLine(){
		declerations = new HashMap<>();
		assignments = new HashMap<>();
	}
	
	/**
	 * Adds a type to the decleration
	 * @param name the name of the variable
	 * @param type the type (with modifiers)
	 */
	public void addDecleration(String name, Type type){
		declerations.put(name, type);
	}
	
	/**
	 * Adds assignment to the decleration
	 * @param name the name of the variable
	 * @param value the value to assign
	 */
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
