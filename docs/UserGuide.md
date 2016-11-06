<!-- @@author A0147924X -->
# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
  * [Primary Commands](#primary-commands)
    * [Extensions](#extensions)
* [Cheat Sheet](#cheat-sheet)
* [Appendix A](#appendix-a)



## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `taskninja.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for Task Ninja.

3. Double-click the file to start the app. The GUI should appear in a few seconds.

4. To communicate with Task Ninja, type a command in the command box and press <kbd>Enter</kbd>

5. Some commands to get you started:
  * `help` : Bring up the help page.
  * `add Dinner with Mum` : Add a task `Dinner with Mum` to your list.
  * `find Dinner` : Finds all the tasks containing the word `Dinner`.
  * `delete 3` : Delete the 3rd task as visible in the current list.



## Features

### Overview
Task Ninja uses some [primary commands](#primary-commands), like `add`, `edit`, `delete` and `find`. Some of these commands, like `add` and `edit`, can then be made more specific using [extensions](#extensions), such as `at`, `by`, `from-to` and `priority`. <br>

This means that the primary commands are used in conjunction with extensions to give the manager more information about what you want to do. <br>

For example, you could edit the venue of the 1st task displayed by typing `edit 1 venue Home`.

> The order of extensions is not fixed.

### Notation used:
`[]`: These square brackets represent optional argument(s)<br>
`<>`: These angular brackets should be replaced with what you wish to enter, eg. an actual time if it says `<time>` <br>

### Primary Commands

<a id="help"></a>
#### Viewing help: `help`
_Overwhelmed by the amazingness of Task Ninja? `help` is here to help you discover more._<br>

Format: `help [<command>]`

> This shows help for all commands (In fact it opens this User Guide)<br>
> If a command is specified, then it shows help for that command only

Examples:
  * `help`
  * `help add`
  * `help delete`


<a id="add"></a>
#### Adding a task to the list: `add`
_The most basic command... let's start adding tasks to the manager._<br>
Format: `add <task description> [<extensions>]`

> * Task description outlines the task to be added. <br>
> * Extensions allow specifying more details about the task, such as deadlines and venues. <br>
> * Without any time information, the task will be added as a task without a time (a floating task). <br>

Examples:
  * `add Dinner with Arthur`
  * `add Elope with Guinevere`


<br>
<a id="extensions"></a>
#### Extensions
_But now you're probably wondering... what extensions can I use? How do they help me?
Well to answer that, let's just dive straight into our extensions:_<br>

<a id="venue"></a>
##### At a certain venue: `venue`
_Going to university but somehow ended up wandering at the mall aimlessly? Remember your destination with this extension._<br>
Format: `venue <description of venue>`

Examples:
  * `add Lunch with Arthur venue Avalon`
  * `add Date with Girlfriend venue Romantic spot`


<a id="from-to"></a>
##### Events that last for a certain period of time: `from-to`
_Mark tasks/events that will be done over a certain period of time._<br>
Format: `from <start time> to <end time>`

> * Start time/date indicates when the task/event begins, end time/date indicates when the task/event finishes. <br>
> * A list of supported time and date inputs are listed in [Appendix A](#appendix-a). You may also refer to the examples for a brief guide. <br>

Examples:
  * `add Meeting with Boss from 11am to 1pm`
  * `add Collect computer from store from 2:30pm to 3:00pm`
  * `add Picnic with Guinevere from tomorrow morning to tomorrow evening`


<a id="at"></a>
##### At a certain time/date: `at`
_Specify a start time for a task._<br>
Format: `at <time>`

Examples:
  * `add Commence update at 23:00`
  * `add Ballet lesson at day after tomorrow 9pm`


<a id="by"></a>
##### By a certain time/date: `by`
_Specify a deadline for a task._<br>
Format: `by <time>`

Examples:
  * `add Finish 2103T Tutorial by 13:00`
  * `add Complete assignment by 15 Nov 23:59`


<a id="priority"></a>
##### Priority level for tasks: `priority`
_Assign a priority level to tasks._<br>
Format: `priority low/med/high`

> * Helps give levels of importance to tasks. <br>
> * Priority level can be low, medium or high. <br>

Examples:
  * `add Lunch with Arthur priority low`
  * `add Steal the Round Table priority high`


<a id="tag"></a>
##### Tag: `tag`
_Adds a tag to your tasks._<br>
Format: `tag <your tag>`

> * Only one tag can be added to a task. <br>
> * You may replace the task's tag using the `edit` command. <br>

Examples:
  * `add Buy Table Lamp tag shopping`
  * `edit 4 tag projects`


<br>
##### Combining multiple extensions
_Extensions can be used together for maximum flexibility. This means that you can add complex tasks using commands as natural as:_
  * `add Complete assignment by 3pm priority high tag School`
  * `add Appointment with dentist at 5:30pm venue Bright Teeth Clinic`


<br>
_Alright! Armed with these extensions, let's take a look at some other primary commands that help enhance your experience with Task Ninja._

<a id="find"></a>
#### Searching for tasks: `find`
_Forgotten when you arranged that date? Use `find`!_<br>
Format: `find [<description>] [<extensions>]`

> * For matching against description and venue:
    * The search is case insensitive
    * The order of the keywords does not matter. e.g. `Tutorial CS2103T` will match `CS2103T Tutorial`.
    * Only full words will be matched e.g. `2103T` will not match `CS2103T`.
    * Tasks matching at least one keyword will be returned (i.e. OR search). e.g. `Dinner` will match `Dinner with Mum`.

Examples:
  * `find Tutorial`<br>
    Shows all tasks containing `Tutorial` in the description.
  * `find venue Room Home`<br>
    Shows all tasks containing `Home` or `Room` in their descriptions.
  * `find by tomorrow`<br>
    Shows all tasks that have to be completed before tomorrow. Includes overdue tasks.
  * `find from tomorrow to day after tomorrow`<br>
    Shows all tasks that are due between tomorrow and day after tomorrow.
  * `find priority high`<br>
    Shows all tasks with a high priority.
  * `find dinner priority med`<br>
    Shows all tasks that contain `dinner` in their descriptions and have a medium priority


<a id="edit"></a>
#### Editing tasks: `edit`
_Decided to postpone your homework? We let you do that too ;)_<br>
Format: `edit <task number> [<new task description>] [<extensions>]`

> * Task number specifies which out of the tasks on the screen you wish to modify. <br>
> * If given, the new task description will replace the old one for this task. <br>
> * Fields for the specified extensions will be changed. <br>

Examples:
  * `edit 1 Dinner with Guinevere tag Romantic`
  * `edit 2 at 1am 3 Oct`
  * `edit 1 Dinner with Guinevere venue Under the stars`


<a id="delete"></a>
#### Deleting tasks: `delete`
_Added a task you don't need? Fear not, for delete is here._<br>
Format: `delete <task number>`

> The task number specifies the task on the current screen that you wish to delete. <br>

Examples:
  * `delete 1`
  * `delete 3`


<!-- @@author A0139621H-->
<a id="undo"></a>
#### Undo previous action: `undo`
_Made a mistake? Fret not! Just use this keyword to revert the last action that you did!_<br>
Format: `undo`

> * Can be used more than once. <br>
> * Cannot be used if you close TaskNinja and reopen it; the temporary history of commands that you use will be cleared and there will be nothing for TaskNinja to undo. <br>
> * The commands that you can undo are: add, edit, delete and alias

Example:
  * `undo`


<a id="sortby"></a>
#### Sortby: `sortby`
_Sorts your task list either by time, or by priority._<br>
Format: `sortby time/priority`

> Even if priority or time have been aliased, this command will still use the words priority and time

Examples:
  * `sortby time`
  * `sortby priority`


<a id="storage"></a>
#### Specify a storage folder: `storage`
_Allows you to edit the storage file._<br>
Format: `storage <path/to/file/fileName.xml>`

> The file's name must have a .xml extension. <br>

Example:
  * `storage data/TaskNinja.xml`


<a id="alias"></a>
#### Alias Commands: `alias`
_If you're an advanced user, you may replace our existing commands with a keyword of your own!_<br>
Format: `alias <command name> <new command name>`

> * Take note that you cannot replace a command with a keyword that is already in use. <br>
> * To a command's alias, simply use help for that command. <br>

Examples:
  * `alias add +`
  * `alias + add`
  * `alias priority p`


<a id="clear"></a>
#### Clear: `clear`
_Wipes off all tasks and tags from TaskNinja._<br>
Format: `clear`

> * *WARNING*: This command is irreversible! It cannot be undone even with the `undo` command! <br>
> * You should hence be VERY sure if you wish to use this command at any point in time, unless you have saved your tasks to another .xml file! <br>


<a id="exit"></a>
#### Exiting Task Ninja: `exit`
_This command closes Task Ninja. Hope to see you back soon!_<br>
Format: `exit`

Example:
  * `exit`


#### Saving the data
Tasks are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


<!-- @@author A0147924X -->
## Cheat Sheet

Type | Command | Format | Example |
:--------: | :--------: | ----- | ---- |
Primary | [Help](#help) | `help [<command>]` | `help add`
Primary | [Add](#add) | `add <task description> [<extensions>]` | `add Complete assignment by 3pm priority high tag School`
Primary | [Find](#find) | `find [<description>] [<extensions>]` | `find dinner priority med`
Primary | [Edit](#edit) | `edit <task number> [<new task description>] [<extensions>]` | `edit 1 Dinner with Mum venue Home`
Primary | [Delete](#delete) | `delete <task number>` | `delete 1`
Primary | [Undo](#undo) | `undo` | `undo`
Primary | [Sortby](#sortby) | `sortby time/priority` | `sortby priority`
Primary | [Storage](#storage) | `storage <path/to/file/fileName.xml>` | `storage data/TaskNinja.xml`
Primary | [Alias](#alias) | `alias <command name> <new command name>` | `alias delete -`
Primary | [Clear](#clear) | `clear` | `clear`
Primary | [Exit](#exit) | `exit` | `exit`
Extension | [Venue](#venue) | `venue <description of venue>` | `venue Avalon`
Extension | [Event](#from-to) | `from <start time> to <end time>` | `from 1pm to 3pm`
Extension | [At](#at) | `at <time>` | `at 5pm`
Extension | [By](#by) | `by <time>` | `by tomorrow evening`
Extension | [Priority](#priority) | `priority low/med/high` | `priority med`
Extension | [Tag](#tag) | `tag <your tag>` | `tag Camelot`


<!-- @@author A0139621H-->
# Appendix A
_This appendix lists down all the inputs of dates and times that are accepted when adding/editing a task._<br>
_As we are using PrettyTime parser to read in dates and times, we strongly recommend you to follow this table in order to let you have the best experience when using Task Ninja!_<br>
_You may also refer to the examples in [Event](#from-to), [At](#at) and [By](#by) sections for an overview of how to input your dates and times when adding/editing your task._<br>

> * The current time (in HH:MM:SS) will be timestamped to your tasks should you decide not to enter a time.
> * The following examples use Wed 19 Oct 12:00:00 2016 as the current time and date, unless otherwise specified

Input | Read in as | Comments
----- | :--------: | ------- |
`2am` | Wed Oct 19 02:00:00 | 12-hour format
`2AM` | Wed Oct 19 02:00:00 | Not case-sensitive
`2pm` | Wed Oct 19 14:00:00 |
`3:30am` | Wed Oct 19 03:30:00 |
`4:45pm` |  Wed Oct 19 16:45:00 |
`0515` | Wed Oct 19 05:15:00 | 24-hour format
`1700` | Wed Oct 19 17:00:00 |
`6:00` | Wed Oct 19 06:00:00 |
`07:55` | Wed Oct 19 07:55:00 |
`19:59` | Wed Oct 19 19:59:00 |
`20 November` | Sun Nov 20 12:00:00 | Uses current time by default
`22 January '17` | Sun Jan 22 12:00:00 2017 | Uses an apostrophe in shortened year
`23rd February 2017` | Tue Feb 23 12:00:00 2017 | Numerals (st, nd, rd, th) are accepted
`20 Oct` | Thu Oct 20 12:00:00 | Shortened months are accepted
`October 20` | Thu Oct 20 12:00:00 | Month and day can be reversed
`10/20` | Thu Oct 20 12:00:00 | Format is MM/DD - DD/MM is not accepted
`10/20/16` | Thu Oct 20 12:00:00 | Format is MM/DD/YY
`10/20/2016` | Thu Oct 20 12:00:00 | Format is MM/DD/YYYY
`today` | Wed Oct 19 12:00:00 | Uses current time by default
`tomorrow` | Thu Oct 20 12:00:00 | Uses current time by default
`next week` | Wed Oct 26 12:00:00 |
`thu/Thu/Thur/Thurs/Thursday` | Thu Oct 20 12:00:00 | Not case sensitive, sets to upcoming Thursday
`next Mon` | Mon Oct 24 12:00:00 | Sets to upcoming Monday
`Tue` | Tue Oct 25 12:00:00 | Sets to upcoming Tuesday
`next Fri` | Fri Oct 28 12:00:00 | Sets to the Friday after the upcoming one
`next month` | Sat Nov 19 12:00:00 | Uses current day and time by default
`next year` | Thu Oct 19 12:00:00 2017 |
`19 Oct 1am` | Thu Oct 20 01:00:00 | Specify date and time
`21 Dec 14:00`/`Dec 21 14:00` | Wed Dec 21 14:00:00 | Specify date and time (24-hour format)
`Nov 2 2016 2359` | Wed Nov 02 23:59:00 2016 | The second 4-digit number will be set as time (24-hour format)

_The following are some formats that are incorrect and may not be read in correctly. An error may be shown for some cases._

Input | Read in as | Comments
----- | :--------: | ------- |
`1` | Wed Oct 19 01:00:00 | Just a single number is ambiguous and should be avoided
`00` | Wed Oct 19 00:00:00 | Similarly, just `00` is also ambiguous
`19/10` | Wed Oct 19 19:00:00 | Read not as a date, but as 1900 hours - remember to use MM/DD
`19/10/16` | Wed Oct 19 19:00:00 | Same as above
`19/10/2016` | Wed Oct 19 19:00:00 | Same as above
`23 Feb 17` | Tue Feb 23 16:00:00 2016 | Without the apostrophe before `16`, it's read incorrectly
`23 Feb 1400` | Mon Feb 23 12:00:00 1400 | `1400` is read in as an year instead of time
`next next week` | Wed Oct 26 12:00:00 | The second `next` is ignored
`the day after` | Cannot be read | Add in a tomorrow, and we can read it :)
`the following day` | Cannot be read |
`the week after` | Cannot be read |
