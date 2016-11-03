package seedu.manager.logic.commands;

// @@author A0148003U
/**
 * interface of UndoCommand.
 * the method is to undo each undo-able commands.
 */
public interface UndoableCommand {
	public CommandResult undoIt();
}