# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
  * [Primary Commands](#primary-commands)
  * [Extensions](#extensions)
* [Cheat Sheet](#cheat-sheet)



## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.

3. Double-click the file to start the app. The GUI should appear in a few seconds.

4. To communicate with your task manager, type a command in the command box and press <kbd>Enter</kbd>

5. Some commands to get you started:
  * `help` : Bring up the help page.
  * `add Dinner with Mum` : Add a task `Dinner with Mum` to your list.
  * `find Dinner` : Finds all the tasks containing the word `Dinner`.
  * `delete 3` : Delete the 3rd task as visible in the current list.



## Features

### Overview
This task manager uses some [primary commands](#primary-commands), like `add`, `edit`, `delete` and `find`. These commands can then be made more specific using [extensions](#extensions), such as `at`, `before`, `after`, `from-to` and `priority`. <br>

This means that the primary commands are used in conjunction with extensions to give the manager more information about what you want to do. <br>

For example, you could edit the venue of the 1st task displayed by typing `edit 1 at Home`.

> The order of extensions is not fixed.

### Notation used:
`[date]`: Date is an optional argument<br>
`<hour>`: Replace with the actual hour

### Primary Commands

<a id="help"></a>
#### Viewing help: `help`
_Overwhelmed by the amazingness of Task Ninja? Help is here to help you discover more._<br>

Format: `help`

> * This shows help for all commands. <br>

Examples:
  * `help`


<a id="add"></a>
#### Adding a task to the list: `add`
_The most basic command... let's start adding tasks to the manager._<br>
Format: `add <task description> [<extensions>]`

> * Task description outlines the task to be added. <br>
> * Extensions allow specifying more details about the task, such as deadlines and venues. <br>
> * Without any timing information, the task will be added as a task without a time (a floating task). <br>

Examples:
  * `add Dinner with Arthur`
  * `add Dinner with Arthur at Avalon from 8:30pm to 9:30pm`
  * `add Finish 2103T Tutorial before 11:59pm`
  * `add Pay bills after 11:59pm priority low`


<a id="find"></a>
#### Searching for tasks: `find`
_Forgotten when you arranged that date? Use find!_<br>
Format: `find <keywords> [<more keywords>]`

> * The search is case sensitive. e.g `dinner` will not match `Dinner`.
> * The order of the keywords does not matter. e.g. `Tutorial CS2103T` will match `CS2103T Tutorial`.
> * Only the description is searched for.
> * Only full words will be matched e.g. `2103T` will not match `CS2103T`.
> * Tasks matching at least one keyword will be returned (i.e. OR search). e.g. `Dinner` will match `Dinner with Mum`.

Examples:
  * `find Tutorial`<br>
    Shows all tasks containing `Tutorial` in the description
  * `find Dinner Tutorial`<br>
    Shows all tasks containing `Dinner` or `Tutorial` in their descriptions


<a id="edit"></a>
#### Editing tasks: `edit`
_Decide to postpone your homework? We let you do that too ;)_<br>
Format: `edit <task number> [<new task description>] [<extensions>]`

> * Task number specifies which out of the tasks on the screen you wish to modify. <br>
> * If given, the new task description will replace the old one for this task. <br>
> * Fields for the specified extensions will be changed. <br>

Examples:
  * `edit 1 Dinner with Guinevere`
  * `edit 2 at 1am 3 Oct`
  * `edit 1 Dinner with Guinevere at Under the stars`


<a id="delete"></a>
#### Deleting tasks: `delete`
_Added a task you don't need? Fear not, for delete is here._<br>
Format: `delete <task number>`

> * Task number specifies which out of the tasks on the screen you wish to delete. <br>

Examples:
  * `delete 1`
  * `delete 3`


<a id="exit"></a>
#### Exiting the task manager: `exit`
_This command closes the task manager. Hope to see you back soon!_<br>
Format: `exit`

Examples:
  * `exit`


#### Saving the data
Tasks are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


### Extensions

<a id="venue"></a>
#### At a certain venue: `at`
_Going to university and ended up at the mall? Remember your destination with this extension._<br>
Format: `at <description of venue>`

Examples:
  * `add Lunch with Arthur at Avalon`
  * `edit 1 at Round Table`


<a id="from-to"></a>
#### Events that last from a certain period of time: `from to`
_Mark tasks/events that will be done in a certain period of time._<br>
Format: `from <start time> to <end time>`

> * Start time indicates when the event begins, end time indicates when the event finishes. <br>
> * Times are stored as plaintext, i.e. exactly how they were entered. <br>

Examples:
  * `add Meeting with Boss from 11am to 1pm`
  * `edit 1 from 12pm to 1pm`


<a id="before"></a>
#### Before a certain time: `before`
_Specify a deadline for a task._<br>
Format: `before <time>`

> * Time is stored as plaintext.

Examples:
  * `add Finish 2103T Tutorial before 1pm`
  * `edit 3 before 12:30pm`


<a id="after"></a>
#### After a certain time: `after`
_Mark a task that should be done after a certain time._<br>
Format: `after <time>`

> * Time is stored as plaintext.

Examples:
  * `add Pay bills after 11:50pm`
  * `edit 2 after 11:59pm`


<a id="priority"></a>
#### Priority level for tasks: `priority`
_Assign a priority level to tasks._<br>
Format: `priority <low/med/high>`

> * Helps give levels of importance to tasks. <br>
> * Priority level can be low, medium or high. <br>

Examples:
  * `add Lunch with Arthur priority high`
  * `edit 1 priority med`



## Cheat Sheet

Type | Command | Format
:--------: | :--------: | ----- |
Primary | [Help](#help) | `help`
Primary | [Add](#add) | `add <task description> [<extensions>]`
Primary | [Find](#find) | `find <keyword> [<more keywords>]`
Primary | [Edit](#edit) | `edit <task number> [<extensions>]`
Primary | [Delete](#delete) | `delete <task number>`
Primary | [Exit](#exit) | `exit`
Extension | [Venue](#venue) | `at <description of venue>`
Extension | [Event](#from-to) | `from <start time> to <end time>`
Extension | [Before](#before) | `before <time>`
Extension | [After](#after) | `after <time>`
Extension | [Priority](#priority) | `priority <low/med/high>`
