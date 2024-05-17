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

import dev.ime.application.exception.EntityAssociatedException;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;
import dev.ime.domain.port.outbound.MediaBackupRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteByIdCommandHandlerTest {

	@Mock
	private ArtistWriteRepositoryPort artistWriteRepositoryPort;		

	@Mock
	private MediaBackupRepositoryPort mediaBackupRepositoryPort;	

	@InjectMocks
	private DeleteByIdCommandHandler deleteByIdCommandHandler;

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private DeleteByIdCommand deleteByIdCommand;
	private UpdateCommand updateCommand;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();
		
		deleteByIdCommand = new DeleteByIdCommand(id);
		updateCommand = new UpdateCommand(id, artistTest);
		
	}
	
	@Test
	void DeleteByIdCommandHandler_handle_ReturnBool() {
		
		Mockito.when(mediaBackupRepositoryPort.existArtistId(Mockito.anyLong())).thenReturn(false);
		Mockito.doNothing().when(artistWriteRepositoryPort).deleteById(Mockito.anyLong());
		Mockito.when(artistWriteRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		Boolean resultValue = deleteByIdCommandHandler.handle(deleteByIdCommand);
		
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

	@Test
	void DeleteByIdCommandHandler_handle_ReturnEntityAssociatedException() {
		
		Mockito.when(mediaBackupRepositoryPort.existArtistId(Mockito.anyLong())).thenReturn(true);

		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(EntityAssociatedException.class, ()-> deleteByIdCommandHandler.handle(deleteByIdCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(EntityAssociatedException.class)
				);
	}

}
