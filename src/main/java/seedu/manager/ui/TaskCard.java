package seedu.manager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Tag;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label venue;
    @FXML
    private Label priority;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label done;
    @FXML 
    private Label tag;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    // @@author A0147924X
    public void initialize() {
        desc.setText(task.getDesc().map(p -> p.getPrettyValue()).orElse(""));
        id.setText(displayedIndex + ". ");
        venue.setText(task.getVenue().map(p -> p.getPrettyValue()).orElse(""));
        priority.setText(task.getPriority().map(p -> p.getPrettyValue()).orElse(""));
        startTime.setText(task.getStartTime().map(p -> p.getPrettyValue()).orElse(""));
        endTime.setText(task.getEndTime().map(p -> p.getPrettyValue()).orElse(""));
        done.setText(task.getDone().map(p -> p.getPrettyValue()).orElse(""));
        tag.setText(task.getTag().map(p -> p.getPrettyValue()).orElse(""));
        
        done.setStyle("-fx-font-size: 20pt; -fx-text-fill: green");
    }
    
    // @@author
    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
