package dev.ime.config;

import org.springframework.stereotype.Component;

import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.entity.ArtistRedisEntity;

@Component
public class ArtistMapper {

	public ArtistMapper() {
		super();
	}
	
	public ArtistRedisEntity fromDomainToRedis(Artist domain) {
		
		ArtistRedisEntity redis = new ArtistRedisEntity();
		redis.setId(domain.getId());
		
		return redis;
	}
	
	public Artist fromDtoToDomain(ArtistDto dto) {
		
		return new Artist.ArtistBuilder()
				.setId(dto.artistId())
				.setName(dto.artistName())
				.setSurname(dto.artistSurname())
				.setArtisticName(dto.artisticName())
				.build();
	}
	
}
