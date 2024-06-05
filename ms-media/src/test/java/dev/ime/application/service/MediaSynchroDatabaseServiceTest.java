package dev.ime.application.service;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.MediaSynchroDatabaseRepositoryPort;

@ExtendWith(MockitoExtension.class)
class MediaSynchroDatabaseServiceTest {

	@Mock
	private MediaSynchroDatabaseRepositoryPort mediaSynchroDatabaseRepositoryPort;

	@InjectMocks
	private MediaSynchroDatabaseService mediaSynchroDatabaseServicePort;

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
	void MediaSynchroDatabaseService_syncCreate_ReturnVoid() {
		
		Mockito.doNothing().when(mediaSynchroDatabaseRepositoryPort).save(Mockito.any(Media.class));
		
		mediaSynchroDatabaseServicePort.syncCreate(mediaTest);
		
		verify(mediaSynchroDatabaseRepositoryPort, times(1)).save(Mockito.any(Media.class));
	}

	@Test
	void MediaSynchroDatabaseService_syncUpdate_ReturnVoid() {
		
		Mockito.doNothing().when(mediaSynchroDatabaseRepositoryPort).update(Mockito.any(Media.class));
		
		mediaSynchroDatabaseServicePort.syncUpdate(mediaTest);
		
		verify(mediaSynchroDatabaseRepositoryPort, times(1)).update(Mockito.any(Media.class));		
	}

	@Test
	void MediaSynchroDatabaseService_syncDelete_ReturnVoid() {
		
		Mockito.doNothing().when(mediaSynchroDatabaseRepositoryPort).deleteById(Mockito.anyLong());
		
		mediaSynchroDatabaseServicePort.syncDelete(id);
		
		verify(mediaSynchroDatabaseRepositoryPort, times(1)).deleteById(Mockito.anyLong());		
	}

}
