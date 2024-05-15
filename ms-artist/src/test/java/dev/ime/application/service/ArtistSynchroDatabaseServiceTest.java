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

import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseRepositoryPort;

@ExtendWith(MockitoExtension.class)
class ArtistSynchroDatabaseServiceTest {

	@Mock
	private ArtistSynchroDatabaseRepositoryPort artistSynchroDatabaseRepositoryPort;

	@InjectMocks
	private ArtistSynchroDatabaseService artistSynchroDatabaseService;

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
	void ArtistSynchroDatabaseService_syncCreate_ReturnArtist() {
		
		Mockito.doNothing().when(artistSynchroDatabaseRepositoryPort).save(Mockito.any(Artist.class));
		
		artistSynchroDatabaseService.syncCreate(artistTest);
		
		verify(artistSynchroDatabaseRepositoryPort, times(1)).save(Mockito.any(Artist.class));
	}

	@Test
	void ArtistSynchroDatabaseService_syncUpdate_ReturnArtist() {
		
		Mockito.doNothing().when(artistSynchroDatabaseRepositoryPort).update(Mockito.any(Artist.class));
		
		artistSynchroDatabaseService.syncUpdate(artistTest);
		
		verify(artistSynchroDatabaseRepositoryPort, times(1)).update(Mockito.any(Artist.class));		
	}

	@Test
	void ArtistSynchroDatabaseService_syncDelete_ReturnBool() {
		
		Mockito.doNothing().when(artistSynchroDatabaseRepositoryPort).deleteById(Mockito.anyLong());
		
		artistSynchroDatabaseService.syncDelete(id);
		
		verify(artistSynchroDatabaseRepositoryPort, times(1)).deleteById(Mockito.anyLong());				
	}

}
