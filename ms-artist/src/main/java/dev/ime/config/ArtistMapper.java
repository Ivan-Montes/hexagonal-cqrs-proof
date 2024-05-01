package dev.ime.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.entity.ArtistJpaEntity;
import dev.ime.infrastructure.entity.ArtistMongoEntity;

@Component
public class ArtistMapper {

	public ArtistMapper() {
		super();
	}
	
	public ArtistDto fromDomainToDto(Artist domain) {
		
		return new ArtistDto(
				domain.getId(),
				domain.getName(),
				domain.getSurname(),
				domain.getArtisticName()
				);	
	}
	
	public List<ArtistDto> fromListDomainToListDto(List<Artist> listDomain) {
		
		if ( listDomain == null ) {
			return new ArrayList<>();
		}
		
		return listDomain.stream()
				.map(this::fromDomainToDto)
				.toList();
	}

	public ArtistJpaEntity fromDomainToJpa(Artist domain) {
		
		ArtistJpaEntity jpa = new ArtistJpaEntity();
		jpa.setId(domain.getId());
		jpa.setName(domain.getName());
		jpa.setSurname(domain.getSurname());
		jpa.setArtisticName(domain.getArtisticName());
		
		return jpa;
	}

	public Artist fromJpaToDomain(ArtistJpaEntity jpa) {
		
		return new Artist.ArtistBuilder()
				.setId(jpa.getId())
				.setName(jpa.getName())
				.setSurname(jpa.getSurname())
				.setArtisticName(jpa.getArtisticName())
				.build();
	}

	public List<Artist> fromListJpaToListDomain(List<ArtistJpaEntity> listJpa) {
		
		if ( listJpa == null ) {
			return new ArrayList<>();
		}
		
		return listJpa.stream()
			.map(this::fromJpaToDomain)
			.toList();	
	}

	public Artist fromMongoToDomain(ArtistMongoEntity mongo) {
		
		return new Artist.ArtistBuilder()
				.setId(mongo.getArtistId())
				.setName(mongo.getName())
				.setSurname(mongo.getSurname())
				.setArtisticName(mongo.getArtisticName())
				.build();
	}

	public List<Artist> fromListMongoToListDomain(List<ArtistMongoEntity> listMongo) {
		
		if ( listMongo == null ) {
			return new ArrayList<>();
		}
		
		return listMongo.stream()
			.map(this::fromMongoToDomain)
			.toList();	
	}

	public Artist fromDtoToDomain(ArtistDto dto) {
		
		return new Artist.ArtistBuilder()
				.setId(dto.artistId())
				.setName(dto.artistName())
				.setSurname(dto.artistSurname())
				.setArtisticName(dto.artisticName())
				.build();
	}
	
	public ArtistMongoEntity fromDomainToMongo(Artist domain) {
	
		ArtistMongoEntity mongo =  new ArtistMongoEntity();
		mongo.setArtistId(domain.getId());
		mongo.setName(domain.getName());
		mongo.setSurname(domain.getSurname());
		mongo.setArtisticName(domain.getArtisticName());
		
		return mongo;
	}
	
}
