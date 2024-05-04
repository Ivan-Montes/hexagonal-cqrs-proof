package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Artist;

public interface ArtistBackupRepositoryPort {

	boolean existById(Long id);
	void save(Artist artist);
	void deleteById(Long id);
}
