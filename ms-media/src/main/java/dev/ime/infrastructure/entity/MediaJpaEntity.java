package dev.ime.infrastructure.entity;

import dev.ime.domain.model.Genre;
import dev.ime.domain.model.MediaClass;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "medias")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class MediaJpaEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "media_id")
	private Long id;
	
	@Column( name = "media_name", nullable = false, length = 100)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column( name = "media_genre", nullable = false, length = 50)
	private Genre genre;
	
	@Enumerated(EnumType.STRING)
	@Column( name = "media_class", nullable = false, length = 50)
	private MediaClass mediaClass;
	
	@Column( name = "artist_id", nullable = false)
	private Long artistId;
}
