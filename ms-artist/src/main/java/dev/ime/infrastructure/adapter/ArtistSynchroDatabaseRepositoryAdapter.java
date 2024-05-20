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
		logger.log(Level.INFO, "### [ArtistSynchroDatabaseRepositoryAdapter] -> [save] -> [ {0} ]", artist);
	}

	@Override
	public void update(Artist artist) {

		Long id = artist.getId();
		Optional<ArtistMongoEntity> optMongoEntity = artistWriteMongoRepository.findFirstByArtistId(id);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logger.log(Level.INFO, "### [ArtistSynchroDatabaseRepositoryAdapter] -> [update] -> [ResourceNotFoundException] -> [ {0} ]", artist);
			return;
			
		}
		
		ArtistMongoEntity mongoEntity = optMongoEntity.get();
		mongoEntity.setName(artist.getName());
		mongoEntity.setSurname(artist.getSurname());
		mongoEntity.setArtisticName(artist.getArtisticName());
		
		artistWriteMongoRepository.save(mongoEntity);
		
		logger.log(Level.INFO, "### [ArtistSynchroDatabaseRepositoryAdapter] -> [update] -> [ {0} ]", artist);
	}

	@Override
	public void deleteById(Long id) {		

		Optional<ArtistMongoEntity> optMongoEntity = artistWriteMongoRepository.findFirstByArtistId(id);
		
		if ( optMongoEntity.isEmpty() ) {
			
			logger.log(Level.INFO, "### [ArtistSynchroDatabaseRepositoryAdapter] -> [deleteById] -> [ResourceNotFoundException] -> [ {0} ]", ApplicationConstant.ARTISTID + " : " +  id);
			return;
			
		}
		
		ArtistMongoEntity mongoEntity = optMongoEntity.get();
		artistWriteMongoRepository.delete(mongoEntity);
		
		logger.info("### [ArtistSynchroDatabaseRepositoryAdapter] -> [deleteById] -> [Artist]");
	}

}
