package seedu.manager.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.ReadOnlyTaskManager;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.Collections;
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
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
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

}
