package dev.ime.application.handler;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.ArtistBackupRepositoryPort;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;

@Component
public class UpdateCommandHandler implements CommandHandler<Optional<Media>>{

	private final MediaWriteRepositoryPort mediaWriteRepositoryPort;
	private final ArtistBackupRepositoryPort artistBackupRepositoryPort;
	
	public UpdateCommandHandler(MediaWriteRepositoryPort mediaWriteRepositoryPort,
			ArtistBackupRepositoryPort artistBackupRepositoryPort) {
		super();
		this.mediaWriteRepositoryPort = mediaWriteRepositoryPort;
		this.artistBackupRepositoryPort = artistBackupRepositoryPort;
	}

	@Override
	public Optional<Media> handle(Command command) {
		
		if ( command instanceof UpdateCommand updateCommand) {
			
			Media media = updateCommand.media();
			
			Media mediaFound = validateMediaExists( updateCommand.id() );
			validateArtistExists(media.getArtistId());

			updateMediaDetails(media, mediaFound);
			
			return mediaWriteRepositoryPort.save(mediaFound);
			
		}else {
			
			throw new IllegalArgumentException(ApplicationConstant.MSG_ILLEGAL_COMMAND);
			
		}
	}
	
	private void validateArtistExists(Long artistId) {
		
	    if (!artistBackupRepositoryPort.existById(artistId)) {
	    	
	        throw new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(artistId)));	        
	    }
	}
	
	private void updateMediaDetails(Media media, Media mediaFound) {
		
		mediaFound.setName(media.getName());
		mediaFound.setGenre(media.getGenre());
		mediaFound.setMediaClass(media.getMediaClass());
		mediaFound.setArtistId(media.getArtistId());
		
	}
	
	private Media validateMediaExists(Long id) {
		
		Optional<Media> optMedia = mediaWriteRepositoryPort.findById(id);
		
		if ( optMedia.isEmpty() ) {

			throw new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID,String.valueOf(id)));
		}
		
		return optMedia.get();
	}

}
