package dev.ime.application.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaReadRepositoryPort;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@Component
public class GetByIdQueryHandler implements QueryHandler<Optional<Media>>{

	private final MediaReadRepositoryPort mediaReadRepositoryPort;
	
	public GetByIdQueryHandler(MediaReadRepositoryPort mediaReadRepositoryPort) {
		super();
		this.mediaReadRepositoryPort = mediaReadRepositoryPort;
	}

	@Override
	public Optional<Media> handle(Query query) {
		
		if ( query instanceof GetByIdQuery getByIdQuery) {
			
			return mediaReadRepositoryPort.findById(getByIdQuery.id());
					
		}else {
			
			throw new IllegalArgumentException(ApplicationConstant.MSG_ILLEGAL_QUERY);
			
		}	
	}
}
