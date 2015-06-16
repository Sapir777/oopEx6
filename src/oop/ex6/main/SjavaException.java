package oop.ex6.main;

public class SjavaException extends Exception {
	private static final long serialVersionUID = 1L;
	public final String MESSAGE = "Uknown Exception";
	@Override
	public String getMessage(){
		return MESSAGE;
	}
}
