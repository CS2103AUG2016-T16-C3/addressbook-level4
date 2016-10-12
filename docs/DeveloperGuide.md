# Developer Guide

* [Setting Up](#setting-up)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)


## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


#### Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given
   in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

#### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.

**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import.
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).


## Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:
* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.manager.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.manager.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.manager.logic.LogicManagerTest`

**Headless GUI Testing** :
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

#### Troubleshooting tests
 **Problem: Tests fail because NullPointException when AssertionError is expected**
 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.


## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file your created.

### Managing Dependencies

A project often depends on third-party libraries. For example, Task Ninja depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>


## Appendix A : User Stories
Likeliness: Likely - `L`, Unlikely - `U`


Likeliness | As a... | I want to... | So that I can...
-------- | :-------- | :--------- | :-----------
`L` | All Users | Interact with the manager using a CLI and keys | Get rid of the need for a mouse
`L` | All Users | Use a keyboard shortcut to activate the manager | Speed up my workflow
`L` | New User | View the procedure of creating a task | Learn how to create a task first
`L` | New User | View info about a command | Learn how to use these commands
`L` | User | Add tasks with a description and a specific deadline | Set deadlines for tasks
`L` | User | Add tasks with description, start time and end time | Create events in the calendar
`L` | User | Add tasks with only a description | Set tasks that need to be completed at some point of time
`L` | User | Modify a task | Update deadlines and descriptions
`L` | User | Delete a task | Reflect my not having to do a task anymore in my to do list
`L` | User | Tick off a task | Record that this task is complete
`L` | User | Search for (a) task(s) | Find (a) task(s) and maybe edit it
`L` | User | See upcoming tasks | Decide what to do next
`L` | User | List [floating tasks](#floating-task) | See whether I want to complete a floating task next
`L` | User | Assign priority to tasks | Gauge which task should be done next
`L` | User | Sort upcoming tasks by priority | Make the decision of which task to complete next faster
`L` | User | See tasks in a specific period of time | See what has been scheduled for a certain period
`L` | User | See upcoming tasks up until a specified time | See less / more upcoming tasks according to the time frame I want
`L` | User | Block multiple slots for a task | Choose later which slot I want to assign this task to and keep the selected slots free for that task
`L` | User | Decide slot for an item blocking multiple slots | Free up the other slots for other tasks
`L` | User | Declare tasks that have to be done after a certain time | Record these tasks somewhere and not be bothered by them until a certain time
`L` | User | Receive emails/notifications about pressing deadlines | Be reminded to complete these tasks
`L` | User | Undo operation(s) | Remove a mistake
`L` | User | Redo operation(s) | Redo a change that had been undone
`L` | User | Declare recurring tasks | Remove the need to enter these tasks multiple times
`L` | User | Search for empty slots (within a given time frame) | Decide when to schedule a task
`L` | User | Specify a folder as the storage area | Access the tasks from multiple computers using cloud storage, or open them from a local storage.
`L` | Advanced User | Edit the storage file | Make changes without having to go through the manager
`L` | Advanced User | Declare my own names for commands | Personalise the experience and make it faster
`U` | User | Integrate with third-party applications like GCalendar | Access my tasks on another platform too


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `User`, unless specified otherwise)


#### Use case: View general help

**MSS**

1. User wants to view help
2. Task Manager opens online User Guide <br>
Use case ends.

**Extensions**

2a. TaskManager is unable to fetch online User Guide

> 2a1. Task Manager shows brief help page on screen <br>
  Use case ends


#### Use case: View help for specific command

**MSS**

1. User wants to view help for a certain command
2. Task Manager opens online User Guide at that command <br>
Use case ends.

**Extensions**

2a. TaskManager is unable to fetch online User Guide

> 2a1. Task Manager shows brief help page for that command on screen <br>
  Use case ends


#### Use case: Add a task

**MSS**

1. Users supplies Task Manager with information for a new task
2. Task Manager adds new task to its data <br>
Use case ends.

**Extensions**

2a. The given information is invalid

> 2a1. User is shown correct format for data <br>
Use case resumes at step 1

2b. Given information is identical to an already existing task

> 2b1. User is notified that task already exists <br>
Use case resumes at step 1


#### Use case: List specific tasks

**MSS**
1. User supplies Task Manager with information about what kinds of tasks should be listed
2. Task Manager shows the list of tasks <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

2b. The given information is invalid

> 2a1. User is shown correct format for data <br>
Use case resumes at step 1


#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. Task Manager shows a list of tasks
3. User requests to delete a specific task in the list
4. Task Manager deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

4a. The given index is invalid

> 4a1. User is notified that index is invalid <br>
Use case resumes at step 3


#### Use case: Edit task

**MSS**

1. User requests to list tasks
2. Task Manager shows a list of tasks
3. Users supplies Task Manager with new information for a specific task
4. Task Manager modifies the corresponding task

**Extensions**
2a. The list is empty

> Use case ends

4a. The given index is invalid

> 4a1. User is notified that index is invalid <br>
Use case resumes at step 3

4b. The given information is invalid

> 4b1. User is shown correct format for data <br>
Use case resumes at step 3


#### Use case: Tick off task

**MSS**

1. User requests to list tasks
2. Task Manager shows a list of tasks
3. User requests to mark a specific task in the list as done
4. Task Manager marks this task as done <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

4a. The given index is invalid

> 4a1. User is notified that index is invalid <br>
Use case resumes at step 3


#### Use case: Undo previous commands

**MSS**

1. User requests to undo a certain number of previous commands
2. Task Manager undoes that many commands <br>
Use case ends.

**Extensions**

2a. There are no previous commands to undo

> 2a1. User is notified that there are no previous commands to undo <br>
Use case ends

2b. There are fewer commands to undo than the given number

> 2b1. User is notified that they have not executed that many commands <br>
Use case resumes at step 1

2c. Given number is greater than 25 (Task Manager only stores previous 25 changes)

> 2b1. User is notified that they number entered is greater than 25 and that this is not allowed <br>
Use case resumes at step 1


#### Use case: Redo previous undoes

**MSS**

1. User requests to redo a certain number of previous commands
2. Task Manager redoes that many commands <br>
Use case ends.

**Extensions**

2a. There are no previous undone commands to redo

> 2a1. User is notified that there are no previous undone commands to redo <br>
Use case ends

2b. There are fewer undone commands to redo than the given number

> 2b1. User is notified that they have not undone that many commands <br>
Use case resumes at step 1

2c. Given number is greater than 25 (Task Manager only stores previous 25 changes)

> 2b1. User is notified that they number entered is greater than 25 and that this is not allowed <br>
Use case resumes at step 1


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should open within 1 second.
5. Should complete all operations within 0.5 seconds.
6. Should not require an internet connection to work.
7. Should not require installation to work.


## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Floating Task

> A task which has no specified date or time


## Appendix E : Product Survey

_We did a research to find out similar products' special features to enhance our design, here is the special features about four products that we searched on the market._

##### Todo.txt
1. Use command line to input task
2. Show a piece of message of the index of task added
3. Use plain text as the normal text
4. Sort the task by priority
5. Enter multiple tasks in a single input box

##### Google Calendar
1. Very detailed, but have to click many times to add a task
2. Visibility, public and private
3. Location, video call, can change the time zone
4. Attachment
5. Command line mode
> For the command line mode, it inputs the task between the [start] and [end], which is different from other products on the market.

##### Wunderlist
1. Amazing GUI
2. Reminding message, and the "Do not disturb" button
3. Mainly use a list to represent all the tasks rather than calendar
4. Wunderline
> the command line mode of Wunderlist <br>
> 1. Use natural words as commands, put the content inside the single quotation market. <br>
> 2. Export as JSON file
> 3. Search by keywords without ''

##### Calendar Vim
1. A calendar application for Vim
2. View events on Google Calendar
3. Have different view modes, like Year view, Month view, Week view and Day view.
