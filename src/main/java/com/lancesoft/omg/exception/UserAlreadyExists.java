package com.lancesoft.omg.exception;

public class UserAlreadyExists extends RuntimeException{
   public UserAlreadyExists(String message)
   {
	   super(message);
   }
}
