package dev.ime.infrastructure.adapter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
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
import dev.ime.infrastructure.entity.MediaRedisEntity;
import dev.ime.infrastructure.repository.MediaRedisRepository;

@ExtendWith(MockitoExtension.class)
class MediaBackupRepositoryAdapterTest {

	@Mock
	private MediaRedisRepository mediaRedisRepository;

	@Mock
	private MediaMapper mediaMapper;

	@Mock
	private Logger logger;

	@InjectMocks
	private MediaBackupRepositoryAdapter mediaBackupRepositoryPort;
	
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaRedisEntity mediaRedisTest;
	private List<MediaRedisEntity> mediaRedisList;
	
	@BeforeEach
	private void createObjects() {	

		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaRedisTest = new MediaRedisEntity(id, artistId);
		
		mediaRedisList = new ArrayList<>();
	}
	
	@Test
	void MediaBackupRepositoryAdapter_save_ReturnVoid() {
		
		Mockito.when(mediaMapper.fromDomainToRedis(Mockito.any(Media.class))).thenReturn(mediaRedisTest);
		Mockito.when(mediaRedisRepository.save(Mockito.any(MediaRedisEntity.class))).thenReturn(mediaRedisTest);
	
		mediaBackupRepositoryPort.save(mediaTest);
		
		verify(mediaMapper,times(1)).fromDomainToRedis(Mockito.any(Media.class));
		verify(mediaRedisRepository, times(1)).save(Mockito.any(MediaRedisEntity.class));
	}

	@Test
	void MediaBackupRepositoryAdapter_deleteById_ReturnVoid() {
		
		Mockito.doNothing().when(mediaRedisRepository).deleteById(Mockito.anyLong());
		
		mediaBackupRepositoryPort.deleteById(id);
		
		verify(mediaRedisRepository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void MediaBackupRepositoryAdapter_existArtistId_ReturnBoolTrue() {
		
		mediaRedisList.add(mediaRedisTest);
		Mockito.when(mediaRedisRepository.findByArtistId(Mockito.anyLong())).thenReturn(mediaRedisList);

		mediaBackupRepositoryPort.existArtistId(id);
		
		verify(mediaRedisRepository, times(1)).findByArtistId(Mockito.anyLong());
	}

	@Test
	void MediaBackupRepositoryAdapter_existArtistId_ReturnBoolFalse() {
		
		Mockito.when(mediaRedisRepository.findByArtistId(Mockito.anyLong())).thenReturn(mediaRedisList);

		mediaBackupRepositoryPort.existArtistId(id);
		
		verify(mediaRedisRepository, times(1)).findByArtistId(Mockito.anyLong());
	}

	@Test
	void MediaBackupRepositoryAdapter_findById_ReturnOptMedia() {
		
		Mockito.when(mediaRedisRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mediaRedisTest));
		Mockito.when(mediaMapper.fromRedisToDomain(Mockito.any(MediaRedisEntity.class))).thenReturn(mediaTest);

		mediaBackupRepositoryPort.findById(artistId);
		
		verify(mediaRedisRepository, times(1)).findById(Mockito.anyLong());
		verify(mediaMapper,times(1)).fromRedisToDomain(Mockito.any(MediaRedisEntity.class));
	}

}
