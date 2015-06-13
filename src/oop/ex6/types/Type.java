package oop.ex6.types;


public class Type {
	private boolean mutuable;
	TypeFactory.Types type;
	boolean isAssigned;
	
	public Type(boolean isFinal, TypeFactory.Types type){
		this.mutuable = isFinal;
		this.type = type;
		isAssigned = false;
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
	
	public void setAssigned(){
		isAssigned = true;
	}

	public String getName() {
		return type.name();

	}

	public boolean isAssinged() {
		// TODO Auto-generated method stub
		return isAssigned;
	}
}
