package dev.ime.infrastructure.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import dev.ime.infrastructure.entity.ArtistMongoEntity;

public interface ArtistWriteMongoRepository  extends MongoRepository<ArtistMongoEntity, ObjectId>{
	
	Optional<ArtistMongoEntity> findFirstByArtistId(Long id);	
	
}
