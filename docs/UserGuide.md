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
Task Ninja uses some [primary commands](#primary-commands), like `add`, `edit`, `delete` and `find`. These commands can then be made more specific using [extensions](#extensions), such as `at`, `before`, `from-to` and `priority`. <br>

This means that the primary commands are used in conjunction with extensions to give the manager more information about what you want to do. <br>

For example, you could edit the venue of the 1st task displayed by typing `edit 1 at Home`.

> The order of extensions is not fixed.

### Notation used:
`[]`: These square brackets represent optional argument(s)<br>
`<>`: These angular should be replaced with what you wish to enter, eg. an actual time if it says `<time>` <br>

### Primary Commands

<a id="help"></a>
#### Viewing help: `help`
_Overwhelmed by the amazingness of Task Ninja? `help` is here to help you discover more._<br>

Format: `help`

> This shows help for all commands. <br>

Example:
  * `help`


<a id="add"></a>
#### Adding a task to the list: `add`
_The most basic command... let's start adding tasks to the manager._<br>
Format: `add <task description> [<extensions>]`

> * Task description outlines the task to be added. <br>
> * Extensions allow specifying more details about the task, such as deadlines and venues. <br>
> * Without any time information, the task will be added as a task without a time (a floating task). <br>

Examples:
  * `add Dinner with Arthur`
  * `add Dinner with Arthur venue Avalon from 8:30pm to 9:30pm`
  * `add Finish 2103T Tutorial before 11:59pm`


<a id="find"></a>
#### Searching for tasks: `find`
_Forgotten when you arranged that date? Use `find`!_<br>
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
_Decided to postpone your homework? We let you do that too ;)_<br>
Format: `edit <task number> [<new task description>] [<extensions>]`

> * Task number specifies which out of the tasks on the screen you wish to modify. <br>
> * If given, the new task description will replace the old one for this task. <br>
> * Fields for the specified extensions will be changed. <br>

Examples:
  * `edit 1 Dinner with Guinevere`
  * `edit 2 at 1am 3 Oct`
  * `edit 1 Dinner with Guinevere venue Under the stars`


<a id="delete"></a>
#### Deleting tasks: `delete`
_Added a task you don't need? Fear not, for delete is here._<br>
Format: `delete <task number>`

> Task number specifies which out of the tasks on the screen you wish to delete. <br>

Examples:
  * `delete 1`
  * `delete 3`


<a id="exit"></a>
#### Exiting Task Ninja: `exit`
_This command closes Task Ninja. Hope to see you back soon!_<br>
Format: `exit`

Example:
  * `exit`


#### Saving the data
Tasks are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


<a id="next"></a>
#### List upcoming tasks: `next`
_Lists all upcoming tasks._<br>
Format: `next [before <time>]`

> * All tasks whose start times are after the current time are listed, by default. <br>
> * Floating tasks are also listed; they are listed at the bottom of the list. <br>
> * Can also be used with `before` to see upcoming tasks until a specified time/date. <br>

Example:
  * `next`
  * `next before 10pm`


<a id="undo"></a>
#### Undo previous action: `undo`
_Made a mistake? Fret not! Just use this keyword to revert the last action that you did!_<br>
Format: `undo`

> Can be used more than once. <br>

Example:
  * `undo`


<a id="storage"></a>
#### Specify a storage folder: `storage`
_Allows you to edit the storage file._<br>
Format: `storage <path/to/file/fileName.xml>`

> The file's name must have a .xml extension. <br>

Example:
  * `storage data/TaskNinja.xml`


### Extensions

<a id="venue"></a>
#### At a certain venue: `venue`
_Going to university and ended up at the mall? Remember your destination with this extension._<br>
Format: `venue <description of venue>`
 
Examples:
  * `add Lunch with Arthur venue Avalon`
  * `edit 1 venue Round Table`


<a id="from-to"></a>
#### Events that last from a certain period of time/date: `from-to`
_Mark tasks/events that will be done in a certain period of time/date._<br>
Format: `from <start time/date> to <end time/date>`

> * Start time/date indicates when the task/event begins, end time/date indicates when the task/event finishes. <br>
> * A list of supported time and date inputs are listed in [Appendix A](#appendix-a). You may also refer to the examples for a brief guide. <br>

Examples:
  * `add Meeting with Boss from 11am to 1pm`
  * `edit 1 from 10/20/16 to 10/25/16`
  * `add Collect computer from store from 2:30pm to 3:00pm`
  * `edit 4 from 15 Oct to 17 Oct`


<a id="before"></a>
#### Before a certain time/date: `before`
_Specify a deadline for a task._<br>
Format: `before <time/date>`

> A list of supported time and date inputs are listed in [Appendix A](#appendix-a). You may also refer to the examples for a brief guide too. <br>

Examples:
  * `add Finish 2103T Tutorial before 13:00`
  * `edit 3 before 6 Feb '17`
  * `add Complete assignment before Nov 2 2016 2359`
  * `edit 5 before 1st May`


<a id="priority"></a>
#### Priority level for tasks: `priority`
_Assign a priority level to tasks._<br>
Format: `priority <low/med/high>`

> * Helps give levels of importance to tasks. <br>
> * Priority level can be low, medium or high. <br>
> * The default priority level of a task is medium ("med"). <br>

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
Primary | [Next](#next) | `next [before <time>]`
Primary | [Undo](#undo) | `undo`
Primary | [Storage](#storage) | `storage <path/to/file/fileName.xml>`
Extension | [Venue](#venue) | `at <description of venue>`
Extension | [Event](#from-to) | `from <start time> to <end time>`
Extension | [Before](#before) | `before <time>`
Extension | [Priority](#priority) | `priority <low/med/high>`


# Appendix A
_This appendix lists down all the inputs of dates and times that are accepted when adding/editing a task._<br>
_As we are using PrettyTime parser to read in dates and times, we strongly recommend you to follow this table in order to let you have the best experience when using Task Ninja!_<br>
_You may also refer to the examples in [Event](#from-to) and [Before](#before) sections for an overview of how to input your dates and times when adding/editing your task._<br>

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
`23 February 2017` | Tue Feb 23 12:00:00 2017 |
`20 Oct` | Thu Oct 20 12:00:00 | Shortened months are accepted
`October 20` | Thu Oct 20 12:00:00 | Month and day can be reversed
`10/20` | Thu Oct 20 12:00:00 | Format is MM/DD - DD/MM is not accepted
`10/20/16` | Thu Oct 20 12:00:00 | Format is MM/DD/YY
`10/20/2016` | Thu Oct 20 12:00:00 | Format is MM/DD/YYYY
`today` | Wed Oct 19 12:00:00 | Uses current time by default
`tomorrow` | Thu Oct 20 12:00:00 | Uses current time by default
`next week` | Wed Oct 26 12:00:00 |
`Thu/Thu/Thur/Thurs/Thursday` | Thu Oct 20 12:00:00 | Not case sensitive, sets to upcoming Thursday
`next Mon` | Mon Oct 24 12:00:00 | Sets to upcoming Monday
`Tue` | Tue Oct 25 12:00:00 | Sets to upcoming Tuesday
`next Fri` | Fri Oct 28 12:00:00 | Sets to the Friday after the upcoming one
`next month` | Sat Nov 19 12:00:00 | Uses current day and time by default
`next year` | Thu Oct 19 12:00:00 2017 |
`19 Oct 1am` | Thu Oct 20 01:00:00 | Specify date and time
`21 Dec 14:00`/`Dec 21 14:00` | Wed Dec 21 14:00:00 | Specify date and time (24-hour format)

_The following are some formats that are incorrect and may not be read in correctly, or show an error._

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
