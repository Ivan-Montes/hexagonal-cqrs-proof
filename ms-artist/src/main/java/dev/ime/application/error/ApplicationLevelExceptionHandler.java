package dev.ime.application.error;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.BasicException;



@ControllerAdvice
public class ApplicationLevelExceptionHandler {

	private final Logger logger;	
	
	public ApplicationLevelExceptionHandler(Logger logger) {
		super();
		this.logger = logger;
	}

	@ExceptionHandler({
		dev.ime.application.exception.ResourceNotFoundException.class,
		dev.ime.application.exception.EntityAssociatedException.class,
		dev.ime.application.exception.BasicException.class
		})
	public ResponseEntity<ExceptionResponse> basicException(BasicException ex){
	
		logSevereAction(ex.getName() + " :=: Impl of Basic Exception Class");
		return new ResponseEntity<>( new ExceptionResponse( ex.getIdentifier(),ex.getName(),ex.getDescription(),ex.getErrors() ),
									HttpStatus.NOT_FOUND );
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException ex){
		
		logSevereAction(ApplicationConstant.EX_ILLEGAL_ARGUMENT);
		return new ResponseEntity<>( new ExceptionResponse( UUID.randomUUID(),
				ApplicationConstant.EX_ILLEGAL_ARGUMENT,
				ApplicationConstant.EX_ILLEGAL_ARGUMENT_DES,
				Map.of( ex.getLocalizedMessage(), ex.getMessage()) ),
				HttpStatus.BAD_REQUEST );
	}

	private void logSevereAction(String msg) {
		
		String logMessage = String.format("### [%s] -> [%s] ###", this.getClass().getSimpleName(), msg);
		
		logger.log(Level.SEVERE, logMessage);
		
	} 
	
}
