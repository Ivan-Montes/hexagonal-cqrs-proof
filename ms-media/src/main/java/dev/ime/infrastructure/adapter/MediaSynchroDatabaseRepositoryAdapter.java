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
		logInfoAction("save", media.toString());
	}

	@Override
	public void update(Media media) {
		
		Long mediaId = media.getId();
		Optional<MediaMongoEntity> optMongoEntity = mediaWriteMongoRepository.findFirstByMediaId(mediaId);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logInfoAction("update] -> [ResourceNotFoundException", ApplicationConstant.MEDIAID + " : "+ mediaId);
			return;
		}
		
		MediaMongoEntity mongoEntity = optMongoEntity.get();
		buildMongoEntity(media, mongoEntity);
		
		mediaWriteMongoRepository.save(mongoEntity);
		
		logInfoAction("update", mongoEntity.toString());
	}

	private void buildMongoEntity(Media media, MediaMongoEntity mongoEntity) {
		mongoEntity.setName(media.getName());
		mongoEntity.setGenre(media.getGenre().name());
		mongoEntity.setMediaClass(media.getMediaClass().name());
		mongoEntity.setArtistId(media.getArtistId());
	}

	@Override
	public void deleteById(Long id) {
		
		Optional<MediaMongoEntity> optMongoEntity = mediaWriteMongoRepository.findFirstByMediaId(id);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logInfoAction("deleteById] -> [ResourceNotFoundException", ApplicationConstant.MEDIAID + " : "+ id);
			return;
		}
		
		MediaMongoEntity mongoEntity = optMongoEntity.get();
				
		mediaWriteMongoRepository.delete(mongoEntity);
		
		logInfoAction("deleteById", ApplicationConstant.MEDIAID + " : " + id);		
	}

	private void logInfoAction(String methodName, String msg) {
		
		String logMessage = String.format("### [%s] -> [%s] -> [ %s ]", this.getClass().getSimpleName(), methodName, msg);
		
		logger.log(Level.INFO, logMessage);
		
	}
		
}
