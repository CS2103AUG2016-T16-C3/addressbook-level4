package seedu.manager.testutil;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.TaskManager;
import seedu.manager.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alpha, beta, charlie, delta, echo, foxtrot, golf, hotel, india, juliet;

    public TypicalTestTasks() {
        try {
            alpha =  new TaskBuilder().withDesc("Do CS2101 UG").withVenue("School of Computing")
                    .withStartTime("8:00pm").withEndTime("9:30pm").withPriority("med").withDone("No").withTag("study")
                    .build();
            beta = new TaskBuilder().withDesc("Do CS2101 DG").withVenue("School of Computing")
                    .withStartTime("9.30pm").withEndTime("11:00pm").withPriority("high").withDone("No").withTag("study")
                    .build();
            charlie = new TaskBuilder().withDesc("Do EE2021 Tutorial 4").withVenue("Faculty of Engineering")
                    .withStartTime("10:00am").withEndTime("12:00pm").withPriority("high").withDone("No").withTag("study")
                    .build();
            delta = new TaskBuilder().withDesc("EE2020 project").withVenue("E4 lab")
                    .withStartTime("2:00pm").withEndTime("5:00pm").withPriority("med").withDone("No").withTag("study")
                    .build();
            echo = new TaskBuilder().withDesc("Continue doing CS2103").withVenue("Home")
                    .withStartTime("8:30pm").withEndTime("10:00pm").withPriority("low").withDone("No").withTag("study")
                    .build();
            foxtrot = new TaskBuilder().withDesc("CS2103 group meetup").withVenue("PGP R5")
                    .withStartTime("8:00pm").withEndTime("10:00pm").withPriority("high").withDone("No").withTag("study")
                    .build();
            golf = new TaskBuilder().withDesc("Buy stuff for mum").withVenue("Popular @ Junction 8")
                    .withStartTime("6:00pm").withEndTime("6:10pm").withPriority("med").withDone("No").withTag("study")
                    .build();

            //Manually added
            hotel = new TaskBuilder().withDesc("Help dad fix his phone").withVenue("Home").withStartTime("6:30pm").withEndTime("7:00pm").withPriority("high").withDone("No").withTag("family").build();
            india = new TaskBuilder().withDesc("Help mum set up the computer properly").withVenue("Home").withStartTime("7:30pm").withEndTime("7:40pm").withPriority("med").withDone("No").withTag("family").build();
            juliet = new TaskBuilder().withDesc("Help Varun with debugging").withVenue("School of Computing").withStartTime("3:00pm").withEndTime("5:30pm").withPriority("high").withDone("No").withTag("family").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(alpha));
            ab.addTask(new Task(beta));
            ab.addTask(new Task(charlie));
            ab.addTask(new Task(delta));
            ab.addTask(new Task(echo));
            ab.addTask(new Task(foxtrot));
            ab.addTask(new Task(golf));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alpha, beta, charlie, delta, echo, foxtrot, golf};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
