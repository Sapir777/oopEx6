package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.line.InvalidOperationException;

public class TypeFactory {

	public enum Types{
		INT {
			Pattern INT_PATTERN = Pattern.compile("-?\\d+");
			boolean isValid(String name){
				return INT_PATTERN.matcher(name).matches();
			}
			boolean isValid(Type type){
				return type.type == Types.INT;
			}
		},
		DOUBLE { 
			Pattern DOUBLE_PATTERN = Pattern.compile("-?\\d+\\.?\\d*");
			boolean isValid(String name){
				return DOUBLE_PATTERN.matcher(name).matches();
			}
			boolean isValid(Type type){
				return type.type == Types.DOUBLE || Types.INT.isValid(type);
			}
		},
		BOOLEAN { 
			boolean isValid(String name){
				return name.trim().equals("true") ||
						name.trim().equals("false") ||
						Types.DOUBLE.isValid(name);
			}
			boolean isValid(Type type){
				return type.type == Types.BOOLEAN || Types.DOUBLE.isValid(type);
			}
		},
		STRING { 
			boolean isValid(String name){
				return name.trim().startsWith("\"" ) && name.trim().endsWith("\"" );
			}
			boolean isValid(Type type){
				return type.type == Types.STRING;
			}
		},
		CHAR { 
			boolean isValid(String name){
				return name.trim().startsWith("\'" ) && name.trim().endsWith("\'" ) &&
						name.trim().length() == 3;
			}
			boolean isValid(Type type){
				return type.type == Types.CHAR;
			}
		};
		
		abstract boolean isValid(String name);
		abstract boolean isValid(Type type);
	}
	
	public static Type createType(String modifier, String type) throws InvalidOperationException, ValueException {
		boolean isFinal = false;
		if( !modifier.equals("")){
			isFinal = modifier.equals("final");
			if(!isFinal){
				throw new InvalidOperationException();
			}
		}
		return new Type(isFinal, Types.valueOf(type));
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
