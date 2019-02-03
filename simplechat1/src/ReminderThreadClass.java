

/**
 * 
 * This class  responsible to build a list of observers
 * in our case observer is the subscriber .
 * 
 * this class implements Runnable, there are a thread that work the 
 * this methods .
 * 
 * The check if there are observer need to return the book in the next day 
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
 * **/




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;



public class ReminderThreadClass implements Loan, Runnable {

	
	List<Observer> observerList= new ArrayList<>();
	
	  private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  public static int loanNumber=0;
	  public static int indexInLoanTableToContinue=0;
	  public static Thread t=null;
	  
	public void getData()
	{
		int flag=0;
		String MaxNumberInLoanNumber = null;
		int indexToLoanTable=1;
		
		loanNumber=EchoServer.getMinimumForLoan();
		System.out.println("5555555 "+loanNumber);
		MaxNumberInLoanNumber=Integer.toString(loanNumber);
         System.out.println("ggggggg "+MaxNumberInLoanNumber);
		while(indexToLoanTable!=0) {
		
		// result, is the arrayList that content the data about the book
		 String loanID=Integer.toString(loanNumber);
		 ArrayList<String>  result = new ArrayList<String>(); 
		result = EchoServer.getDataForObserver(loanID);
	    System.out.println(result);
	    if(result.isEmpty())
	    {
	    	MaxNumberInLoanNumber="0";
	    	indexToLoanTable=0;
	    }
	    else {
	    	if(result.get(0).equals("dataSeleced"))
	    	{
	    		System.out.println("Rigth data is here");
	    		String studentID=result.get(2);
	    		String studentName=result.get(3);
	    		String studentEmail=result.get(4);
	    		String bookSerialNumer=result.get(5);
	    		String loanBook=result.get(6);
	    		String returnBook=result.get(7);
	    		MaxNumberInLoanNumber=result.get(8);
	    		Student studentInLoan=new Student(studentID,studentName,studentEmail,bookSerialNumer,loanBook,returnBook,"reminder");
	    		addObserver(studentInLoan);
	    	}
	    }
	   	    
	    	if(flag==0)
	    	{
	    		System.out.println("flaag");
	    		 indexToLoanTable=Integer.parseInt(MaxNumberInLoanNumber);
	    		 indexInLoanTableToContinue=indexToLoanTable;
	    		flag=1;
	    	}
	    	System.out.println("index--------");
	        	if(indexToLoanTable>0)
	    	         indexToLoanTable--;
	    	
	    	loanNumber++;	    	
		}//while
		
	}
	    @Override
		public void addObserver(Observer student) {
			// TODO Auto-generated method stub
	    	/** First of all we go to data base to bring all the data we are need 
	    	 *  After that we make student and enter them to Observe List 
	    	 *  At the end in notifyAllObservers() function we check which observer has to return book tomorrow */
	    	System.out.println(" Add ObserverList ");
			 observerList.add(student);
		}

	    
	    @Override
	    public void removeObserver(Observer student) {
	    	System.out.println("\n---> "+((Student) student).getStudentName()+" DELETED\n");
	        observerList.remove(student);
	    }
	    
	    
	@Override
		public void notifyAllObservers() {
			// TODO Auto-generated method stub
			Calendar calendar = Calendar.getInstance();
			getData();
			
			//System.out.println("NotifyAllObservers Function ");
			for(int i=0;i<observerList.size();) {
				 System.out.println("\n_____________________________________________________________________________________\n\n"+((Student) observerList.get(i)).getStudentName());
				 observerList.get(i).update(null, sdf.format(calendar.getTime()));
				 removeObserver(observerList.get(i));
		        }
		}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println(Thread.currentThread().getName());
		System.out.println("!!!!!!!!!!!!!");
		 
		//while(true)
		//{
			 
			System.out.println("Run Function ");
			//System.out.println(Thread.currentThread().getName());
			notifyAllObservers();
		/*	 
			try {
				//t.sleep(1000 * 60 * 60 * 24);
				t.sleep(1000 * 10);
				//t.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//}		
	}
	
	
   public  void start () {
		String threadName="notifyThread";
	    System.out.println("Starting " +  threadName );
	    
	    if(t!=null) {
		       this.run();
		       System.out.println("555555555555");
		    }
	    
	    if (t == null) {
	       t = new Thread (threadName);
	     // t.start();     
	       this.run();
	       
	       System.out.println("44444444444444");
	    }
	   
	 }
		

   
   
	
}
