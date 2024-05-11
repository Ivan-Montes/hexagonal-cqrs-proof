package dev.ime.config;


import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.infrastructure.dto.MediaDto;
import dev.ime.infrastructure.entity.MediaJpaEntity;
import dev.ime.infrastructure.entity.MediaMongoEntity;

@ExtendWith(MockitoExtension.class)
class MediaMapperTest {
	
	@InjectMocks
	private MediaMapper mediaMapper;
	
	private List<Media>mediaList;
	private List<MediaMongoEntity>mediaMongoList;
	private List<MediaJpaEntity>mediaJpaList;
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaMongoEntity mediaMongoEntityTest;
	private MediaDto mediaDtoTest;
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
		
		mediaMongoEntityTest = new MediaMongoEntity();
		mediaMongoEntityTest.setMediaId(id);
		mediaMongoEntityTest.setName(name);
		mediaMongoEntityTest.setGenre(genre.name());
		mediaMongoEntityTest.setMediaClass(mediaClass.name());
		mediaMongoEntityTest.setArtistId(artistId);
		
		mediaDtoTest = new MediaDto(id, name, genre.name(),mediaClass.name(),artistId);

		mediaJpaEntityTest = new MediaJpaEntity();
		mediaJpaEntityTest.setId(id);
		mediaJpaEntityTest.setName(name);
		mediaJpaEntityTest.setGenre(genre);
		mediaJpaEntityTest.setMediaClass(mediaClass);
		mediaJpaEntityTest.setArtistId(artistId);		
		
