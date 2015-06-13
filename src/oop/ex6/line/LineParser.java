package oop.ex6.line;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import oop.ex6.context.Context;
import oop.ex6.context.GlobalContext;
import oop.ex6.context.NameException;
import oop.ex6.context.NameExistsException;
import oop.ex6.line.Line.LineVariant;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;
import oop.ex6.types.ValueException;

public class LineParser {
	final Pattern CONDITION_PATTERN = Pattern.compile("\\s*(?:if|while)\\s*\\(\\s*-?['\"\\.\\w]+\\s*(?:" + 
													 "[<>]=?|==|!=)\\s*-?['\"\\w\\.]+(?:\\s+(?:\\|\\||&&)" + 
			                                         "\\s+-?['\"\\w\\.]+\\s*(?:[<>]=?|==|!=)\\s*-?['\"\\w\\.]+)*\\s*" + 
													 "\\)\\s*\\{");
	final Pattern VARIABLE_DEFINE_PATTERN = Pattern.compile("\\s*((?:final|))\\s*(\\w+)+\\s+(\\w+" + 
													     "\\s*(?:=\\s*-?['\"\\w\\.]+|)(?:\\s*,\\s*\\w+\\s*" +
			                                             "(?:=\\s*-?['\"\\w\\.]+|)|))\\s*;\\s*");
	final Pattern METHOD_PATTERN = Pattern.compile("\\s*void\\s+([a-zA-Z_]\\w*)\\(((?:final)?\\s*" + 
			                                       "\\w+\\s+\\w+\\s*(?:,\\s*(?:final)?\\s*" + 
												   "\\w+\\s+\\w+)*)?\\)\\s*\\{\\s*");
	final Pattern METHOD_CALL_PATTERN = Pattern.compile("\\s*\\([a-zA-Z_]\\w*\\)\\s*\\(\\s*(-?['\"\\.\\w]+" + 
				 								        "\\s*(?:,\\s*-?['\"\\.\\w]+)*\\s*|)\\)\\s*;\\s*");
	final Pattern VARIABLE_ASSIGNEMT_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*(-?['\"\\.\\w]+)");
	final Pattern RETURN_PATTERN = Pattern.compile("\\s*return\\s*;\\s*");
	
	final Pattern CONDITION_SPLIT_PATTERN = Pattern.compile("(-?['\"\\.\\w]+)\\s*(?:==|>=)\\s*(-?['\"\\.\\w]+)");
	
	final Pattern VARIABLE_SPLIT_PATTERN = Pattern.compile("(\\w+)\\s*(?:=\\s*(-?['\"\\w\\.]+)|)");
	final Pattern METHOD_SPLIT_PATTERN = Pattern.compile("(final\\s+|)(\\w+)\\s+(\\w+)");
	final Pattern METHOD_CALL_SPLIT_PATTERN = Pattern.compile("(-?['\"\\.\\w]+)");
	
	final private int REGEX_FIRST_GROUP = 1;
	final private int REGEX_SECOND_GROUP = 2;
	final private int REGEX_THIRD_GROUP = 3;
	
	private Context currentContext;
	private RandomAccessFile reader;
	
	public LineParser(RandomAccessFile reader){
		this.reader = reader;
		currentContext = new GlobalContext();
	}
	
	public void read() throws IOException, InvalidOperationException, ValueException, NameExistsException, NameException{
		String line = reader.readLine();
		while(line != null){
			Line nextLine = parseLine(line);
			if (nextLine.getLineType() == LineVariant.METHOD_DECLERATION){
				currentContext = currentContext.handleLine(nextLine);
			}
			line = reader.readLine();
		}
		reader.seek(0);
		line = reader.readLine();
		while(line != null){
			Line nextLine = parseLine(line);
			if (nextLine.getLineType() == LineVariant.METHOD_DECLERATION){
				currentContext = currentContext.getMethod(nextLine.getName());
			}
			else {
				currentContext = currentContext.handleLine(nextLine);
			}
			line = reader.readLine();
		}
	}
		
	
	public Line parseLine(String inputLine) throws InvalidOperationException, ValueException, NameExistsException{
		Matcher lineMatcher = CONDITION_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			return parseConditionLine(inputLine);
		}
		
		lineMatcher = VARIABLE_DEFINE_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			String finalModifier = lineMatcher.group(REGEX_FIRST_GROUP);
			String variableType = lineMatcher.group(REGEX_SECOND_GROUP);
			String variables = lineMatcher.group(REGEX_THIRD_GROUP);
			return parseVariableLine(finalModifier, variableType, variables);
		}
		
		lineMatcher = METHOD_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			String methodName = lineMatcher.group(REGEX_FIRST_GROUP);
			String methodVariables = lineMatcher.group(REGEX_SECOND_GROUP);
			return parseMethodLine(methodName, methodVariables);
		}
		
