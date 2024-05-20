package dev.ime.infrastructure.dto;


import dev.ime.application.config.ApplicationConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record ArtistDto(
		Long artistId,
		@NotEmpty @Size( min = 1, max = 50 ) String artistName,
		@NotEmpty @Size( min = 1, max = 50 ) String artistSurname,
		@NotEmpty @Size( min = 1, max = 50 ) String artisticName)
		{
	
	public ArtistDto() {
		this(0L, ApplicationConstant.NODATA, "", "");
	}
	
}
