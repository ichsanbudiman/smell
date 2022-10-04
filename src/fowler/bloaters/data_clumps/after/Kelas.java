package fowler.bloaters.data_clumps.after;

import java.util.Date;

public class Kelas {
	private String course;
	private DateRange dateRange;
	
	public Kelas(String course, Date start, Date end){
		this.course = course;
		this.dateRange = new DateRange(start,end);
	}
	
	public String getCourse(){
		return course;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
}
