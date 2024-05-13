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
import dev.ime.domain.port.outbound.ArtistBackupServicePort;
import dev.ime.domain.port.outbound.MediaSynchroDatabaseServicePort;
import dev.ime.infrastructure.config.InfrastructureConstant;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.dto.MediaDto;

@ExtendWith(MockitoExtension.class)
class KafkaMediaSubscriberAdapterTest {

	@Mock
	private MediaSynchroDatabaseServicePort mediaSynchroDatabaseServicePort;

	@Mock
	private MediaMapper mediaMapper;

	@Mock
	private ArtistBackupServicePort artistBackupServicePort;

	@Mock
	private ArtistMapper artistMapper;

	@Mock
	private Logger logger;
	
	@InjectMocks
	private KafkaMediaSubscriberAdapter mediaSubscriberPort;

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
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchMediaCreated() {		
		
		Mockito.when(consumerRecord.topic()).thenReturn(InfrastructureConstant.MEDIA_CREATED);
		Mockito.when(consumerRecord.value()).thenReturn(mediaDtoTest);
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.doNothing().when(mediaSynchroDatabaseServicePort).syncCreate(Mockito.any(Media.class));
		
		mediaSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(mediaMapper,times(1)).fromDtoToDomain(Mockito.any(MediaDto.class));
		verify(mediaSynchroDatabaseServicePort, times(1)).syncCreate(Mockito.any(Media.class));
	}

	@Test
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchMediaUpdated() {		
		
		Mockito.when(consumerRecord.topic()).thenReturn(InfrastructureConstant.MEDIA_UPDATED);
		Mockito.when(consumerRecord.value()).thenReturn(mediaDtoTest);
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.doNothing().when(mediaSynchroDatabaseServicePort).syncUpdate(Mockito.any(Media.class));
		
		mediaSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(mediaMapper,times(1)).fromDtoToDomain(Mockito.any(MediaDto.class));
		verify(mediaSynchroDatabaseServicePort, times(1)).syncUpdate(Mockito.any(Media.class));
	}

	@Test
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchMediaDeleted() {		
		
		Mockito.when(consumerRecord.topic()).thenReturn(InfrastructureConstant.MEDIA_DELETED);
		Mockito.when(consumerRecord.value()).thenReturn(id);
		Mockito.doNothing().when(mediaSynchroDatabaseServicePort).syncDelete(Mockito.anyLong());
		
		mediaSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(mediaSynchroDatabaseServicePort, times(1)).syncDelete(Mockito.anyLong());
	}

	@Test
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchArtistCreated() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(InfrastructureConstant.ARTIST_CREATED);
		Mockito.when(consumerRecord.value()).thenReturn(artistDtoTest);
		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.doNothing().when(artistBackupServicePort).save(Mockito.any(Artist.class));
		
		mediaSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(artistMapper,times(1)).fromDtoToDomain(Mockito.any(ArtistDto.class));
		verify(artistBackupServicePort, times(1)).save(Mockito.any(Artist.class));
	
	}
	
	@Test
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchArtistDeleted() {

		Mockito.when(consumerRecord.topic()).thenReturn(InfrastructureConstant.ARTIST_DELETED);
		Mockito.when(consumerRecord.value()).thenReturn(artistId);
		Mockito.doNothing().when(artistBackupServicePort).deleteById(Mockito.anyLong());
		
		mediaSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
		verify(consumerRecord, times(1)).value();
		verify(artistBackupServicePort, times(1)).deleteById(Mockito.anyLong());
	}	

	@Test
	void KafkaMediaSubscriberAdapter_onMessage_ReturnVoid_BranchDefault() {
		
		Mockito.when(consumerRecord.topic()).thenReturn(InfrastructureConstant.EX_EX);

		mediaSubscriberPort.onMessage(consumerRecord);
		
		verify(consumerRecord, times(1)).topic();
	}
	
}
