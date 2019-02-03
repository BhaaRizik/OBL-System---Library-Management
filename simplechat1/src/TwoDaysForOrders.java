


/**
 * 
 * This class  responsible to build a list of observers
 * in our case observer is the subscriber .
 * 
 * this class implements Runnable, there are a thread that work the 
 * this methods .
 * 
 * The check if there are observer need to take the book during 2 days.
 * 
 * after it end, the thread sleep for 24 hour and wake up to check one more time.
 * 
 * getData :
     * this function take the data of the observer
     * create student 
     * add to observerList  
 * 
 * notifyAllObservers:
    * Scan the observerList and call "update" function in student class 
 *
 *
 *addObserver:
    * add observer to observerList
 *
 *
 * removeObserver :
     * remove observer from observerList
 * 
 * 
 * 
 *      @author Bhaa Rizik
 * 
 * 
 * 
 * */





import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;
import java.util.TimerTask;


public class TwoDaysForOrders  implements Loan, Runnable {

	
	  List<Observer> observerList;
	  public static int orderNumber=0;
	  public static int indexInLoanTableToContinue=0;
	  private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  public static Thread t1=null;
	
	public void getData()
	{
		int flag=0;
		String MaxNumberInLoanNumber = null;
		int indexToOrderTable=1;
		orderNumber=EchoServer.getMinimum();
		System.out.println("5555555 "+orderNumber);
		MaxNumberInLoanNumber=Integer.toString(orderNumber);
         System.out.println("ggggggg "+MaxNumberInLoanNumber);
		
		while(indexToOrderTable!=0) {
		
		// result, is the arrayList that content the data about the book
		// String loanID=Integer.toString(orderNumber);
		 ArrayList<String>  result = new ArrayList<String>(); 
		result = EchoServer.getDataForObserverOrder(orderNumber);
	    System.out.println("Result  // "+result);
	    if(result.isEmpty())
	    { 
	    	indexToOrderTable=0;
	    }
	    else {
	    	if(result.get(0).equals("dataSelecedOrder"))
	    	{
	    		System.out.println("Rigth data is here");
	    		String studentID=result.get(2);
	    		String studentName=result.get(3);
	    		String studentEmail=result.get(4);
	    		String bookSerialNumer=result.get(5);
	    		String availability=result.get(6);
	    		String reachBook=result.get(7);
	    		MaxNumberInLoanNumber=result.get(8);
	    		
	    		Student studentInLoan=new Student(Integer.toString(orderNumber),studentName,studentEmail,bookSerialNumer,"twoDays",reachBook);
	    		addObserver(studentInLoan);
	    	}
	    	
	    	if(result.get(0).equals("noBook"))
	    	{
	    		orderNumber++;
	    		System.out.println("All the books in Order Table without date ");
	    	}
	    }	   	    
	    	if(flag==0)
	    	{
	    		System.out.println("flaag");
	    		System.out.println(MaxNumberInLoanNumber);
	    		 indexToOrderTable=Integer.parseInt(MaxNumberInLoanNumber); 
	    		 System.out.println(indexToOrderTable);
	    		flag=1;
	    	}
	    	System.out.println("index--------");
	    	if(indexToOrderTable>0)
	    	       indexToOrderTable--;
	    	orderNumber++;
		}//while		
	}
	
	@Override
	public void run() {	 
			//System.out.println("Run Function ");		
			notifyAllObservers();
		//	System.out.println("after notifyAllObservers : Order ");
	}

	
	public  void start () {
		String threadName="OrderThread";
		
	    System.out.println("Starting " +  threadName );
	    ReminderThreadClass remindMe = new ReminderThreadClass();
	    observerList= new ArrayList<>();
	    if (t1 == null) {
	       t1 = new Thread (threadName);	       
	     //  System.out.println("333333333333333");
	    }
	  //  System.out.println("22222222222222");
	   if(t1!=null) {
	    while(true) {
		       this.run();	       
			    remindMe.start();
			    System.out.println("\n#########################################################################################################################################################################################################\n");
			    try {
					t1.sleep(1000 * 60 * 60 * 24);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	     }
	   }
	 }
	
	
	@Override
    public void removeObserver(Observer student) {
		System.out.println("\n---> "+((Student) student).getStudentName()+" DELETED\n");
        observerList.remove(student);
        
    }
	
	@Override
	public void addObserver(Observer student) {
		
    	/** 
    	 * First of all we go to data base to bring all the data we are need (they have a 'reach status in ordernew table)
    	 *  After that we make student and enter them to Observe List 
    	 *  At the end in notifyAllObservers() function we check which observer has to take the book during 2 days
    	 *  we calculate the difference between the date of today and the reach date of the book to the library
    	 *  if it is 1 :
    	 *       send an email to remind the subscriber, that their are a book, he need to take
    	 *  if it is 2 :
    	 *       send an email to remind the subscriber, that their are a book, he need to take  
    	 *  if it is 3:
    	 *        the system cancel the order requisite, and send an email to subscriber to tell him that his order deleted     
    	 *           
    	 *          */
		
    	System.out.println(" Add ObserverList Order ");
		 observerList.add(student);
		
	}

	@Override
	public void notifyAllObservers() {
		
		Calendar calendar = Calendar.getInstance();
		System.out.println("before Get Data : Order");
		getData();
		System.out.println("After Get Data : Order");
		
		for(int i=0;i<observerList.size();) {
		   System.out.println("\n**************************************************************************************\n\n"+((Student) observerList.get(i)).getStudentName());
		   observerList.get(i).update(null, sdf.format(calendar.getTime()));		
		       removeObserver(observerList.get(i));
	     }
		
		System.out.println("After update : Order");
	}

}





