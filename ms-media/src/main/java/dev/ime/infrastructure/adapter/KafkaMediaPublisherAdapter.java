package dev.ime.infrastructure.adapter;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaPublisherPort;
import dev.ime.application.config.ApplicationConstant;

@Service
public class KafkaMediaPublisherAdapter implements MediaPublisherPort{

	private final KafkaTemplate<String, Object> kafkaTemplate;	
	private final Logger logger;
	private final MediaMapper mediaMapper;
	
	public KafkaMediaPublisherAdapter(KafkaTemplate<String, Object> kafkaTemplate, Logger logger,
			MediaMapper mediaMapper) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.logger = logger;
		this.mediaMapper = mediaMapper;
	}

	@Override
	public void publishInsertEvent(Media media) {
		
		send(new ProducerRecord<>(ApplicationConstant.MEDIA_CREATED, mediaMapper.fromDomainToDto(media)));
		logger.info("### [KafkaMediaPublisherAdapter] -> [publishInsertEvent]");

	}

	@Override
	public void publishUpdateEvent(Media media) {
		
		send(new ProducerRecord<>(ApplicationConstant.MEDIA_UPDATED, mediaMapper.fromDomainToDto(media)));
		logger.info("### [KafkaMediaPublisherAdapter] -> [publishUpdateEvent]");

	}

	@Override
	public void publishDeleteEvent(Long id) {
		
		send(new ProducerRecord<>(ApplicationConstant.MEDIA_DELETED, id));		
		logger.info("### [KafkaMediaPublisherAdapter] -> [publishDeleteEvent]");
		
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
		logger.info("### [KafkaMediaPublisherAdapter] -> [send] -> [handleSuccess] -> [" + result.getProducerRecord().topic() + "]:[" + result.getProducerRecord().value() + "]");
	}

    private void handleFailure(SendResult<String, Object> result, Throwable ex) {
		logger.info("### [KafkaMediaPublisherAdapter] -> [send] -> [handleFailure] -> [" + result.getProducerRecord().topic() + "]:[" + result.getProducerRecord().value() + "]:[" + ex.getMessage() + "]");
    }	

}
