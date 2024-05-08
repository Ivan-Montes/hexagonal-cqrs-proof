package dev.ime.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@RedisHash
@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
public class MediaRedisEntity {
	
	@Id
    private Long id;
	@Indexed
	private Long artistId;
}
