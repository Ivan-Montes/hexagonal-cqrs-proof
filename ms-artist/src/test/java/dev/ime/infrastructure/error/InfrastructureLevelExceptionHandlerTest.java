package dev.ime.infrastructure.error;


import java.util.ArrayList;
import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.ime.application.error.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class InfrastructureLevelExceptionHandlerTest {

	@Mock
	private Logger logger;
	
	@InjectMocks
	private InfrastructureLevelExceptionHandler infrastructureLevelExceptionHandler;

	private final String name = "Our Exception";
	private final String description = "Our Exception, born and raised here";
	
	
	@Test
	void InfrastructureLevelExceptionHandler_methodArgumentNotValidException_ReturnResponseEntityExceptionResponse() {
		
		MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
		List<ObjectError>listErrors = new ArrayList<>();
		listErrors.add(new FieldError(name, description, description));
		Mockito.when(bindingResult.getAllErrors()).thenReturn(listErrors);
		
		ResponseEntity<ExceptionResponse> responseEntity = infrastructureLevelExceptionHandler.methodArgumentNotValidException(ex);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(responseEntity).isNotNull(),
				()-> Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE)
				);	
	}

	@Test
	void InfrastructureLevelExceptionHandler_methodArgumentTypeMismatchException_ReturnResponseEntityExceptionResponse() {
		
		MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(null, MethodArgumentTypeMismatchException.class, "MethodArgumentTypeMismatchException", null, null);
		
		ResponseEntity<ExceptionResponse> responseEntity = infrastructureLevelExceptionHandler.methodArgumentTypeMismatchException(ex);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(responseEntity).isNotNull(),
				()-> Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
				);	
	}

	@Test
	void InfrastructureLevelExceptionHandler_jakartaValidationConstraintViolationException_ReturnResponseEntityExceptionResponse() {
		
		ConstraintViolationException ex = Mockito.mock(ConstraintViolationException.class);
		Mockito.when(ex.getLocalizedMessage()).thenReturn(name);
		Mockito.when(ex.getMessage()).thenReturn(description);

		ResponseEntity<ExceptionResponse> responseEntity = infrastructureLevelExceptionHandler.jakartaValidationConstraintViolationException(ex);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(responseEntity).isNotNull(),
				()-> Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
				);	
	}

	@Test
	void InfrastructureLevelExceptionHandler_noResourceFoundException_ReturnResponseEntityExceptionResponse() {
		
		NoResourceFoundException ex = Mockito.mock(NoResourceFoundException.class);
		Mockito.when(ex.getLocalizedMessage()).thenReturn(name);
		Mockito.when(ex.getMessage()).thenReturn(description);

		ResponseEntity<ExceptionResponse> responseEntity = infrastructureLevelExceptionHandler.noResourceFoundException(ex);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(responseEntity).isNotNull(),
				()-> Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
				);		
	}
	
}
