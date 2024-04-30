package dev.ime.infrastructure.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("artists")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtistMongoEntity {
	
	@Id
	private ObjectId mongoId;
	
	@Field( name = "artistId" )
	private Long artistId;

	@Field( name = "artistName" )
	private String name;

	@Field( name = "artistSurname" )
	private String surname;
	
	@Field( name = "artisticName" )
	private String artisticName;	
	
}