		mediaList = new ArrayList<>();
		mediaMongoList = new ArrayList<>();
		mediaJpaList = new ArrayList<>();
	}
	
	@Test
	void MediaMapper_fromDomainToDto_ReturnMediaDto() {
		
		MediaDto dto = mediaMapper.fromDomainToDto(mediaTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(dto).isNotNull(),
				()-> Assertions.assertThat(dto.mediaId()).isEqualTo(mediaTest.getId()),
				()-> Assertions.assertThat(dto.mediaName()).isEqualTo(mediaTest.getName()),
				()-> Assertions.assertThat(dto.mediaGenre()).isEqualTo(mediaTest.getGenre().name()),
				()-> Assertions.assertThat(dto.mediaClass()).isEqualTo(mediaTest.getMediaClass().name()),
				()-> Assertions.assertThat(dto.artistId()).isEqualTo(mediaTest.getArtistId())
				);		
	}

	@Test
	void MediaMapper_fromListDomainToListDto_ReturnListMediaDto() {
		
		mediaList.add(mediaTest);
		List<MediaDto> list = mediaMapper.fromListDomainToListDto(mediaList);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void MediaMapper_fromListDomainToListDto_ReturnListEmpty() {
		
		List<MediaDto> list = mediaMapper.fromListDomainToListDto(null);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);
	}

	@Test
	void MediaMapper_fromMongoToDomain_ReturnMedia() {
		
		Media media = mediaMapper.fromMongoToDomain(mediaMongoEntityTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(media).isNotNull(),
				()-> Assertions.assertThat(media.getId()).isEqualTo(mediaMongoEntityTest.getMediaId()),
				()-> Assertions.assertThat(media.getName()).isEqualTo(mediaMongoEntityTest.getName()),
				()-> Assertions.assertThat(media.getGenre().name()).isEqualTo(mediaMongoEntityTest.getGenre()),
				()-> Assertions.assertThat(media.getMediaClass().name()).isEqualTo(mediaMongoEntityTest.getMediaClass()),
				()-> Assertions.assertThat(media.getArtistId()).isEqualTo(mediaMongoEntityTest.getArtistId())
				);
	}

	@Test
	void MediaMapper_fromListMongoToListDomain_ReturnListMedia() {
		
		mediaMongoList.add(mediaMongoEntityTest);
		mediaList.add(mediaTest);
		List<Media> list = mediaMapper.fromListMongoToListDomain(mediaMongoList);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void MediaMapper_fromListMongoToListDomain_ReturnListEmpty() {		
		
		List<Media> list = mediaMapper.fromListMongoToListDomain(null);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);
	}

	@Test
	void MediaMapper_fromJpaToDomain_ReturnMedia() {	
		
		Media media = mediaMapper.fromJpaToDomain(mediaJpaEntityTest);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(media).isNotNull(),
				()-> Assertions.assertThat(media.getId()).isEqualTo(mediaJpaEntityTest.getId()),
				()-> Assertions.assertThat(media.getName()).isEqualTo(mediaJpaEntityTest.getName()),
				()-> Assertions.assertThat(media.getGenre()).isEqualTo(mediaJpaEntityTest.getGenre()),
				()-> Assertions.assertThat(media.getMediaClass()).isEqualTo(mediaJpaEntityTest.getMediaClass()),
				()-> Assertions.assertThat(media.getArtistId()).isEqualTo(mediaJpaEntityTest.getArtistId())
				);
	}

	@Test
	void MediaMapper_fromListJpaToListDomain_ReturnListMedia() {
		
		mediaJpaList.add(mediaJpaEntityTest);
		mediaList.add(mediaTest);
		List<Media> list = mediaMapper.fromListJpaToListDomain(mediaJpaList);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).hasSize(1)
				);
	}

	@Test
	void MediaMapper_fromListJpaToListDomain_ReturnListEmpty() {
		
		List<Media> list = mediaMapper.fromListJpaToListDomain(null);

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(list).isNotNull(),
				()-> Assertions.assertThat(list).isEmpty()
				);
	}

	@Test
	void MediaMapper_fromDtoToDomain_ReturnMedia(){
		
		Media media = mediaMapper.fromDtoToDomain(mediaDtoTest);		

		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(media).isNotNull(),
				()-> Assertions.assertThat(media.getId()).isEqualTo(mediaDtoTest.mediaId()),
				()-> Assertions.assertThat(media.getName()).isEqualTo(mediaDtoTest.mediaName()),
				()-> Assertions.assertThat(media.getGenre().name()).isEqualTo(mediaDtoTest.mediaGenre()),
				()-> Assertions.assertThat(media.getMediaClass().name()).isEqualTo(mediaDtoTest.mediaClass()),
				()-> Assertions.assertThat(media.getArtistId()).isEqualTo(mediaDtoTest.artistId())
				);
	}

	@Test
	void MediaMapper_fromDomainToJpa_ReturnMediaJpaEntity() {
	
		MediaJpaEntity jpa = mediaMapper.fromDomainToJpa(mediaTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(jpa).isNotNull(),
				()-> Assertions.assertThat(jpa.getId()).isEqualTo(mediaTest.getId()),
				()-> Assertions.assertThat(jpa.getName()).isEqualTo(mediaTest.getName()),
				()-> Assertions.assertThat(jpa.getGenre()).isEqualTo(mediaTest.getGenre()),
				()-> Assertions.assertThat(jpa.getMediaClass()).isEqualTo(mediaTest.getMediaClass()),
				()-> Assertions.assertThat(jpa.getArtistId()).isEqualTo(mediaTest.getArtistId())
				);
	}

	@Test
	void MediaMapper_fromDomainToMongo_ReturnMediaMongoEntity() {
		
		MediaMongoEntity mongo = mediaMapper.fromDomainToMongo(mediaTest);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(mongo).isNotNull(),
				()-> Assertions.assertThat(mongo.getMediaId()).isEqualTo(mediaTest.getId()),
				()-> Assertions.assertThat(mongo.getName()).isEqualTo(mediaTest.getName()),
				()-> Assertions.assertThat(mongo.getGenre()).isEqualTo(mediaTest.getGenre().name()),
				()-> Assertions.assertThat(mongo.getMediaClass()).isEqualTo(mediaTest.getMediaClass().name()),
				()-> Assertions.assertThat(mongo.getArtistId()).isEqualTo(mediaTest.getArtistId())
				);
	}
	
}
