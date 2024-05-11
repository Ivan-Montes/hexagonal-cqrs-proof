package dev.ime.application.dispatch;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ime.application.handler.CreateCommandHandler;
import dev.ime.application.handler.DeleteByIdCommandHandler;
import dev.ime.application.handler.UpdateCommandHandler;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;

@ExtendWith(MockitoExtension.class)
class CommandDispatcherTest {

	@Mock
	private CreateCommandHandler createCommandHandler;	

	@Mock
	private DeleteByIdCommandHandler deleteByIdCommandHandler;	

	@Mock
	private UpdateCommandHandler updateCommandHandler;
	
	@InjectMocks
	private CommandDispatcher commandDispatcher;
	
	private class CommandTest implements Command{}
	
	@Test
	void CommandDispatcher_getCommandHandler_ReturnDeleteByIdCommandHandler() {
		
		DeleteByIdCommand command = new DeleteByIdCommand(3L);
		
		CommandHandler<Object> queryHandler = commandDispatcher.getCommandHandler(command);
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(queryHandler).isNotNull(),
				()-> Assertions.assertThat(queryHandler).isEqualTo(deleteByIdCommandHandler)
				);		
	}
	
	@Test
	void CommandDispatcher_getCommandHandler_ReturnIllegalArgumentException() {	
		
		CommandTest command = new CommandTest();
		
		Exception ex =  org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> commandDispatcher.getCommandHandler(command));
		
		org.junit.jupiter.api.Assertions.assertAll(
				()-> Assertions.assertThat(ex).isNotNull(),
				()-> Assertions.assertThat(ex.getClass()).isEqualTo(IllegalArgumentException.class)
				);			
	}
	
}
