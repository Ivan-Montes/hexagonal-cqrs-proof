package dev.ime.domain.model;

import java.util.Objects;

public class Artist {

	private Long id;
	private String name;
	private String surname;
	private String artisticName;
	
	private Artist(ArtistBuilder artistBuilder) {
		
		this.id = artistBuilder.id;
		this.name = artistBuilder.name;
		this.surname = artistBuilder.surname;
		this.artisticName = artistBuilder.artisticName;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getArtisticName() {
		return artisticName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setArtisticName(String artisticName) {
		this.artisticName = artisticName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(artisticName, id, name, surname);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		return Objects.equals(artisticName, other.artisticName) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}
	
	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", surname=" + surname + ", artisticName=" + artisticName + "]";
	}

	public static class ArtistBuilder {
		
		private Long id;
		private String name;
		private String surname;
		private String artisticName;
		
		public ArtistBuilder() {
			super();
		}

		public ArtistBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public ArtistBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ArtistBuilder setSurname(String surname) {
			this.surname = surname;
			return this;
		}

		public ArtistBuilder setArtisticName(String artisticName) {
			this.artisticName = artisticName;
			return this;
		}
		
		public Artist build() {
			return new Artist(this);
		}
		
	}
	
}
