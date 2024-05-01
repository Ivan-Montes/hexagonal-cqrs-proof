package dev.ime.domain.port.outbound;


import dev.ime.domain.model.Artist;

public interface ArtistSynchroDatabaseRepositoryPort {

	void save(Artist artist);
	void update(Artist artist);
	void deleteById(Long id);
	
}
