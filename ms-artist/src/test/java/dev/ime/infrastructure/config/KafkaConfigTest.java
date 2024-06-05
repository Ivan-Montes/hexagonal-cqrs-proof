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
	void KafkaConfig_artistCreatedTopic_ReturnNewTopic() {
	
		NewTopic newTopic = kafkaConfig.artistCreatedTopic();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(newTopic).isNotNull(),
				()-> Assertions.assertThat(newTopic.name()).isEqualTo(ApplicationConstant.ARTIST_CREATED)
				);
	}

	@Test
	void KafkaConfig_artistUpdatedTopic_ReturnNewTopic() {
	
		NewTopic newTopic = kafkaConfig.artistUpdatedTopic();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(newTopic).isNotNull(),
				()-> Assertions.assertThat(newTopic.name()).isEqualTo(ApplicationConstant.ARTIST_UPDATED)
				);
	}

	@Test
	void KafkaConfig_artistDeletedTopic_ReturnNewTopic() {
	
		NewTopic newTopic = kafkaConfig.artistDeletedTopic();
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(newTopic).isNotNull(),
				()-> Assertions.assertThat(newTopic.name()).isEqualTo(ApplicationConstant.ARTIST_DELETED)
				);
	}	

}
