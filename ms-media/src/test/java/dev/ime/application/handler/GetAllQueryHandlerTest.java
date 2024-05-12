package dev.ime.application.handler;


import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.usecase.GetAllQuery;
import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaReadRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetAllQueryHandlerTest {

	@Mock
	private MediaReadRepositoryPort mediaReadRepositoryPort;	

	@InjectMocks
	private GetAllQueryHandler getAllQueryHandler;
	
	private List<Media> mediaList;
	private GetAllQuery getAllQuery;
	private GetByIdQuery getByIdQuery;
	
	@BeforeEach
	private void createObjects() {
		
		mediaList = new ArrayList<>();
		getAllQuery = new GetAllQuery();
		getByIdQuery = new GetByIdQuery(17L);
	}	
	
	@Test
	void GetAllQueryHandler_handle_ReturnListMedia() {		
		
		Mockito.when(mediaReadRepositoryPort.findAll()).thenReturn(mediaList);
		
		List<Media>list = getAllQueryHandler.handle(getAllQuery);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);		
	}

	@Test
	void GetAllQueryHandler_handle_ReturnIllegalArgumentException() {		
				
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> getAllQueryHandler.handle(getByIdQuery));
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);		
	}

}
