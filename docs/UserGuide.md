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
`<>`: These chevrons are to be replaced with what you wish to enter, eg. an actual time if it shows `<time>` <br>

### Primary Commands

<a id="help"></a>
#### Viewing help: `help`
_Overwhelmed by the amazingness of Task Ninja? Help is here to help you discover more._<br>

Format: `help`

> * This shows help for all commands. <br>

Example:
  * `_help_`


<a id="add"></a>
#### Adding a task to the list: `add`
_The most basic command... let's start adding tasks to the manager._<br>
Format: `add <task description> [<extensions>]`

> * Task description outlines the task to be added. <br>
> * Extensions allow specifying more details about the task, such as deadlines and venues. <br>
> * Without any timing information, the task will be added as a task without a time (a floating task). <br>

Examples:
  * `_add_ Dinner with Arthur`
  * `_add_ Dinner with Arthur venue Avalon from 8:30pm to 9:30pm`
  * `_add_ Finish 2103T Tutorial before 11:59pm`


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
  * `_find_ Tutorial`<br>
    Shows all tasks containing `Tutorial` in the description
  * `_find_ Dinner Tutorial`<br>
    Shows all tasks containing `Dinner` or `Tutorial` in their descriptions


<a id="edit"></a>
#### Editing tasks: `edit`
_Decide to postpone your homework? We let you do that too ;)_<br>
Format: `edit <task number> [<new task description>] [<extensions>]`

> * Task number specifies which out of the tasks on the screen you wish to modify. <br>
> * If given, the new task description will replace the old one for this task. <br>
> * Fields for the specified extensions will be changed. <br>

Examples:
  * `_edit_ 1 Dinner with Guinevere`
  * `_edit_ 2 at 1am 3 Oct`
  * `_edit_ 1 Dinner with Guinevere venue Under the stars`


<a id="delete"></a>
#### Deleting tasks: `delete`
_Added a task you don't need? Fear not, for delete is here._<br>
Format: `delete <task number>`

> * Task number specifies which out of the tasks on the screen you wish to delete. <br>

Examples:
  * `_delete_ 1`
  * `_delete_ 3`


<a id="exit"></a>
#### Exiting Task Ninja: `exit`
_This command closes Task Ninja. Hope to see you back soon!_<br>
Format: `exit`

Example:
  * `_exit_`


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
  * `_next_`
  * `_next_ before 10pm`


<a id="undo"></a>
#### Undo previous action: `undo`
_Made a mistake? Fret not! Just use this keyword to revert the last action that you did!_<br>
Format: `undo'

> * Can be used more than once. <br>

Example:
  * `_undo_`


<a id="storage"></a>
#### Specify a storage folder: `storage`
_Allows you to edit the storage file._<br>
Format: `storage <FilePath\<FileName>.xml>`

> * The file's name must have a .xml extension. <br>

Example:
  * `storage .\src\test\data\sandbox\TaskNinja.xml`


### Extensions

<a id="venue"></a>
#### At a certain venue: `venue`
_Going to university and ended up at the mall? Remember your destination with this extension._<br>
Format: `venue <description of venue>`
 
Examples:
  * `_add_ Lunch with Arthur venue Avalon`
  * `_edit_ 1 venue Round Table`


<a id="from-to"></a>
#### Events that last from a certain period of time/date: `from-to`
_Mark tasks/events that will be done in a certain period of time/date._<br>
Format: `from <start time/date> to <end time/date>`

> * Start time/date indicates when the task/event begins, end time/date indicates when the task/event finishes. <br>
> * A list of supported time and date inputs are listed in [Appendix A](#appendix-a). You may also refer to the examples for a brief guide too. <br>

Examples:
  * `add Meeting with Boss _from_ 11am _to_ 1pm`
  * `edit 1 _from_ 10/20/16 _to_ 10/25/16`
  * `add Collect computer from repairs _from_ 2:30pm _to_ 3:00pm_
  * `edit 4 _from_ 15 Oct _to_ 17 Oct`


<a id="before"></a>
#### Before a certain time/date: `before`
_Specify a deadline for a task._<br>
Format: `before <time/date>`

