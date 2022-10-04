package fowler.bloaters.data_clumps.after;

import java.util.Date;

public class Semester {
	private String label;
	private DateRange dateRange;
	
	public Semester(String label, Date start, Date end) {
		super();
		this.label = label;
		this.dateRange = new DateRange(start,end);
	}
	
	public String getLabel() {
		return label;
	}
	
	public Date getDateRange() {
		return dateRange;
	}
}
