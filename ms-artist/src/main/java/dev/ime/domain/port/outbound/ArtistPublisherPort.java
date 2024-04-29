package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Artist;

public interface ArtistPublisherPort {

	void publishInsertEvent(Artist artist);
	void publishUpdateEvent(Artist artist);
	void publishDeleteEvent(Long id);
	
}