		lineMatcher = METHOD_CALL_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			String methodName = lineMatcher.group(REGEX_FIRST_GROUP);
			String methodVariables = lineMatcher.group(REGEX_SECOND_GROUP);
			return parseMethodCall(methodName, methodVariables);
		}
		
		lineMatcher = VARIABLE_ASSIGNEMT_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			String variableName = lineMatcher.group(REGEX_FIRST_GROUP);
			String variableValue = lineMatcher.group(REGEX_SECOND_GROUP);
			return new AssignmentLine(variableName, variableValue);
		}
		
		lineMatcher = RETURN_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			return new ReturnLine();
		}
		
		if (inputLine.trim().equals("}")){
			return new BraceLine();
		}
		
		throw new InvalidOperationException();
	}
	
	private Line parseConditionLine(String inputLine){
		Matcher splitMatcher = CONDITION_SPLIT_PATTERN.matcher(inputLine);
		List<String> values = new ArrayList<>();
		while(splitMatcher.find()){
			for( int i = REGEX_FIRST_GROUP; i < splitMatcher.groupCount(); i++){
				values.add(splitMatcher.group(i));
				/*if(!currentContext.hasVariable() && !TypeFactory.isValid(splitMatcher.group(i))){
					throw new InvalidOperationException();
				}*/
			}
		}
		return new ConditionLine(values);
	}
	
	private Line parseVariableLine(String finalModifier, String variableType, String variables) throws InvalidOperationException, ValueException{
		Matcher splitVariables = VARIABLE_SPLIT_PATTERN.matcher(variables);
		TypeLine types = new TypeLine();
		while(splitVariables.find()){
			Type toAdd = TypeFactory.createType(finalModifier, variableType);
			String variableName = splitVariables.group(REGEX_FIRST_GROUP);
			String variableValue = splitVariables.group(REGEX_SECOND_GROUP);
			types.addDecleration(variableName, toAdd);
			types.addAssignment(variableName, variableValue);
		}
		return types;
	}
	
	private Line parseMethodLine(String methodName, String methodVariables) throws InvalidOperationException, ValueException, NameExistsException{
		List<String> names = new ArrayList<>();
		List<Type> types = new ArrayList<>();
		if( methodVariables !=null){
		Matcher splitMatcher = METHOD_SPLIT_PATTERN.matcher(methodVariables);
			while( splitMatcher.find() ){
				String finalModifier = splitMatcher.group(REGEX_FIRST_GROUP).trim();
				String variableType = splitMatcher.group(REGEX_SECOND_GROUP).trim();
				String variableName = splitMatcher.group(REGEX_THIRD_GROUP).trim();
				names.add(variableName);
				types.add(TypeFactory.createType(finalModifier, variableType));
			}
		}
		return new MethodLine(methodName, types, names);
	}
	
	private Line parseMethodCall(String methodName, String methodVariables){
		Matcher splitMatcher = METHOD_CALL_SPLIT_PATTERN.matcher(methodVariables);
		List<String> variables = new ArrayList<>();
		while(splitMatcher.find()){
			//TODO: check in context!!!!!
			/*String variable = splitMatcher.group(REGEX_FIRST_GROUP);
			if (currentContext.hasVariable(variable)){
				variables.add(currentContext.getVariable(variable));
			}
			else {
				variables.add(TypeFactory.anonymousType(variable));
			}*/
			variables.add(splitMatcher.group(REGEX_FIRST_GROUP));
		}
		return new MethodCallLine(methodName, variables);
	}
}
