package dev.ime.infrastructure.adapter;

import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dev.ime.config.ArtistMapper;
import dev.ime.config.MediaMapper;
import dev.ime.domain.port.inbound.MediaSubscriberPort;
import dev.ime.domain.port.outbound.ArtistBackupServicePort;
import dev.ime.domain.port.outbound.MediaSynchroDatabaseServicePort;
import dev.ime.application.config.ApplicationConstant;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.dto.MediaDto;

@Component
public class KafkaMediaSubscriberAdapter implements MediaSubscriberPort{

	private final MediaSynchroDatabaseServicePort mediaSynchroDatabaseServicePort;
	private final MediaMapper mediaMapper;
	private final ArtistBackupServicePort artistBackupServicePort;
	private final ArtistMapper artistMapper;
	private final Logger logger;
	
	public KafkaMediaSubscriberAdapter(MediaSynchroDatabaseServicePort mediaSynchroDatabaseServicePort,
			MediaMapper mediaMapper, ArtistBackupServicePort artistBackupServicePort, ArtistMapper artistMapper,
			Logger logger) {
		super();
		this.mediaSynchroDatabaseServicePort = mediaSynchroDatabaseServicePort;
		this.mediaMapper = mediaMapper;
		this.artistBackupServicePort = artistBackupServicePort;
		this.artistMapper = artistMapper;
		this.logger = logger;
	}

	@Override
	@KafkaListener(topics = {ApplicationConstant.MEDIA_CREATED, ApplicationConstant.MEDIA_UPDATED, ApplicationConstant.MEDIA_DELETED,
					ApplicationConstant.ARTIST_CREATED, ApplicationConstant.ARTIST_DELETED},
	groupId = "media-consumer-01")
	public void onMessage(ConsumerRecord<String, Object> consumerRecord) {
		
		logger.info("### [KafkaMediaSubscriberAdapter] -> [onMessage] -> [Received]");

		switch (consumerRecord.topic()) {
		
		case ApplicationConstant.MEDIA_CREATED -> mediaSynchroDatabaseServicePort.syncCreate(mediaMapper.fromDtoToDomain((MediaDto) consumerRecord.value()));
		case ApplicationConstant.MEDIA_UPDATED -> mediaSynchroDatabaseServicePort.syncUpdate(mediaMapper.fromDtoToDomain((MediaDto) consumerRecord.value()));
		case ApplicationConstant.MEDIA_DELETED -> mediaSynchroDatabaseServicePort.syncDelete((long)consumerRecord.value());
		case ApplicationConstant.ARTIST_CREATED -> artistBackupServicePort.save(artistMapper.fromDtoToDomain((ArtistDto) consumerRecord.value()));
		case ApplicationConstant.ARTIST_DELETED -> artistBackupServicePort.deleteById((long)consumerRecord.value());
		default -> logger.warning("### [KafkaMediaSubscriberAdapter] -> [onMessage] -> [Switch] -> [Default] ###");
		   
	    }
		
	}

}
