package seedu.manager.commons.core;

public class CommandWord {
	
	public enum Commands {
		ADD("ADD"), EDIT("EDIT"), DELETE("DELETE"), UNDO("UNDO"), 
		FIND("FIND"), STORAGE("STORAGE"), CLEAR("CLEAR"), DONE("DONE"), 
		EXIT("EXIT"), HELP("HELP"), LIST("LIST"), SORT("SORT"), ALIAS("ALIAS");
		
		private String commandRep;
		
		private Commands(String commandRep) {
			this.commandRep = commandRep;
		}
		
		public String getCommandRep() {
			return commandRep;
		}
		
		public String toString() {
			return commandRep;
		}
	}
}
