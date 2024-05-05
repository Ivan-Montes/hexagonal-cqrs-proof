package dev.ime.application.usecase;

import dev.ime.domain.command.Command;
import dev.ime.domain.model.Media;

public record CreateCommand(Media media) implements Command {

}