> * A list of supported time and date inputs are listed in [Appendix A](#appendix-a). You may also refer to the examples for a brief guide too. <br>

Examples:
  * `add Finish 2103T Tutorial _before_ 13:00`
  * `edit 3 _before_ 6 Feb '17`
  * `add Complete assignment _before_ Nov 2 2016 2359`
  * `edit 5 _before_ 1st May`


<a id="priority"></a>
#### Priority level for tasks: `priority`
_Assign a priority level to tasks._<br>
Format: `priority <low/med/high>`

> * Helps give levels of importance to tasks. <br>
> * Priority level can be low, medium or high. <br>

Examples:
  * `add Lunch with Arthur priority high`
  * `edit 1 priority med`

_The default priority level of a task is medium ("med")._


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
Primary | [Storage](#storage) | `storage <FilePath\<FileName>.xml>`
Extension | [Venue](#venue) | `at <description of venue>`
Extension | [Event](#from-to) | `from <start time> to <end time>`
Extension | [Before](#before) | `before <time>`
Extension | [Priority](#priority) | `priority <low/med/high>`


# Appendix A
_This appendix lists down all the inputs of dates and times that are accepted when adding/editing a task._
_As we are using PrettyTime parser to read in dates and times, we strongly recommend you to follow this table in order to let you have the best experience when using Task Ninja!_
_You may also refer to the examples in [Event](#from-to) and [Storage](#storage) sections for an overview of how to input your dates and times when adding/editing your task._

> * The format of dates and times are as follows: `<*Day of week* (Mon/Tue/Wed, etc)> <*Month* (Jan/Feb/Mar, etc)> <*Day* (DD)> <*Time* (HH:MM:SS)> <*Timezone (fixed)* (GMT+08:00)> <*Year* (YYYY)>` <br>
> * The current time (in HH:MM:SS GMT+08:00) will be timestamped to your tasks should you decide not to enter a time.

Input | Type | Parsed As (Example (Using 19 Oct 2016 as the date, unless otherwise specified)):
----- | :--------: | :--------:
`2am` | Time (relaxed) | Wed Oct 19 02:00:00 GMT+08:00 2016
`2PM` | Time (relaxed) | Wed Oct 19 14:00:00 GMT+08:00 2016
`3:30am` | Time (12-hr format) | Wed Oct 19 03:30:00 GMT+08:00 2016
`4:45PM` | Time (12-hr format) | Wed Oct 19 16:45:00 GMT+08:00 2016
`0515` | Time (24-hr format) | Wed Oct 19 05:15:00 GMT+08:00 2016
`1700` | Time (24-hr format) | Wed Oct 19 17:00:00 GMT+08:00 2016
`6:00` | Time (24-hr format) | Wed Oct 19 06:00:00 GMT+08:00 2016
`07:55` | Time (24-hr format) | Wed Oct 19 07:55:00 GMT+08:00 2016
`19:59` | Time (24-hr format) | Wed Oct 19 19:59:00 GMT+08:00 2016
`1` | Time (incorrect) | Wed Oct 19 01:00:00 GMT+08:00 2016
`00` | Time (incorrect) | Wed Oct 19 00:00:00 GMT+08:00 2016
`19 Oct` | Date (relaxed; uses current year) | Wed Oct 19 16:10:04 GMT+08:00 2016
`20 November` | Date (relaxed; uses current year) | Sun Nov 20 16:10:26 GMT+08:00 2016
`21 Dec '16` | Date (relaxed) | Wed Dec 21 16:10:26 GMT+08:00 2016
`22 January '17` | Date (relaxed) | Sun Jan 22 16:14:49 GMT+08:00 2017
`23 Feb 2016` | Date (relaxed) | Tue Feb 23 16:16:32 GMT+08:00 2017
`24 March 2017` | Date (relaxed) | Fri Mar 24 16:17:02 GMT+08:00 2017
`Oct 19`/`October 19` | Date (relaxed) | Wed Oct 19 16:10:48 GMT+08:00 2016
`Dec 21 '16`/`December 21 '16` | Date (relaxed) | Wed Dec 21 16:11:03 GMT+08:00 2016
`Feb 23 2016`/`Febuary 23 2016` | Date (relaxed) | Tue Feb 23 16:12:22 GMT+08:00 2017
`10/19` | Date (formal; uses current year) | Wed Oct 19 16:11:19 GMT+08:00 2016
`10/19/16` | Date (formal) | Wed Oct 19 16:13:25 GMT+08:00 2016
`10/19/2016` | Date (formal) | Wed Oct 19 16:14:42 GMT+08:00 2016
`today` | Date (relative) | Wed Oct 19 16:24:23 GMT+08:00 2016
`tomorrow` | Date (relative) | Thu Oct 20 16:24:41 GMT+08:00 2016
`next week` | Date (relative) | Wed Oct 26 16:26:56 GMT+08:00 2016
`last week` | Date (relative) | Wed Oct 12 16:27:33 GMT+08:00 2016
`next mon/monday/Mon/Monday` | Date (relative) | Mon Oct 24 16:33:46 GMT+08:00 2016
`last tue/tues/tuesday/Tue/Tues/Tuesday` | Date (relative) | Tue Oct 11 16:34:10 GMT+08:00 2016
`wed/wednesday/Wed/Wednesday` | Date (relative; automatically set on the day of the upcoming week if the day today is the same or past the day entered) | Wed Oct 26 16:34:21 GMT+08:00 2016
`thu/thu/thur/thurs/thursday/Thu/Thu/Thur/Thurs/Thursday` | Date (relative; automatically set on the day of the same week if the day today is less than the day entered) | Thu Oct 20 16:34:57 GMT+08:00 2016
`next fri/friday/Fri/Friday` | Date (relative) | Fri Oct 28 16:35:17 GMT+08:00 2016
`last sat/saturday/Sat/Saturday` | Date (relative) | Sat Oct 15 16:35:59 GMT+08:00 2016
'next sun/sunday/Sun/Sunday` | Date (relative) | Sun Oct 23 16:36:28 GMT+08:00 2016
`next month` | Date (relative) | Sat Nov 19 16:36:42 GMT+08:00 2016
`next year` | Date (relative) | Thu Oct 19 16:37:23 GMT+08:00 2017
`19/10` | Date (incorrect; parsed as time instead) | Wed Oct 19 10:00:00 GMT+08:00 2016
'19/10/16' | Date (incorrect; parsed as time instead) | Wed Oct 19 10:00:00 GMT+08:00 2016
'19/10/2016' | Date (incorrect; parsed as time instead) | Wed Oct 19 10:00:00 GMT+08:00 2016
'the day after' | Date (incorrect) | N/A | N/A
'the following day' | Date (incorrect) | N/A | N/A
'the week after' | Date (incorrect) | N/A | N/A
'next next week' | Date (incorrect; 1st "next" is ignored) | Wed Oct 26 16:38:09 GMT+08:00 2016
`next next month` | Date (incorrect; 1st "next" is ignored) | Sat Nov 19 16:38:44 GMT+08:00 2016
`next next year` | Date (incorrect; 1st "next" is ignored) | Thu Oct 19 16:39:23 GMT+08:00 2017
`19 Oct 1am`/`19 October 1AM` | Date (without year) and Time (relaxed) | Wed Oct 19 01:00:00 GMT+08:00 2016
`20 Nov 1:00pm`/`20 November 1:00PM` | Date (without year) and Time (12-hr format) | Sun Nov 20 13:00:00 GMT+08:00 2016
`21 Dec 14:00`/`Dec 21 14:00` | Date (without year) and Time (24-hr format) | Wed Dec 21 14:00:00 GMT+08:00 2016
`22 Jan '16 02:00am`/`22 January '16 02:00AM` | Date (with year) and Time (12-hr format) | Sun Jan 22 02:00:00 GMT+08:00 2016
`Jan 22 '16 02:00am`/`January 22 '16 02:00AM` | Date (with year) and Time (12-hr format) | Sun Jan 22 02:00:00 GMT+08:00 2016
`23 Feb 2016 14:00`/`23 February 2017 1400` | Date (with year) and Time (24-hr format) | Tue Feb 23 14:00:00 GMT+08:00 2017
`Feb 23 2016 14:00`/`February 23 2017 1400` | Date (with year) and Time (24-hr format) | Tue Feb 23 14:00:00 GMT+08:00 2017
`23 Feb 16`/`23 February 16` | Date (without year) and Time (incorrect; "16" will be parsed as time) | Tue Feb 23 16:00:00 GMT+08:00 2016
`23 Feb 1400`/`23 February 1400` | Date and Time (incorrect; "1400" will be parsed as year) | Mon Feb 23 10:08:27 GMT+08:00 1400
