package seedu.manager.logic.commands;

//@@ author Jiang Yisong.

/**
 * interface of UndoCommand.
 * @author JYS
 *
 */

public interface UndoableCommand {
	public CommandResult undoIt();
}
