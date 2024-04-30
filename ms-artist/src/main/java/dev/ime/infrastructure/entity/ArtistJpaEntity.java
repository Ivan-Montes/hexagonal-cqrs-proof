package dev.ime.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "artists")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArtistJpaEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "artist_id" )
	private Long id;

	@Column( name = "artist_name", nullable = false, length = 50)
	private String name;

	@Column( name = "artist_surname", nullable = false, length = 50)
	private String surname;

	@Column( name = "artistic_name", nullable = false, length = 50)
	private String artisticName;
	
}
