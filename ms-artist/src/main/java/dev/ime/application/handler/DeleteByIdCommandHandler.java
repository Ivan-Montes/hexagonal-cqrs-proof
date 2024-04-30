package dev.ime.application.handler;


import org.springframework.stereotype.Component;

import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;

@Component
public class DeleteByIdCommandHandler implements CommandHandler<Boolean>{
	
	private final ArtistWriteRepositoryPort artistWriteRepositoryPort;		
	
	public DeleteByIdCommandHandler(ArtistWriteRepositoryPort artistWriteRepositoryPort) {
		super();
		this.artistWriteRepositoryPort = artistWriteRepositoryPort;
	}

	@Override
	public Boolean handle(Command command) {

		if (command instanceof DeleteByIdCommand deleteByIdCommand) {
			
			Long id = deleteByIdCommand.id();
			artistWriteRepositoryPort.deleteById(id);
			return artistWriteRepositoryPort.findById(id).isEmpty();
			
		} else {
				
			throw new IllegalArgumentException("Command not supported");
		
		}	
	}
	
}
