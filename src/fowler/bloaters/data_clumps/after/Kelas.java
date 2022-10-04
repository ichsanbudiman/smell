package fowler.bloaters.data_clumps.after;

import java.util.Date;

public class Kelas {
	private String course;
	private DateRange dateRange;
	
	public Kelas(String course, DateRange dateRange){
		this.course = course;
		this.dateRange = dateRange;
	}
	
	public String getCourse(){
		return course;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
}
