package dev.ime.infrastructure.adapter;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistPublisherPort;
import dev.ime.application.config.ApplicationConstant;

@Service
public class KafkaArtistPublisherAdapter implements ArtistPublisherPort{

	private final KafkaTemplate<String, Object> kafkaTemplate;	
	private final Logger logger;
	private final ArtistMapper artistMapper;
	
	public KafkaArtistPublisherAdapter(KafkaTemplate<String, Object> kafkaTemplate, Logger logger,
			ArtistMapper artistMapper) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.logger = logger;
		this.artistMapper = artistMapper;
	}

	@Override
	public void publishInsertEvent(Artist artist) {
		
		send(new ProducerRecord<>(ApplicationConstant.ARTIST_CREATED, artistMapper.fromDomainToDto(artist)));
		logInfoAction("publishInsertEvent", artist.toString());

	}

	@Override
	public void publishUpdateEvent(Artist artist) {
		
		send(new ProducerRecord<>(ApplicationConstant.ARTIST_UPDATED, artistMapper.fromDomainToDto(artist)));
		logInfoAction("publishUpdateEvent", artist.toString());

	}

	@Override
	public void publishDeleteEvent(Long id) {
		
		send(new ProducerRecord<>(ApplicationConstant.ARTIST_DELETED, id));		
		logInfoAction("publishDeleteEvent", id.toString() );

	}

	private void send(ProducerRecord<String, Object> producerRecord) {
			
		CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(producerRecord);
		completableFuture.whenComplete( (result, ex) -> {
			if (ex == null) {
	            handleSuccess(result);
	        }
	        else {
	            handleFailure(result, ex);
	        }
		});	
	}

	private void handleSuccess(SendResult<String, Object> result) {
		logInfoAction("handleSuccess", result.getProducerRecord().topic() + "]:[" + result.getProducerRecord().value() + "] ###");
	}

    private void handleFailure(SendResult<String, Object> result, Throwable ex) {
    	logInfoAction("handleFailure", result.getProducerRecord().topic() + "]:[" + result.getProducerRecord().value() + "]:[" + ex.getMessage() + "] ###");
    }	

	private void logInfoAction(String methodName, String msg) {
		
		String logMessage = String.format("### [%s] -> [%s] -> [ %s ]", this.getClass().getSimpleName(), methodName, msg);
		
		logger.log(Level.INFO, logMessage);
		
	}
		
}
