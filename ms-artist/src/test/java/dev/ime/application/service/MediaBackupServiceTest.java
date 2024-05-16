package dev.ime.application.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
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
import dev.ime.domain.port.outbound.MediaBackupRepositoryPort;

@ExtendWith(MockitoExtension.class)
class MediaBackupServiceTest {
	
	@Mock
	private MediaBackupRepositoryPort mediaBackupRepositoryPort;
	
	@InjectMocks
	private MediaBackupService mediaBackupService;
	

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
	void MediaBackupService_save_ReturnVoid() {
		
		Mockito.doNothing().when(mediaBackupRepositoryPort).save(Mockito.any(Media.class));
		
		mediaBackupService.save(mediaTest);
		
		verify(mediaBackupRepositoryPort, times(1)).save(Mockito.any(Media.class));
	}

	@Test
	void MediaBackupService_deleteById_ReturnVoid() {
		
		Mockito.doNothing().when(mediaBackupRepositoryPort).deleteById(Mockito.anyLong());
		
		mediaBackupService.deleteById(id);
		
		verify(mediaBackupRepositoryPort, times(1)).deleteById(Mockito.anyLong());
		
	}

	@Test
	void MediaBackupService_update_ReturnVoid() {
		
		Mockito.when(mediaBackupRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaTest));
		Mockito.doNothing().when(mediaBackupRepositoryPort).save(Mockito.any(Media.class));
		
		mediaBackupService.update(mediaTest);
		
		verify(mediaBackupRepositoryPort, times(1)).save(Mockito.any(Media.class));		
	}

	@Test
	void MediaBackupService_existArtistId_ReturnBool() {
		
		Mockito.when(mediaBackupRepositoryPort.existArtistId(Mockito.anyLong())).thenReturn(true);
		
		boolean resultValue = mediaBackupService.existArtistId(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(resultValue).isTrue()
				);
	}

}
