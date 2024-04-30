package dev.ime.application.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.ime.application.usecase.GetAllQuery;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistReadNosqlRepositoryPort;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@Component
public class GetAllQueryHandler  implements QueryHandler<List<Artist>>{

	private final ArtistReadNosqlRepositoryPort artistReadRepositoryPort;	
	
	public GetAllQueryHandler(ArtistReadNosqlRepositoryPort artistReadRepositoryPort) {
		super();
		this.artistReadRepositoryPort = artistReadRepositoryPort;
	}

	@Override
	public List<Artist> handle(Query query) {
		
		if (query instanceof GetAllQuery) {
			
			return artistReadRepositoryPort.findAll();
			
		}else {
			
		throw new IllegalArgumentException("Command not supported");
		
		}
	}

}
