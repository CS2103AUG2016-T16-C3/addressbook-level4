package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Email email;
    private Phone phone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        sb.append("a/" + this.getPriority().getValue() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
