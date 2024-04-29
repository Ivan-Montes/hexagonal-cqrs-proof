package dev.ime.application.usecase;

import dev.ime.domain.command.Command;
import dev.ime.domain.model.Artist;

public record CreateCommand(
		Artist artist
		) implements Command {

}
