

/**
 * 
 * Subscriber is a observer, then he implements Observer interface
 * 
 * In this class we have the main function : "update", it is Override
 * 
 * server check if the "update" function called from ReminderThreadClass OR TwoDaysForOrders
 * 
 * and after that the server check the times according to deceleration class
 * 
 * 
 * 
 * @author Bhaa Rizik
 * 
 * */




import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;



public class Student implements Observer {

	private String studentID;
	private String studentName;
	private String studentEmail;
	private String bookID;
	private String loanBook;
	private String returnBook;
	private String choice;
	private String reachBook;
	private String orderNumber;
	long remindDays;
	long remindDaysOrder;
	
	

	public Student(String studentID2, String studentName2, String studentEmail2, String bookSerialNumer,
			String loanBook2, String returnBook2,String choice) {
		// TODO Auto-generated constructor stub
		this.studentID = studentID2;
		this.studentName = studentName2;
		this.studentEmail = studentEmail2;
		this.bookID = bookSerialNumer;
		this.loanBook = loanBook2;
		this.returnBook = returnBook2;
		this.choice=choice;
	}

	
	public Student(String orderNumber, String studentName2, String studentEmail2, String bookSerialNumer, String choice,String reachBook) {
		// TODO Auto-generated constructor stub
		
		this.orderNumber = orderNumber;
		this.studentName = studentName2;
		this.studentEmail = studentEmail2;
		this.bookID = bookSerialNumer;
		this.choice=choice;
		this.reachBook=reachBook;
		
	}


	@Override
	public void update(Observable o, Object dateOfTheDay) {
		
	if(choice.equals("reminder"))	
	{
		System.out.println("date of the day "+(String)dateOfTheDay);
		System.out.println("Return Date "+this.returnBook);
		
		DateFormat formatter;
		Date returnDate = null,currentDate = null;
		formatter = new SimpleDateFormat("ddddd/MM/yy");
		try {
			returnDate = formatter.parse(this.returnBook);
			currentDate=formatter.parse((String)dateOfTheDay);
			System.out.println(returnDate);
			System.out.println(currentDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 remindDays = (int)( (returnDate.getTime() - currentDate.getTime())/ (1000 * 60 * 60 * 24) );
		 System.out.println("remind Days  : " + remindDays);
		 
		 if(remindDays==1)
		 {
			 System.out.println(this.studentName+ " have to return book tomorrow ");
			 System.out.println("student ID :"+this.studentID+" name  "+this.studentName+" email "+this.studentEmail);
			 System.out.println("book ID :"+this.bookID+" loan date  "+this.loanBook+" returndate "+this.returnBook);			 
			 SendingEmails.sendEmail("Reminder ! Ort Braude Library ","Hi "+this.studentName+"\n\n In "+this.loanBook+" You lent a book with serial number : "+this.bookID+"\nThis email to remind you, that you have to return the book tomorrow\n Return date :  "+this.returnBook+"\nIt's appear in the email that you recieved when you lent the book ",studentEmail);

		 }
		 if(remindDays<0) {
			 System.out.println(this.studentName+ " delay !!! ");
	
		    	// result, is the arrayList that content the data about the book
			 
			  ArrayList<String>  result = new ArrayList<String>(); 
		    	System.out.println(Thread.currentThread().getName());
		    	result = EchoServer.increaseDelayTimes(studentID);
		        System.out.println(result);
			 
		    	if(result.get(0).equals("frozen"))
		    	{
		    		SendingEmails.sendEmail("Change status of reader card","Hi "+this.studentName+"\n\n Your status reader card changed to FROZEN !!\n After you return the book the status can be changed   ",studentEmail);
		    	}
		    	
		    	/// more than 3 times delay 
		    	if(result.get(0).equals("Lock"))
		    	{
                    System.out.println("Send to manager for lock reader card for student ");		
                }	
		    	
		    	if(result.get(0).equals("excutingError"))
		    	{
                    System.out.println("Error in SQL Query");		
                }	
		 }
	   }     //if(choice.equals("reminder"))
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  IF THE OPRATION IS FROM ORDER TO CHECK TWO DAYS  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	if(choice.equals("twoDays"))	
	{
		System.out.println("date of the day "+(String)dateOfTheDay);
		System.out.println("REACH Date "+this.reachBook);
		
		DateFormat formatter;
		Date reachDate = null,currentDate = null;
		formatter = new SimpleDateFormat("ddddd/MM/yy");
		try {
			reachDate = formatter.parse(this.reachBook);
			currentDate=formatter.parse((String)dateOfTheDay);
			System.out.println(reachDate);
			System.out.println(currentDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 remindDaysOrder = (int)( (currentDate.getTime() - reachDate.getTime())/ (1000 * 60 * 60 * 24) );
		 System.out.println("remind Days  : " + remindDaysOrder);
		 
		 if(remindDaysOrder==1)
		 {
			 System.out.println(this.studentName+ " have to take the book until tomorrow ");
			 
			 //System.out.println("student ID :"+this.studentID+" name  "+this.studentName+" email "+this.studentEmail);
			// System.out.println("book ID :"+this.bookID+" loan date  "+this.loanBook+" returndate "+this.returnBook);			 
		   SendingEmails.sendEmail("Order Book ! Ort Braude Library ","Hi "+this.studentName+"\n\n  "+"The book you ordered reach. \nYou have to take the book until tomorrow \n  ",studentEmail);

		 }
		 if(remindDaysOrder==2) {
			 System.out.println(this.studentName+ " have to take the book TODAY ");
		   	   SendingEmails.sendEmail("Order Book ! Ort Braude Library ","Hi "+this.studentName+"\n\n  "+"The book you ordered reach. \nYou have to take the book TODAY  \n  ",studentEmail);

		 }
		 
		 if(remindDaysOrder==3) {
			 System.out.println(this.studentName+ " Your order DELETED ");
			
			 ArrayList<String>  result = new ArrayList<String>(); 
		   	 result = EchoServer.removeDataFromOrder(Integer.parseInt(orderNumber));
		   	 System.out.println(result);
		   if(result.get(0).equals("deleteSuccessfuly"));
		   {
				 ArrayList<String>  result1 = new ArrayList<String>(); 
				// result1=
		   }
			 
			 
		 }
		 
	}
	
	
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}


	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

  public String getLoanBook() {
		return loanBook;
	}

	public void setLoanBook(String loanBook) {
		this.loanBook = loanBook;
	}

	public String getReturnBook() {
		return returnBook;
	}

	public void setReturnBook(String returnBook) {
		this.returnBook = returnBook;
	}



	
	
	
	
	
	

}
