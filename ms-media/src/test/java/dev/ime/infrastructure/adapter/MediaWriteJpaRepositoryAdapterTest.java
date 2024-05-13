package dev.ime.infrastructure.adapter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import dev.ime.infrastructure.entity.MediaJpaEntity;
import dev.ime.infrastructure.repository.MediaWriteJpaRepository;

@ExtendWith(MockitoExtension.class)
class MediaWriteJpaRepositoryAdapterTest {

	@Mock
	private MediaWriteJpaRepository mediaWriteJpaRepository;

	@Mock
	private MediaMapper mediaMapper;

	@InjectMocks
	private MediaWriteJpaRepositoryAdapter mediaWriteRepositoryPort;
	
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaJpaEntity mediaJpaEntityTest;	
	
	@BeforeEach
	private void createObjects() {
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaJpaEntityTest = new MediaJpaEntity();
		mediaJpaEntityTest.setId(artistId);
		mediaJpaEntityTest.setName(name);
		mediaJpaEntityTest.setGenre(genre);
		mediaJpaEntityTest.setMediaClass(mediaClass);
		mediaJpaEntityTest.setArtistId(artistId);		
		
	}
	
	
	@Test
	void MediaWriteJpaRepositoryAdapter_save_ReturnOptionalMedia() {
		
		Mockito.when(mediaMapper.fromDomainToJpa(Mockito.any(Media.class))).thenReturn(mediaJpaEntityTest);
		Mockito.when(mediaWriteJpaRepository.save(Mockito.any(MediaJpaEntity.class))).thenReturn(mediaJpaEntityTest);
		Mockito.when(mediaMapper.fromJpaToDomain(Mockito.any(MediaJpaEntity.class))).thenReturn(mediaTest);
		
		Optional<Media> optMedia = mediaWriteRepositoryPort.save(mediaTest);
		
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
	void MediaWriteJpaRepositoryAdapter_deleteById_ReturnVoid() {
		
		Mockito.doNothing().when(mediaWriteJpaRepository).deleteById(Mockito.anyLong());
		
		mediaWriteRepositoryPort.deleteById(artistId);
		
		verify(mediaWriteJpaRepository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void MediaWriteJpaRepositoryAdapter_findById_ReturnOptionalMedia() {
		
		Mockito.when(mediaMapper.fromJpaToDomain(Mockito.any(MediaJpaEntity.class))).thenReturn(mediaTest);
		Mockito.when(mediaWriteJpaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mediaJpaEntityTest));
		
		Optional<Media> optMedia = mediaWriteRepositoryPort.findById(artistId);		

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
