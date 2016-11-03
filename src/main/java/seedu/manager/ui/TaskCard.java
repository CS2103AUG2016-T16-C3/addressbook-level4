package seedu.manager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.manager.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private HBox overdue;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label venue;
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
    	initText();
    	initId();
    	initVenue();
        initTime();
        initDone();
        initTag();
        initPriorityColor();
        initTaskOverdue();
    }
    
    // @@author A0147924X
    /**
     * Colour overdue bar red if task is overdue
     */
    public void initTaskOverdue() {
        if (task.isTaskOverdue()) {
        	overdue.setStyle("-fx-background-color: red");
        }
    }
    
    // @@author
    public void initText() {
        desc.setText(task.getDesc().isPresent() ? task.getDesc().get().getPrettyValue() : "");
    }
    
    public void initId() {
        id.setText(displayedIndex + ". ");
    }
    
    public void initVenue() {
        venue.setText(task.getVenue().isPresent() ? "Venue : " + task.getVenue().get().getPrettyValue() : "");
    }
    
    // @@ author A0148003U
    public void initTime() {
    	if (task.getStartTime().isPresent()) {
    		if (task.getEndTime().isPresent()) {
    			startTime.setText("From   : " + task.getStartTime().get().getPrettyValue());
    			endTime.setText("To       : " + task.getEndTime().get().getPrettyValue()); 
    		}
    		
    		else {
    			startTime.setText("At        : " + task.getStartTime().get().getPrettyValue());
    			endTime.setText("");
    		}
    	}
    	
    	else {
    		if ((task.getEndTime().isPresent())) {
    			startTime.setText("By       : " + task.getEndTime().get().getPrettyValue());
    			endTime.setText("");  
    		}
    		
    		else {
    			startTime.setText("");
    			endTime.setText("");
    		}
    		}
    	
    }
    
    public void initDone() {
    	done.setText(task.getDone().isPresent() ? task.getDone().get().getPrettyValue() : "");
    	done.setStyle("-fx-font-size: 20pt; -fx-text-fill: green");
    }
    
    public void initTag() {
    	tag.setText(task.getTag().isPresent() ? "Tag     : " + task.getTag().get().getPrettyValue() : "");
    }
    
    // @@ author A0148003U
    public void initPriorityColor() {
        if (task.isPriorityHigh()) {
            setTextColor("red");
        } else if (task.isPriorityMedium()) {
        	setTextColor("orange");
        } else if (task.isPriorityLow()) {
        	setTextColor("green");
        }
    }
    
    public void setTextColor (String color) {
    	desc.setStyle("-fx-text-fill: " + color);        
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
