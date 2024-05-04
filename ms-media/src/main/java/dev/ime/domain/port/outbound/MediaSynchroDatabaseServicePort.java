package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Media;

public interface MediaSynchroDatabaseServicePort {

	void syncCreate(Media media);
	void syncUpdate(Media media);
	void syncDelete(Long id);
}
