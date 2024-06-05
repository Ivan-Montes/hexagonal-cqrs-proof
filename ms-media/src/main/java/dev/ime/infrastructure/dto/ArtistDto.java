package dev.ime.infrastructure.dto;


import dev.ime.application.config.ApplicationConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record ArtistDto(
		Long artistId,
		@NotBlank @Size( min = 1, max = 50 ) String artistName,
		@NotBlank @Size( min = 1, max = 50 ) String artistSurname,
		@NotBlank @Size( min = 1, max = 50 ) String artisticName)
		{
	
	public ArtistDto() {
		this(0L, ApplicationConstant.NODATA, "", "");
	}
	
}
