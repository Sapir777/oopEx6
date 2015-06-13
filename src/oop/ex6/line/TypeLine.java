package oop.ex6.line;

import java.util.ArrayList;
import java.util.HashMap;

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
		assignments.put(name, value);
	}
	
	
}
