package dev.ime.application.handler;


import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteByIdCommandHandlerTest {

	@Mock
	private MediaWriteRepositoryPort mediaWriteRepositoryPort;

	@InjectMocks
	private DeleteByIdCommandHandler deleteByIdCommandHandler;
	
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private UpdateCommand updateCommand;
	private DeleteByIdCommand deleteByIdCommand;
	

	@BeforeEach
	private void createObjects() {
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		updateCommand = new UpdateCommand(id, mediaTest);
		deleteByIdCommand = new DeleteByIdCommand(id);
	}	
	
	@Test
	void DeleteByIdCommandHandler_handle_ReturnBool() {
		
		Mockito.doNothing().when(mediaWriteRepositoryPort).deleteById(Mockito.anyLong());
		Mockito.when(mediaWriteRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		boolean resultValue = deleteByIdCommandHandler.handle(deleteByIdCommand);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(resultValue).isTrue()
				);
	}

	@Test
	void DeleteByIdCommandHandler_handle_ReturnIllegalArgumentException() {
	
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> deleteByIdCommandHandler.handle(updateCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);
	}
	
	
}
