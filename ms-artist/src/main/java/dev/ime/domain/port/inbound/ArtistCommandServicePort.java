package dev.ime.domain.port.inbound;

import java.util.Optional;

import dev.ime.domain.model.Artist;

public interface ArtistCommandServicePort {

	Optional<Artist>create(Artist artist);
	Optional<Artist>update(Long id, Artist artist);
	Boolean deleteById(Long id);
	
}
