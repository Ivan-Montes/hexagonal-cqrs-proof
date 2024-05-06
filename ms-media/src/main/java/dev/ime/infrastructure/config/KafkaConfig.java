package dev.ime.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
	
	@Bean
    NewTopic mediaCreatedTopic() {

        return TopicBuilder.name(InfrastructureConstant.MEDIA_CREATED)
                .partitions(1)
                .replicas(1)
                .build();
    }

	@Bean
    NewTopic mediaUpdatedTopic() {

        return TopicBuilder.name(InfrastructureConstant.MEDIA_UPDATED)
                .partitions(1)
                .replicas(1)
                .build();
    }

	@Bean
    NewTopic mediaDeletedTopic() {

        return TopicBuilder.name(InfrastructureConstant.MEDIA_DELETED)
                .partitions(1)
                .replicas(1)
                .build();
    }
	
}
