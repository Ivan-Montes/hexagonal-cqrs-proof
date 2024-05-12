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
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateCommandHandlerTest {

	@Mock
	private MediaWriteRepositoryPort mediaWriteRepositoryPort;

	@Mock
	private ArtistBackupRepositoryPort artistBackupRepositoryPort;

	@InjectMocks
	private CreateCommandHandler createCommandHandler;

	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private CreateCommand createCommand;
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
		
		createCommand = new CreateCommand(mediaTest);
		deleteByIdCommand = new DeleteByIdCommand(id);
	}	
	
	@Test
	void CreateCommandHandler_handle_ReturnOptMedia() {
		
		Mockito.when(artistBackupRepositoryPort.existById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(mediaWriteRepositoryPort.save(Mockito.any(Media.class))).thenReturn(Optional.ofNullable(mediaTest));
		
		Optional<Media> optMedia = createCommandHandler.handle(createCommand);

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
	void CreateCommandHandler_handle_ReturnResourceNotFoundException() {
	
		Mockito.when(artistBackupRepositoryPort.existById(Mockito.anyLong())).thenReturn(false);
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> createCommandHandler.handle(createCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
	}
	
	@Test
	void CreateCommandHandler_handle_ReturnIllegalArgumentException() {
	
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> createCommandHandler.handle(deleteByIdCommand));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);
	}
	
}
