package dev.ime.application.error;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.ime.application.exception.BasicException;

@ExtendWith(MockitoExtension.class)
class ApplicationLevelExceptionHandlerTest {

	@Mock
	private Logger logger;
	
	@InjectMocks
	private ApplicationLevelExceptionHandler applicationLevelExceptionHandler;
	
	private final UUID uuid = UUID.randomUUID();
	private final String name = "Our Exception";
	private final String description = "Our Exception, born and raised here";
	private Map<String, String> errors;
	
	@BeforeEach
	private void createObjects() {		
		
		errors = new HashMap<String, String>();
		
	}
	
	@Test
	void ApplicationLevelExceptionHandler_basicException_ReturnResponseEntity() {
		
		ResponseEntity<ExceptionResponse> responseEntity = applicationLevelExceptionHandler.basicException(new BasicException(uuid, name, description, errors));
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(responseEntity).isNotNull(),
				()-> Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
				);	
	}

	@Test
	void ApplicationLevelExceptionHandler_illegalArgumentException_ReturnResponseEntity() {
		
		IllegalArgumentException ex = Mockito.mock(IllegalArgumentException.class);
		Mockito.when(ex.getLocalizedMessage()).thenReturn(name);
		Mockito.when(ex.getMessage()).thenReturn(description);
		
		ResponseEntity<ExceptionResponse> responseEntity = applicationLevelExceptionHandler.illegalArgumentException(ex);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(responseEntity).isNotNull(),
				()-> Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
				);	
	}
	

}
