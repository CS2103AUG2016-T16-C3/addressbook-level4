To load the sample data, open the app once so that the config.json file is created. Then close the app. Copy the SampleData.xml file to the data folder. Change the file path in the config.json file to data/SampleData.xml. Start up the app again and the sample data will be loaded.

1.	*Command:*<br>
    `add Buy Dell P2715Q venue ITFair priority high tag DIYCom`<br>
    *Expected Result:*<br>
    Adds task with venue, priority and tag, is a floating task
2.	*Command:*<br>
    `add Meet up with Gideon venue at 7pm`<br>
    *Expected Result:*<br>
    Adds task with venue and at a certain time, no specified duration
3.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    Previously added item disappears
4.	*Command:*<br>
    `add Commence upgrade of Win 10 by 11pm priority low`<br>
    *Expected Result:*<br>
    Adds task with deadline and low priority
5.	*Command:*<br>
    `add Visit the IT Fair venue ITFair from 2pm to 5pm`<br>
    *Expected Result:*<br>
    Adds event with venue and a specific period of time
6.	*Command:*<br>
    `edit 52 priority low`<br>
    *Expected Result:*<br>
    Changes the priority of the task `52` to `low`, reflected in the title color changing to green
7.	*Command:*<br>
    `edit 51 Buy gift venue Expo`<br>
    *Expected Result:*<br>
    Changes the venue of task `51` to `Expo` and description to `Buy gift`
8.	*Command:*<br>
    `delete 51`<br>
    *Expected Result:*<br>
    The task with index `51` is removed
9.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The previously deleted task is added back to the list
10.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The edit made in step `7` is rolled back
11.	*Command:*<br>
    `find venue SoC`<br>
    *Expected Result:*<br>
    Lists all tasks with venue containing `SoC`
12.	*Command:*<br>
    `find venue SoC priority high`<br>
    *Expected Result:*<br>
    Lists all tasks with venue containing `SoC` and `high` priority
13.	*Command:*<br>
    `find from now to day after tomorrow`<br>
    *Expected Result:*<br>
    Lists all the tasks that occur between now and the day after tomorrow
14.	*Command:*<br>
    `find by tomorrow`<br>
    *Expected Result:*<br>
    Lists all the tasks that end before tomorrow (including overdue tasks - red bar on left)
15.	*Command:*<br>
    `find tag Errands`<br>
    *Expected Result:*<br>
    Lists all tasks with the tag `Errands`, and the `Errands` tag on the left will be highlighted
16.	*Command:*<br>
    `sortby priority`<br>
    *Expected Result:*<br>
    The listed tasks are sorted by priority
17.	*Command:*<br>
    `done 3`<br>
    *Expected Result:*<br>
    The task with index `3` is marked as done (green tick on the right)
18.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The task just marked as done is marked as undone (no green tick)
19.	*Command:*<br>
    `sortby time`<br>
    *Expected Result:*<br>
    The listed tasks are sorted by time
20.	*Command:*<br>
    Click on the `SoC` tag on the left<br>
    *Expected Result:*<br>
    The tasks with tag `SoC` are listed on the right
21.	*Command:*<br>
    `list`<br>
    *Expected Result:*<br>
    All tasks are listed
22.	*Command:*<br>
    `help add`<br>
    *Expected Result:*<br>
    Shows the usage message for the `add` command in the result display box. Alias for this command is shown to be `add`
23.	*Command:*<br>
    `alias add +`<br>
    *Expected Result:*<br>
    The result display box indicates that `add` has been aliased to `+`
24.	*Command:*<br>
    `+ Dinner with Arthur`<br>
    *Expected Result:*<br>
    The new task is added to the list
25.	*Command:*<br>
    `alias venue @`<br>
    *Expected Result:*<br>
    The result display box indicates that `venue` has been aliased to `@`
26. *Command:*<br>
    `help venue`<br>
    *Expected Result:*<br>
    Shows the usage message for the `venue` command in the result display box. Alias for this command is shown to be `@`
27.	*Command:*<br>
    `alias priority pr`<br>
    *Expected Result:*<br>
    The result display box indicates that `priority` has been aliased to `pr`
28.	*Command:*<br>
    `alias pr p`<br>
    *Expected Result:*<br>
    The result display box indicates that `pr` has been aliased to `p`
28.	*Command:*<br>
    `+ buy newspaper @ Kent Ridge p high`<br>
    *Expected Result:*<br>
    The new task with given venue and priority is added
29.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The previously added task disappears
30.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The alias for priority set in step `27` is undone back to `priority`
31.	*Command:*<br>
		`+ Party with Jimmy @ NTU hall 24 priority high`<br>
    *Expected Result:*<br>
    Adds a new task with the given venue and priority
32.	*Command:*<br>
    `find @ Square`<br>
    *Expected Result:*<br>
    Lists tasks whose venues contain `Square`
33.	*Command:*<br>
    `edit 29 @ Jem`<br>
    *Expected Result:*<br>
    The venue of task `29` is changed to `Jem`
34.	*Command:*<br>
    `edit 1 tag Hobbies`<br>
    *Expected Result:*<br>
    The tag of task `1` is changed to `Hobbies` and the new tag shows up on the left
35.	*Command:*<br>
    `undo`<br>
    *Expected Result:*<br>
    The tag changed in the previous step is reset and the `Hobbies` tag disappears
36.	*Command:*<br>
    `storage data/newFile.xml`<br>
    *Expected Result:*<br>
    Creates a new file in the data folder, and saves the current task list to that location. File path also updates in the status bar
37.	*Command:*<br>
    `alias storage @`<br>
    *Expected Result:*<br>
    Result display box indicates that this alias is already taken
38.	*Command:*<br>
    `alias storage *`<br>
    *Expected Result:*<br>
    The result display box indicates that `storage` has been aliased to `*`
39.	*Command:*<br>
		`* data/anotherNewFile.xml`<br>
    *Expected Result:*<br>
	  Creates a new file in the data folder, and saves the current task list to that location
40.	*Command:*<br>
		`clear`<br>
    *Expected Result:*<br>
	  Removes all tasks from the task manager
41.	*Command:*<br>
    `Exit`<br>
    *Expected Result:*<br>
    Exits the application
