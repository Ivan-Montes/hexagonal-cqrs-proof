package dev.ime.application.dispatch;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.handler.GetAllQueryHandler;
import dev.ime.application.handler.GetByIdQueryHandler;
import dev.ime.application.usecase.GetAllQuery;
import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Media;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@ExtendWith(MockitoExtension.class)
class QueryDispatcherTest {

	@Mock
	private GetAllQueryHandler getAllQueryHandler;
	
	@Mock
	private GetByIdQueryHandler getByIdQueryHandler;
	
	@InjectMocks
	private QueryDispatcher queryDispatcher;
	
	private class QueryTest implements Query{}
	
	@Test
	void QueryDispatcher_getQueryHandler_ReturnGetAllHandler() {
		
		GetAllQuery query = new GetAllQuery();
		
		QueryHandler<List<Media>> queryHandler = queryDispatcher.getQueryHandler(query);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(queryHandler).isNotNull(),
				()-> Assertions.assertThat(queryHandler).isEqualTo(getAllQueryHandler)
				);		
	}

	@Test
	void QueryDispatcher_getQueryHandler_ReturnGetByIdHandler() {
		
		GetByIdQuery query = new GetByIdQuery(3L);
		
		QueryHandler<Media> queryHandler = queryDispatcher.getQueryHandler(query);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(queryHandler).isNotNull(),
				()-> Assertions.assertThat(queryHandler).isEqualTo(getByIdQueryHandler)
				);		
	}

	@Test
	void QueryDispatcher_getQueryHandler_ReturnIllegalArgumentException() {	
				
		QueryTest query = new QueryTest();
		Exception ex =  org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> queryDispatcher.getQueryHandler(query));
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);		
	}

}
