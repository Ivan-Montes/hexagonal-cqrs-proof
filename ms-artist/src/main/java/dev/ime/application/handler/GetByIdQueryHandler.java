package dev.ime.application.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistReadRepositoryPort;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@Component
public class GetByIdQueryHandler implements QueryHandler<Optional<Artist>>{

	private final ArtistReadRepositoryPort artistReadRepositoryPort;

	public GetByIdQueryHandler(ArtistReadRepositoryPort artistReadRepositoryPort) {
		super();
		this.artistReadRepositoryPort = artistReadRepositoryPort;
	}

	@Override
	public Optional<Artist> handle(Query query) {
		
		if ( query instanceof GetByIdQuery getByIdQuery) {
			
		return artistReadRepositoryPort.findById(getByIdQuery.id());
		
		} else {
			
			throw new IllegalArgumentException("Command not supported");
			
			}	
	}

}
