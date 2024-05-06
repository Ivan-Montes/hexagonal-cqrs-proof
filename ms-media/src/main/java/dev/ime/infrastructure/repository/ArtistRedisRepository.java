package dev.ime.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

import dev.ime.infrastructure.entity.ArtistRedisEntity;

public interface ArtistRedisRepository  extends CrudRepository<ArtistRedisEntity, Long>{

}
