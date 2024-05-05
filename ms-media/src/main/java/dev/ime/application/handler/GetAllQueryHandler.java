package dev.ime.application.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.usecase.GetAllQuery;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaReadRepositoryPort;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@Component
public class GetAllQueryHandler implements QueryHandler<List<Media>>{

	private final MediaReadRepositoryPort mediaReadRepositoryPort;	
	
	public GetAllQueryHandler(MediaReadRepositoryPort mediaReadRepositoryPort) {
		super();
		this.mediaReadRepositoryPort = mediaReadRepositoryPort;
	}
	
	@Override
	public List<Media> handle(Query query) {
		
		if ( query instanceof GetAllQuery ) {
			
			return mediaReadRepositoryPort.findAll();
			
		}else {
			
			throw new IllegalArgumentException(ApplicationConstant.MSG_ILLEGAL_QUERY);
		
		}
	}
}
