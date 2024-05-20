package dev.ime.infrastructure.adapter;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import dev.ime.application.config.ApplicationConstant;
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
		
		Long mediaId = media.getId();
		Optional<MediaMongoEntity> optMongoEntity = mediaWriteMongoRepository.findFirstByMediaId(mediaId);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logger.log(Level.INFO, "### [MediaSynchroDatabaseRepositoryAdapter] -> [update] -> [ResourceNotFoundException] -> [ " + ApplicationConstant.MEDIAID + " : {0} ]", mediaId);
			return;
		}
		
		MediaMongoEntity mongoEntity = optMongoEntity.get();
		mongoEntity.setName(media.getName());
		mongoEntity.setGenre(media.getGenre().name());
		mongoEntity.setMediaClass(media.getMediaClass().name());
		mongoEntity.setArtistId(media.getArtistId());
		
		mediaWriteMongoRepository.save(mongoEntity);
		
		logger.log(Level.INFO, "### [MediaSynchroDatabaseRepositoryAdapter] -> [update] -> [ {0} ]", mongoEntity);
	}

	@Override
	public void deleteById(Long id) {
		
		Optional<MediaMongoEntity> optMongoEntity = mediaWriteMongoRepository.findFirstByMediaId(id);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logger.log(Level.INFO, "### [MediaSynchroDatabaseRepositoryAdapter] -> [deleteById] -> [ResourceNotFoundException] -> [ " + ApplicationConstant.MEDIAID + " : {0} ]", id);
			return;
		}
		
		MediaMongoEntity mongoEntity = optMongoEntity.get();
				
		mediaWriteMongoRepository.delete(mongoEntity);
		
		logger.log(Level.INFO, "### [MediaSynchroDatabaseRepositoryAdapter] -> [deleteById] -> [ {0} ]", ApplicationConstant.MEDIAID + " : " + id);		
	}

}
