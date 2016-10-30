package seedu.manager.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * @@author A0147924X
 * Represents a tasks time 
 *
 */
public abstract class Time extends TaskProperty {
	public static final String MESSAGE_TIME_CONSTRAINTS =
            "Invalid Time. While times are quite flexible, don't forget that I'm just a computer. :)";
    public static final String TIME_VALIDATION_REGEX = ".+"; // Time validation done by timeParser
    
    private static final Pattern DATE_STRING_FORMAT_REGEX = 
    		Pattern.compile("([A-Z][a-z]{2} ){2}\\d{2} \\d{2}:\\d{2}:\\d{2} [A-Z]{3} \\d{4}");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
    private static final PrettyTimeParser timeParser = new PrettyTimeParser();
    private static final PrettyTime timePrettify = new PrettyTime();
    
    protected Date value;
    
    static {
    	DATE_FORMAT.setLenient(false);
    }
    
    /**
     * Parses and validates given time. If valid, assigns value to the time
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        super(time, TIME_VALIDATION_REGEX, MESSAGE_TIME_CONSTRAINTS);
        
        // To parse strings created by Date.toString
        Matcher matcher = DATE_STRING_FORMAT_REGEX.matcher(time);
        if (matcher.matches()) {
			try {
				value = DATE_FORMAT.parse(time);
			} catch (ParseException e) {
				value = parseTime(time);
			}
		} else {
			value = parseTime(time);
		}
    }

    @Override
    public String toString() {
        return value.toString();
    }
    
    @Override
    public String toPrettyString() {
    	return timePrettify.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }
    
    /**
     * Parses the time string using PrettyTime
     * 
     * @param time
     * @throws IllegalValueException
     */
    private Date parseTime(String time) throws IllegalValueException {
        List<Date> groups;
    	try {
    	    groups = timeParser.parse(time);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid Time!");
        }
    	
    	if (groups.size() != 1) {
			throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
		}
    	return groups.get(0);
    }
    
    public Date getTime() {
        return value;
    }
    
    /**
     * @@author A0139621H
     * Checks if the start time of a task is equal to or later than that of the search function's input
     */
	@Override
    public boolean matches(TaskProperty time) {
	    if (time instanceof StartTime) {
	    	return (!((StartTime) time).value.after(this.value));
		} else if (time instanceof EndTime) {
			return (!((EndTime) time).value.before(this.value));
		} else {
			return false;
		}
    }

	public boolean isBefore(Date now) {
	
		if (value.before(now)) {
			return true;
		}
		return false;
	}
}
