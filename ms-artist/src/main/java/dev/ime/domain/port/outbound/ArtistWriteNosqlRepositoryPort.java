package dev.ime.domain.port.outbound;


import dev.ime.domain.model.Artist;

public interface ArtistWriteNosqlRepositoryPort {

	void save(Artist artist);
	void update(Artist artist);
	void deleteById(Long id);
	
}
