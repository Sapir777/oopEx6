package oop.ex6.types;

import oop.ex6.line.InvalidOperationException;

public class StringType extends Type {

	public StringType(String value, boolean isFinal) throws ValueException, InvalidOperationException {
		super(value, isFinal);
	}

}
