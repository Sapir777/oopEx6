package oop.ex6.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;

import oop.ex6.context.NameException;
import oop.ex6.context.NameExistsException;
import oop.ex6.line.InvalidOperationException;
import oop.ex6.line.LineParser;
import oop.ex6.types.ValueException;

public class Sjavac {

	public static void main(String[] args) {
		RandomAccessFile reader;
		try {
			reader = new RandomAccessFile(args[0], "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("2");
			return;
		}
		LineParser parser = new LineParser(reader);
		try {
			parser.read();
		} catch ( InvalidOperationException | ValueException
				| NameExistsException | NameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("1");
			return;
		} catch (IOException e){
			System.out.println("2");
			return;
		}
		System.out.println("0");

	}

}
