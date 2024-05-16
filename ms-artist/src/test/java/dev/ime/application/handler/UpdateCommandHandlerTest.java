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

import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.application.usecase.CreateCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdateCommandHandlerTest {

	@Mock
	private ArtistWriteRepositoryPort artistWriteRepositoryPort;	

	@InjectMocks
	private UpdateCommandHandler updateCommandHandler;

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private CreateCommand createCommand;
	private UpdateCommand updateCommand;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();
		
		createCommand = new CreateCommand(artistTest);
		updateCommand = new UpdateCommand(id, artistTest);
		
	}	
	
	@Test
	void UpdateCommandHandlerTest_handle_ReturnOptArtist() {		
		
		Mockito.when(artistWriteRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artistTest));
		Mockito.when(artistWriteRepositoryPort.save(Mockito.any(Artist.class))).thenReturn(Optional.ofNullable(artistTest));
		
		Optional<Artist> optArtist = updateCommandHandler.handle(updateCommand);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}


	@Test
	void UpdateCommandHandler_handle_ReturnIllegalArgumentException() {
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> updateCommandHandler.handle(createCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);
	}

	@Test
	void UpdateCommandHandler_handle_ReturnResourceNotFoundException() {
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> updateCommandHandler.handle(updateCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
	}

}
