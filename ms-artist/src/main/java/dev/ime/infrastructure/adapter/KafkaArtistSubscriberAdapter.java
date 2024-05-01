package dev.ime.infrastructure.adapter;

import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.port.inbound.ArtistSubscriberPort;
import dev.ime.domain.port.outbound.ArtistSynchroDatabaseServicePort;
import dev.ime.infrastructure.config.InfrastructureConstant;
import dev.ime.infrastructure.dto.ArtistDto;

@Component
public class KafkaArtistSubscriberAdapter implements ArtistSubscriberPort{
	
	private final ArtistSynchroDatabaseServicePort artistSynchroDatabaseServicePort;
	private final ArtistMapper artistMapper;
	private final Logger logger;
	
	public KafkaArtistSubscriberAdapter(ArtistSynchroDatabaseServicePort artistSynchroDatabasePort, ArtistMapper artistMapper, Logger logger) {
		super();
		this.artistSynchroDatabaseServicePort = artistSynchroDatabasePort;
		this.artistMapper = artistMapper;
		this.logger = logger;
	}

	@Override
	@KafkaListener(topics = {InfrastructureConstant.ARTIST_CREATED, InfrastructureConstant.ARTIST_UPDATED, InfrastructureConstant.ARTIST_DELETED},
					groupId = "artist-consumer-01")
	public void onMessage(ConsumerRecord<String, Object> consumerRecord) {	
		
		logger.info("### [KafkaArtistSubscriberAdapter] -> [onMessage] -> [Received] ###");
		
	    switch (consumerRecord.topic()) {
	    
		    case InfrastructureConstant.ARTIST_CREATED -> artistSynchroDatabaseServicePort.syncCreate(artistMapper.fromDtoToDomain((ArtistDto) consumerRecord.value()));
		    case InfrastructureConstant.ARTIST_UPDATED -> artistSynchroDatabaseServicePort.syncUpdate(artistMapper.fromDtoToDomain((ArtistDto) consumerRecord.value()));
		    case InfrastructureConstant.ARTIST_DELETED -> artistSynchroDatabaseServicePort.syncDelete((long)consumerRecord.value());
		    default -> logger.warning("### [KafkaArtistSubscriberAdapter] -> [onMessage] -> [Switch] -> [Default] ###");
	   
	    }
	    
	}	
	
}
