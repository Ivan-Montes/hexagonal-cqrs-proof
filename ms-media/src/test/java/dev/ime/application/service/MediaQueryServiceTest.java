package dev.ime.application.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.dispatch.QueryDispatcher;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.query.Query;
import dev.ime.domain.query.QueryHandler;

@ExtendWith(MockitoExtension.class)
class MediaQueryServiceTest {

	@Mock
	private QueryDispatcher queryDispatcher;

	@InjectMocks
	private MediaQueryService mediaQueryServicePort;

	@Mock
	private QueryHandler<Object> queryHandlerObj;
	
	private List<Media>mediaList;
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	
	@BeforeEach
	private void createObjects() {
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaList = new ArrayList<>();		
	}
	
	
	@Test
	void MediaQueryService_getAll_ReturnListMedia() {
		
		mediaList.add(mediaTest);
		Mockito.when(queryDispatcher.getQueryHandler(Mockito.any(Query.class))).thenReturn(queryHandlerObj);
		Mockito.when(queryHandlerObj.handle(Mockito.any(Query.class))).thenReturn(mediaList);
		
		List<Media> list =  mediaQueryServicePort.getAll();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isNotEmpty(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void MediaQueryService_getById_ReturnOptionalMedia() {
		
		Mockito.when(queryDispatcher.getQueryHandler(Mockito.any(Query.class))).thenReturn(queryHandlerObj);
		Mockito.when(queryHandlerObj.handle(Mockito.any(Query.class))).thenReturn(Optional.of(mediaTest));
		
		Optional<Media> optMedia = mediaQueryServicePort.getById(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optMedia).isNotEmpty(),
				()-> Assertions.assertThat(optMedia.get().getId()).isEqualTo(id),
				()-> Assertions.assertThat(optMedia.get().getName()).isEqualTo(name),
				()-> Assertions.assertThat(optMedia.get().getGenre()).isEqualTo(genre),
				()-> Assertions.assertThat(optMedia.get().getMediaClass()).isEqualTo(mediaClass),
				()-> Assertions.assertThat(optMedia.get().getArtistId()).isEqualTo(artistId)
				);		
	}

	@Test
	void MediaQueryService_getById_ReturnResourceNotFoundException() {
		
		Mockito.when(queryDispatcher.getQueryHandler(Mockito.any(Query.class))).thenReturn(queryHandlerObj);
		Mockito.when(queryHandlerObj.handle(Mockito.any(Query.class))).thenReturn(Optional.ofNullable(null));
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> mediaQueryServicePort.getById(id));  
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(ResourceNotFoundException.class)
				);
	}
}
