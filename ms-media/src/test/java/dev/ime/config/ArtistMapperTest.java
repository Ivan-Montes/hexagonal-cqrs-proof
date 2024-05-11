package dev.ime.config;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.entity.ArtistRedisEntity;

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
	
	@BeforeEach
	private void createObjects() {
		
		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();		
		
		artistDtoTest = new ArtistDto(id, name, surname, artisticName);
	}

	@Test
	void ArtistMapper_fromDomainToRedis_ReturnArtistRedisEntity() {
		
		ArtistRedisEntity redis = artistMapper.fromDomainToRedis(artistTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(redis).isNotNull(),
				()-> Assertions.assertThat(redis.getId()).isEqualTo(artistTest.getId())
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
	

}
