package oop.ex6.types;

import oop.ex6.line.InvalidOperationException;

public class CharType extends Type {

	public CharType(String value, boolean isFinal) throws ValueException, InvalidOperationException {
		super(value, isFinal);
	}

}
