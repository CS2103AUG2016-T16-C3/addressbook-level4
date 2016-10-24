package seedu.manager.commons.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandWord {
	
	public enum Commands {
		ADD("ADD"), EDIT("EDIT"), DELETE("DELETE"), UNDO("UNDO"), 
		FIND("FIND"), STORAGE("STORAGE"), CLEAR("CLEAR"), DONE("DONE"), 
		EXIT("EXIT"), HELP("HELP"), LIST("LIST"), SORT("SORT");
		
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
	
	private Commands command = null;
	private String commandWord = null;
	
	@JsonCreator
	public CommandWord(@JsonProperty("command") Commands command, 
			           @JsonProperty("commandWord") String commandWord) {
		this.command = command;
		this.commandWord = commandWord;
	}
	
	@Override
	public String toString() {
		return command.commandRep + " " + commandWord;
	}
}
