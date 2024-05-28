package dev.ime.infrastructure.adapter;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseRepositoryPort;
import dev.ime.infrastructure.entity.ArtistMongoEntity;
import dev.ime.infrastructure.repository.ArtistWriteMongoRepository;

@Repository
public class ArtistSynchroDatabaseRepositoryAdapter implements ArtistSynchroDatabaseRepositoryPort{
	
	private final ArtistWriteMongoRepository artistWriteMongoRepository;
	private final ArtistMapper artistMapper;
	private final Logger logger;
	
	public ArtistSynchroDatabaseRepositoryAdapter(ArtistWriteMongoRepository artistWriteMongoRepository,
			ArtistMapper artistMapper, Logger logger) {
		super();
		this.artistWriteMongoRepository = artistWriteMongoRepository;
		this.artistMapper = artistMapper;
		this.logger = logger;
	}

	@Override
	public void save(Artist artist) {
		
		artistWriteMongoRepository.save(artistMapper.fromDomainToMongo(artist));		
		logInfoAction("save", artist.toString());
	}

	@Override
	public void update(Artist artist) {

		Long id = artist.getId();
		Optional<ArtistMongoEntity> optMongoEntity = artistWriteMongoRepository.findFirstByArtistId(id);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logInfoAction("update] -> [ResourceNotFoundException", ApplicationConstant.ARTISTID + " : " +  id);
			return;
			
		}
		
		ArtistMongoEntity mongoEntity = optMongoEntity.get();
		buildMongoEntity(artist, mongoEntity);
		
		artistWriteMongoRepository.save(mongoEntity);
		
		logInfoAction("update", artist.toString());
	}

	private void buildMongoEntity(Artist artist, ArtistMongoEntity mongoEntity) {
		mongoEntity.setName(artist.getName());
		mongoEntity.setSurname(artist.getSurname());
		mongoEntity.setArtisticName(artist.getArtisticName());
	}

	@Override
	public void deleteById(Long id) {		

		Optional<ArtistMongoEntity> optMongoEntity = artistWriteMongoRepository.findFirstByArtistId(id);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logInfoAction("deleteById] -> [ResourceNotFoundException", ApplicationConstant.ARTISTID + " : " +  id);
			return;
			
		}
		
		ArtistMongoEntity mongoEntity = optMongoEntity.get();
		artistWriteMongoRepository.delete(mongoEntity);
		
		logInfoAction("deleteById", ApplicationConstant.ARTISTID + " : " +  id);
	}

	private void logInfoAction(String methodName, String msg) {
		
		String logMessage = String.format("### [%s] -> [%s] -> [ %s ]", this.getClass().getSimpleName(), methodName, msg);
		
		logger.log(Level.INFO, logMessage);
		
	}
		
}
