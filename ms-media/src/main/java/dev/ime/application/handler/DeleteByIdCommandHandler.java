package dev.ime.application.handler;

import org.springframework.stereotype.Component;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.port.outbound.MediaWriteRepositoryPort;

@Component
public class DeleteByIdCommandHandler implements CommandHandler<Boolean>{

	private final MediaWriteRepositoryPort mediaWriteRepositoryPort;
	
	public DeleteByIdCommandHandler(MediaWriteRepositoryPort mediaWriteRepositoryPort) {
		super();
		this.mediaWriteRepositoryPort = mediaWriteRepositoryPort;
	}

	@Override
	public Boolean handle(Command command) {
		
		if ( command instanceof DeleteByIdCommand deleteByIdCommand) {

			Long id = deleteByIdCommand.id();
			mediaWriteRepositoryPort.deleteById(id);
			return mediaWriteRepositoryPort.findById(id).isEmpty();
			
		}else {
			
			throw new IllegalArgumentException(ApplicationConstant.MSG_ILLEGAL_COMMAND);
			
		}
	}
}
