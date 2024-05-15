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
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistReadRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetByIdQueryHandlerTest {

	@Mock
	private ArtistReadRepositoryPort artistReadRepositoryPort;	

	@InjectMocks
	private GetByIdQueryHandler getByIdQueryHandler;

	private GetAllQuery getAllQuery;
	private GetByIdQuery getByIdQuery;

	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";

	@BeforeEach
	private void createObjects() {		
		
		getAllQuery = new GetAllQuery();
		getByIdQuery = new GetByIdQuery(id);
		
		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();		
	}	
	
	@Test
	void GetByIdQueryHandler_handle_ReturnOptArtist() {
		
		Mockito.when(artistReadRepositoryPort.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artistTest));
		
		Optional<Artist> optArtist = getByIdQueryHandler.handle(getByIdQuery);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optArtist).isNotEmpty(),
				()-> Assertions.assertThat(optArtist.get().getId()).isEqualTo(getByIdQuery.id()),
				()-> Assertions.assertThat(optArtist.get().getName()).isEqualTo(artistTest.getName()),
				()-> Assertions.assertThat(optArtist.get().getSurname()).isEqualTo(artistTest.getSurname()),
				()-> Assertions.assertThat(optArtist.get().getArtisticName()).isEqualTo(artistTest.getArtisticName())
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
