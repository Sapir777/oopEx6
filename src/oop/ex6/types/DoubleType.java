package oop.ex6.types;

import oop.ex6.line.InvalidOperationException;

public class DoubleType extends IntType {

	public DoubleType(String value, boolean isFinal) throws ValueException, InvalidOperationException {
		super(value, isFinal);
	}

}
