package dev.ime.config;

import org.springframework.stereotype.Component;

import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.infrastructure.dto.MediaDto;
import dev.ime.infrastructure.entity.MediaRedisEntity;

@Component
public class MediaMapper {

	public MediaMapper() {
		super();
	}

	public MediaRedisEntity fromDomainToRedis(Media media) {
		
		MediaRedisEntity redis = new MediaRedisEntity();
		redis.setId(media.getId());
		redis.setArtistId(media.getArtistId());
		
		return redis;		
	}
	
	public Media fromDtoToDomain(MediaDto dto) {
		
		return new Media.MediaBuilder()
				.setId(dto.mediaId())
				.setName(dto.mediaName())
				.setGenre(Genre.findByName(dto.mediaGenre()))
				.setMediaClass(MediaClass.findByName(dto.mediaClass()))
				.setArtistId(dto.artistId())
				.build();
	}
	
	public Media fromRedisToDomain(MediaRedisEntity media) {
		
		return new Media.MediaBuilder()
				.setId(media.getId())
				.setArtistId(media.getArtistId())
				.build();
				}
	
}
