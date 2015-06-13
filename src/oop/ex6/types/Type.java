package oop.ex6.types;


public class Type {
	private boolean mutuable;
	TypeFactory.Types type;
	boolean isAssigned;
	
	public Type(boolean isFinal, TypeFactory.Types type){
		this.mutuable = isFinal;
		this.type = type;
	}
		
	public boolean isValid(String value){
		return type.isValid(value);
	}
	
	public boolean isValid(Type value){
		return type.isValid(value);
	}
	
	public boolean isFinal(){
		return mutuable;
	}
}
