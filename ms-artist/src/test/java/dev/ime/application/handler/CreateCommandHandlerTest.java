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

import dev.ime.application.usecase.CreateCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateCommandHandlerTest {

	@Mock
	private ArtistWriteRepositoryPort artistWriteRepositoryPort;	

	@InjectMocks
	private CreateCommandHandler createCommandHandler;	

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
	void CreateCommandHandler_handle_ReturnOptArtist() {
		
		Mockito.when(artistWriteRepositoryPort.save(Mockito.any(Artist.class))).thenReturn(Optional.ofNullable(artistTest));
		
		Optional<Artist> optArtist = createCommandHandler.handle(createCommand);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}	

	@Test
	void CreateCommandHandler_handle_ReturnIllegalArgumentException() {
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> createCommandHandler.handle(updateCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);
	}

}
