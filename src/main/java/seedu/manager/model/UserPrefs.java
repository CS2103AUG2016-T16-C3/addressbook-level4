package seedu.manager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.manager.commons.core.CommandWord;
import seedu.manager.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public List<CommandWord> commandWords;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
        this.setCommandWords(
        		new String[]{
        				"ADD", "EDIT", "DELETE"
    				},
        		new String[]{
        				"add", "edit", "delete"
        			}
        		);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    public List<CommandWord> getCommandWords() {
    	return commandWords == null ? new ArrayList<>() : commandWords;
    }
    
    public void updateLastUsedCommandWords(List<CommandWord> commandWords) {
		this.commandWords = commandWords;
	}
    
    public void setCommandWords(String[] commands, String[] commandStrings) {
    	assert commands.length == commandStrings.length;
    	
		this.commandWords = new ArrayList<>();
		for (int i = 0; i < commands.length; i++) {
			commandWords.add(new CommandWord(commands[i], commandStrings[i]));
		}
	}

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
        return guiSettings.toString() + "\n" + Arrays.toString(commandWords.toArray());
    }

}
