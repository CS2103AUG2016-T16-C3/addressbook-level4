To load the sample data, open the app once so that the config.json file is created. Then close the app. Copy the SampleData.xml file to the data folder. Change the file path in the config.json file to data/SampleData.xml. Start up the app again and the sample data will be loaded.

0.    *Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    No commands to undo
1.    *Command:*<br>
    `add`<br>
    *Expected Result:*<br>
    Add commands needs description
2.    *Command:*<br>
    `delete 100`<br>
    *Expected Result:*<br>
    The task index provided in invalid
3.    *Command:*<br>
    `edit 100 venue BookHeaven`<br>
    *Expected Result:*<br>
    The task index provided in invalid
4.    *Command:*<br>
    `edit 20 from 3pm to 2pm`<br>
    *Expected Result:*<br>
    The start time should be before end time
5.	*Command:*<br>
    `add Buy Dell P2715Q venue ITFair priority high tag DIYCom`<br>
    *Expected Result:*<br>
    Adds task with venue, priority and tag, is a floating task
6.	*Command:*<br>
    `add Meet up with Gideon venue at 7pm`<br>
    *Expected Result:*<br>
    Adds task with venue and at a certain time, no specified duration
7.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    Previously added item disappears
8.	*Command:*<br>
    `add Commence upgrade of Win 10 by 11pm priority low`<br>
    *Expected Result:*<br>
    Adds task with deadline and low priority
9.	*Command:*<br>
    `add Visit the IT Fair venue ITFair from 2pm to 5pm`<br>
    *Expected Result:*<br>
    Adds event with venue and a specific period of time
10.	*Command:*<br>
    `add buy food by the day after`<br>
    *Expected Result:*<br>
    Task is not added as the time entered cannot be recognised and parsed.
11.	*Command:*<br>
    `edit 52 priority low`<br>
    *Expected Result:*<br>
    Changes the priority of the task `52` to `low`, reflected in the title color changing to green
12.	*Command:*<br>
    `edit 51 Buy gift venue Expo`<br>
    *Expected Result:*<br>
    Changes the venue of task `51` to `Expo` and description to `Buy gift`
13.	*Command:*<br>
    `delete 51`<br>
    *Expected Result:*<br>
    The task with index `51` is removed
14.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The previously deleted task is added back to the list
15.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The edit made in step `7` is rolled back
16.	*Command:*<br>
    `find venue SoC`<br>
    *Expected Result:*<br>
    Lists all tasks with venue containing `SoC`
17.	*Command:*<br>
    `find venue SoC priority high`<br>
    *Expected Result:*<br>
    Lists all tasks with venue containing `SoC` and `high` priority
18.	*Command:*<br>
    `find from now to day after tomorrow`<br>
    *Expected Result:*<br>
    Lists all the tasks that occur between now and the day after tomorrow
19.	*Command:*<br>
    `find by tomorrow`<br>
    *Expected Result:*<br>
    Lists all the tasks that end before tomorrow (including overdue tasks - red bar on left)
20.	*Command:*<br>
    `find tag Errands`<br>
    *Expected Result:*<br>
    Lists all tasks with the tag `Errands`, and the `Errands` tag on the left will be highlighted
21.	*Command:*<br>
    `sortby`<br>
    *Expected Result:*<br>
    No valid property to sort by.
22.	*Command:*<br>
    `sortby priority`<br>
    *Expected Result:*<br>
    The listed tasks are sorted by priority
23.	*Command:*<br>
    `done 3`<br>
    *Expected Result:*<br>
    The task with index `3` is marked as done (green tick on the right)
24.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The task just marked as done is marked as undone (no green tick)
25.	*Command:*<br>
    `sortby time`<br>
    *Expected Result:*<br>
    No valid property to sort by.
26.	*Command:*<br>
    `sortby date`<br>
    *Expected Result:*<br>
    The listed tasks are sorted by time
27.	*Command:*<br>
    Click on the `SoC` tag on the left<br>
    *Expected Result:*<br>
    The tasks with tag `SoC` are listed on the right
28.	*Command:*<br>
    `list`<br>
    *Expected Result:*<br>
    All tasks are listed
29.	*Command:*<br>
    `help add`<br>
    *Expected Result:*<br>
    Shows the usage message for the `add` command in the result display box. Alias for this command is shown to be `add`
30.	*Command:*<br>
    `edit venue`<br>
    *Expected Result:*<br>
    Result displays invalid command format, and the user is shown how the `edit` command should be used properly.
31.	*Command:*<br>
    `alias add +`<br>
    *Expected Result:*<br>
    The result display box indicates that `add` has been aliased to `+`
32.	*Command:*<br>
    `+ Dinner with Arthur`<br>
    *Expected Result:*<br>
    The new task is added to the list
33.	*Command:*<br>
    `alias venue @`<br>
    *Expected Result:*<br>
    The result display box indicates that `venue` has been aliased to `@`
34.	*Command:*<br>
    `help venue`<br>
    *Expected Result:*<br>
    Shows the usage message for the `venue` command in the result display box. Alias for this command is shown to be `@`
35.	*Command:*<br>
    `alias priority pr`<br>
    *Expected Result:*<br>
    The result display box indicates that `priority` has been aliased to `pr`
36.	*Command:*<br>
    `alias pr p`<br>
    *Expected Result:*<br>
    The result display box indicates that `pr` has been aliased to `p`
37.	*Command:*<br>
    `+ buy newspaper @ Kent Ridge p high`<br>
    *Expected Result:*<br>
    The new task with given venue and priority is added
38.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The previously added task disappears
39.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The alias for priority set in step `27` is undone back to `priority`
40.	*Command:*<br>
    `+ Party with Jimmy @ NTU hall 24 priority high`<br>
    *Expected Result:*<br>
    Adds a new task with the given venue and priority
41.	*Command:*<br>
    `find @ Square`<br>
    *Expected Result:*<br>
    Lists tasks whose venues contain `Square`
42.	*Command:*<br>
    `edit 29 @ Jem`<br>
    *Expected Result:*<br>
    The venue of task `29` is changed to `Jem`
43.	*Command:*<br>
    `+ buy ink toner tag Errands tag printer`<br>
    *Expected Result:*<br>
    Task is not added due to the occurence of more than one `tag` keyword.
44.	*Command:*<br>
    `edit 1 tag Hobbies`<br>
    *Expected Result:*<br>
    The tag of task `1` is changed to `Hobbies` and the new tag shows up on the left
45.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The tag changed in the previous step is reset and the `Hobbies` tag disappears
46.	*Command:*<br>
    `storage data/newFile.xml`<br>
    *Expected Result:*<br>
    Creates a new file in the data folder, and saves the current task list to that location. File path also updates in the status bar
47.	*Command:*<br>
    `alias storage @`<br>
    *Expected Result:*<br>
    Result display box indicates that this alias is already taken
48.	*Command:*<br>
    `alias storage *`<br>
    *Expected Result:*<br>
    The result display box indicates that `storage` has been aliased to `*`
49.	*Command:*<br>
    `alias minus -`<br>
    *Expected Result:*<br>
    Result display shows that "minus" is not recognised
50.	*Command:*<br>
    `* data/anotherNewFile.xml`<br>
    *Expected Result:*<br>
    Creates a new file in the data folder, and saves the current task list to that location
51.	*Command:*<br>
    `clear`<br>
    *Expected Result:*<br>
    Removes all tasks from the task manager
52.	*Command:*<br>
    `Exit`<br>
    *Expected Result:*<br>
    Exits the application
