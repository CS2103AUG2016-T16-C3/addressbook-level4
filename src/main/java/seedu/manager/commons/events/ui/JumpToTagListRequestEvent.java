package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;

/**
 * @@author A0147924X
 * Indicates a request to jump to a certain tag
 *
 */
public class JumpToTagListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTagListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
