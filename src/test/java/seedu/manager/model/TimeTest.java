package seedu.manager.model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

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
	}
	
	private void assertInvalidTime(String time) {
		try {
			new StartTime(time);
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
	
	private void assertParsedTimeEquals(Time time, Calendar expected) throws IllegalValueException {
		assertEquals(expected.getTime().toString(), time.getValue());
	}
}
