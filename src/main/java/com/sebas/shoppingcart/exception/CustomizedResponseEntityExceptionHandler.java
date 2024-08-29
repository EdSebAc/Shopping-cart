package com.sebas.shoppingcart.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.sebas.shoppingcart.controllers.UserNotFoundException;
import com.sebas.shoppingcart.controllers.WishlistNotFoundException;
import com.sebas.shoppingcart.controllers.ProductNotFoundException;
import com.sebas.shoppingcart.controllers.EmailAlreadyInUseException;
import com.sebas.shoppingcart.controllers.NoMoreProductException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> habldeUserNotFoundException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailAlreadyInUseException.class)
	public final ResponseEntity<ErrorDetails> handleEmailAlreadyInUseException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public final ResponseEntity<ErrorDetails> hanldeProductNotFoundException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(WishlistNotFoundException.class)
	public final ResponseEntity<ErrorDetails> hanldeWishlistNotFoundException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoMoreProductException.class)
	public final ResponseEntity<ErrorDetails> hanldeNoMoreProductException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),"Total Errors: " + ex.getErrorCount() + ". First Error: " + ex.getFieldError().getDefaultMessage(),request.getDescription(false));
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
