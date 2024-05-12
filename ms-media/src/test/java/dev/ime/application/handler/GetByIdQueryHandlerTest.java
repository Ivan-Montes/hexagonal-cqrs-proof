package dev.ime.application.handler;


import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.usecase.GetAllQuery;
import dev.ime.application.usecase.GetByIdQuery;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.MediaReadRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetByIdQueryHandlerTest {

	@Mock
	private MediaReadRepositoryPort mediaReadRepositoryPort;	

	@InjectMocks
	private GetByIdQueryHandler getByIdQueryHandler;	

	private GetAllQuery getAllQuery;
	private GetByIdQuery getByIdQuery;

	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	
	@BeforeEach
	private void createObjects() {		
		
		getAllQuery = new GetAllQuery();
		getByIdQuery = new GetByIdQuery(id);
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
	}	
	
	@Test
	void GetByIdQueryHandler_handle_ReturnOptMedia() {
		
		Mockito.when(mediaReadRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaTest));
		
		Optional<Media>optMedia = getByIdQueryHandler.handle(getByIdQuery);
		
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
	void GetByIdQueryHandler_handle_ReturnIllegalArgumentException() {
		
		Exception ex = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> getByIdQueryHandler.handle(getAllQuery));

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);
	}
	
}
