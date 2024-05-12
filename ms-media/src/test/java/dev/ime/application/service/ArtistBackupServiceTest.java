package dev.ime.application.service;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;


@ExtendWith(MockitoExtension.class)
class ArtistBackupServiceTest {

	@Mock
	private ArtistBackupRepositoryPort artistBackupRepositoryPort;
	
	@InjectMocks
	private ArtistBackupService artistBackupServicePort;	

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
	void ArtistBackupService_existById_ReturnBool() {
		
		Mockito.when(artistBackupRepositoryPort.existById(Mockito.anyLong())).thenReturn(true);
		
		boolean resultValue = artistBackupServicePort.existById(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(resultValue).isTrue()
				);
	}

	@Test
	void ArtistBackupService_save_ReturnVoid() {
		
		Mockito.doNothing().when(artistBackupRepositoryPort).save(Mockito.any(Artist.class));
		
		artistBackupServicePort.save(artistTest);
		
		verify(artistBackupRepositoryPort, times(1)).save(Mockito.any(Artist.class));
	}

	@Test
	void ArtistBackupService_deleteById_ReturnVoid() {
		
		Mockito.doNothing().when(artistBackupRepositoryPort).deleteById(Mockito.anyLong());
		
		artistBackupServicePort.deleteById(id);
		
		verify(artistBackupRepositoryPort, times(1)).deleteById(Mockito.anyLong());		
	}

}
