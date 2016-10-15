package seedu.manager.model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.StartTime;
import seedu.manager.model.task.Time;

public class TimeTest {
	@Test
	public void parseTime_invalid_throwsError() {
		assertInvalidTime("This is not a time");
		assertInvalidTime("123");
		assertInvalidTime("tomorrow and day after tomorrow"); // multiple times
		assertInvalidTime("5:95pm");
		assertInvalidTime("Abc Oct 16 01:00:00 SGT 2100");
		assertInvalidTime("Sat Abc 16 01:00:00 SGT 2100");
		assertInvalidTime("Sat Oct 50 01:00:00 SGT 2100");
		assertInvalidTime("Sat Oct 16 25:00:00 SGT 2100");
		assertInvalidTime("Sat Oct 16 23:61:00 SGT 2100");
		assertInvalidTime("Sat Oct 16 23:00:61 SGT 2100");
		assertInvalidTime("Sat Oct 16 23:00:00 ABC 2100");
		
		// PrettyTime accepts these
		// assertInvalidTime("tomorrow day after tomorrow"); only tomorrow
		// assertInvalidTime("32 Oct"); Tue Oct 01 <time> SGT 2047
	}
	
	@Test
	public void parseTime_compareWithExpected_Succeeds() throws IllegalValueException {
		assertParsedTimeEquals(new StartTime("now"), getCalendar());
		assertParsedTimeEquals(new StartTime("tomorrow"), addDaysToCal(getCalendar(), 1));
		assertParsedTimeEquals(new StartTime("5pm"), setTime(5, 0, false));
		assertParsedTimeEquals(new StartTime("5:30pm tomorrow"), addDaysToCal(setTime(5, 30, false), 1));
		assertParsedTimeEquals(new StartTime("day after tomorrow"), addDaysToCal(getCalendar(), 2));
		assertParsedTimeEquals(new StartTime("3 days later"), addDaysToCal(getCalendar(), 3));
		assertParsedTimeEquals(new StartTime("Sun next week"), setDay(addDaysToCal(getCalendar(), 7), Calendar.SUNDAY));
		assertParsedTimeEquals(new StartTime("day after tomorrow evening"), addDaysToCal(setTime(7, 0, false), 2));
		assertParsedTimeEquals(new StartTime("tonight"), setTime(8, 0, false));
		assertParsedTimeEquals(new StartTime("today noon"), setTime(12, 0, true)); // calendar take 12am to be noon
		
		String timeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
		assertParsedTimeEquals(new StartTime("Sat Oct 16 01:00:00 " + timeZone + " 2100"), setAll(2100, Calendar.OCTOBER, 16, 1, 0, true));
	}
	
	private void assertInvalidTime(String time) {
		try {
			StartTime startTime = new StartTime(time);
			System.out.println(startTime);
			fail("didn't throw exception");
		} catch (IllegalValueException e) {
			assertEquals(e.getMessage(), Time.MESSAGE_TIME_CONSTRAINTS);
		}
	}
	
	private Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}
	
	private Calendar addDaysToCal(Calendar calendar, int numDays) {
		calendar.add(Calendar.DATE, numDays);
		return calendar;
	}
	
	private Calendar setTime(int hour, int minute, boolean isAM) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, isAM ? Calendar.AM : Calendar.PM);
		return calendar;
	}
	
	private Calendar setDay(Calendar calendar, int day) {
		calendar.set(Calendar.DAY_OF_WEEK, day);
		return calendar;
	}
	
	private Calendar setAll(int year, int month, int date, int hourOfDay, int minute, boolean isAM) {
		Calendar calendar = setTime(hourOfDay, minute, isAM);
		calendar.set(year, month, date);
		return calendar;
	}
	
	private void assertParsedTimeEquals(Time time, Calendar expected) throws IllegalValueException {
		assertEquals(expected.getTime().toString(), time.getValue());
	}
}
