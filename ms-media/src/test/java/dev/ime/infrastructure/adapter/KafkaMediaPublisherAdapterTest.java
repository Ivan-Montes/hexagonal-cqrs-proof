package dev.ime.infrastructure.adapter;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.infrastructure.dto.MediaDto;

@ExtendWith(MockitoExtension.class)
class KafkaMediaPublisherAdapterTest {

	@Mock
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Mock
	private Logger logger;
	
	@Mock
	private MediaMapper mediaMapper;

	@InjectMocks
	private KafkaMediaPublisherAdapter mediaPublisherPort;	

	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;
	private MediaDto mediaDtoTest;	
	private CompletableFuture<SendResult<String, Object>> completableFuture;
	@Mock
	private SendResult<String, Object> sendResult;
	private ProducerRecord<String, Object> producerRecord;
	
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
		completableFuture = new CompletableFuture<>();
	    producerRecord = new ProducerRecord<>("topic", "key", "value");
	}
		
	
	@SuppressWarnings("unchecked")
	@Test
	void KafkaMediaPublisherAdapter_publishInsertEvent_ReturnVoid() {
		
		Mockito.when(mediaMapper.fromDomainToDto(Mockito.any(Media.class))).thenReturn(mediaDtoTest);
	    completableFuture.complete(sendResult);
        Mockito.when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(completableFuture);
	    Mockito.when(sendResult.getProducerRecord()).thenReturn(producerRecord);
		
	    mediaPublisherPort.publishInsertEvent(mediaTest);
		
		verify(mediaMapper,times(1)).fromDomainToDto(Mockito.any(Media.class));
		verify(kafkaTemplate, times(1)).send(Mockito.any(ProducerRecord.class));
		verify(sendResult, times(2)).getProducerRecord();
	}

	@SuppressWarnings("unchecked")
	@Test
	void KafkaMediaPublisherAdapter_publishUpdateEvent_ReturnVoid() {		

		Mockito.when(mediaMapper.fromDomainToDto(Mockito.any(Media.class))).thenReturn(mediaDtoTest);
	    completableFuture.complete(sendResult);
	    Mockito.when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(completableFuture);
	    Mockito.when(sendResult.getProducerRecord()).thenReturn(producerRecord);

        mediaPublisherPort.publishUpdateEvent(mediaTest);
        
        verify(mediaMapper,times(1)).fromDomainToDto(Mockito.any(Media.class));
		verify(kafkaTemplate, times(1)).send(Mockito.any(ProducerRecord.class));
		verify(sendResult, times(2)).getProducerRecord();
	}

	@SuppressWarnings("unchecked")
	@Test
	void KafkaMediaPublisherAdapter_publishDeleteEvent_ReturnVoid() {
		
		completableFuture.complete(sendResult);
        Mockito.when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(completableFuture);
	    Mockito.when(sendResult.getProducerRecord()).thenReturn(producerRecord);

	    mediaPublisherPort.publishDeleteEvent(artistId);
	    
	    verify(kafkaTemplate, times(1)).send(Mockito.any(ProducerRecord.class));
		verify(sendResult, times(2)).getProducerRecord();
	}

}
