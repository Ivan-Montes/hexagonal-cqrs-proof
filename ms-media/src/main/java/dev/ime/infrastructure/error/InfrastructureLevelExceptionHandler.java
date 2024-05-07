package dev.ime.infrastructure.error;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import dev.ime.application.error.ExceptionResponse;
import dev.ime.infrastructure.config.*;

@ControllerAdvice
public class InfrastructureLevelExceptionHandler {

	private final Logger logger;
	
	public InfrastructureLevelExceptionHandler(Logger logger) {
		super();
		this.logger = logger;
	}

	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex){

		logger.severe("### [InfrastructureLevelExceptionHandler] -> [Throw " + InfrastructureConstant.EX_METHOD_ARGUMENT_INVALID + "]");
		Map<String, String> errors = new HashMap<>();
		    ex.getBindingResult().getAllErrors().forEach( error -> {
		        String fieldName = ((FieldError) error).getField();
		        String errorMessage = error.getDefaultMessage();
		        errors.put(fieldName, errorMessage);
		    });
		
		return new ResponseEntity<>( new ExceptionResponse( UUID.randomUUID(),
				InfrastructureConstant.EX_METHOD_ARGUMENT_INVALID,
				InfrastructureConstant.EX_METHOD_ARGUMENT_INVALID_DESC,
				errors ),
				HttpStatus.NOT_ACCEPTABLE );
	}
	
	@ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionResponse>methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){

		logger.severe("### [InfrastructureLevelExceptionHandler] -> [Launch " + InfrastructureConstant.EX_METHOD_ARGUMENT_TYPE + "]");
		String attrName = ex.getName();
		String typeName = "Unknown type";				
		Class<?> requiredType = ex.getRequiredType();
		
		if ( requiredType != null && requiredType.getName() != null ) typeName = requiredType.getName();
		
		return new ResponseEntity<>( new ExceptionResponse( UUID.randomUUID(),
				InfrastructureConstant.EX_METHOD_ARGUMENT_TYPE,
				InfrastructureConstant.EX_METHOD_ARGUMENT_TYPE_DESC,
				Map.of( attrName, typeName)  ),
				HttpStatus.BAD_REQUEST );
	}	
	
	@ExceptionHandler(
		jakarta.validation.ConstraintViolationException.class
		)		
	public ResponseEntity<ExceptionResponse> jakartaValidationConstraintViolationException(Exception ex){

		logger.severe("### [InfrastructureLevelExceptionHandler] -> [Cast "+ InfrastructureConstant.EX_JAKARTA_VAL + "]");
		return new ResponseEntity<>( new ExceptionResponse( UUID.randomUUID(),
				InfrastructureConstant.EX_JAKARTA_VAL,
				InfrastructureConstant.EX_JAKARTA_VAL_DESC,
				Map.of( ex.getLocalizedMessage(), ex.getMessage())  ),
				HttpStatus.BAD_REQUEST );
	}	
	
	@ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
	public ResponseEntity<ExceptionResponse> noResourceFoundException(Exception ex){
		
		logger.severe("### [InfrastructureLevelExceptionHandler] -> [Summon "+ InfrastructureConstant.EX_NO_RESOURCE + "]");
		return new ResponseEntity<>( new ExceptionResponse( UUID.randomUUID(),
				InfrastructureConstant.EX_NO_RESOURCE,
				InfrastructureConstant.EX_NO_RESOURCE_DESC,
				Map.of( ex.getLocalizedMessage(), ex.getMessage())  ),
				HttpStatus.BAD_REQUEST );		
	}
	
	@ExceptionHandler(Exception.class)		
	public ResponseEntity<ExceptionResponse> generalException(Exception ex){

		logger.severe("### [InfrastructureLevelExceptionHandler] -> [Spit "+ InfrastructureConstant.EX_EX + "]");
		return new ResponseEntity<>( new ExceptionResponse( UUID.randomUUID(),
				InfrastructureConstant.EX_EX,
				InfrastructureConstant.EX_EX_DESC,
				Map.of( ex.getLocalizedMessage(), ex.getMessage())  ),
				HttpStatus.BAD_REQUEST );
	}
	
}
