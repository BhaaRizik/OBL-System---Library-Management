package application;

import javafx.beans.property.SimpleStringProperty;

public class DurationOfLateness 
{
	SimpleStringProperty Range ;
	SimpleStringProperty no;
	
	
	public DurationOfLateness(String Range,String no) 
	{
		this.Range= new SimpleStringProperty(Range);
		this.no=new SimpleStringProperty(no);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DurationOfLateness [Range=" + Range + ", no=" + no + "]";
	}


	/**
	 * @return the range
	 */
	public String getRange() {
		return Range.get();
	}


	/**
	 * @param range the range to set
	 */
	public void setRange(SimpleStringProperty range) {
		Range = range;
	}


	/**
	 * @return the no
	 */
	public String getNo() {
		return no.get();
	}


	/**
	 * @param no the no to set
	 */
	public void setNo(SimpleStringProperty no) {
		this.no = no;
	}
	
}
