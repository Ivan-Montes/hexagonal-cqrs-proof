package dev.ime.application.handler;


import java.util.Map;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.EntityAssociatedException;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;
import dev.ime.domain.port.outbound.MediaBackupRepositoryPort;

@Component
public class DeleteByIdCommandHandler implements CommandHandler<Boolean>{
	
	private final ArtistWriteRepositoryPort artistWriteRepositoryPort;		
	private final MediaBackupRepositoryPort mediaBackupRepositoryPort;	

	public DeleteByIdCommandHandler(ArtistWriteRepositoryPort artistWriteRepositoryPort,
			MediaBackupRepositoryPort mediaBackupRepositoryPort) {
		super();
		this.artistWriteRepositoryPort = artistWriteRepositoryPort;
		this.mediaBackupRepositoryPort = mediaBackupRepositoryPort;
	}

	@Override
	public Boolean handle(Command command) {

		if (command instanceof DeleteByIdCommand deleteByIdCommand) {
			
			Long id = deleteByIdCommand.id();
			if( mediaBackupRepositoryPort.existArtistId(id) ) throw new EntityAssociatedException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(id)));
			artistWriteRepositoryPort.deleteById(id);
			return artistWriteRepositoryPort.findById(id).isEmpty();
			
		} else {
				
			throw new IllegalArgumentException("Command not supported");
		
		}	
	}
	
}
