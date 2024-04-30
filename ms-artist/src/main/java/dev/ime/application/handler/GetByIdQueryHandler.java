package dev.ime.application.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistReadNosqlRepositoryPort;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@Component
public class GetByIdQueryHandler implements QueryHandler<Optional<Artist>>{

	private final ArtistReadNosqlRepositoryPort artistReadNosqlRepositoryPort;

	public GetByIdQueryHandler(ArtistReadNosqlRepositoryPort artistReadNosqlRepositoryPort) {
		super();
		this.artistReadNosqlRepositoryPort = artistReadNosqlRepositoryPort;
	}

	@Override
	public Optional<Artist> handle(Query query) {
		
		if ( query instanceof GetByIdQuery getByIdQuery) {
			
		return artistReadNosqlRepositoryPort.findById(getByIdQuery.id());
		
		} else {
			
			throw new IllegalArgumentException("Command not supported");
			
			}	
	}

}
