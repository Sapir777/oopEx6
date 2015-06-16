package oop.ex6.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import oop.ex6.line.LineParser;

public class Sjavac {

	public static void main(String[] args)  {
		RandomAccessFile reader;
		try {
			reader = new RandomAccessFile(args[0], "r");
		} catch (FileNotFoundException e) {
			System.out.println("2");
			return;
		}
		LineParser parser = new LineParser(reader);
		try {
			parser.read();
		} catch ( SjavaException e) {
			System.out.println("1");
			System.err.println(e.getMessage());
			return;
		} catch (IOException e){
			System.out.println("2");
			return;
		}
		System.out.println("0");

	}

}
