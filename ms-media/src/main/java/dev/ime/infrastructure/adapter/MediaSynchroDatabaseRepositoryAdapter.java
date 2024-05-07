package dev.ime.infrastructure.adapter;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaSynchroDatabaseRepositoryPort;
import dev.ime.infrastructure.entity.MediaMongoEntity;
import dev.ime.infrastructure.repository.MediaWriteMongoRepository;

@Repository
public class MediaSynchroDatabaseRepositoryAdapter implements MediaSynchroDatabaseRepositoryPort {
	
	private final MediaWriteMongoRepository mediaWriteMongoRepository;
	private final MediaMapper mediaMapper;
	private final Logger logger;
	
	public MediaSynchroDatabaseRepositoryAdapter(MediaWriteMongoRepository mediaWriteMongoRepository,
			MediaMapper mediaMapper, Logger logger) {
		super();
		this.mediaWriteMongoRepository = mediaWriteMongoRepository;
		this.mediaMapper = mediaMapper;
		this.logger = logger;
	}

	@Override
	public void save(Media media) {
		
		mediaWriteMongoRepository.save(mediaMapper.fromDomainToMongo(media));
		logger.info("### [MediaSynchroDatabaseRepositoryAdapter] -> [save] -> [Media]");
	}

	@Override
	public void update(Media media) {
		
		MediaMongoEntity mongoEntity = mediaWriteMongoRepository.findFirstByMediaId(media.getId()).orElseThrow(() -> new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID, String.valueOf(media.getId()))));
		mongoEntity.setName(media.getName());
		mongoEntity.setGenre(media.getGenre().name());
		mongoEntity.setMediaClass(media.getMediaClass().name());
		mongoEntity.setArtistId(media.getArtistId());
		
		mediaWriteMongoRepository.save(mongoEntity);
		logger.info("### [MediaSynchroDatabaseRepositoryAdapter] -> [update] -> [Media]");
	}

	@Override
	public void deleteById(Long id) {
		
		MediaMongoEntity mongoEntity = mediaWriteMongoRepository.findFirstByMediaId(id).orElseThrow(() -> new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID,String.valueOf(id))));

		mediaWriteMongoRepository.delete(mongoEntity);
		logger.info("### [MediaSynchroDatabaseRepositoryAdapter] -> [deleteById] -> [Media]");		
	}

}
