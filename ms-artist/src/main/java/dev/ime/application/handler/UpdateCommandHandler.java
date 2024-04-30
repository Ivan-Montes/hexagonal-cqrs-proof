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
			
			Artist artist = updateCommand.artist();
			Long id = updateCommand.id();
			
			Artist artistFound = artistWriteRepositoryPort.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID,String.valueOf(id))));
			
			artistFound.setName(artist.getName());
			artistFound.setSurname(artist.getSurname());
			artistFound.setArtisticName(artist.getArtisticName());
			
			return artistWriteRepositoryPort.save(artistFound);
			
		} else {
				
			throw new IllegalArgumentException("Command not supported");
			
		}	
	}

}
