package seedu.manager.commons.core;

/**
 * Represents a command in the task manager
 * @@author A0147924X
 *
 */
public class CommandWord {
	
	public enum Commands {
		ADD("add"), EDIT("edit"), DELETE("delete"), UNDO("undo"), 
		FIND("find"), STORAGE("storage"), CLEAR("clear"), DONE("done"), 
		EXIT("exit"), HELP("help"), LIST("list"), SORT("sort"), ALIAS("alias");
		
		private String commandRep;
		
		private Commands(String commandRep) {
			this.commandRep = commandRep;
		}
		
		public String toString() {
			return commandRep;
		}
	}
}
