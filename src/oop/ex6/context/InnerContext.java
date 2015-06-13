package oop.ex6.context;
import java.util.List;

import oop.ex6.line.InvalidOperationException;
import oop.ex6.line.Line;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;

public class InnerContext extends Context {
	private Context parent;
	
	public InnerContext(Context parent) {
		super();
		this.parent = parent;
	}

	@Override
	public Context getParent() throws InvalidOperationException {
		return parent;
	};
	
	@Override
	public Type getVariable(String name) throws NameException {
		if (hasLocalVariable(name)) {
			return getLocalVariable(name);
		}
		return parent.getVariable(name);
	}

	@Override
	public MethodContext getMethod(String name) throws NameException {
		return parent.getMethod(name);
	}

	@Override
	public void setLocalMethod(String name, MethodContext method) throws InvalidOperationException {
		throw new InvalidOperationException();
	}

	@Override
	public boolean supportsCondtion() {
		return true;
	}

	//TODO: split this monster to functions!!!!
	@Override
	public Context handleLine(Line line) throws NameException, InvalidOperationException, NameExistsException {
		// TODO Auto-generated method stub
		switch(line.getLineType()){
		case BRACES:
			return parent;
		case ASSIGNMENT:
			List<String> names = line.getVariablesNames();
			List<String> values = line.getVariablesValues();
			for (int i = 0; i < names.size(); i++){
				Type type = getVariable(names.get(i));
				if (type.isFinal()){
					throw new InvalidOperationException();
				}
				if(hasVariable(values.get(i))){
					Type variable = getVariable(values.get(i));
					if (!type.isValid(variable)){
						throw new InvalidOperationException();
					}
				} else {
					if (!type.isValid(values.get(i))){
						throw new InvalidOperationException();
					}
				}
			}
			break;
		case CONDITION:
			values = line.getVariablesValues();
			for( String value: values){
				if(!hasVariable(value) && !TypeFactory.isValid(value)){
					throw new InvalidOperationException();
				}
			}
			return new ConditionContext(this);
		case METHOD_CALL:
			names = line.getVariablesNames();
			values = line.getVariablesValues(); 
			if(names.size() == 1){
				MethodContext method = getMethod(names.get(0));
				List<Type> types = method.getTypes();
				for(int i = 0; i < types.size(); i++){
					if(hasVariable(values.get(i))){
						types.get(i).isValid(getVariable(values.get(i)));
					}
					else{
						types.get(i).isValid(values.get(i));
					}
				}
			}
		case TYPE_DECLARTION:
			List<Type> types = line.getVariableTypes();
			names = line.getVariablesNames();
			values = line.getVariablesValues();
			for(int i=0; i<names.size(); i++){
				/*if (hasLocalVariable(names.get(i))){
					throw new NameExistsException();
				}*/
				if (!values.get(i).equals("") && !types.get(i).isValid(values.get(i))){
					throw new InvalidOperationException();
				}
				if (values.get(i).equals("") && types.get(i).isFinal()){
					throw new InvalidOperationException();
				}
				setLocalVariable(names.get(i), types.get(i));
			}
			break;
		case RETURN:
			break;
		default:
			throw new InvalidOperationException();
		}
		return this;
	}

	@Override
	public boolean hasVariable(String name) {
		return hasLocalVariable(name) || parent.hasVariable(name);
	}

}
