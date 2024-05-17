package dev.ime.infrastructure.adapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import dev.ime.infrastructure.entity.ArtistMongoEntity;
import dev.ime.infrastructure.repository.ArtistReadMongoRepository;

@ExtendWith(MockitoExtension.class)
class ArtistReadMongoRepositoryAdapterTest {

	@Mock
	private ArtistReadMongoRepository artistReadMongoRepository;

	@Mock
	private ArtistMapper artistMapper;

	@InjectMocks
	private ArtistReadMongoRepositoryAdapter artistReadRepositoryPort;

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistMongoEntity artistMongoTest;
	private List<ArtistMongoEntity> mongoList;
	private List<Artist> artistList;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();	
		artistMongoTest = new ArtistMongoEntity(null, id, name, surname, artisticName);	
		mongoList = new ArrayList<>();
		artistList = new ArrayList<>();
	}
	
	@Test
	void ArtistReadMongoRepositoryAdapter_findAll_ReturnListArtist() {
		
		Mockito.when(artistReadMongoRepository.findAll()).thenReturn(mongoList);
		Mockito.when(artistMapper.fromListMongoToListDomain(Mockito.anyList())).thenReturn(artistList);
		
		List<Artist> list = artistReadRepositoryPort.findAll();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);	
	}

	@Test
	void ArtistReadMongoRepositoryAdapter_findById_ReturnOptArtist() {
		
		Mockito.when(artistReadMongoRepository.findFirstByArtistId(Mockito.anyLong())).thenReturn(Optional.of(artistMongoTest));
		Mockito.when(artistMapper.fromMongoToDomain(Mockito.any(ArtistMongoEntity.class))).thenReturn(artistTest);

		Optional<Artist> optArtist = artistReadRepositoryPort.findById(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}

}
