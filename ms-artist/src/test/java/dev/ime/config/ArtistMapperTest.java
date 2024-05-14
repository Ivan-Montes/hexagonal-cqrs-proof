package dev.ime.config;


import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.entity.*;

@ExtendWith(MockitoExtension.class)
class ArtistMapperTest {

	@InjectMocks
	private ArtistMapper artistMapper;	

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistDto artistDtoTest;
	private ArtistJpaEntity artistJpaTest;
	private ArtistMongoEntity artistMongoTest;
	private List<ArtistMongoEntity> mongoList;
	private List<ArtistJpaEntity> jpaList;
	private List<Artist> artistList;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();	

		artistDtoTest = new ArtistDto(id, name, surname, artisticName);
		artistJpaTest = new ArtistJpaEntity(id, name, surname, artisticName);
		artistMongoTest = new ArtistMongoEntity(null, id, name, surname, artisticName);

		mongoList = new ArrayList<>();
		jpaList = new ArrayList<>();
		artistList = new ArrayList<>();
	}
	
	@Test
	void ArtistMapper_fromDomainToDto_ReturnArtistDto() {
		
		ArtistDto dto = artistMapper.fromDomainToDto(artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(dto).isNotNull(),
				()-> Assertions.assertThat(dto.artistId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(dto.artistName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(dto.artistSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(dto.artisticName()).isEqualTo(artistTest.getArtisticName())
				);	
	}

	@Test
	void ArtistMapper_fromListDomainToListDto_ReturnListArtist() {
		
		artistList.add(artistTest);
		
		List<ArtistDto> list = artistMapper.fromListDomainToListDto(artistList);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isNotEmpty(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void ArtistMapper_fromListDomainToListDto_ReturnListEmpty() {
		
		List<ArtistDto> list = artistMapper.fromListDomainToListDto(null);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);
	}

	@Test
	void ArtistMapper_fromDomainToJpa_ReturnArtistJpaEntity() {
		
		ArtistJpaEntity jpa = artistMapper.fromDomainToJpa(artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(jpa).isNotNull(),
				()-> Assertions.assertThat(jpa.getId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(jpa.getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(jpa.getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(jpa.getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);		
	}

	@Test
	void ArtistMapper_fromJpaToDomain_ReturnArtist() {
		
		Artist artist = artistMapper.fromJpaToDomain(artistJpaTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(artist).isNotNull(),
				()-> Assertions.assertThat(artist.getId()).isEqualTo(artistJpaTest.getId()),
				()-> Assertions.assertThat(artist.getName()).isEqualTo(artistJpaTest.getName()),
				()-> Assertions.assertThat(artist.getSurname()).isEqualTo(artistJpaTest.getSurname()),
				()-> Assertions.assertThat(artist.getArtisticName()).isEqualTo(artistJpaTest.getArtisticName())
				);	
	}

	@Test
	void ArtistMapper_fromListJpaToListDomain_ReturnListArtist() {
		
		jpaList.add(artistJpaTest);
		
		List<Artist> list = artistMapper.fromListJpaToListDomain(jpaList);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isNotEmpty(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void ArtistMapper_fromListJpaToListDomain_ReturnListEmpty() {		
		
		List<Artist> list = artistMapper.fromListJpaToListDomain(null);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);
	}
	

	@Test
	void ArtistMapper_fromMongoToDomain_ReturnArtist() {
		
		Artist artist = artistMapper.fromMongoToDomain(artistMongoTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(artist).isNotNull(),
				()-> Assertions.assertThat(artist.getId()).isEqualTo(artistMongoTest.getArtistId()),
				()-> Assertions.assertThat(artist.getName()).isEqualTo(artistMongoTest.getName()),
				()-> Assertions.assertThat(artist.getSurname()).isEqualTo(artistMongoTest.getSurname()),
				()-> Assertions.assertThat(artist.getArtisticName()).isEqualTo(artistMongoTest.getArtisticName())
				);		
	}

	@Test
	void ArtistMapper_fromListMongoToListDomain_ReturnListArtist() {
	
		mongoList.add(artistMongoTest);
		
		List<Artist> list = artistMapper.fromListMongoToListDomain(mongoList);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isNotEmpty(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void ArtistMapper_fromListMongoToListDomain_ReturnListEmpty() {
			
		List<Artist> list = artistMapper.fromListMongoToListDomain(null);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);
	}

	@Test
	void ArtistMapper_fromDtoToDomain_ReturnArtist() {
		
		Artist artist = artistMapper.fromDtoToDomain(artistDtoTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(artist).isNotNull(),
				()-> Assertions.assertThat(artist.getId()).isEqualTo(artistDtoTest.artistId()),
				()-> Assertions.assertThat(artist.getName()).isEqualTo(artistDtoTest.artistName()),
				()-> Assertions.assertThat(artist.getSurname()).isEqualTo(artistDtoTest.artistSurname()),
				()-> Assertions.assertThat(artist.getArtisticName()).isEqualTo(artistDtoTest.artisticName())
				);	
	}

	@Test
	void ArtistMapper_fromDomainToMongo_ReturnArtistMongoEntity() {
		
		ArtistMongoEntity mongo = artistMapper.fromDomainToMongo(artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(mongo).isNotNull(),
				()-> Assertions.assertThat(mongo.getArtistId()).isEqualTo(artistTest.getId()),
				()-> Assertions.assertThat(mongo.getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(mongo.getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(mongo.getArtisticName()).isEqualTo(artistTest.getArtisticName())
				);		
		}	
	
}
