package dev.ime.domain.model;

import java.util.Objects;

public class Media {

	private Long id;
	private String name;
	private Genre genre;
	private MediaClass mediaClass;
	private Long artistId;
	
	private Media(Media.MediaBuilder mediaBuilder) {
		
		this.id = mediaBuilder.id;
		this.name = mediaBuilder.name;
		this.genre = mediaBuilder.genre;
		this.mediaClass = mediaBuilder.mediaClass;
		this.artistId = mediaBuilder.artistId;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
	public MediaClass getMediaClass() {
		return mediaClass;
	}
	
	public Long getArtistId() {
		return artistId;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public void setMediaClass(MediaClass mediaClass) {
		this.mediaClass = mediaClass;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(artistId, genre, id, mediaClass, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		return Objects.equals(artistId, other.artistId) && genre == other.genre && Objects.equals(id, other.id)
				&& mediaClass == other.mediaClass && Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		return "Media [id=" + id + ", name=" + name + ", genre=" + genre + ", mediaClass=" + mediaClass + ", artistId="
				+ artistId + "]";
	}
	
	public static class MediaBuilder {

		private Long id;
		private String name;
		private Genre genre;
		private MediaClass mediaClass;
		private Long artistId;
		
		public MediaBuilder() {
			super();			
		}

		public MediaBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public MediaBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public MediaBuilder setGenre(Genre genre) {
			this.genre = genre;
			return this;
		}

		public MediaBuilder setMediaClass(MediaClass mediaClass) {
			this.mediaClass = mediaClass;
			return this;
		}

		public MediaBuilder setArtistId(Long artistId) {
			this.artistId = artistId;
			return this;
		}
		
		public Media build() {			
			return new Media(this);
		}
		
	}
	
}
