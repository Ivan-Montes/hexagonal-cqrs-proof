package dev.ime.infrastructure.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Document("medias")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
public class MediaMongoEntity {

	@Id
	private ObjectId mongoId;
	
	@Field( name = "mediaId")
	private Long mediaId;

	@Field( name = "mediaName")
	private String name;

	@Field( name = "mediaGenre")
	private String genre;

	@Field( name = "mediaClass")
	private String mediaClass;

	@Field( name = "artistId")
	private Long artistId;
}
