package dev.ime.application.service;


import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.dispatch.CommandDispatcher;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.MediaPublisherPort;

@ExtendWith(MockitoExtension.class)
class MediaCommandServiceTest {


	@Mock
	private CommandDispatcher commandDispatcher;	

	@Mock
	private MediaPublisherPort mediaPublisherPort;
	
	@InjectMocks
	private MediaCommandService mediaCommandServicePort;
	
	@Mock
	private CommandHandler<Object> commandHandler;
	
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	
	@BeforeEach
	private void createObjects() {
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
	}
	
	@Test
	void MediaCommandService_create_ReturnOptionalMedia() {
	
		Mockito.when(commandDispatcher.getCommandHandler(Mockito.any(Command.class))).thenReturn(commandHandler);
		Mockito.when(commandHandler.handle(Mockito.any(Command.class))).thenReturn(Optional.of(mediaTest));
		
		Optional<Media> optMedia = mediaCommandServicePort.create(mediaTest);
		
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
	void MediaCommandService_update_ReturnOptionalMedia() {
	
		Mockito.when(commandDispatcher.getCommandHandler(Mockito.any(Command.class))).thenReturn(commandHandler);
		Mockito.when(commandHandler.handle(Mockito.any(Command.class))).thenReturn(Optional.of(mediaTest));
		
		Optional<Media> optMedia = mediaCommandServicePort.update(id, mediaTest);
		
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
	void MediaCommandService_deleteById_ReturnBoolTrue() {

		Mockito.when(commandDispatcher.getCommandHandler(Mockito.any(Command.class))).thenReturn(commandHandler);
		Mockito.when(commandHandler.handle(Mockito.any(Command.class))).thenReturn(true);
		Mockito.doNothing().when(mediaPublisherPort).publishDeleteEvent(Mockito.anyLong());
		
		boolean resultValue = mediaCommandServicePort.deleteById(artistId);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(resultValue).isTrue()
				);	
	}
	
	

}
