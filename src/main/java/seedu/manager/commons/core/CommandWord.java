package seedu.manager.commons.core;

// @@author A0147924X
/**
 * Represents a command in the task manager
 *
 */
public class CommandWord {
	
	public enum Commands {
		ADD("add"), EDIT("edit"), DELETE("delete"), UNDO("undo"), 
		FIND("find"), STORAGE("storage"), CLEAR("clear"), DONE("done"), 
		EXIT("exit"), HELP("help"), LIST("list"), SORTBY("sortby"),
		ALIAS("alias"), BY("by"), AT("at"), EVENT("from"), 
		PRIORITY("priority"), TAG("tag"), VENUE("venue");
		
		private String commandRep;
		
		private Commands(String commandRep) {
			this.commandRep = commandRep;
		}
		
		public String toString() {
			return commandRep;
		}
	}
	
	// @author A0148042M
	/**
	 * 
	 * @return the FIND command word
	 */
	public static String getFindCommandWord() {
	    return Commands.FIND.toString();
	}
}
