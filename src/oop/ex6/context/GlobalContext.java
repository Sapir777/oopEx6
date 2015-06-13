package oop.ex6.context;

import java.util.HashMap;
import java.util.List;

import oop.ex6.line.InvalidOperationException;
import oop.ex6.line.Line;
import oop.ex6.types.Type;

public class GlobalContext extends Context{
	HashMap<String, MethodContext> methods;
	
	public GlobalContext(){
		super();
		methods = new HashMap<>();
	}

	@Override
	public Type getVariable(String name) throws NameException {
		return getLocalVariable(name);
	}

	@Override
	public MethodContext getMethod(String name) throws NameException {
		if (!methods.containsKey(name)){
			throw new NameException();
		}
		return methods.get(name);
	}

	@Override
	public void setLocalMethod(String name, MethodContext method)
			throws InvalidOperationException {
		methods.put(name, method);
		
	}

	@Override
	public boolean supportsCondtion() {
		return false;
	}

	@Override
	public boolean hasVariable(String name) {
		// TODO Auto-generated method stub
		return hasLocalVariable(name);
	}

	@Override
	public Context handleLine(Line line) throws NameException,
			InvalidOperationException, NameExistsException {
		// TODO Auto-generated method stub
		switch(line.getLineType()){
		case METHOD_DECLERATION:
			if(methods.containsKey(line.getName())){
				throw new InvalidOperationException();
			}
			methods.put(line.getName(), new MethodContext(this, line.getVariablesNames(), line.getVariableTypes()));
			return this;

		default:
			break;
			
		}
		return super.handleLine(line);
	}
	
	
}
