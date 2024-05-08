package dev.ime.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaBackupRepositoryPort;
import dev.ime.domain.port.outbound.MediaBackupServicePort;

@Service
public class MediaBackupService implements MediaBackupServicePort {

	private final MediaBackupRepositoryPort mediaBackupRepositoryPort;
	
	public MediaBackupService(MediaBackupRepositoryPort mediaBackupRepositoryPort) {
		super();
		this.mediaBackupRepositoryPort = mediaBackupRepositoryPort;
	}

	@Override
	public void save(Media media) {
		
		mediaBackupRepositoryPort.save(media);
	}

	@Override
	public void deleteById(Long id) {
		
		mediaBackupRepositoryPort.deleteById(id);
	}

	@Override
	public void update(Media media) {
		
		Optional<Media> optMediaFound = mediaBackupRepositoryPort.findById(media.getId());
		
		if ( optMediaFound.isPresent() ) {
			
			Media mediaFound = optMediaFound.get();
			mediaFound.setArtistId(media.getArtistId());
			
			mediaBackupRepositoryPort.save(mediaFound);
		}
		
	}

	@Override
	public boolean existArtistId(Long artistId) {
		
		return mediaBackupRepositoryPort.existArtistId(artistId);
	}

}
