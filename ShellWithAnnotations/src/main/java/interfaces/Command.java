package interfaces;

import commands.CommandExecutionException;

public interface Command {
    void execute(String[] args) throws CommandExecutionException;
}
