package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;

/**
 * @@author A0148042M
 * Indicates a request to jump to the list of tasks
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTaskListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}