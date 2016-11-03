package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;

// @@author A0147924X
/**
 * Indicates a request to jump to a certain tag
 *
 */
public class JumpToTagListRequestEvent extends BaseEvent {

    private final int targetIndex;

    public JumpToTagListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    public int getTargetIndex() {
		return targetIndex;
	}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
