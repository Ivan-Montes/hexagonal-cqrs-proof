package dev.ime.domain.port.inbound;

import java.util.Optional;

import dev.ime.domain.model.Media;

public interface MediaCommandServicePort {
	
	Optional<Media>create(Media media);
	Optional<Media>update(Long id, Media media);
	Boolean deleteById(Long id);
	
}
