package dev.ime.domain.port.outbound;

import java.util.Optional;

import dev.ime.domain.model.Artist;

public interface ArtistWriteRepositoryPort {
	
	Optional<Artist>save(Artist artist);
	void deleteById(Long id);
	Optional<Artist>findById(Long id);
}
