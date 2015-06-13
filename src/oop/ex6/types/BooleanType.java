package oop.ex6.types;

import oop.ex6.line.InvalidOperationException;

public class BooleanType extends DoubleType {

	public BooleanType(String value, boolean isFinal) throws ValueException, InvalidOperationException {
		super(value, isFinal);
	}

}
