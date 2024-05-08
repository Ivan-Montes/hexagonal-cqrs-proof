package dev.ime.infrastructure.adapter;

import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaBackupRepositoryPort;
import dev.ime.infrastructure.repository.MediaRedisRepository;

@Repository
public class MediaBackupRepositoryAdapter implements MediaBackupRepositoryPort{

	private final MediaRedisRepository mediaRedisRepository;
	private final MediaMapper mediaMapper;
	private final Logger logger;
	
	public MediaBackupRepositoryAdapter(MediaRedisRepository mediaRedisRepository, MediaMapper mediaMapper,
			Logger logger) {
		super();
		this.mediaRedisRepository = mediaRedisRepository;
		this.mediaMapper = mediaMapper;
		this.logger = logger;
	}

	@Override
	public void save(Media media) {
		
		mediaRedisRepository.save(mediaMapper.fromDomainToRedis(media));
		logger.info("### [MediaBackupRepositoryAdapter] -> [save] -> [Media]");		
	}

	@Override
	public void deleteById(Long id) {
		
		mediaRedisRepository.deleteById(id);
		logger.info("### [MediaBackupRepositoryAdapter] -> [deleteById] -> [Media]");	
	}
	
	@Override
	public boolean existArtistId(Long artistId) {
		
		logger.info("### [MediaBackupRepositoryAdapter] -> [existArtistId] -> [Media]");
		return !mediaRedisRepository.findByArtistId(artistId).isEmpty();
	}

	@Override
	public Optional<Media> findById(Long id) {
		
		logger.info("### [MediaBackupRepositoryAdapter] -> [findById] -> [Media]");	
		return mediaRedisRepository.findById(id).map(mediaMapper::fromRedisToDomain);
	}
}
