package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Artist;

public interface ArtistSyncroDataBaseServicePort {

	void syncCreate(Artist artist);
	void syncUpdate(Artist artist);
	void syncDelete(Long id);
	
}
