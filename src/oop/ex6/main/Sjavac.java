package oop.ex6.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import oop.ex6.context.NameException;
import oop.ex6.context.NameExistsException;
import oop.ex6.line.InvalidOperationException;
import oop.ex6.line.LineParser;
import oop.ex6.types.ValueException;

public class Sjavac {

	public static void main(String[] args) {
		Reader reader;
		try {
			reader = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		LineParser parser = new LineParser(reader);
		try {
			parser.read();
		} catch (IOException | InvalidOperationException | ValueException
				| NameExistsException | NameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
