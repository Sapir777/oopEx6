package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.line.InvalidOperationException;

public class TypeFactory {

	public enum Types{
		INT{
			final public String NAME = "int";
			Pattern INT_PATTERN = Pattern.compile("-?\\d+");
			public boolean isValid(String name){
				return INT_PATTERN.matcher(name).matches();
			}
			public boolean isValid(Type type){
				return type.type == Types.INT;
			}
		},
		DOUBLE {
			final public String NAME = "double";
			Pattern DOUBLE_PATTERN = Pattern.compile("-?\\d+\\.?\\d*");
			public boolean isValid(String name){
				return DOUBLE_PATTERN.matcher(name).matches();
			}
			public boolean isValid(Type type){
				return type.type == Types.DOUBLE || Types.INT.isValid(type);
			}
		},
		BOOLEAN { 
			final public String NAME = "bool";
			public boolean isValid(String name){
				return name.trim().equals("true") ||
						name.trim().equals("false") ||
						Types.DOUBLE.isValid(name);
			}
			public boolean isValid(Type type){
				return type.type == Types.BOOLEAN || Types.DOUBLE.isValid(type);
			}
		},
		STRING { 
			final public String NAME = "String";
			public boolean isValid(String name){
				return name.trim().startsWith("\"" ) && name.trim().endsWith("\"" );
			}
			public boolean isValid(Type type){
				return type.type == Types.STRING;
			}
		},
		CHAR { 
			final public String NAME = "char";
			public boolean isValid(String name){
				return name.trim().startsWith("\'" ) && name.trim().endsWith("\'" ) &&
						name.trim().length() == 3;
			}
			public boolean isValid(Type type){
				return type.type == Types.CHAR;
			}
		};
		
		final public String NAME = "";
		abstract public boolean isValid(String name);
		abstract public boolean isValid(Type type);
		static Types parse(String name){
			Types type = Types.valueOf(name.toUpperCase());
			if (type.NAME.equals(name)){
				throw new IllegalArgumentException(); 
			}
			return type;
		}
	}
	
	public static Type createType(String modifier, String type) throws InvalidOperationException, ValueException {
		boolean isFinal = false;
		if( !modifier.equals("")){
			isFinal = modifier.equals("final");
			if(!isFinal){
				throw new InvalidOperationException();
			}
		}
		try {
			return new Type(isFinal, Types.parse(type));
		} catch (IllegalArgumentException e){
			throw new InvalidOperationException();
		}
	}
	
	public static boolean isValid(String value){
		for (Types t : Types.values()){
			if (t.isValid(value)){
				return true;
			}
		}
		return false;
	}
	
}
