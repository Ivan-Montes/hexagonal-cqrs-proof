package dev.ime.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;
import dev.ime.infrastructure.repository.MediaWriteJpaRepository;

@Repository
public class MediaWriteJpaRepositoryAdapter implements MediaWriteRepositoryPort{

	private final MediaWriteJpaRepository mediaWriteJpaRepository;
	private final MediaMapper mediaMapper;
	
	public MediaWriteJpaRepositoryAdapter(MediaWriteJpaRepository mediaWriteJpaRepository, MediaMapper mediaMapper) {
		super();
		this.mediaWriteJpaRepository = mediaWriteJpaRepository;
		this.mediaMapper = mediaMapper;
	}

	@Override
	public Optional<Media> save(Media media) {
		
		return Optional.ofNullable(
				mediaMapper.fromJpaToDomain(
						mediaWriteJpaRepository.save(
								mediaMapper.fromDomainToJpa(media))));
	}

	@Override
	public void deleteById(Long id) {
		
		mediaWriteJpaRepository.deleteById(id);
		
	}

	@Override
	public Optional<Media> findById(Long id) {
		
		return mediaWriteJpaRepository.findById(id)
				.map(mediaMapper::fromJpaToDomain);
	}

}
