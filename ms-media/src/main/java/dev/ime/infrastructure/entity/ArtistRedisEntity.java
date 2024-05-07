package dev.ime.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@RedisHash
@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
public class ArtistRedisEntity {
	
	@Id
    private Long id;
	
}
