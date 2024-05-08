package dev.ime.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.ime.infrastructure.entity.MediaRedisEntity;

public interface MediaRedisRepository extends CrudRepository<MediaRedisEntity, Long> {

	List<MediaRedisEntity> findByArtistId(Long artistId);
}
