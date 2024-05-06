package dev.ime.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ime.infrastructure.entity.MediaJpaEntity;

public interface MediaWriteJpaRepository  extends JpaRepository<MediaJpaEntity, Long>{

}
