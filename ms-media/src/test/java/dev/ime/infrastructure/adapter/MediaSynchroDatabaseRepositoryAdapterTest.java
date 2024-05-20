package dev.ime.infrastructure.adapter;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.logging.Logger;

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
import dev.ime.infrastructure.repository.MediaWriteMongoRepository;

@ExtendWith(MockitoExtension.class)
class MediaSynchroDatabaseRepositoryAdapterTest {

	@Mock
	private MediaWriteMongoRepository mediaWriteMongoRepository;
	
	@Mock
	private MediaMapper mediaMapper;
	
	@Mock
	private Logger logger;

	@InjectMocks
	private MediaSynchroDatabaseRepositoryAdapter mediaSynchroDatabaseRepositoryPort;

	private Media mediaTest;
	private MediaMongoEntity mediaMongoEntityTest;
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
		
		mediaMongoEntityTest = new MediaMongoEntity();
		mediaMongoEntityTest.setMediaId(id);
		mediaMongoEntityTest.setName(name);
		mediaMongoEntityTest.setGenre(genre.name());
		mediaMongoEntityTest.setMediaClass(mediaClass.name());
		mediaMongoEntityTest.setArtistId(artistId);		
		
	}	
	
	@Test
	void MediaSynchroDatabaseRepositoryAdapter_save_ReturnVoid() {

		Mockito.when(mediaMapper.fromDomainToMongo(Mockito.any(Media.class))).thenReturn(mediaMongoEntityTest);
		Mockito.when(mediaWriteMongoRepository.save(Mockito.any(MediaMongoEntity.class))).thenReturn(null);
	
		mediaSynchroDatabaseRepositoryPort.save(mediaTest);
		
		verify(mediaWriteMongoRepository, times(1)).save(Mockito.any(MediaMongoEntity.class));
		verify(mediaMapper, times(1)).fromDomainToMongo(Mockito.any(Media.class));
	}

	@Test
	void MediaSynchroDatabaseRepositoryAdapter_update_ReturnVoid() {

		Mockito.when(mediaWriteMongoRepository.findFirstByMediaId(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaMongoEntityTest));
		Mockito.when(mediaWriteMongoRepository.save(Mockito.any(MediaMongoEntity.class))).thenReturn(null);

		mediaSynchroDatabaseRepositoryPort.update(mediaTest);
		
		verify(mediaWriteMongoRepository, times(1)).findFirstByMediaId(Mockito.anyLong());
		verify(mediaWriteMongoRepository, times(1)).save(Mockito.any(MediaMongoEntity.class));
	}

	@Test
	void MediaSynchroDatabaseRepositoryAdapter_update_ReturnForVoidOptEpty() {

		Mockito.when(mediaWriteMongoRepository.findFirstByMediaId(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

		mediaSynchroDatabaseRepositoryPort.update(mediaTest);
		
		verify(mediaWriteMongoRepository, times(1)).findFirstByMediaId(Mockito.anyLong());
	}
	
	@Test
	void MediaSynchroDatabaseRepositoryAdapter_deleteById_ReturnVoid() {

		Mockito.when(mediaWriteMongoRepository.findFirstByMediaId(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaMongoEntityTest));
		Mockito.doNothing().when(mediaWriteMongoRepository).delete(Mockito.any(MediaMongoEntity.class));
	
		mediaSynchroDatabaseRepositoryPort.deleteById(id);
		
		verify(mediaWriteMongoRepository, times(1)).findFirstByMediaId(Mockito.anyLong());
		verify(mediaWriteMongoRepository, times(1)).delete(Mockito.any(MediaMongoEntity.class));
	}	

	@Test
	void MediaSynchroDatabaseRepositoryAdapter_deleteById_ReturnVoidForOptEmpty() {

		Mockito.when(mediaWriteMongoRepository.findFirstByMediaId(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
	
		mediaSynchroDatabaseRepositoryPort.deleteById(id);
		
		verify(mediaWriteMongoRepository, times(1)).findFirstByMediaId(Mockito.anyLong());
	}	


}
