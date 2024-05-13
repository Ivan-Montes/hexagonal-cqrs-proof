package dev.ime.infrastructure.adapter;


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

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.infrastructure.entity.MediaMongoEntity;
import dev.ime.infrastructure.repository.MediaReadMongoRepository;

@ExtendWith(MockitoExtension.class)
class MediaReadMongoRepositoryAdapterTest {
	
	@Mock
	private MediaReadMongoRepository mediaReadMongoRepository;
	
	@Mock
	private MediaMapper mediaMapper;

	@InjectMocks
	private MediaReadMongoRepositoryAdapter mediaReadRepositoryPort;
	
	private List<Media>mediaList;
	private List<MediaMongoEntity>mediaMongoList;
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaMongoEntity mediaMongoEntityTest;
	
	@BeforeEach
	private void createObjects() {

		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaMongoEntityTest = new MediaMongoEntity();
		mediaMongoEntityTest.setMediaId(id);
		mediaMongoEntityTest.setName(name);
		mediaMongoEntityTest.setGenre(genre.name());
		mediaMongoEntityTest.setMediaClass(mediaClass.name());
		mediaMongoEntityTest.setArtistId(artistId);
		
		mediaList = new ArrayList<>();
		mediaMongoList = new ArrayList<>();
		
	}
	
	@Test
	void MediaReadMongoRepositoryAdapter_findAll_ReturnListMedia() {
		
		mediaList.add(mediaTest);
		Mockito.when(mediaReadMongoRepository.findAll()).thenReturn(mediaMongoList);
		Mockito.when(mediaMapper.fromListMongoToListDomain(Mockito.anyList())).thenReturn(mediaList);
		
		List<Media> list = mediaReadRepositoryPort.findAll();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isNotEmpty(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void MediaReadMongoRepositoryAdapter_findById_ReturnOptionalMedia() {
		
		Mockito.when(mediaReadMongoRepository.findFirstByMediaId(Mockito.anyLong())).thenReturn(Optional.of(mediaMongoEntityTest));
		Mockito.when(mediaMapper.fromMongoToDomain(Mockito.any(MediaMongoEntity.class))).thenReturn(mediaTest);
		
		Optional<Media> optMedia = mediaReadRepositoryPort.findById(id);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(optMedia).isNotEmpty(),
				()-> Assertions.assertThat(optMedia.get().getId()).isEqualTo(id),
				()-> Assertions.assertThat(optMedia.get().getName()).isEqualTo(name),
				()-> Assertions.assertThat(optMedia.get().getGenre()).isEqualTo(genre),
				()-> Assertions.assertThat(optMedia.get().getMediaClass()).isEqualTo(mediaClass),
				()-> Assertions.assertThat(optMedia.get().getArtistId()).isEqualTo(artistId)
				);
	}

}
