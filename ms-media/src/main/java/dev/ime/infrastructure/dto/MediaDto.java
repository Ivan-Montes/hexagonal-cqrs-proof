package dev.ime.infrastructure.dto;

import dev.ime.infrastructure.config.InfrastructureConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MediaDto(
		Long mediaId,
		@NotEmpty @Size( min = 1, max = 100 ) String mediaName,
		@NotEmpty @Size( min = 1, max = 50 ) String mediaGenre,
		@NotEmpty @Size( min = 1, max = 50 ) String mediaClass,
		@NotNull Long artistId
		) {

	public MediaDto() {
		this(0L, InfrastructureConstant.NODATA, "", "", 0L);
	}
}