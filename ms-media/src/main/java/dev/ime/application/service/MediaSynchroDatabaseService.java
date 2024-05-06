package dev.ime.application.service;

import org.springframework.stereotype.Service;

import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaSynchroDatabaseRepositoryPort;
import dev.ime.domain.port.outbound.MediaSynchroDatabaseServicePort;

@Service
public class MediaSynchroDatabaseService implements MediaSynchroDatabaseServicePort{

	private final MediaSynchroDatabaseRepositoryPort mediaSynchroDatabaseRepositoryPort;
	
	public MediaSynchroDatabaseService(MediaSynchroDatabaseRepositoryPort mediaSynchroDatabaseRepositoryPort) {
		super();
		this.mediaSynchroDatabaseRepositoryPort = mediaSynchroDatabaseRepositoryPort;
	}

	@Override
	public void syncCreate(Media media) {
		
		mediaSynchroDatabaseRepositoryPort.save(media);
	}

	@Override
	public void syncUpdate(Media media) {
		
		mediaSynchroDatabaseRepositoryPort.update(media);
	}

	@Override
	public void syncDelete(Long id) {
		
		mediaSynchroDatabaseRepositoryPort.deleteById(id);
	}

}
