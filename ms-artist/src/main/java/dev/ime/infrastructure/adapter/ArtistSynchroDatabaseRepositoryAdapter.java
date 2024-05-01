package dev.ime.infrastructure.adapter;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.ResourceNotFoundException;
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
		logger.info("### [ArtistSynchroDatabaseRepositoryAdapter] -> [save] -> [Artist]");
	}

	@Override
	public void update(Artist artist) {

		ArtistMongoEntity mongoEntity = artistWriteMongoRepository.findFirstByArtistId(artist.getId()).orElseThrow(() -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(artist.getId()))));
		mongoEntity.setName(artist.getName());
		mongoEntity.setSurname(artist.getSurname());
		mongoEntity.setArtisticName(artist.getArtisticName());
		
		artistWriteMongoRepository.save(mongoEntity);
		logger.info("### [ArtistSynchroDatabaseRepositoryAdapter] -> [update] -> [Artist]");
	}

	@Override
	public void deleteById(Long id) {
		
		ArtistMongoEntity mongoEntity = artistWriteMongoRepository.findFirstByArtistId(id).orElseThrow(() -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID,String.valueOf(id))));

		artistWriteMongoRepository.delete(mongoEntity);
		logger.info("### [ArtistSynchroDatabaseRepositoryAdapter] -> [deleteById] -> [Artist]");
	}

}
