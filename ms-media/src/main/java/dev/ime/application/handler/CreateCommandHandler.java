package dev.ime.application.handler;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.application.usecase.CreateCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;

@Component
public class CreateCommandHandler implements CommandHandler<Optional<Media>>{

	private final MediaWriteRepositoryPort mediaWriteRepositoryPort;
	private final ArtistBackupRepositoryPort artistBackupRepositoryPort;
	

	public CreateCommandHandler(MediaWriteRepositoryPort mediaWriteRepositoryPort,
			ArtistBackupRepositoryPort artistBackupRepositoryPort) {
		super();
		this.mediaWriteRepositoryPort = mediaWriteRepositoryPort;
		this.artistBackupRepositoryPort = artistBackupRepositoryPort;
	}


	@Override
	public Optional<Media> handle(Command command) {
		
		if ( command instanceof CreateCommand createCommand) {
			
			Long artistId = createCommand.media().getArtistId();
			if ( !artistBackupRepositoryPort.existById(artistId) ) throw new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(artistId)));
			
			return mediaWriteRepositoryPort.save(createCommand.media());
			
		}else {
			
			throw new IllegalArgumentException(ApplicationConstant.MSG_ILLEGAL_COMMAND);
			
		}
	}
}
