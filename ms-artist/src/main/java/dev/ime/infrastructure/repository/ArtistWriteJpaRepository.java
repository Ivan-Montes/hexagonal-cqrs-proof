package dev.ime.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ime.infrastructure.entity.ArtistJpaEntity;

public interface ArtistWriteJpaRepository  extends JpaRepository<ArtistJpaEntity, Long>{

}
