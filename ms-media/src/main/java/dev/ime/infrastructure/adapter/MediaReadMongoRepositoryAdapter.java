package dev.ime.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaReadRepositoryPort;
import dev.ime.infrastructure.repository.MediaReadMongoRepository;

@Repository
public class MediaReadMongoRepositoryAdapter implements MediaReadRepositoryPort{

	private final MediaReadMongoRepository mediaReadMongoRepository;
	private final MediaMapper mediaMapper;
	
	public MediaReadMongoRepositoryAdapter(MediaReadMongoRepository mediaReadMongoRepository, MediaMapper mediaMapper) {
		super();
		this.mediaReadMongoRepository = mediaReadMongoRepository;
		this.mediaMapper = mediaMapper;
	}

	@Override
	public List<Media> findAll() {
		
		return mediaMapper.fromListMongoToListDomain(mediaReadMongoRepository.findAll());
	}

	@Override
	public Optional<Media> findById(Long id) {
		
		return mediaReadMongoRepository.findFirstByMediaId(id)
				.map(mediaMapper::fromMongoToDomain);
	}

}
