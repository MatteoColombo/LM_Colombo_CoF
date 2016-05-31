package model.exceptions;

public class ConfigurationErrorException extends Exception{
	public ConfigurationErrorException(String errorMessage){
		super(errorMessage);
	}
	
	public ConfigurationErrorException(Exception e){
		super(e);
	}
}
