package dev.ime.application.dispatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import dev.ime.application.handler.GetAllQueryHandler;
import dev.ime.application.handler.GetByIdQueryHandler;
import dev.ime.application.usecase.GetAllQuery;
import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@Component
public class QueryDispatcher {

	private final Map<Class<? extends Query>, QueryHandler<?>> queryHandlers = new HashMap<>();

	public QueryDispatcher(GetAllQueryHandler getAllQueryHandler, GetByIdQueryHandler getByIdQueryHandler) {
		super();
		queryHandlers.put(GetAllQuery.class, getAllQueryHandler);
		queryHandlers.put(GetByIdQuery.class, getByIdQueryHandler);
	}
	
	public <U> QueryHandler<U> getQueryHandler(Query query){
		
		@SuppressWarnings("unchecked")
		QueryHandler<U> handler = (QueryHandler<U>) queryHandlers.get(query.getClass());
		
		if (handler == null) {
			
			throw new IllegalArgumentException("No handler found for query of type " + query.getClass().getName());
		} 
		
		return handler;
		
	}
}
