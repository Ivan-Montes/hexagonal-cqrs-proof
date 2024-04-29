package dev.ime.domain.port.inbound;

import java.util.List;
import java.util.Optional;

import dev.ime.domain.model.Artist;

public interface ArtistQueryServicePort {
	
	List<Artist>getAll();
	Optional<Artist>getById(Long id);
	
}
