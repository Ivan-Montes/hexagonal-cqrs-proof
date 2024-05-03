package dev.ime.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.infrastructure.dto.MediaDto;
import dev.ime.infrastructure.entity.MediaJpaEntity;
import dev.ime.infrastructure.entity.MediaMongoEntity;

@Component
public class MediaMapper {

	public MediaMapper() {
		super();
	}

	public MediaDto fromDomainToDto(Media domain) {
		
		return new MediaDto(
				domain.getId(),
				domain.getName(),
				domain.getGenre().name(),
				domain.getMediaClass().name(),
				domain.getArtistId()
				);
	}
	
	public List<MediaDto> fromListDomainToListDto(List<Media> listDomain) {

		if ( listDomain == null ) {
			return new ArrayList<>();
		}
		
		return listDomain.stream()
				.map(this::fromDomainToDto)
				.toList();
	}

	public Media fromMongoToDomain(MediaMongoEntity mongo) {
		
		return new Media.MediaBuilder()
				.setId(mongo.getMediaId())
				.setName(mongo.getName())
				.setGenre(Genre.findByName(mongo.getGenre()))
				.setMediaClass(MediaClass.findByName(mongo.getMediaClass()))
				.setArtistId(mongo.getArtistId())
				.build();
	}
	
	public List<Media> fromListMongoToListDomain(List<MediaMongoEntity> listMongo) {

		if ( listMongo == null ) {
			return new ArrayList<>();
		}
		
		return listMongo.stream()
			.map(this::fromMongoToDomain)
			.toList();	
	}

	public Media fromJpaToDomain(MediaJpaEntity jpa) {
		
		return new Media.MediaBuilder()
				.setId(jpa.getId())
				.setName(jpa.getName())
				.setGenre(jpa.getGenre())
				.setMediaClass(jpa.getMediaClass())
				.setArtistId(jpa.getArtistId())
				.build();
	}

	public List<Media> fromListJpaToListDomain(List<MediaJpaEntity> listJpa) {
		
		if ( listJpa == null ) {
			return new ArrayList<>();
		}
		
		return listJpa.stream()
			.map(this::fromJpaToDomain)
			.toList();	
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
	
	public MediaJpaEntity fromDomainToJpa(Media domain) {
		
		MediaJpaEntity jpa = new MediaJpaEntity();
		jpa.setId(domain.getId());
		jpa.setName(domain.getName());
		jpa.setGenre(domain.getGenre());
		jpa.setMediaClass(domain.getMediaClass());
		jpa.setArtistId(domain.getArtistId());
		
		return jpa;
	}
	
	public MediaMongoEntity fromDomainToMongo(Media domain) {
		
		MediaMongoEntity mongo = new MediaMongoEntity();
		mongo.setMediaId(domain.getId());
		mongo.setName(domain.getName());
		mongo.setGenre(domain.getGenre().name());
		mongo.setMediaClass(domain.getMediaClass().name());
		mongo.setArtistId(domain.getArtistId());
		
		return mongo;		
	}
	
}
