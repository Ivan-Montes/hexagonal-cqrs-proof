package dev.ime.infrastructure.adapter;

import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dev.ime.config.ArtistMapper;
import dev.ime.config.MediaMapper;
import dev.ime.domain.port.inbound.ArtistSubscriberPort;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseServicePort;
import dev.ime.domain.port.outbound.MediaBackupServicePort;
import dev.ime.infrastructure.config.InfrastructureConstant;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.dto.MediaDto;

@Component
public class KafkaArtistSubscriberAdapter implements ArtistSubscriberPort{
	
	private final ArtistSynchroDatabaseServicePort artistSynchroDatabaseServicePort;
	private final ArtistMapper artistMapper;
	private final MediaBackupServicePort mediaBackupServicePort;
	private final MediaMapper mediaMapper;
	private final Logger logger;

	public KafkaArtistSubscriberAdapter(ArtistSynchroDatabaseServicePort artistSynchroDatabaseServicePort,
			ArtistMapper artistMapper, MediaBackupServicePort mediaBackupServicePort, MediaMapper mediaMapper,
			Logger logger) {
		super();
		this.artistSynchroDatabaseServicePort = artistSynchroDatabaseServicePort;
		this.artistMapper = artistMapper;
		this.mediaBackupServicePort = mediaBackupServicePort;
		this.mediaMapper = mediaMapper;
		this.logger = logger;
	}

	@Override
	@KafkaListener(topics = {InfrastructureConstant.ARTIST_CREATED, InfrastructureConstant.ARTIST_UPDATED, InfrastructureConstant.ARTIST_DELETED,
			InfrastructureConstant.MEDIA_CREATED, InfrastructureConstant.MEDIA_UPDATED, InfrastructureConstant.MEDIA_DELETED},
					groupId = "artist-consumer-01")
	public void onMessage(ConsumerRecord<String, Object> consumerRecord) {	
		
		logger.info("### [KafkaArtistSubscriberAdapter] -> [onMessage] -> [Received] ###");
		
	    switch (consumerRecord.topic()) {
	    
		    case InfrastructureConstant.ARTIST_CREATED -> artistSynchroDatabaseServicePort.syncCreate(artistMapper.fromDtoToDomain( (ArtistDto)consumerRecord.value() ));
		    case InfrastructureConstant.ARTIST_UPDATED -> artistSynchroDatabaseServicePort.syncUpdate(artistMapper.fromDtoToDomain( (ArtistDto)consumerRecord.value() ));
		    case InfrastructureConstant.ARTIST_DELETED -> artistSynchroDatabaseServicePort.syncDelete( (long)consumerRecord.value() );
		    case InfrastructureConstant.MEDIA_CREATED -> mediaBackupServicePort.save(mediaMapper.fromDtoToDomain( (MediaDto)consumerRecord.value() ));
		    case InfrastructureConstant.MEDIA_UPDATED -> mediaBackupServicePort.save(mediaMapper.fromDtoToDomain( (MediaDto)consumerRecord.value() ));
		    case InfrastructureConstant.MEDIA_DELETED -> mediaBackupServicePort.deleteById( (long)consumerRecord.value() );
		    default -> logger.warning("### [KafkaArtistSubscriberAdapter] -> [onMessage] -> [Switch] -> [Default] ###");
	   
	    }
	    
	}	
	
}
