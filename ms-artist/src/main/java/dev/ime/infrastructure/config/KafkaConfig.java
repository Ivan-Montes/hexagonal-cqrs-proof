package dev.ime.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import dev.ime.application.config.ApplicationConstant;

@Configuration
public class KafkaConfig {
	
	@Bean
    NewTopic artistCreatedTopic() {

        return TopicBuilder.name(ApplicationConstant.ARTIST_CREATED)
                .partitions(1)
                .replicas(1)
                .build();
    }

	@Bean
    NewTopic artistUpdatedTopic() {

        return TopicBuilder.name(ApplicationConstant.ARTIST_UPDATED)
                .partitions(1)
                .replicas(1)
                .build();
    }

	@Bean
    NewTopic artistDeletedTopic() {

        return TopicBuilder.name(ApplicationConstant.ARTIST_DELETED)
                .partitions(1)
                .replicas(1)
                .build();
    }
	
}
