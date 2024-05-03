package dev.ime.domain.port.inbound;

import java.util.List;
import java.util.Optional;

import dev.ime.domain.model.Media;

public interface MediaQueryServicePort {
	
	List<Media>getAll();
	Optional<Media>getById(Long id);
	
}
