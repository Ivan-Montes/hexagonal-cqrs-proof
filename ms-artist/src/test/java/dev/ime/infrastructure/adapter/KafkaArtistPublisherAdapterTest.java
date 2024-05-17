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

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.dto.ArtistDto;

@ExtendWith(MockitoExtension.class)
class KafkaArtistPublisherAdapterTest {

	@Mock
	private KafkaTemplate<String, Object> kafkaTemplate;	

	@Mock
	private Logger logger;

	@Mock
	private ArtistMapper artistMapper;

	@InjectMocks
	private KafkaArtistPublisherAdapter artistPublisherPort;
	
	private ArtistDto artistDtoTest;
	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	@Mock
	private SendResult<String, Object> sendResult;
	private ProducerRecord<String, Object> producerRecord;	
	private CompletableFuture<SendResult<String, Object>> completableFuture;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();	
		
		artistDtoTest = new ArtistDto(id, name, surname, artisticName);
		completableFuture = new CompletableFuture<>();
	    producerRecord = new ProducerRecord<>("topic", "key", "value");
	}

	@SuppressWarnings("unchecked")
	@Test
	void KafkaArtistPublisherAdapter_publishInsertEvent_ReturnVoid() {
		
		Mockito.when(artistMapper.fromDomainToDto(Mockito.any(Artist.class))).thenReturn(artistDtoTest);
		completableFuture.complete(sendResult);
		Mockito.when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(completableFuture);
		Mockito.when(sendResult.getProducerRecord()).thenReturn(producerRecord);

		artistPublisherPort.publishInsertEvent(artistTest);
			
		verify(artistMapper,times(1)).fromDomainToDto(Mockito.any(Artist.class));
		verify(kafkaTemplate, times(1)).send(Mockito.any(ProducerRecord.class));
		verify(sendResult, times(2)).getProducerRecord();
	}

	@SuppressWarnings("unchecked")
	@Test
	void KafkaArtistPublisherAdapter_publishUpdateEvent_ReturnVoid() {
		
		Mockito.when(artistMapper.fromDomainToDto(Mockito.any(Artist.class))).thenReturn(artistDtoTest);
		completableFuture.complete(sendResult);
		Mockito.when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(completableFuture);
		Mockito.when(sendResult.getProducerRecord()).thenReturn(producerRecord);

		artistPublisherPort.publishUpdateEvent(artistTest);
			
		verify(artistMapper,times(1)).fromDomainToDto(Mockito.any(Artist.class));
		verify(kafkaTemplate, times(1)).send(Mockito.any(ProducerRecord.class));
		verify(sendResult, times(2)).getProducerRecord();
	}

	@SuppressWarnings("unchecked")
	@Test
	void KafkaArtistPublisherAdapter_publishDeleteEvent_ReturnVoid() {

		completableFuture.complete(sendResult);
        Mockito.when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(completableFuture);
	    Mockito.when(sendResult.getProducerRecord()).thenReturn(producerRecord);

	    artistPublisherPort.publishDeleteEvent(id);
	    
	    verify(kafkaTemplate, times(1)).send(Mockito.any(ProducerRecord.class));
		verify(sendResult, times(2)).getProducerRecord();
	}

}
