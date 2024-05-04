package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Media;

public interface MediaSynchroDatabaseRepositoryPort {

	void save(Media media);
	void update(Media media);
	void deleteById(Long id);
}
