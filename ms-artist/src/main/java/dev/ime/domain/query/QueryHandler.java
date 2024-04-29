package dev.ime.domain.query;

public interface QueryHandler<R> {
	
	R handle(Query query);
	
}
