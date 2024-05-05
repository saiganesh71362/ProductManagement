package com.product.exceptionhandle;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.product.appconstants.AppConstants;

@ControllerAdvice
public class GlobalExceptionHandler
{

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(UserNotFoundException exception, WebRequest request)
	{
		ErrorResponse message = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleIdNotFoundException(UserNotFoundException exception, WebRequest request)
	{
		ErrorResponse message = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception ex) {
        return AppConstants.ERROR; // Generic error message for other exceptions
    }


	

}
