package dev.ime.infrastructure.adapter;

import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;
import dev.ime.infrastructure.repository.ArtistRedisRepository;

@Repository
public class ArtistBackupRepositoryAdapter implements ArtistBackupRepositoryPort{

	private final ArtistRedisRepository artistRedisRepository;
	private final ArtistMapper artistMapper;
	private final Logger logger;
	
	public ArtistBackupRepositoryAdapter(ArtistRedisRepository artistRedisRepository, ArtistMapper artistMapper, Logger logger) {
		super();
		this.artistRedisRepository = artistRedisRepository;
		this.artistMapper = artistMapper;
		this.logger = logger;
	}

	@Override
	public void save(Artist artist) {
		
		artistRedisRepository.save(artistMapper.fromDomainToRedis(artist));
		logger.info("### [ArtistBackupRepositoryAdapter] -> [save] -> [Artist]");
	}

	@Override
	public void deleteById(Long id) {
		
		artistRedisRepository.deleteById(id);		
		logger.info("### [ArtistBackupRepositoryAdapter] -> [deleteById] -> [Artist]");		
	}

	@Override
	public boolean existById(Long id) {
		
		logger.info("### [ArtistBackupRepositoryAdapter] -> [existById] -> [Artist]");	
		return artistRedisRepository.existsById(id);
	}

}
