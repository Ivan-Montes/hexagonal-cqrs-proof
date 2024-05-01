package dev.ime.application.service;

import org.springframework.stereotype.Service;

import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseServicePort;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseRepositoryPort;

@Service
public class ArtistSynchroDatabaseService implements ArtistSynchroDatabaseServicePort{
	
	private final ArtistSynchroDatabaseRepositoryPort artistSynchroDatabaseRepositoryPort;

	public ArtistSynchroDatabaseService(ArtistSynchroDatabaseRepositoryPort artistSynchroDatabaseRepositoryPort) {
		super();
		this.artistSynchroDatabaseRepositoryPort = artistSynchroDatabaseRepositoryPort;
	}

	@Override
	public void syncCreate(Artist artist) {
	
		artistSynchroDatabaseRepositoryPort.save(artist);
	}

	@Override
	public void syncUpdate(Artist artist) {		
		
		artistSynchroDatabaseRepositoryPort.update(artist);
	}

	@Override
	public void syncDelete(Long id) {
		
		artistSynchroDatabaseRepositoryPort.deleteById(id);
	}
	
}
