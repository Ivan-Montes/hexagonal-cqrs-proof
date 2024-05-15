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
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistPublisherPort;

@ExtendWith(MockitoExtension.class)
class ArtistCommandServiceTest {

	@Mock
	private CommandDispatcher commandDispatcher;	

	@Mock
	private ArtistPublisherPort artistPublisherPort;

	@InjectMocks
	private ArtistCommandService artistCommandService;
		
	@Mock
	private CommandHandler<Object> commandHandler;
	
	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();		
	}
	
	@Test
	void ArtistCommandService_create_ReturnOptArtist() {	
		
		Mockito.when(commandDispatcher.getCommandHandler(Mockito.any(Command.class))).thenReturn(commandHandler);
		Mockito.when(commandHandler.handle(Mockito.any(Command.class))).thenReturn(Optional.ofNullable(artistTest));
		Mockito.doNothing().when(artistPublisherPort).publishInsertEvent(Mockito.any(Artist.class));
		
		Optional<Artist> optArtist = artistCommandService.create(artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}

	@Test
	void ArtistCommandService_update_ReturnOptArtist() {
		
		Mockito.when(commandDispatcher.getCommandHandler(Mockito.any(Command.class))).thenReturn(commandHandler);
		Mockito.when(commandHandler.handle(Mockito.any(Command.class))).thenReturn(Optional.ofNullable(artistTest));
		Mockito.doNothing().when(artistPublisherPort).publishUpdateEvent(Mockito.any(Artist.class));
		
		Optional<Artist> optArtist = artistCommandService.update(id, artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);			
	}

	@Test
	void ArtistCommandService_deleteById_ReturnBoolean() {
		
		Mockito.when(commandDispatcher.getCommandHandler(Mockito.any(Command.class))).thenReturn(commandHandler);
		Mockito.when(commandHandler.handle(Mockito.any(Command.class))).thenReturn(true);
		Mockito.doNothing().when(artistPublisherPort).publishDeleteEvent(Mockito.anyLong());
		
		boolean resultValue = artistCommandService.deleteById(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(resultValue).isTrue()
				);
	}

}
