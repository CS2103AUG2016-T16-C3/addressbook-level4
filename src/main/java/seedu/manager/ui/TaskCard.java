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
    public void initialize() {
        desc.setText(task.getDesc().isPresent() ? task.getDesc().get().getPrettyValue() : "");
        id.setText(displayedIndex + ". ");
        venue.setText(task.getVenue().isPresent() ? task.getVenue().get().getPrettyValue() : "");
        priority.setText(task.getPriority().isPresent() ? task.getPriority().get().getPrettyValue() : "");
        startTime.setText(task.getStartTime().isPresent() ? task.getStartTime().get().getPrettyValue() : "");
        endTime.setText(task.getEndTime().isPresent() ? task.getEndTime().get().getPrettyValue() : "");
        done.setText(task.getDone().isPresent() ? task.getDone().get().getPrettyValue() : "");
        tag.setText(task.getTag().isPresent() ? task.getTag().get().getPrettyValue() : "");
    }

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
