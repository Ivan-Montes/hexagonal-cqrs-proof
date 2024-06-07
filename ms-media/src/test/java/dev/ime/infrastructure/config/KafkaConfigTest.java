package dev.ime.infrastructure.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.config.ApplicationConstant;

@ExtendWith(MockitoExtension.class)
class KafkaConfigTest {

	@InjectMocks
	private KafkaConfig kafkaConfig;
	
	@Test
	void KafkaConfig_mediaCreatedTopic_ReturnNewTopic() {
	
		NewTopic newTopic = kafkaConfig.mediaCreatedTopic();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(newTopic).isNotNull(),
				()-> Assertions.assertThat(newTopic.name()).isEqualTo(ApplicationConstant.MEDIA_CREATED)
				);
	}

	@Test
	void KafkaConfig_mediaUpdatedTopic_ReturnNewTopic() {
	
		NewTopic newTopic = kafkaConfig.mediaUpdatedTopic();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(newTopic).isNotNull(),
				()-> Assertions.assertThat(newTopic.name()).isEqualTo(ApplicationConstant.MEDIA_UPDATED)
				);
	}

	@Test
	void KafkaConfig_mediaDeletedTopic_ReturnNewTopic() {
	
		NewTopic newTopic = kafkaConfig.mediaDeletedTopic();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(newTopic).isNotNull(),
				()-> Assertions.assertThat(newTopic.name()).isEqualTo(ApplicationConstant.MEDIA_DELETED)
				);
	}

}
