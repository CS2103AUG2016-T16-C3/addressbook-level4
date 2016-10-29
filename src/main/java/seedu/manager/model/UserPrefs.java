package seedu.manager.model;

import java.util.HashMap;
import java.util.Objects;

import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.events.storage.UserPrefsChangedEvent;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public HashMap<Commands, String> commandWords;
    public HashMap<Commands, String> extensionWords;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }
    
    // @@author A0147924X
    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
        this.setCommandWords(
        		new Commands[]{
        				Commands.ADD, Commands.EDIT, Commands.DELETE, Commands.UNDO, 
        				Commands.FIND, Commands.STORAGE, Commands.CLEAR, Commands.DONE, 
        				Commands.EXIT, Commands.HELP, Commands.LIST, Commands.SORT,
        				Commands.ALIAS
    				},
        		new String[]{
        				"add", "edit", "delete", "undo", 
        				"find", "storage", "clear", "done", 
        				"exit", "help", "list", "sort",
        				"alias"
        			}
        		);
        this.setExtensionsWords(
        		new Commands[]{
        				Commands.BY, Commands.AT, Commands.EVENT, Commands.PRIORITY, 
        				Commands.TAG, Commands.VENUE
    				},
        		new String[]{
        				"by", "at", "from", "priority", 
        				"tag", "venue"
        			}
        		);
    }
    
    // @@author
    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    // @@author A0147924X
    public HashMap<Commands, String> getCommandWords() {
    	return commandWords;
    }
    
    public HashMap<Commands, String> getExtensionsWords() {
    	return extensionWords;
    }
    
    public void setCommandWords(Commands[] commands, String[] commandStrings) {
    	assert commands.length == commandStrings.length;
    	
		this.commandWords = new HashMap<>();
		for (int i = 0; i < commands.length; i++) {
			commandWords.put(commands[i], commandStrings[i]);
		}
	}
    
    public void setExtensionsWords(Commands[] commands, String[] commandStrings) {
    	assert commands.length == commandStrings.length;
    	
    	this.extensionWords = new HashMap<>();
    	for (int i = 0; i < commands.length; i++) {
    		extensionWords.put(commands[i], commandStrings[i]);
    	}
    }
    
    public void setSingleCommandWord(String commandToChange, String alias,
    		String messageNoMatch, String messageAliasAlreadyTaken) throws IllegalValueException {
    	
    	Commands matchedCommand = getMatchingCommand(commandToChange, messageNoMatch);
    	throwExceptionIfAliasAlreadyExists(matchedCommand, alias, messageAliasAlreadyTaken);
    	
    	if (commandWords.containsKey(matchedCommand)) {
    		commandWords.put(matchedCommand, alias);
		} else {
			extensionWords.put(matchedCommand, alias);
		}
		
    	EventsCenter.getInstance().post(new UserPrefsChangedEvent(this));
    }
    
    private Commands getMatchingCommand(String commandToChange, String messageNoMatch) throws IllegalValueException {
    	for (Commands command : Commands.values()) {
			if (commandWords.containsKey(command) && commandWords.get(command).equals(commandToChange)) {
				return command;
			} else if (extensionWords.containsKey(command) && extensionWords.get(command).equals(commandToChange)) {
				return command;
			}
		}
    	
    	throw new IllegalValueException(messageNoMatch);
    }
    
    private void throwExceptionIfAliasAlreadyExists(Commands matchedCommand, String alias, String messageAliasAlreadyTaken)
    		throws IllegalValueException {
    	for (Commands command : Commands.values()) {
			if (!command.equals(matchedCommand)) {
				if (commandWords.containsKey(command) && commandWords.get(command).equals(alias)) {
					throw new IllegalValueException(String.format(messageAliasAlreadyTaken, command));
				} else if (extensionWords.containsKey(command) && extensionWords.get(command).equals(alias)) {
					
				}
			}
		}
    }
    
    // @@author
    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString() + "\n" + commandWords;
    }

}
