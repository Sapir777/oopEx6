package oop.ex6.line;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.context.Context;
import oop.ex6.context.GlobalContext;
import oop.ex6.line.Line.LineVariant;
import oop.ex6.main.SjavaException;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;

/**
 * LineParser - parses a line, and manages the contexts
 * @author sapir, tmrlvi
 */
public class LineParser {
	//Regex catching conditions
	private final Pattern CONDITION_PATTERN = Pattern.compile("\\s*(?:if|while)\\s*\\(\\s*(-?" +
                                                      "[^,\"'\\|&]+\\s*(?:\\s+(?:\\|\\" + 
													  "||&&)\\s+-?[^,\"'&\\|]+\\s*)*)" + 
                                                      "\\s*\\)\\s*\\{\\s*");
	//Regex catching variable definitions
	private final Pattern VARIABLE_DEFINE_PATTERN = Pattern.compile("\\s*((?:final|))\\s*(\\w+)+" +
                                                       "\\s+(_*(?:[a-zA-Z]|_[a-zA-Z0-9])\\w*"+ 
													   "\\s*(?:=\\s*[\"']?-?[^,\\\"']*[\"']?|)" + 
                                                       "(?:\\s*,\\s*_*(?:[a-zA-Z]|_[a-zA-Z0-9])\\w*\\s*" +
			                                           "(?:=\\s*[\"']?-?[^,\\\"']*[\"']?|)|))\\s*;\\s*");
	//Regex catching method definitions
	private final Pattern METHOD_PATTERN = Pattern.compile("\\s*void\\s+([a-zA-Z]\\w*)\\s*\\(\\s*" +
												   "((?:final)?\\s*" +
			                                       "\\w+\\s+\\w+\\s*(?:,\\s*(?:final)?\\s*" + 
												   "\\w+\\s+\\w+)*)?\\s*\\)\\s*\\{\\s*");
	//Regex catching method calls 
	private final Pattern METHOD_CALL_PATTERN = Pattern.compile("\\s*([a-zA-Z]\\w*)\\s*\\((?:\\s*" + 
	                                                    "([\"']?-?[^,\\\"']*[\"']?\\s*(?:,\\s*[\"']?" + 
				 								        "-?[^,\\\"']*[\"']?)*\\s*|))?\\)\\s*;\\s*");
	//Regex catching variable assignments
	private final Pattern VARIABLE_ASSIGNEMT_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*([\"']?-?" + 
															           "[^,\\\"']*[\"']?)\\s*;\\s*");
	//Regex catching the return line
	private final Pattern RETURN_PATTERN = Pattern.compile("\\s*return\\s*;\\s*");
	
	//Regex for splitting the condition line
	private final Pattern CONDITION_SPLIT_PATTERN = Pattern.compile("-?[^\\(\\)\\s,\"'\\|&]+");
	//Regex for splitting the variable assignment list
	private final Pattern VARIABLE_SPLIT_PATTERN = Pattern.compile("(\\w+)\\s*(?:=\\s*([\"']?-?" + 
																   "[^,\\\"']*[\"']?)|)");
	//Regex for splitting the variable deceleration in a method
	private final Pattern METHOD_SPLIT_PATTERN = Pattern.compile("(final\\s+|)(\\w+)\\s+(\\w+)");
	//Regex for splitting the variable list in a method call
	private final Pattern METHOD_CALL_SPLIT_PATTERN = Pattern.compile("\\s*([\"']?-?[^,\\\"']+" +
																	  "[\"']?)\\s*");
	
	// The position of the first argument in regex
	final private int REGEX_FIRST_GROUP = 1;
	// The position of the second argument in regex
	final private int REGEX_SECOND_GROUP = 2;
	// The position of the third argument in regex
	final private int REGEX_THIRD_GROUP = 3;
	
	// The value of a comment
	private static String COMMENT = "//";
	// Position of file begining
	private static int FILE_START = 0;
	
	// The current context
	private Context currentContext;
	// The file to read
	private RandomAccessFile reader;

	/**
	 * Constructor - creates a new parser
	 * @param reader - the file to read
	 */
	public LineParser(RandomAccessFile reader){
		this.reader = reader;
		currentContext = new GlobalContext();
	}
	
	/**
	 * reads and validates the file
	 * @throws IOException error reading the file
	 * @throws SjavaException Error in parsing the file or context
	 */
	public void read() throws IOException, SjavaException {
		String line = reader.readLine();
		int level = 0; //keep track on which context we are
		while(line != null ){
			if( !line.trim().equals("") && !line.trim().startsWith(COMMENT)){
				Line nextLine = parseLine(line);
				if (level == 0 && (nextLine.getLineType() == LineVariant.TYPE_DECLARTION || 
						nextLine.getLineType() == LineVariant.METHOD_DECLERATION)){
					currentContext = currentContext.handleLine(nextLine);
				}
				if(nextLine.getLineType() == LineVariant.METHOD_DECLERATION || 
						nextLine.getLineType() == LineVariant.CONDITION){
					level++;
				}
				else if(nextLine.getLineType() == LineVariant.BRACES){
					level--;
				}
			}
			line = reader.readLine();
		}
		reader.seek(FILE_START);
		line = reader.readLine();
		while(line != null){
			if( !line.trim().equals("") && !line.trim().startsWith(COMMENT)){
				Line nextLine = parseLine(line);
				if (nextLine.getLineType() == LineVariant.METHOD_DECLERATION){
					currentContext = currentContext.getMethod(nextLine.getName());
				}
				else if(currentContext.getParent() != null || 
						nextLine.getLineType() != LineVariant.TYPE_DECLARTION){
					currentContext = currentContext.handleLine(nextLine);
				}
			}
			line = reader.readLine();
		}
	}
		
	/**
	 * parses a line
	 * @param inputLine the line to parse
	 * @return line object representing the line
	 * @throws ParsingException the expression is wrong somehow
	 */
	public Line parseLine(String inputLine) throws ParsingException {
		Matcher lineMatcher = CONDITION_PATTERN.matcher(inputLine);
		if(lineMatcher.matches()){
			return parseConditionLine(lineMatcher.group(REGEX_FIRST_GROUP));
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
		
		throw new InvalidExpressionException();
	}
	
	/**
	 * Parse the condition line
	 * @param inputLine the line to parse
	 * @return the condition line relevant
	 */
	private Line parseConditionLine(String inputLine){
		Matcher splitMatcher = CONDITION_SPLIT_PATTERN.matcher(inputLine);
		List<String> values = new ArrayList<>();
		while(splitMatcher.find()){
			values.add(splitMatcher.group());
		}
		return new ConditionLine(values);
	}
	
	/**
	 * Parse the variable deceleration line
	 * @param inputLine the line to parse
	 * @return the variable line relevant
	 */
	private Line parseVariableLine(String finalModifier, String variableType, String variables) 
			throws ParsingException{
		Matcher splitVariables = VARIABLE_SPLIT_PATTERN.matcher(variables);
		TypeLine types = new TypeLine();
		while(splitVariables.find()){
			Type toAdd = TypeFactory.createType(finalModifier, variableType);
			String variableName = splitVariables.group(REGEX_FIRST_GROUP);
			String variableValue = splitVariables.group(REGEX_SECOND_GROUP);
			if (variableValue == null){
				variableValue = "";
			}
			types.addDecleration(variableName.trim(), toAdd);
			types.addAssignment(variableName.trim(), variableValue.trim());
		}
		return types;
	}
	
	/**
	 * Parse the method deceleration line
	 * @param inputLine the line to parse
	 * @return the method line relevant
	 */
	private Line parseMethodLine(String methodName, String methodVariables) 
			throws ParsingException {
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
	
	/**
	 * Parse the method call line
	 * @param inputLine the line to parse
	 * @return the method call line relevant
	 */
	private Line parseMethodCall(String methodName, String methodVariables){
		Matcher splitMatcher = METHOD_CALL_SPLIT_PATTERN.matcher(methodVariables);
		List<String> variables = new ArrayList<>();
		while(splitMatcher.find()){
			variables.add(splitMatcher.group(REGEX_FIRST_GROUP).trim());
		}
		return new MethodCallLine(methodName, variables);
	}
}
