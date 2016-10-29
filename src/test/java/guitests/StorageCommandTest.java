package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.manager.TestApp;
import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.events.storage.ConfigFilePathChangedEvent;
import seedu.manager.logic.commands.StorageCommand;
import seedu.manager.testutil.TestUtil;

// @@author A0147924X
public class StorageCommandTest extends TaskManagerGuiTest {
	@Test
	public void storage() throws IOException, InterruptedException {
		String wrongExtensionFilePath = "WrongExtension";
		commandBox.runCommand("storage " + wrongExtensionFilePath);
		assertResultMessage(StorageCommand.MESSAGE_WRONG_EXTENSION);
		
		String unWriteableFilePath = TestUtil.getFilePathInSandboxFolder("unwritable.xml");
		File unWriteableFile = new File(unWriteableFilePath);
		File unWriteableFolder = new File(unWriteableFilePath).getParentFile();
		unWriteableFolder.setWritable(false);
		Thread.sleep(300);
		if (!System.getProperty("os.name").startsWith("Windows")) {
			// Test fails on windows, cannot restrict access to folders
			commandBox.runCommand("storage " + unWriteableFilePath);
			assertResultMessage(StorageCommand.MESSAGE_NO_PERMISSION);
		}
		
		
		unWriteableFolder.setWritable(true);
		Thread.sleep(300);
		unWriteableFile.createNewFile();
		Thread.sleep(300);
		unWriteableFolder.setWritable(false);
		Thread.sleep(300);
		if (!System.getProperty("os.name").startsWith("Windows")) {
			// Test fails on windows, cannot restrict access to folders
			commandBox.runCommand("storage " + unWriteableFilePath);
			assertResultMessage(StorageCommand.MESSAGE_ALREADY_EXISTS_NO_OVERWRITE);
		}
		
		unWriteableFolder.setWritable(true);
		Thread.sleep(300);
		unWriteableFile.delete();
		Thread.sleep(300);
		
		String alreadyExistsFilePath = TestApp.SAVE_LOCATION_FOR_TESTING;
		commandBox.runCommand("storage " + alreadyExistsFilePath);
		assertResultMessage(String.format(StorageCommand.MESSAGE_ALREADY_EXISTS_SUCCESS, alreadyExistsFilePath));
		
		String newFilePath = TestUtil.getFilePathInSandboxFolder("newFile.xml");
		File newFile = new File(newFilePath);
		newFile.delete();
		Thread.sleep(300);
		commandBox.runCommand("storage " + newFilePath);
		assertResultMessage(String.format(StorageCommand.MESSAGE_SUCCESS, newFilePath));
		
		String throwsNullExceptionPath = "taskmanager.xml";
		commandBox.runCommand("storage " + throwsNullExceptionPath);
		assertResultMessage(StorageCommand.MESSAGE_NO_PERMISSION);

		String throwsNullExceptionOverwritePath = "taskninja.xml";
		File throwsNullExceptionOverwriteFile = new File(throwsNullExceptionOverwritePath);
		throwsNullExceptionOverwriteFile.createNewFile();
		Thread.sleep(300);
		commandBox.runCommand("storage " + throwsNullExceptionOverwritePath);
		assertResultMessage(StorageCommand.MESSAGE_ALREADY_EXISTS_NO_OVERWRITE);
		throwsNullExceptionOverwriteFile.delete();
		Thread.sleep(300);
		
		String resetFilePath = "data/taskmanager.xml";
		EventsCenter.getInstance().post(new ConfigFilePathChangedEvent(resetFilePath)); // Reset storage location back to default
	}
}
