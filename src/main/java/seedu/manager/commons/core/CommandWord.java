package seedu.manager.commons.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandWord {
	
//	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	public enum Commands {
		ADD("ADD"), EDIT("EDIT"), DELETE("DELETE");
		
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
	public CommandWord(@JsonProperty("command") String command, @JsonProperty("commandWord") String commandWord) {
		for (Commands commandVar : Commands.values()) {
			if (commandVar.commandRep.equals(command)) {
				this.command = commandVar;
				break;
			}
		}
		
		assert this.command != null;
		
		this.commandWord = commandWord;
	}
	
	@Override
	public String toString() {
		return command.commandRep + " " + commandWord;
	}
}
