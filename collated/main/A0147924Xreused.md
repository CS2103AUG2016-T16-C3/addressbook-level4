# A0147924Xreused
###### \java\seedu\manager\ui\UiManager.java
``` java
    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent event) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	mainWindow.getTaskListPanel().scrollTo(event.getTargetIndex());
    }
    
```
