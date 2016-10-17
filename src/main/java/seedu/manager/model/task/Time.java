package seedu.manager.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a tasks time 
 * @author varungupta
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
    
    private Date value;
    
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
    	List<Date> groups = timeParser.parse(time);
    	if (groups.size() != 1) {
			throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
		}
    	return groups.get(0);
    }
}
