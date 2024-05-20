package dev.ime.infrastructure.adapter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.config.ArtistMapper;
import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseServicePort;
import dev.ime.domain.port.outbound.MediaBackupServicePort;
import dev.ime.application.config.ApplicationConstant;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.dto.MediaDto;

@ExtendWith(MockitoExtension.class)
class KafkaArtistSubscriberAdapterTest {

	@Mock
	private ArtistSynchroDatabaseServicePort artistSynchroDatabaseServicePort;

	@Mock
	private ArtistMapper artistMapper;

	@Mock
	private MediaBackupServicePort mediaBackupServicePort;

	@Mock
	private MediaMapper mediaMapper;

	@Mock
	private Logger logger;

	@InjectMocks
	private KafkaArtistSubscriberAdapter artistSubscriberPort;
	

	@Mock
	private ConsumerRecord<String, Object> consumerRecord;
	
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaDto mediaDtoTest;
	private Artist artistTest;
	private final String artistName = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistDto artistDtoTest;
	
	@BeforeEach
	private void createObjects() {	

		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaDtoTest = new MediaDto(id, name, genre.name(),mediaClass.name(),artistId);
		
		artistTest = new Artist.ArtistBuilder()
				.setId(artistId)
				.setName(artistName)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();		
		
		artistDtoTest = new ArtistDto(artistId, artistName, surname, artisticName);
	}

	@Test
	void KafkaArtistSubscriberAdapter_onMessage_ReturnVoid_BranchArtistCreated() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.ARTIST_CREATED);
		Mockito.when(consumerRecord.value()).thenReturn(artistDtoTest);
		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.doNothing().when(artistSynchroDatabaseServicePort).syncCreate(Mockito.any(Artist.class));
		
		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(artistMapper,times(1)).fromDtoToDomain(Mockito.any(ArtistDto.class));
		verify(artistSynchroDatabaseServicePort, times(1)).syncCreate(Mockito.any(Artist.class));
	}

	@Test
	void KafkaArtistSubscriberAdapter_onMessage_ReturnVoid_BranchArtistUpdated() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.ARTIST_UPDATED);
		Mockito.when(consumerRecord.value()).thenReturn(artistDtoTest);
		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.doNothing().when(artistSynchroDatabaseServicePort).syncUpdate(Mockito.any(Artist.class));
		
		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(artistMapper,times(1)).fromDtoToDomain(Mockito.any(ArtistDto.class));
		verify(artistSynchroDatabaseServicePort, times(1)).syncUpdate(Mockito.any(Artist.class));
	}

	@Test
	void KafkaArtistSubscriberAdapter_onMessage_ReturnVoid_BranchArtistDeleted() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.ARTIST_DELETED);
		Mockito.when(consumerRecord.value()).thenReturn(id);
		Mockito.doNothing().when(artistSynchroDatabaseServicePort).syncDelete(Mockito.anyLong());
		
		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(artistSynchroDatabaseServicePort, times(1)).syncDelete(Mockito.anyLong());
	}

	@Test
	void KafkaArtistSubscriberAdapter_onMessage_ReturnVoid_BranchMediaCreated() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.MEDIA_CREATED);
		Mockito.when(consumerRecord.value()).thenReturn(mediaDtoTest);
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.doNothing().when(mediaBackupServicePort).save(Mockito.any(Media.class));
		
		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(mediaMapper,times(1)).fromDtoToDomain(Mockito.any(MediaDto.class));
		verify(mediaBackupServicePort, times(1)).save(Mockito.any(Media.class));
	}

	@Test
	void KafkaArtistSubscriberAdapter_onMessage_ReturnVoid_BranchMediaUpdated() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.MEDIA_UPDATED);
		Mockito.when(consumerRecord.value()).thenReturn(mediaDtoTest);
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.doNothing().when(mediaBackupServicePort).save(Mockito.any(Media.class));
		
		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(mediaMapper,times(1)).fromDtoToDomain(Mockito.any(MediaDto.class));
		verify(mediaBackupServicePort, times(1)).save(Mockito.any(Media.class));
	}

	@Test
	void KafkaArtistSubscriberAdapter_onMessage_ReturnVoid_BranchMediaDeleted() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.MEDIA_DELETED);
		Mockito.when(consumerRecord.value()).thenReturn(id);
		Mockito.doNothing().when(mediaBackupServicePort).deleteById(Mockito.anyLong());
		
		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(mediaBackupServicePort, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchDefault() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(ApplicationConstant.EX_EX);

		artistSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
	}
	
}
