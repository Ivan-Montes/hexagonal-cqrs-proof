package dev.ime.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import dev.ime.infrastructure.entity.MediaMongoEntity;

public interface MediaReadMongoRepository extends MongoRepository<MediaMongoEntity, ObjectId>{
	
	Optional<MediaMongoEntity> findFirstByMediaId(Long id);
	List<MediaMongoEntity> findByArtistId(Long id);	
	
}
