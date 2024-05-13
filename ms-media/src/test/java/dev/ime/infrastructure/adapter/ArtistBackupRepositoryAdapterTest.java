package dev.ime.infrastructure.adapter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.entity.ArtistRedisEntity;
import dev.ime.infrastructure.repository.ArtistRedisRepository;

@ExtendWith(MockitoExtension.class)
class ArtistBackupRepositoryAdapterTest {

	@Mock
	private ArtistRedisRepository artistRedisRepository;

	@Mock
	private ArtistMapper artistMapper;

	@Mock
	private Logger logger;

	@InjectMocks
	private ArtistBackupRepositoryAdapter artistBackupRepositoryPort;

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistRedisEntity artistRedisEntityTest;
	
	@BeforeEach
	private void createObjects() {
		
		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();		
		
		artistRedisEntityTest = new ArtistRedisEntity(id);
		
	}

	@Test
	void ArtistBackupRepositoryAdapter_save_ReturnVoid() {

		Mockito.when(artistMapper.fromDomainToRedis(Mockito.any(Artist.class))).thenReturn(artistRedisEntityTest);
		Mockito.when(artistRedisRepository.save(Mockito.any(ArtistRedisEntity.class))).thenReturn(null);
		
		artistBackupRepositoryPort.save(artistTest);
		
		verify(artistMapper, times(1)).fromDomainToRedis(Mockito.any(Artist.class));
		verify(artistRedisRepository, times(1)).save(Mockito.any(ArtistRedisEntity.class));
	}

	@Test
	void ArtistBackupRepositoryAdapter_deleteById_ReturnVoid() {

		Mockito.doNothing().when(artistRedisRepository).deleteById(Mockito.anyLong());

		artistBackupRepositoryPort.deleteById(id);
		
		verify(artistRedisRepository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void ArtistBackupRepositoryAdapter_existById_ReturnBool() {

		Mockito.when(artistRedisRepository.existsById(Mockito.anyLong())).thenReturn(true);
		
		boolean resultValue = artistBackupRepositoryPort.existById(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(resultValue).isTrue()
				);		
	}

}
