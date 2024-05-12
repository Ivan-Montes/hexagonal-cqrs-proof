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
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdateCommandHandlerTest {

	@Mock
	private MediaWriteRepositoryPort mediaWriteRepositoryPort;

	@Mock
	private ArtistBackupRepositoryPort artistBackupRepositoryPort;

	@InjectMocks
	private UpdateCommandHandler updateCommandHandler;

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
	void UpdateCommandHandler_handle_ReturnOptMedia() {
	
		Mockito.when(mediaWriteRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaTest));
		Mockito.when(artistBackupRepositoryPort.existById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(mediaWriteRepositoryPort.save(Mockito.any(Media.class))).thenReturn(Optional.ofNullable(mediaTest));
		
		Optional<Media> optMedia = updateCommandHandler.handle(updateCommand);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optMedia).isNotEmpty(),
				()-> Assertions.assertThat(optMedia.get().getId()).isEqualTo(id),
				()-> Assertions.assertThat(optMedia.get().getName()).isEqualTo(name),
				()-> Assertions.assertThat(optMedia.get().getGenre()).isEqualTo(genre),
				()-> Assertions.assertThat(optMedia.get().getMediaClass()).isEqualTo(mediaClass),
				()-> Assertions.assertThat(optMedia.get().getArtistId()).isEqualTo(artistId)
				);	
	}
	

	@Test
	void UpdateCommandHandler_handle_ReturnResourceNotFoundExceptionArtist() {
		
		Mockito.when(mediaWriteRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaTest));
		Mockito.when(artistBackupRepositoryPort.existById(Mockito.anyLong())).thenReturn(false);
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> updateCommandHandler.handle(updateCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
	}

	@Test
	void UpdateCommandHandler_handle_ReturnResourceNotFoundExceptionMedia() {
	
		Mockito.when(mediaWriteRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> updateCommandHandler.handle(updateCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
	}
	
	@Test
	void UpdateCommandHandler_handle_ReturnIllegalArgumentException() {
	
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> updateCommandHandler.handle(deleteByIdCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);
	}
}
