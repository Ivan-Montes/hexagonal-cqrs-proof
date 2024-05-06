package dev.ime.application.service;

import org.springframework.stereotype.Service;

import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;
import dev.ime.domain.port.outbound.ArtistBackupServicePort;

@Service
public class ArtistBackupService implements ArtistBackupServicePort{

	private final ArtistBackupRepositoryPort artistBackupRepositoryPort;
	
	public ArtistBackupService(ArtistBackupRepositoryPort artistBackupRepositoryPort) {
		super();
		this.artistBackupRepositoryPort = artistBackupRepositoryPort;
	}

	@Override
	public boolean existById(Long id) {
		
		return artistBackupRepositoryPort.existById(id);
	}

	@Override
	public void save(Artist artist) {
		
		artistBackupRepositoryPort.save(artist);
	}

	@Override
	public void deleteById(Long id) {
		
		artistBackupRepositoryPort.deleteById(id);
	}

}
