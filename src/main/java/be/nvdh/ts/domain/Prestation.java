package be.nvdh.ts.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class Prestation {

	private LocalDate day = new LocalDate();

	private Duration duration = Duration.ZERO;
	private Duration neededDuration = Duration.ZERO;
	private Duration overtime = Duration.ZERO;
	private Duration weekOvertime = Duration.ZERO;

	private List<LocalTime> tickTimes = new ArrayList<LocalTime>();
	private List<LocalTime> manualRegistrations = new ArrayList<LocalTime>();

	private String dayCode = "";
	private String irregularities = "";
	private String comments = "";
	
	private boolean lastDayOfWeek = false;

	public Prestation(LocalDate day, Duration duration, Duration neededDuration, Duration overtime, Duration weekOvertime, List<LocalTime> tickTimes, List<LocalTime> manualRegistrations, String dayCode, String irregularities, String comments) {
		super();
		this.day = day;
		this.duration = duration;
		this.neededDuration = neededDuration;
		this.tickTimes = tickTimes;
		this.manualRegistrations = manualRegistrations;
		this.dayCode = dayCode;
		this.irregularities = irregularities;
		this.comments = comments;
		this.overtime = overtime;
		this.weekOvertime = weekOvertime;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Duration getNeededDuration() {
		return neededDuration;
	}

	public void setNeededDuration(Duration neededDuration) {
		this.neededDuration = neededDuration;
	}

	public Duration getOvertime() {
		return overtime;
	}

	public void setOvertime(Duration overtime) {
		this.overtime = overtime;
	}

	public Duration getWeekOvertime() {
		return weekOvertime;
	}

	public void setWeekOvertime(Duration weekOvertime) {
		this.weekOvertime = weekOvertime;
	}

	public List<LocalTime> getTickTimes() {
		return tickTimes;
	}

	public void setTickTimes(List<LocalTime> tickTimes) {
		this.tickTimes = tickTimes;
	}

	public List<LocalTime> getManualRegistrations() {
		return manualRegistrations;
	}

	public void setManualRegistrations(List<LocalTime> manualRegistrations) {
		this.manualRegistrations = manualRegistrations;
	}

	public String getDayCode() {
		return dayCode;
	}

	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}

	public String getIrregularities() {
		return irregularities;
	}

	public void setIrregularities(String irregularities) {
		this.irregularities = irregularities;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public boolean isLastDayOfWeek() {
		return lastDayOfWeek;
	}

	public void setLastDayOfWeek(boolean lastDayOfWeek) {
		this.lastDayOfWeek = lastDayOfWeek;
	}

}
