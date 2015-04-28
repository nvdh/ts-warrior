package be.nvdh.ts.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class FetchResult {

	private LocalDate fetchDate;
	private LocalDate fetchedMonth;
	
	private Duration totalTime;
	private Duration totalOvertime;
	
	private LocalTime timeToGoHome;
	
	private List<Prestation> prestations = new ArrayList<Prestation>();
	
	public FetchResult(LocalDate fetchDate, LocalDate fetchedMonth, List<Prestation> prestations, Duration totalTime, Duration totalOverTime, LocalTime timeToGoHome) {
		super();
		this.fetchDate = fetchDate;
		this.fetchedMonth = fetchedMonth;
		this.prestations = prestations;
		this.totalTime = totalTime;
		this.totalOvertime = totalOverTime;
		this.timeToGoHome = timeToGoHome;
	}

	public void addPrestation(Prestation prestation){
		prestations.add(prestation);
	}

	public List<Prestation> getPrestations(){
		return prestations;
	}
	
	public LocalDate getFetchDate() {
		return fetchDate;
	}
	
	public LocalDate getFetchedMonth() {
		return fetchedMonth;
	}
	
	public Duration getTotalTime() {
		return totalTime;
	}
	
	public Duration getTotalOvertime() {
		return totalOvertime;
	}
	
	public LocalTime getTimeToGoHome() {
		return timeToGoHome;
	}
	
}
