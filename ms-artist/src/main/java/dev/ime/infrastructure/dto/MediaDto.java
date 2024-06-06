package dev.ime.infrastructure.dto;

import dev.ime.application.config.ApplicationConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MediaDto(
		Long mediaId,
		@NotBlank @Size( min = 1, max = 100 ) String mediaName,
		@NotBlank @Size( min = 1, max = 50 ) String mediaGenre,
		@NotBlank @Size( min = 1, max = 50 ) String mediaClass,
		@NotNull Long artistId
		) {

	public MediaDto() {
		this(0L, ApplicationConstant.NODATA, "", "", 0L);
	}
}
