package dev.ime.application.handler;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;

@Component
public class UpdateCommandHandler implements CommandHandler<Optional<Artist>>{

	private final ArtistWriteRepositoryPort artistWriteRepositoryPort;	
	
	public UpdateCommandHandler(ArtistWriteRepositoryPort artistWriteRepositoryPort) {
		super();
		this.artistWriteRepositoryPort = artistWriteRepositoryPort;
	}

	@Override
	public Optional<Artist> handle(Command command) {
		
		if (command instanceof UpdateCommand updateCommand) {
			
			Artist artistFound = validateArtistExists( updateCommand.id() );
			
			updateArtistDetails(artistFound, updateCommand.artist());
			
			return artistWriteRepositoryPort.save(artistFound);
			
		} else {
				
			throw new IllegalArgumentException("Command not supported");
			
		}	
	}
	
	private Artist validateArtistExists(Long id) {
		
		Optional<Artist> optArtist = artistWriteRepositoryPort.findById(id);
		
		if ( optArtist.isEmpty() ) {
			 
			throw new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID,String.valueOf(id)));
		}
		
		return optArtist.get();
	}

    private void updateArtistDetails(Artist artistFound, Artist artist) {
    	
        artistFound.setName(artist.getName());
        artistFound.setSurname(artist.getSurname());
        artistFound.setArtisticName(artist.getArtisticName());
        
    }
}
