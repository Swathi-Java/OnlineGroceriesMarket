package com.lancesoft.omg.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lancesoft.omg.exception.CustomException;
import com.lancesoft.omg.exception.NotValidException;
import com.lancesoft.omg.exception.NotValidPasswordException;
import com.lancesoft.omg.exception.UserAlreadyExists;

@RestControllerAdvice
public class ExceptionHandlers {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField() , error.getDefaultMessage());
		});
		return errorMap;
	}


	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ExceptionHandler(CustomException.class)
	public Map<String, String>handleBussinessException( CustomException exception)
	{
		Map<String, String>hashMap=new HashMap<String, String>();
		hashMap.put("errorMessage",exception.getMessage());
		return hashMap;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotValidException.class)
	public Map<String ,String>handleBussinessException (NotValidException ex)
	{
		Map<String ,String> map=new HashMap<String ,String>();
		map.put("error", ex.getMessage());
		return map;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserAlreadyExists.class)
	public Map<String, String>handleBusinessException(UserAlreadyExists exception)
	{
		Map<String, String>hashmap=new HashMap<String,String>();
		hashmap.put("Error", exception.getMessage());
		return hashmap;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotValidPasswordException.class)
	public Map<String, String>handleBusinessException(NotValidPasswordException exception)
	{
		Map<String, String>hashmap=new HashMap<String,String>();
		hashmap.put("Error", exception.getMessage());
		return hashmap;
	}
}
