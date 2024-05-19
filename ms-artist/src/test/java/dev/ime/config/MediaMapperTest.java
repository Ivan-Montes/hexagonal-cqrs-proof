package dev.ime.config;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.infrastructure.dto.MediaDto;
import dev.ime.infrastructure.entity.MediaRedisEntity;

@ExtendWith(MockitoExtension.class)
class MediaMapperTest {

	@InjectMocks
	private MediaMapper mediaMapper;

	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;
	private MediaDto mediaDtoTest;	
	private MediaRedisEntity mediaRedisTest;
	
	@BeforeEach
	private void createObjects() {

		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaDtoTest = new MediaDto(id, name, genre.name(),mediaClass.name(),artistId);
		
		mediaRedisTest = new MediaRedisEntity(id, artistId);
	}
	
	@Test
	void MediaMapper_fromDomainToRedis_ReturnMediaRedisEntity() {
		
		MediaRedisEntity redis = mediaMapper.fromDomainToRedis(mediaTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(redis).isNotNull(),
				()-> Assertions.assertThat(redis.getId()).isEqualTo(mediaTest.getId()),
				()-> Assertions.assertThat(redis.getArtistId()).isEqualTo(mediaTest.getArtistId())
				);
	}	

	@Test
	void MediaMapper_fromDtoToDomain_ReturnMedia() {
		
		Media media = mediaMapper.fromDtoToDomain(mediaDtoTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(media).isNotNull(),
				()-> Assertions.assertThat(media.getId()).isEqualTo(mediaDtoTest.mediaId()),
				()-> Assertions.assertThat(media.getName()).isEqualTo(mediaDtoTest.mediaName()),
				()-> Assertions.assertThat(media.getGenre().name()).isEqualTo(mediaDtoTest.mediaGenre()),
				()-> Assertions.assertThat(media.getMediaClass().name()).isEqualTo(mediaDtoTest.mediaClass()),
				()-> Assertions.assertThat(media.getArtistId()).isEqualTo(mediaDtoTest.artistId())
				);
	}

	@Test
	void MediaMapper_fromRedisToDomain_ReturnMedia() {
		
		Media media = mediaMapper.fromRedisToDomain(mediaRedisTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(media).isNotNull(),
				()-> Assertions.assertThat(media.getId()).isEqualTo(mediaRedisTest.getId()),
				()-> Assertions.assertThat(media.getArtistId()).isEqualTo(mediaRedisTest.getArtistId())
				);
	}
	
}
