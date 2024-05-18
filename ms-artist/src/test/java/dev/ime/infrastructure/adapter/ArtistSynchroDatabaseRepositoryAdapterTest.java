package dev.ime.infrastructure.adapter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.entity.ArtistMongoEntity;
import dev.ime.infrastructure.repository.ArtistWriteMongoRepository;

@ExtendWith(MockitoExtension.class)
class ArtistSynchroDatabaseRepositoryAdapterTest {

	@Mock
	private ArtistWriteMongoRepository artistWriteMongoRepository;

	@Mock
	private ArtistMapper artistMapper;

	@Mock
	private Logger logger;
	
	@InjectMocks
	private ArtistSynchroDatabaseRepositoryAdapter artistSynchroDatabaseRepositoryPort;

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistMongoEntity artistMongoTest;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();	
		
		artistMongoTest = new ArtistMongoEntity(null, id, name, surname, artisticName);	
	}
	
	@Test
	void ArtistSynchroDatabaseRepositoryAdapter_save_ReturnVoid() {
		
		Mockito.when(artistMapper.fromDomainToMongo(Mockito.any(Artist.class))).thenReturn(artistMongoTest);
		Mockito.when(artistWriteMongoRepository.save(Mockito.any())).thenReturn(artistMongoTest);
		
		artistSynchroDatabaseRepositoryPort.save(artistTest);
		
		verify(artistMapper, times(1)).fromDomainToMongo(Mockito.any(Artist.class));
		verify(artistWriteMongoRepository, times(1)).save(Mockito.any());
	}

	@Test
	void ArtistSynchroDatabaseRepositoryAdapter_update_ReturnVoid() {
		
		Mockito.when(artistWriteMongoRepository.findFirstByArtistId(Mockito.anyLong())).thenReturn(Optional.ofNullable(artistMongoTest));
		Mockito.when(artistWriteMongoRepository.save(Mockito.any())).thenReturn(artistMongoTest);

		artistSynchroDatabaseRepositoryPort.update(artistTest);
		
		verify(artistWriteMongoRepository, times(1)).save(Mockito.any());
		verify(artistWriteMongoRepository, times(1)).findFirstByArtistId(Mockito.anyLong());
	}

	@Test
	void ArtistSynchroDatabaseRepositoryAdapter_update_ReturnResourceNotFoundException() {
		
		Mockito.when(artistWriteMongoRepository.findFirstByArtistId(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> artistSynchroDatabaseRepositoryPort.update(artistTest));
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
		verify(artistWriteMongoRepository, times(1)).findFirstByArtistId(Mockito.anyLong());
	}

	@Test
	void ArtistSynchroDatabaseRepositoryAdapter_deleteById_ReturnVoid() {
		
		Mockito.when(artistWriteMongoRepository.findFirstByArtistId(Mockito.anyLong())).thenReturn(Optional.ofNullable(artistMongoTest));
		Mockito.doNothing().when(artistWriteMongoRepository).delete(Mockito.any(ArtistMongoEntity.class));
		
		artistSynchroDatabaseRepositoryPort.deleteById(id);
		
		verify(artistWriteMongoRepository, times(1)).findFirstByArtistId(Mockito.anyLong());
		verify(artistWriteMongoRepository, times(1)).delete(Mockito.any(ArtistMongoEntity.class));
	}

	@Test
	void ArtistSynchroDatabaseRepositoryAdapter_deleteById_ReturnResourceNotFoundException() {
		
		Mockito.when(artistWriteMongoRepository.findFirstByArtistId(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> artistSynchroDatabaseRepositoryPort.deleteById(id));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
		verify(artistWriteMongoRepository, times(1)).findFirstByArtistId(Mockito.anyLong());
	}

}
