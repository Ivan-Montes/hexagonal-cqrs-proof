package dev.ime.infrastructure.adapter;

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

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.entity.ArtistJpaEntity;
import dev.ime.infrastructure.repository.ArtistWriteJpaRepository;

@ExtendWith(MockitoExtension.class)
class ArtistWriteJpaRepositoryAdapterTest {

	@Mock
	private ArtistWriteJpaRepository artistWriteJpaRepository;	

	@Mock
	private ArtistMapper artistMapper;

	@InjectMocks
	private ArtistWriteJpaRepositoryAdapter artistWriteRepositoryPort;
	
	private ArtistJpaEntity artistJpaTest;
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
		
		artistJpaTest = new ArtistJpaEntity(id, name, surname, artisticName);
	}
	
	@Test
	void ArtistWriteJpaRepositoryAdapterTest_save_ReturnOptArtist() {
		
		Mockito.when(artistMapper.fromDomainToJpa(Mockito.any(Artist.class))).thenReturn(artistJpaTest);
		Mockito.when(artistWriteJpaRepository.save(Mockito.any(ArtistJpaEntity.class))).thenReturn(artistJpaTest);
		Mockito.when(artistMapper.fromJpaToDomain(Mockito.any(ArtistJpaEntity.class))).thenReturn(artistTest);
		
		Optional<Artist> optArtist = artistWriteRepositoryPort.save(artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}

	@Test
	void ArtistWriteJpaRepositoryAdapterTest_deleteById_ReturnVoid() {
		
		Mockito.doNothing().when(artistWriteJpaRepository).deleteById(Mockito.anyLong());
		
		artistWriteRepositoryPort.deleteById(id);
		
		verify(artistWriteJpaRepository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void ArtistWriteJpaRepositoryAdapterTest_findById_ReturnOptArtist() {
		
		Mockito.when(artistMapper.fromJpaToDomain(Mockito.any(ArtistJpaEntity.class))).thenReturn(artistTest);
		Mockito.when(artistWriteJpaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artistJpaTest));
		
		Optional<Artist> optArtist = artistWriteRepositoryPort.findById(id);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}

}
