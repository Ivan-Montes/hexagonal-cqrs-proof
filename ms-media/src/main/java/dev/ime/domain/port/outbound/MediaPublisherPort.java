package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Media;

public interface MediaPublisherPort {

	void publishInsertEvent(Media media);
	void publishUpdateEvent(Media media);
	void publishDeleteEvent(Long id);
}
