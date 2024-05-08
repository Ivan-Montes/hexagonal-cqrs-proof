package dev.ime.domain.port.outbound;

import java.util.Optional;

import dev.ime.domain.model.Media;

public interface MediaBackupRepositoryPort {

	boolean existArtistId(Long artistId);
	void save(Media media);
	void deleteById(Long id);
	Optional<Media> findById(Long id);
}
