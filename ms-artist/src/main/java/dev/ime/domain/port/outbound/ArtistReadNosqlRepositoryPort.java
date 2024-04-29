package dev.ime.domain.port.outbound;

import java.util.List;
import java.util.Optional;

import dev.ime.domain.model.Artist;

public interface ArtistReadNosqlRepositoryPort {

	List<Artist>findAll();
	Optional<Artist>findById(Long id);
}
