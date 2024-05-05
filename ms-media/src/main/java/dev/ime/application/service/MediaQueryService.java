package dev.ime.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.dispatch.QueryDispatcher;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.application.usecase.GetAllQuery;
import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.inbound.MediaQueryServicePort;
import dev.ime.domain.query.QueryHandler;

@Service
public class MediaQueryService implements MediaQueryServicePort{

	private final QueryDispatcher queryDispatcher; 	
	
	public MediaQueryService(QueryDispatcher queryDispatcher) {
		super();
		this.queryDispatcher = queryDispatcher;
	}

	@Override
	public List<Media> getAll() {
		
		GetAllQuery getAllQuery = new GetAllQuery();
		QueryHandler<List<Media>>queryHandler = queryDispatcher.getQueryHandler(getAllQuery);
		
		return queryHandler.handle(getAllQuery);
		
	}

	@Override
	public Optional<Media> getById(Long id) {
		
		GetByIdQuery getByIdQuery = new GetByIdQuery(id);
		QueryHandler<Optional<Media>> queryHandler = queryDispatcher.getQueryHandler(getByIdQuery);
		
		return Optional.ofNullable(queryHandler.handle(getByIdQuery).orElseThrow(() -> new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID,String.valueOf(id)))));

	}

}
