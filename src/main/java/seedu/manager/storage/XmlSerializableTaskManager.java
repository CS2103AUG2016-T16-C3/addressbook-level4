package seedu.manager.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.ReadOnlyTaskManager;
import seedu.manager.model.tag.UniqueTagList;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskManager() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                Task toAdd = p.toModelType(); 
                lists.add(toAdd);
            } catch (IllegalValueException e) {
                //TODO: better error handling
            	e.printStackTrace();
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tag> getTagList() {
        // TODO Auto-generated method stub
        return null;
    }

}
