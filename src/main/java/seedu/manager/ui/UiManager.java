package seedu.manager.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.manager.MainApp;
import seedu.manager.commons.core.ComponentManager;
import seedu.manager.commons.core.Config;
import seedu.manager.commons.core.LogsCenter;
import seedu.manager.commons.events.storage.ConfigFilePathChangedEvent;
import seedu.manager.commons.events.storage.DataSavingExceptionEvent;
import seedu.manager.commons.events.ui.JumpToTagListRequestEvent;
import seedu.manager.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.manager.commons.events.ui.ShowHelpRequestEvent;
import seedu.manager.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.manager.commons.events.ui.TagPanelSelectionChangedEvent;
import seedu.manager.commons.util.StringUtil;
import seedu.manager.logic.Logic;
import seedu.manager.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }
    
    // @@author A0147924X-reused
    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent event) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	mainWindow.getTaskListPanel().scrollTo(event.getTargetIndex());
    }
    
    // @@author A0147924X
    @Subscribe
    /**
     * Scrolls to a tag when requested
     * @param event Event that requests jumping to a certain tag
     */
    private void handleJumpToTagListRequestEvent(JumpToTagListRequestEvent event) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	mainWindow.getTagListPanel().scrollTo(event.getTargetIndex());
    }
    
    // @@author A0148042M
    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
    
    @Subscribe
    private void handleTagListPanelSelectionChangedEvent(TagPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        System.out.println(event.getNewSelection());
        mainWindow.updateTaskListPanel(event.getNewSelection());
        
    }
    
    // @@author A0147924X
    @Subscribe
    /**
     * Updates file path on GUI when it is changed
     * @param event Event indicating that the file path has changed
     */
    public void handleConfigFilePathChangedEvent(ConfigFilePathChangedEvent event) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(event, "Storage location changed, updating status bar"));
    	mainWindow.rerenderStatusBarFooter();
    }
}
