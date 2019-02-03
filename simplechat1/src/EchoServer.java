


// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import application.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.sql.*;
import ocsf.server.*;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Timer;


 
/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
	
  //Class variables *************************************************
	static Connection conn ; 
	private Logger logger = new Logger(true);

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
//  public void handleMessageFromClient (Object msg, ConnectionToClient client)
//  {
//	    System.out.println("Message received: " + msg + " from " + client);
//	    this.sendToAllClients(msg);
//  }
  
 
  public void handleMessageFromClient(Object message, ConnectionToClient client)
  {
	  
	 
	    System.out.println("Message received: " + message + " from " + client);
	    ArrayList<String> msg= new ArrayList<String>();
	    ArrayList<Book>BookList=new  ArrayList<Book>();
	    ArrayList<Order> orderList=new  ArrayList<Order>();
		ArrayList<ReportsActions> ReportsList =new ArrayList<ReportsActions>(); 
        boolean req;

	    msg = (ArrayList<String>) message ; 
	    Object arr12 = null;

	    switch(msg.get(msg.size()-1))
	    { 
	    case "PDFOpen":    	  
	  	  try {
	  		  try {
				msg = PDFOpen(msg.get(0))	;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    	   System.out.println("Get order data");
	  		client.sendToClient(msg);
	  	} catch (IOException e) {
	  		
	  		e.printStackTrace();
	  	}
	      break;
	    
	    
	    
	    case "RangeNumberOfDelays":
	    	  try {
				arr12 = (ArrayList<Integer>)NumberOfDelays();
				try {
					client.sendToClient(arr12);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	    	  
	      case "DurationDelays":
	    	  try {
				arr12 = (ArrayList<Integer>) DurationDelays();
				try {
					client.sendToClient(arr12);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	    case "DurationLendingNormal":
	    	  try {
	    		  arr12 =(ArrayList<NormalLending>)DurationLendingNormal();
	    		  try {
						client.sendToClient(arr12);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	    case "DurationLendingRequest": 
	    	  try {
					arr12 =(ArrayList<RequestLending>)DurationLendingRequest();
					try {
						client.sendToClient(arr12);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	  break ;
	    	  
	    	  
	    	  
	    	  /////////////////////////////////////////////////////////
	    	  
	    	  
	      
case "getOrderInfo":    	  
	    	  try {
	    		  orderList = getOrdersInformation(msg.get(0));	    	  
		    	   System.out.println("Get order data");
				client.sendToClient(orderList);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		      break;

case "removeOrder":    	  
	  try {
		  msg = removeDataFromOrder(Integer.parseInt(msg.get(0)));	    	  
  	   System.out.println("Get order data");
		client.sendToClient(msg);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    break;
	    	 
	    
	    case "Check if most request":
	    	
	   
	    
			try {
				 boolean result=	CheckIfRequest(msg.get(0));
				client.sendToClient(result);
			} catch (IOException e10) {
				// TODO Auto-generated catch block
				e10.printStackTrace();
			}
	    break;
	    case"get All Subscribers":
	    	
	    	try {
	    		ArrayList<Subscriber> AllSubscribers=new ArrayList<Subscriber>();
		    	try {
					AllSubscribers=GetAllSubscribers(msg);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				client.sendToClient(AllSubscribers);
			} catch (IOException e8) {				
				e8.printStackTrace();
			}
	    	break;
	    
	    case "Get all actions of subscriber":
	    	ArrayList<Actions> SubscriberAction;
			try {
				SubscriberAction = SubscriberActions(msg);
				client.sendToClient(SubscriberAction);
			} catch (SQLException  | IOException e9) {
				
				e9.printStackTrace();
			}
			break;
				
				    	   	    
	    case "Extension Return Date of book":
	    	
	    	try {
				String NewDateReturn=ExtensionReturnDateOfBook(msg);
				try {
					client.sendToClient(NewDateReturn);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			} catch (SQLException e7) {
				
				e7.printStackTrace();
			}
			
	    	break;
	    
	    
	    case "Get all the books from item in loan":
	    	
	    	try {
	    		
				try {
					ArrayList<ItemInLoan> BooksInLoan;
					System.out.println("maaaaaaaaaaadfgsfgsdgaaa3");
					System.out.println(msg);
					BooksInLoan = GetAllBookFromItemInLoan(msg);
					client.sendToClient(BooksInLoan);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
			} catch (IOException e6) {
				
				e6.printStackTrace();
			}
	    	break;
	    	
	    case "add new action":
	    	
	    	try {
	    		AddAction(msg);
				client.sendToClient(null);
			} catch (IOException e6) {
				
				e6.printStackTrace();
			}
	    	break;
	    
	    
	    case "Remove Book From Library":
	    	
	    	try {
				client.sendToClient(RemoveBook(msg));
			} 
	    	catch (IOException e5) {}
	    	break;
	    
	    case"Add New Book":
	    	
	    	try {
	    		AddNewBook(msg);
	    		
				client.sendToClient(null);
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
	    	break;
	    
	    case"If the book exists in the library":
	    	String Result;
			try {
				
	    		Result=CheckBookInLib(msg);
				client.sendToClient(Result);
				
			} catch (IOException  | InterruptedException e) {
								
				e.printStackTrace();
			}
		
			break;
	    		
	    	
	    case "Librarian Or Manager Update Subscriber Information":
	    	
	    	try {
	    		UpdateInfoSub(msg);
				client.sendToClient(null);
				
			} catch (IOException e4) {
				
				e4.printStackTrace();
			}
	    	 break;
	    
	      case "Edit Information Librarian":
	    	  try {
				EditInformationLibrarian(msg);
				client.sendToClient(null);
				
				
			} catch (SQLException | IOException e3) {
				
				e3.printStackTrace();
			}	    
	    break;
	    
	      case "Edit Information Subscriber":
	    	  try {
				EditInformationSubscriber(msg);
				client.sendToClient(null);
				
				
			} catch (SQLException | IOException e3) {
				
				e3.printStackTrace();
			}	    
	    break;
	    
	    
	      case "GetData":	    	
			 try {
				 msg = (printCUserData(msg.get(0),msg.get(1)));
				 System.out.println("Return User data");
				 client.sendToClient(msg);
			} catch (IOException e3) {
				
				e3.printStackTrace();
			}
	    	 break;
	    	 
	    	 
	      case "GetDataSubescriberToLibrarian":
	    	  
			try {
				msg =printCUserData1(msg.get(0));
				System.out.println("Return User data");				 				
				client.sendToClient(msg);																		
			} catch (SQLException | IOException e2) {
				
				e2.printStackTrace();
			}				
		    	 break;
		    	 
		    	 		    	 		    	 
	      case "UpdatePassword":
	    	  
	    	  try {
	    	   UpdateSubscriberPassword(msg.get(0),msg.get(1));
	    	   System.out.println("Update Password data");
			   client.sendToClient(null);
				
			} catch (IOException e2) {
				
				e2.printStackTrace();
			}
	    	  break; 
	    	  
	    	  
	      case "GetBook":
	    	    	  
	    	  try {
	    		     
		    		 BookList=PrintBookData(msg.get(0),msg.get(1));				    	
			    	 System.out.println("Return User data");	  
				     client.sendToClient(BookList);
				     
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		    	 break;		
		    	 
	      case "Edit Book Details":
	    	  try {
				EditBookDetails(msg);
				client.sendToClient(null);
				
				
			} catch (SQLException | IOException e3) {
				
				e3.printStackTrace();
			}	    
	    break;    	 
		    	 
		    	 
		    	 
		    	 
		    	 
 case "free":
	    	  
	    	  try {
	    		     
		    		 BookList=FreeFillsearch(msg.get(1));				    	
			    	 System.out.println("Return User data");	  
				     client.sendToClient(BookList);
				     
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		    	 break;		
		    	 
 case "GetReports":
	  
	  try {
		     
		  ReportsList=GetAllReports();				    	
	    	 System.out.println("Return User data");	  
		     client.sendToClient(ReportsList);
		     
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
  	 break;		   
		    	 
		    	 
		    	 
		    	 
		    	 
 case "SortDate":    
	  
     try {
		try {
			System.out.println("case sort date");
			msg=sortDate(msg.get(0));
			client.sendToClient(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

 break;			 
		    	 
	    	 		    	 
	      case "AddNewSubscriber":
	    	
	    	  try {
	    		 boolean result1= NewSubscriber(msg);
				client.sendToClient(result1);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	    	  break;
	    	  //bhaa
	    	  
	    	  
	      case "searchForLoan":
	    	  
			try {
				  msg = loanSearchBook(msg.get(0));
		    	  System.out.println("Search For A Book (Loan Page)");
		    	  System.out.println(msg);
				client.sendToClient(msg);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		      break;
		      
		      
		      
		      
	      case "checkStudentStatusForLoan":
	    	
			try {
				  msg = checkStatusOfStudent(msg.get(0),msg.get(1));
		    	  System.out.println("Checking Status of student");		    	 
			      client.sendToClient(msg);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	    	  break;
	    	  
	      case "temporality":
	    		    	  	    	 
			try {
				 msg = setNewPassword(msg.get(0),msg.get(1),msg.get(2));
				 System.out.println("send new password");		    	  
		    	
				 client.sendToClient(msg);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		      break;
		      
		      
		      
	      case "checkIfBookInShelfIs0":
	    	
			try {
				  msg = checkNumberOfBookInShelf(msg.get(0));	    	  
		    	  System.out.println("Book in shelf");		    	  
				  client.sendToClient(msg);
			} catch (IOException e) {				
				e.printStackTrace();
			}
		      break;
		      
		      
		      
		      
	      case "addOrderToDB":
	    	  
			try {
				msg = addNewOrder(msg.get(0),msg.get(1),msg.get(2));	    	  
		        System.out.println("Add new order");	    	  
		    	
				client.sendToClient(msg);
			} catch (IOException e) {			
				e.printStackTrace();
			}
		      break;
		      
		      
		      
		      
	      case "insertToLoanTable":
	    	  
			try {
				msg = addNewLoan(msg.get(0),msg.get(1),msg.get(2),msg.get(3),msg.get(4),msg.get(5));	    	  
		    	System.out.println("Add new loan");		    	  
		    	System.out.println(msg);
				client.sendToClient(msg);
			} catch (IOException e) {			
				e.printStackTrace();
			}
		      break;
		      
		      
	      case "dataForObserve":	    	
			try {
				  msg = getDataForObserver(msg.get(0));	    	  
		    	  System.out.println("Get data for Observer");		    	  
		    	 
				client.sendToClient(msg);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		      break;
		      
		      
		      
	      case "delayTimes++":
	    	
			try {
				  System.out.println(Thread.currentThread().getName());
		    	  msg = increaseDelayTimes(msg.get(0));	    	  		    	  
		    	  System.out.println("Increase delays time");
		    	  System.out.println(msg);
			      client.sendToClient(msg);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		      break;
		      
		      
		      
		      
		      
	      case "numberToContinue":
	    	 
	    	  try {
	    		  msg = getMaximumIndexInItemInLoanTable(msg.get(0));		    	  
		    	  System.out.println("Get max index");
				client.sendToClient(msg);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		      break;
	      case"returnBook":
	    	  
	    	  try {
	    		  msg = returnBook(msg.get(0),msg.get(1));	    	  
		    	  System.out.println("Return book "); 
				client.sendToClient(msg);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	    	  break;
	    	  
	      case "maxForOrder":
	    	  
	    	  try {
	    		  msg = getMaximumIndexInItemInOrderTable();	    	  
		    	   System.out.println("Get max index");
				client.sendToClient(msg);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		      break;
		      
		      
	      case "NumberOfDelays":
	    	  try {
	    		  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	    		  arr12 =NumberOfDelays(msg.get(0),msg.get(1));
					try {
						client.sendToClient(arr12);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			} catch (SQLException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	    	  
	      case "copiesNumber":
	    	  try {
	    		  arr12 =copiesNumber(msg.get(0));
					try {
						client.sendToClient(arr12);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	    	  
	      case "ActiveLockedFrozenSubscribersNumber":
	    	  try {
	    		  arr12 =(ArrayList<String>)ActiveLockedFrozenSubscribersNumber(msg.get(0));
	    		  try {
						client.sendToClient(arr12);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	      	
	      case "RequestActionReport":
	    	  try {
	    		  arr12 =(String)RequestActionReport(msg.get(0),msg.get(1),msg.get(2),msg.get(3),msg.get(4),msg.get(5));
	    		  try {
						client.sendToClient(arr12);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  break ;
	    	  
	      case "ifRequire":
			
			  try {
				  System.out.println("ddddddddddddd");
				  req =CheckIfRequest(msg.get(0));
				  System.out.println(req);
					client.sendToClient(req);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	  break ;
	    	  
	    }	 
	    
	   	   
}
    
















/**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
    
    printStatus();
  }
	protected void printStatus() {
		logger.info("Status");
		logger.info("\t[Server is " + (this.isListening() == true ? "online" : "offline"));
		logger.info("\t[Port " + this.getPort());
		logger.info("\t[Clients connected " + this.getNumberOfClients());
		logger.info("-------------------------");
	}
  
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("New client connected: " + client.getInetAddress() + ", total : " + this.getNumberOfClients());
	}

	protected void clientDisconnected(ConnectionToClient client) {
		System.out.println(" client disconnected: " + client.getInetAddress() + ", total : " + this.getNumberOfClients());
	} 
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on
    TwoDaysForOrders    twoDays=new TwoDaysForOrders();

    connectToDB();
    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    twoDays.start();
  }
  //connectToDB() func to connect to data base //schema's name : librarysys
  
  public static void connectToDB()
  {
	try 
	{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (Exception ex) {/* handle the error*/}
    
    try 
    {
       conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysys","root","123456");
      System.out.println("SQL connection succeed");
 	}catch (SQLException ex) 
 	 {/* handle any errors*/
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
 	   System.out.println("VendorError: " + ex.getErrorCode());
 	 }
  }

  
  public ArrayList<Subscriber> GetAllSubscribers(ArrayList<String> msg) throws SQLException {
	  Statement stmt;
	  stmt = conn.createStatement();
	
	  ResultSet rs = stmt.executeQuery("SELECT * FROM subscriber");
	  ArrayList<Subscriber> AllSubscribers = new ArrayList<Subscriber>();
		
		while(rs.next()) 
			AllSubscribers.add(new Subscriber(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
	
	
		return AllSubscribers;	
	  
	  
	  
		
		
	}  
  
  
  
  
  private ArrayList<Actions> SubscriberActions(ArrayList<String> msg) throws SQLException {
	
	  Statement stmt;
	  stmt = conn.createStatement();
	
	  ResultSet rs = stmt.executeQuery("SELECT * FROM subscriberactions  WHERE SubscriberId = '"+msg.get(0)+"';");
	  ArrayList<Actions> ActionsSub = new ArrayList<Actions>();
		
		while(rs.next()) 
			ActionsSub.add(new Actions(rs.getString(1),rs.getString(2),rs.getString(3)));
	
	//	System.out.println(ActionsSub);
		return ActionsSub;		
	}
 
  
  
  
  

public String ExtensionReturnDateOfBook(ArrayList<String> ExtensionBookReturnDate) throws SQLException {
System.out.println(ExtensionBookReturnDate);	

	 Statement stmt;
	  stmt = conn.createStatement();
	  ResultSet rs = stmt.executeQuery("SELECT * FROM iteminloan  WHERE BookID = '"+ExtensionBookReturnDate.get(0)+"';");
	 
		if(rs.next())//set WasExtensioned to "1" for cant to extension 
		{
			stmt.executeUpdate("UPDATE iteminloan SET returnDate ='"+ExtensionBookReturnDate.get(1)+"'WHERE BookID ='"+ExtensionBookReturnDate.get(0)+"';");
           SendingEmails.sendEmail("Extension Done ! ","The book  "+ExtensionBookReturnDate.get(0)+"\nwas extensioned .\n\nBest Regards ! \n  OBL System","bha_r1995@hotmail.com");

			
			
		}
		else{
		 rs.close();
		}
		return ExtensionBookReturnDate.get(1);
		
	  
	
} 
  
  
  
  
  
  public static void EditBookDetails(ArrayList<String> editBookDetails) throws SQLException {
	  Statement stmt;
	  stmt = conn.createStatement();
	  ResultSet rs = stmt.executeQuery("SELECT * FROM book  WHERE BookId = '"+editBookDetails.get(0)+"';");
	  
	  
	 
		if(rs.next())
			
 		{
			
			String currentBookCopies=rs.getString(6);            // The number of book exist in the library
			String newBookCopiesEnterd=editBookDetails.get(5);  // The number of copies that increased to the library
			int newBookCopies=Integer.valueOf(newBookCopiesEnterd)+Integer.valueOf(currentBookCopies); //The final number of copies 
			
			String bookInShelf=rs.getString(7);
			int newBookShelf=Integer.valueOf(newBookCopiesEnterd)+Integer.valueOf(bookInShelf);
			
			if(Integer.valueOf(newBookCopiesEnterd)>0)
			{DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 			LocalDate localDate = LocalDate.now();
				stmt.executeUpdate("INSERT INTO bookshistoryactions (BookID,BookQuantity,Action,historyDate) VALUES('"+editBookDetails.get(0)+"','"+newBookCopiesEnterd+"','"+"Update"+"','"+dtf.format(localDate)+"')");
			}
			
			
			
			
 			stmt.executeUpdate("UPDATE book SET BookName ='"+editBookDetails.get(1)+"'WHERE BookId ='"+editBookDetails.get(0)+"';");
 			stmt.executeUpdate("UPDATE book SET BookAuthorName ='"+editBookDetails.get(2)+"'WHERE BookId ='"+editBookDetails.get(0)+"';");
 			stmt.executeUpdate("UPDATE book SET BookGenre ='"+editBookDetails.get(3)+"'WHERE BookId ='"+editBookDetails.get(0)+"';");
 			stmt.executeUpdate("UPDATE book SET BookDescription ='"+editBookDetails.get(4)+"'WHERE BookId ='"+editBookDetails.get(0)+"';");
 			stmt.executeUpdate("UPDATE book SET bookCopies ='"+newBookCopies+"'WHERE BookId ='"+editBookDetails.get(0)+"';");

 			//stmt.executeUpdate("UPDATE book SET bookCopies ='"+Integer.valueOf(editBookDetails.get(5))+"'WHERE BookId ='"+editBookDetails.get(0)+"';");
 		//	stmt.executeUpdate("UPDATE book SET BookInShelf ='"+Integer.valueOf(editBookDetails.get(6))+"'WHERE BookId ='"+editBookDetails.get(0)+"';");
	 			stmt.executeUpdate("UPDATE book SET BookInShelf ='"+newBookShelf+"'WHERE BookId ='"+editBookDetails.get(0)+"';");

 			stmt.executeUpdate("UPDATE book SET LocationBook ='"+editBookDetails.get(7)+"'WHERE BookId ='"+editBookDetails.get(0)+"';");

 		}
		else{
		 rs.close();
		}
		
	  
  }
  
 
  //////////////////////////////bhaaaa/////////////////////////////////////////
  
  
 
  public static ArrayList<String> loanSearchBook(String bookSerialNumer)
  {
	  Statement stmt;
		ArrayList<String> BookInfo = new ArrayList<String>();
		System.out.println("Befor Data base");
		try 
		{
			
			stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM book  WHERE BookID = '"+bookSerialNumer+"' AND bookInShelf > '"+0+"' AND bookInShelf > numberOfOrders  ;");
	 		if(rs1.next())
	 		{
	 			BookInfo.add("BookFound");
	 			BookInfo.add(rs1.getString(1));
	 			BookInfo.add(rs1.getString(2)); 
	 			BookInfo.add(rs1.getString(3)); 
	 			BookInfo.add(rs1.getString(4)); 
	 			BookInfo.add(rs1.getString(5)); 
	 			BookInfo.add(rs1.getString(6)); 
	 			BookInfo.add(rs1.getString(7)); 
	 			BookInfo.add(rs1.getString(8)); 
	 			BookInfo.add(rs1.getString(9)); 
	 			BookInfo.add(rs1.getString(10)); 
	 			BookInfo.add(rs1.getString(11)); 
	 			System.out.println(BookInfo);
	 			//return BookInfo;
	 		}
	 		else {
	 		   if(bookSerialNumer.isEmpty()) {
	 				BookInfo.add("emptyField");
	 		   }else { 
		 			  ResultSet rs2 = stmt.executeQuery("SELECT * FROM book  WHERE BookID = '"+bookSerialNumer+"';");
		 			  if(rs2.next())
		 			  {
			 	      	  BookInfo.add("orderIt");
		 			  }
		 			  else {
		 	      	     BookInfo.add("BookInfoNotFound");
		 			  }
		 		   }
	 		}
	 		rs1.close();
		} catch (SQLException e) {e.printStackTrace();}	
	   
		
		return BookInfo;
  }
  
  
  public static ArrayList<String> checkStatusOfStudent(String StudentID,String bookID)
  {
	  int flag=0;
  	Statement stmt,stmt1 ;
		ArrayList<String> studentStatus = new ArrayList<String>();
		System.out.println("Befor Data base");
		
	if(StudentID.isEmpty())	
	{
		studentStatus.add("studentFieldEmpty");
	}
	else {	
	
		try {
			stmt1 = conn.createStatement();
			ResultSet rs2 = stmt1.executeQuery("SELECT loanDate,returnDate FROM iteminloan  WHERE StudentID = '"+StudentID+"' AND  BookID = '"+bookID+"';");
			if(rs2.next()) // If the student exist in loanTaable with the same bookID
			{
				flag=1;
				    studentStatus.add("studentLentThisBook");
		 			studentStatus.add(rs2.getString(1));
		 			studentStatus.add(rs2.getString(2));				
			}else {
				stmt = conn.createStatement();
				ResultSet rs1 = stmt.executeQuery("SELECT SubscriberStatus,EmailSubscriber,PhoneNumber,NameSubscriber FROM subscriber  WHERE IdSubscriber = '"+StudentID+"' AND SubscriberStatus = 'Active';");
		 		if(rs1.next())  //check the status of subscriber
		 		{
		 			
		 			 studentStatus.add("studentIsActive");
		 			studentStatus.add(rs1.getString(2));
		 			studentStatus.add(rs1.getString(3));
		 			studentStatus.add(rs1.getString(4));
		 			 System.out.println(rs1.getString(1) +"   "+ rs1.getString(2)+"   "+rs1.getString(3) );
		 			 System.out.println("student Is Active :)");
		 		}
		 		else { // To check if the  subscriber exist 
					ResultSet rs3 = stmt.executeQuery("SELECT EmailSubscriber,NameSubscriber FROM subscriber  WHERE IdSubscriber = '"+StudentID+"';");
					if(rs3.next()) {
		 			 studentStatus.add("studentIsNotActive");
		 			 studentStatus.add(rs3.getString(1));
		 			 studentStatus.add(rs3.getString(2));
		 			 
		 			 System.out.println("student Is Not Active :(");
					}
					else {
					    studentStatus.add("studentIsNotExist");
						System.out.println("student Is Not exist ");
					} //else
		 		} //else				
			} //else	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // catch

	} // else
	 	return studentStatus;
 }
  
  public static ArrayList<String> setNewPassword(String username, String id , String newPasswordToSet) {
  	
	  Statement stmt;
		ArrayList<String> newPassword = new ArrayList<String>();
		if(username.isEmpty() || id.isEmpty())
		{
			newPassword.add("fieldEmpty");
		}
		try 
		{
			//String pass="123";
			System.out.println("id= "+id);
			stmt = conn.createStatement();
			ResultSet rs2 = stmt.executeQuery("SELECT EmailSubscriber FROM subscriber  WHERE IdSubscriber = '"+id+"'AND NameSubscriber = '"+username+"';");
				
			if(rs2.next())
	 		{
				
	 			newPassword.add("newpassword");
	 			newPassword.add(rs2.getString(1));
	 			newPassword.add(newPasswordToSet);
	 			 stmt.executeUpdate("UPDATE subscriber SET SubscriberPassword ='"+newPasswordToSet+"' WHERE IdSubscriber = '"+id+"' AND NameSubscriber = '"+username+"';");
	 		}
	 		else {
	 			newPassword.add("noChange");
	 		}
	 			
	 			
	 		}catch (SQLException e) {e.printStackTrace();}	
  	
  	return newPassword;
  }
  
  private ArrayList<String> checkNumberOfBookInShelf(String bookID) {
  	
	  Statement stmt;
		ArrayList<String> bookInfoShelf = new ArrayList<String>();
		
		try 
		{	
			
			stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT BookId,bookCopies FROM book  WHERE (bookInShelf='"+0+"' AND BookId = '"+bookID+"') OR (numberOfOrders>=bookInShelf AND BookId = '"+bookID+"');");
	 	
			if(rs1.next())
	 		{
  	        System.out.println("Book in shelf is zero, user can order book with ID"+bookID);
  	        bookInfoShelf.add("bookInShelfIs0");
  	        bookInfoShelf.add(rs1.getString(1));
  	        bookInfoShelf.add(rs1.getString(2));
	 		}
			else {
				 if(bookID.isEmpty()) {
					 bookInfoShelf.add("empty");
				 }
				 else {
			      	System.out.println("Book with id "+bookID+" is in the shelf you can loan it");
				    bookInfoShelf.add("bookInShelfIsNot0");
				 }
			}
		}catch (SQLException e) {e.printStackTrace();}			
	
  	
  	return bookInfoShelf;
  }
  
  
  private ArrayList<String> addNewLoan(String index, String studentID, String bookID, String loanDate, String returnDate,String librarianID) {
  	Statement stmt, stmt2, stmt3;
		ArrayList<String> addSucceedLoan = new ArrayList<String>();
		try 		
		{	int bookInShelf ;
			stmt3 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs1 = stmt2.executeQuery("SELECT bookInShelf,bookCopies FROM book WHERE BookId='"+bookID+"';");
			if(rs1.next()) {
          int inShelf=rs1.getInt(1);
			int copy=rs1.getInt(2);
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM numberofborrowing WHERE BookId='"+bookID+"';");
			if(rs2.next())
			{
				int numberOflending=rs2.getInt(2);
				numberOflending++;
				 stmt2.executeUpdate("UPDATE numberofborrowing SET NumberOfBorrowings ='"+numberOflending+"' WHERE BookId = '"+bookID+"';");
			}
			else {
				int one=1;
				stmt.executeUpdate("INSERT INTO numberofborrowing (BookId,NumberOfBorrowings) VALUES('"+bookID+"','"+one+"')");
			}
			stmt.executeUpdate("INSERT INTO iteminloan (loanNumber,StudentID,BookID,loanDate,returnDate,librarianID) VALUES('"+index+"','"+studentID+"','"+bookID+"','"+loanDate+"','"+returnDate+"','"+librarianID+"')");
			stmt.executeUpdate("INSERT INTO iteminloan2 (StudentID,BookID,CopyID,delay,loanDate,returnDate,returnOnTime) VALUES('"+studentID+"','"+bookID+"','"+0+"','"+0+"','"+loanDate+"','"+returnDate+"','"+0+"')");

			addSucceedLoan.add("SucceedLoan");			
		//	int bookInShelf = Integer.parseInt(rs1.getString(1));
			bookInShelf=inShelf;
			bookInShelf--;	
		//	addSucceedLoan.add(Integer.toString(bookInShelf));
		 stmt3.executeUpdate("UPDATE book SET bookInShelf ='"+bookInShelf+"' WHERE BookId = '"+bookID+"';");

		}
	}catch (SQLException e) {e.printStackTrace();}
  	
  	return addSucceedLoan;
  }
  

  
  
  private ArrayList<String> addNewOrder(String orderNumber, String userID, String bookID) {	
	  Statement stmt,stmt1,stmt2,stmt3;
  	String available="noBook";
  	String reachDate="1/1/2050";
		ArrayList<String> addSucceed = new ArrayList<String>();
		try 
		{	
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			
			ResultSet rs4 = stmt2.executeQuery("SELECT orderNumber FROM ordernew  WHERE bookID='"+bookID+"' AND subscriberID='"+userID+"';");

if(rs4.next()) {
		addSucceed.add("orderExist");
		System.out.println("Order exist !");
	}
  else {
		ResultSet rs3 = stmt2.executeQuery("SELECT numberOfOrders,bookCopies FROM book  WHERE BookId='"+bookID+"';");
		if(rs3.next()) {
		    int x=rs3.getInt(1);
		    
			if(x<rs3.getInt(2)) {
		    x++;
			 stmt1.executeUpdate("UPDATE book SET numberOfOrders ='"+x+"' WHERE BookId = '"+bookID+"';");	
			   stmt.executeUpdate("INSERT INTO ordernew (orderNumber,subscriberID,bookID,availble,reachDate) VALUES('"+orderNumber+"','"+userID+"','"+bookID+"','"+available+"','"+reachDate+"')");
			  ResultSet rs1 = stmt.executeQuery("SELECT MAX(orderNumber) FROM ordernew ;");
			  if(rs1.next())
			  {
				addSucceed.add("Succeed");
				addSucceed.add(rs1.getString(1));
			 }
			 }
			 else{
				addSucceed.add("orderNotSucceed");
			 }
		}
	}
		}catch (SQLException e) {
			addSucceed.add("NoOrder");
			System.out.println("Can't insert this order, it's exist");
			e.printStackTrace();}

  	return addSucceed;
  }
  
  
  
  static ArrayList<String> getDataForObserver(String loanNumber) {
  	Statement stmt,stmt1,stmt2;
		ArrayList<String> subscribeDtata = new ArrayList<String>();
		ResultSet rs1,rs2,rs3;
		String studentID;
		try {
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("SELECT * FROM iteminloan  WHERE loanNumber='"+loanNumber+"' ;");
			/* Bring the name and email of student */
			stmt1 = conn.createStatement();
			/* select the last loan entered*/
			stmt2 = conn.createStatement();
			if(rs1.next())
			{
				System.out.println("Data selected ");
				subscribeDtata.add("dataSeleced");
				subscribeDtata.add(rs1.getString(1));	//Loan Number			
				subscribeDtata.add(rs1.getString(2)); //StudentID
				studentID=rs1.getString(2);
				System.out.println(studentID);
				rs2 = stmt1.executeQuery("SELECT NameSubscriber,EmailSubscriber FROM subscriber  WHERE IdSubscriber='"+studentID+"' ;");
				if(rs2.next()) {
				subscribeDtata.add(rs2.getString(1));      //Student name
				subscribeDtata.add(rs2.getString(2));     //Student email
				}
				subscribeDtata.add(rs1.getString(3));    //book serial number
				subscribeDtata.add(rs1.getString(4));   //loan date
				subscribeDtata.add(rs1.getString(5));  //return date
				rs3 = stmt2.executeQuery("SELECT MAX(loanNumber) FROM iteminloan ;");
				if(rs3.next())
				{
					subscribeDtata.add(rs3.getString(1)); // last loan
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
  	return subscribeDtata;
  }
  
  static ArrayList<String> increaseDelayTimes(String studentID) {


  	
	  Statement stmt,stmt1,stmt2;
		ArrayList<String> delays = new ArrayList<String>();
		System.out.println(Thread.currentThread().getName());
		//System.out.println("+++++++++++++++++++");
		System.out.println(Thread.currentThread().getName());
		ResultSet rs1;
		try 
		{	
			System.out.println(studentID);
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			rs1 = stmt.executeQuery("SELECT delayTime FROM librarysys.subscriber WHERE IdSubscriber='"+studentID+"';");
			//System.out.println("5555555555555555555555555555555555555");
			if(rs1.next())
			{				
				System.out.println(rs1.getString(1));
				int delaysTime = Integer.parseInt(rs1.getString(1));
				delaysTime++;

			if(delaysTime>=0 && delaysTime<=3) {///* If the delays time is 3 the status of subscriber have to be FROZEN 
	 		  stmt1.executeUpdate("UPDATE subscriber SET SubscriberStatus ='Frozen' WHERE IdSubscriber = '"+studentID+"';");
	 		 delays.add("frozen");
			
		     // /* Increase the time of delay 
				///System.out.println("-----------------------------");
				//System.out.println(Thread.currentThread().getName());
				//delays.add("plus");
				delays.add(Integer.toString(delaysTime));
 			stmt2.executeUpdate("UPDATE subscriber SET delayTime ='"+Integer.toString(delaysTime)+"' WHERE IdSubscriber = '"+studentID+"';");			
			}
			
			if(delaysTime>=4)
			{
		 		  stmt1.executeUpdate("UPDATE subscriber SET SubscriberStatus ='Lock' WHERE IdSubscriber = '"+studentID+"';");
			 	   delays.add("Lock");
					delays.add(Integer.toString(delaysTime));
		 			stmt2.executeUpdate("UPDATE subscriber SET delayTime ='"+4+"' WHERE IdSubscriber = '"+studentID+"';");			

			}
	    	}
		}catch (SQLException e) {
			delays.add("excutingError");
			e.printStackTrace();
			}		
	return delays;
  }
  
  
  
  
  

  private ArrayList<String> getMaximumIndexInItemInLoanTable(String string) {
  	Statement stmt;
  	ResultSet rs1;
  	ArrayList<String> maxIndex = new ArrayList<String>();
  	System.out.println("kkkkkkkkkkkkk");
  	try {
  		stmt = conn.createStatement();
  		System.out.println("kjjjjjjjopikj");
			rs1 = stmt.executeQuery("SELECT max(loanNumber) FROM librarysys.iteminloan;");
			System.out.println("56464646464");
	  		
			if(rs1.next())
			{				
				System.out.println("ffffffffffffff "+rs1.getString(1));
				maxIndex.add("maxIndex");
				maxIndex.add(rs1.getString(1));			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
  	return maxIndex;
  }
  
 
  
  
  private ArrayList<String> getMaximumIndexInItemInOrderTable() {
  	Statement stmt;
  	ResultSet rs1;
  	ArrayList<String> maxIndex = new ArrayList<String>();
  	try {
  		stmt = conn.createStatement();
  		System.out.println("kjjjjjjjopikj");
			rs1 = stmt.executeQuery("SELECT max(orderNumber) FROM librarysys.ordernew;");	
			if(rs1.next())
			{				
				System.out.println("ffffffffffffff "+rs1.getString(1));
				maxIndex.add("maxIndexOrder");
				maxIndex.add(rs1.getString(1));			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
  	return maxIndex;
  }
  
  
  
  private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  private ArrayList<String> returnBook(String bookID, String studentID) {
  	
  	Statement stmt,stmt1,stmt2,stmt3,stmt4,stmt5;
		ArrayList<String> ret = new ArrayList<String>();
		
		System.out.println(bookID);
       System.out.println(studentID);
       System.out.println("*************************************************\n");
	 if(bookID.isEmpty() || studentID.isEmpty())
		{
			ret.add("bookEmpty");
		}
		else {
  	try {
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmt4 = conn.createStatement();
			stmt5 = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT bookInShelf,bookCopies FROM book WHERE BookID='"+bookID+"';");
			

   if(rs1.next()) {
		    	 int x=rs1.getInt(1);
		    	 int copy=rs1.getInt(2);
		    	 
		    	 System.out.println("shelf "+x);
		    	 System.out.println("copy "+copy);
    if(copy>x)
   	   {
			         x++;			
	 		  stmt.executeUpdate("UPDATE book SET bookInShelf ='"+x+"' WHERE BookID = '"+bookID+"';");
         
            
             //// Convert status to Active 
          System.out.println(bookID);
          System.out.println(studentID);
	 		  
	 		  ResultSet rs2 = stmt1.executeQuery("SELECT loanNumber  FROM librarysys.iteminloan  WHERE BookID='"+bookID+"' AND StudentID='"+studentID+"';");            
	 		//  System.out.println("$$$$$$$$$$$$$$$$$$$$$"+rs2.getInt(1));
            
	 		  if(rs2.next())
            {
               // String studentID=rs2.getString(1);
          	//  System.out.println("-------- "+studentID);  	 		 
                System.out.println("ppppppppppppppppp");
          	  int loanNum=rs2.getInt(1);
          	  System.out.println("********* "+loanNum);
          	  
          	  stmt1.executeUpdate("UPDATE subscriber SET SubscriberStatus ='Active' WHERE IdSubscriber = '"+studentID+"' AND delayTime< '3';");
                System.out.println("Status changed");
                Calendar calendar1 = Calendar.getInstance();
                stmt1.executeUpdate("UPDATE iteminloan2 SET returnOnTime ='"+sdf.format(calendar1.getTime())+"' WHERE StudentID = '"+studentID+"' AND BookID = '"+bookID+"';");
  	 		 
  	 		  stmt2.executeUpdate("DELETE FROM iteminloan WHERE loanNumber = '"+loanNum+"';");
                System.out.println("Loan "+loanNum+" Deleted from iteminloan");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                 
                ResultSet rs5 = stmt5.executeQuery("SELECT  * FROM librarysys.ordernew where bookID='"+bookID+"' order by orderNumber;");
               while(rs5.next())
               {
              	 
              	 String availabele=rs5.getString(4);
              	 System.out.println(availabele);
            	 if(availabele.equals("noBook")) {
                int orderID=rs5.getInt(1);
                Calendar calendar = Calendar.getInstance();
               
            	  stmt3.executeUpdate("UPDATE ordernew SET  reachDate='"+sdf.format(calendar.getTime())+"'   WHERE orderNumber = '"+orderID+"';");  
          	  stmt3.executeUpdate("UPDATE ordernew SET availble ='reach'   WHERE orderNumber = '"+orderID+"';");
                System.out.println("Book reach to library");
                ResultSet rs3 = stmt4.executeQuery("SELECT subscriberID,orderNumber  FROM ordernew  WHERE bookID='"+bookID+"' AND availble ='reach' ;");
                   if(rs3.next()) {
	                        String studentIDOrder=rs3.getString(1);
	                      
                       ResultSet rs4 = stmt4.executeQuery("SELECT NameSubscriber,EmailSubscriber  FROM subscriber  WHERE IdSubscriber='"+studentIDOrder+"' AND SubscriberStatus ='Active' ;");
                   if(rs4.next()) {
                  	  String studentName =rs4.getString(1);
                  	  String studentEmail=rs4.getString(2);
	                          SendingEmails.sendEmail("Book you ordered, reach to the library ","Hi "+studentName+"\n\nThe book you ordered with serial number "+bookID+"\nreach to the library, it save to you during two days \n If you doesn' take it, he order will be cancel and the book pass to another subscriber.\n\nBest Regards ! \n  OBL System",studentEmail);
                  	      System.out.println(studentName+" Order is available");
	                          System.out.println("Order is reachable ");
                   }    //  if(rs4.next())
                  	 
                  	 
                     }  // if(rs3.next())
                                 
               
                break;
              }
            }   //   while(rs5.next())
               ret.add("returnSuccessfuly");
          }    //  if(rs2.next())
	   }        //if(copy>x) 
 else {
	   System.out.println("Book in shelf == Copies");
	   ret.add("bookStarvies");
 }
}	     //if(rs1.next())
else {
	  ret.add("bookNotExist");
}
     
     
		} catch (SQLException e) {
			 ret.add("errorExecuting");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
}
  	
  	return ret;
  }


  static ArrayList<String> getDataForObserverOrder(int orderNumber) {
  	Statement stmt,stmt1,stmt2;
		ArrayList<String> subscribeDtataOrder = new ArrayList<String>();
		ResultSet rs1,rs2,rs3;
		String studentID;
		try {
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("SELECT * FROM ordernew  WHERE orderNumber='"+orderNumber+"'  ;");
			System.out.println("eeeeeee  ");
			/* Bring the name and email of student */
			stmt1 = conn.createStatement();
			/* select the last loan entered*/
			stmt2 = conn.createStatement();
			if(rs1.next())
			{
				System.out.println("Data selected ");
				subscribeDtataOrder.add("dataSelecedOrder");
				subscribeDtataOrder.add(rs1.getString(1));	//Order Number			
				subscribeDtataOrder.add(rs1.getString(2)); //StudentID
				studentID=rs1.getString(2);
				System.out.println(studentID);
				rs2 = stmt1.executeQuery("SELECT NameSubscriber,EmailSubscriber FROM subscriber  WHERE IdSubscriber='"+studentID+"' ;");
				if(rs2.next()) {
					subscribeDtataOrder.add(rs2.getString(1));      //Student name
					subscribeDtataOrder.add(rs2.getString(2));     //Student email
				}
				subscribeDtataOrder.add(rs1.getString(3));    //book serial number
				subscribeDtataOrder.add(rs1.getString(4));   //Availability
				subscribeDtataOrder.add(rs1.getString(5));  //Reach date
				
				rs3 = stmt2.executeQuery("SELECT MAX(orderNumber) FROM ordernew ;");
				if(rs3.next())
				{
					System.out.println(rs3.getString(1));
					subscribeDtataOrder.add(rs3.getString(1)); // last loan to set while loop in client
				}
			}
			else {
				subscribeDtataOrder.add("noBook");
			}
		} catch (SQLException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
  	return subscribeDtataOrder;
  }
  

  static ArrayList<String> removeDataFromOrder(int orderNumber) {
		
		ArrayList<String> remove = new ArrayList<String>();

  	Statement stmt2,stmt,stmt3;
  	try {
  		stmt = conn.createStatement();
  		stmt2 = conn.createStatement();
  		stmt3 = conn.createStatement();
			ResultSet rs2 = stmt.executeQuery("SELECT bookID FROM ordernew  WHERE orderNumber='"+orderNumber+"' ;");
          if(rs2.next())
          {
	           String bookID=rs2.getString(1);
	           ResultSet rs1 = stmt3.executeQuery("SELECT numberOfOrders FROM book  WHERE BookId='"+bookID+"' ;");
	           if(rs1.next()) {
	           int x;
	           x=rs1.getInt(1);
	           x--;
		 		  stmt3.executeUpdate("UPDATE book SET numberOfOrders ='"+x+"' WHERE BookID = '"+bookID+"';");
		 		 stmt2.executeUpdate("DELETE FROM ordernew WHERE orderNumber = '"+orderNumber+"';");
					
					remove.add("deleteSuccessfuly");
	           }
	           
          }
  		
  		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
  	
  	return remove;
  }
  
/* 
 * In this function 
 * we get the minimum index to start with it 
 * */
  static int getMinimum()
  {   	
  	Statement stmt;
  	ResultSet rs1;
  	int min = 0;
  	System.out.println("kkkkkkkkkkkkk");
  	try {
  		stmt = conn.createStatement();
  		System.out.println("kjjjjjjjopikj");
			rs1 = stmt.executeQuery("SELECT min(orderNumber) FROM librarysys.ordernew;");
			System.out.println("56464646464");	  		
			if(rs1.next())
			{				
				System.out.println("ffffffffffffff "+rs1.getString(1));
				min=rs1.getInt(1);		
			}
			
		} catch (SQLException e) {e.printStackTrace();}   	
  	return min;   	
  }
  
  /* 
   * In this function 
   * we get the minimum index to start with it 
   * */
    static int getMinimumForLoan()
    {   	
    	Statement stmt;
    	ResultSet rs1;
    	int min = 0;
    	System.out.println("kkkkkkkkkkkkk");
    	try {
    		stmt = conn.createStatement();
    		System.out.println("kjjjjjjjopikj");
			rs1 = stmt.executeQuery("SELECT min(loanNumber) FROM librarysys.iteminloan;");
			System.out.println("56464646464");	  		
			if(rs1.next())
			{				
				System.out.println("ffffffffffffff "+rs1.getString(1));
				min=rs1.getInt(1);		
			}
			
		} catch (SQLException e) {e.printStackTrace();}   	
    	return min;   	
    }
  
  
  
    private ArrayList<Order> getOrdersInformation(String subscriberID) {
    	
   	 ArrayList<Order> orderData =new  ArrayList<Order>();
   	 
   	 System.out.println("gggggggggggjjjjjjjjjfffffffff");
   	 
   	 Statement stmt;
   	
   	 try {
			stmt = conn.createStatement();
			ResultSet rs2 = stmt.executeQuery("SELECT * FROM  librarysys.ordernew  WHERE subscriberID='"+subscriberID+"' ;");
	
			int i=0;
			 while(rs2.next()) {
				 System.out.println("befor add order");
				 orderData.add(new Order(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(4),rs2.getString(5)));
				 System.out.println("after add order");			 
			 System.out.println(orderData.get(i).getOrderNumber()+ " "+orderData.get(i).getSubscriberID()+"  "+orderData.get(i).getBookID()+" "+orderData.get(i).getAvailable()+" "+orderData.get(i).getReachDate());
			 i++;
			 }
			  rs2.close();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	    	 
   	return orderData;
   }
  
  
  
  
  ///////////////////////////////////bhaa///////////////////////////////////////
  
  
  
  private ArrayList< ItemInLoan> GetAllBookFromItemInLoan(ArrayList<String> msg) throws ParseException {

	  Statement stmt;		 	
	  ArrayList< ItemInLoan> ArrayItemInLoan=new ArrayList< ItemInLoan>();
	  ArrayList< ItemInLoan> ArrayItemInLoanAfterOrder=new ArrayList< ItemInLoan>();
			try 
			{
				
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM iteminloan  WHERE StudentID = '"+msg.get(0)+"';");
		 			while(rs.next())
		 				ArrayItemInLoan.add(new ItemInLoan(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
					
                  
                
		 			rs.close();		 	
		 			ArrayList< String> BookInOrder=new ArrayList< String>();
		 			ResultSet rs1 = stmt.executeQuery("SELECT bookID FROM ordernew");
		 			while(rs1.next())
		 				BookInOrder.add(rs1.getString(1));
		 			
		
		 			
	int flag=0;	 			
for(int i=0;i<ArrayItemInLoan.size();i++) {
	String BokId=ArrayItemInLoan.get(i).getBookID();
	
	if(!(CheckIfRequest(BokId)))//////
		flag=1;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
 	Date date = formatter.parse(ArrayItemInLoan.get(i).getReturnDate());
 	Calendar calender = Calendar.getInstance();
 	calender.setTime(date);
 	calender.add(Calendar.DATE, -7);
 	date=calender.getTime();//new date       	
	Calendar Currentcalender = Calendar.getInstance();
	Date CurrentDate;
	CurrentDate=Currentcalender.getTime();
	
	 for(int j=0;j<BookInOrder.size();j++) {	
		if(ArrayItemInLoan.get(i).getBookID().equals(BookInOrder.get(j)))
			flag=1;
		
		 

     	}// of for j++	
 	
	if(flag==0&&CurrentDate.after(date) ) {// if flage =1 dont add the book to the table view.
		ResultSet rsBook = stmt.executeQuery("SELECT * FROM book  WHERE BookId = '"+ArrayItemInLoan.get(i).getBookID()+"';");
		String	BookName=null;
            if(rsBook.next()) {
            	BookName=rsBook.getString(2);
            	ArrayItemInLoan.get(i).setBookName(BookName);;
            }
            
		ArrayItemInLoanAfterOrder.add(ArrayItemInLoan.get(i));                 //check date if to extension the book 7 day before
	}
	flag=0;
}//of for i++
	
			} catch (SQLException e) {
				e.printStackTrace();}
			
			return ArrayItemInLoanAfterOrder;		
			
 }
		
		
		
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public void AddAction(ArrayList<String> msg) {
	  Statement stmt;
	  try {
		stmt = conn.createStatement();
		  stmt.executeUpdate(String.format("INSERT INTO subscriberactions(SubscriberId, date, Action) VALUES('%s','%s','%s')",msg.get(0),msg.get(1),msg.get(2)));
			
		}
							
	 catch (SQLException e) {
		e.printStackTrace();
		
	}
		return;
		
}
  
  
  
  
  
  
  
  
 private static String RemoveBook(ArrayList<String> msg) {
	 
       int bookCopies=0;
	  Statement stmt;		 			 						
			try 
			{
				
				stmt = conn.createStatement();
				System.out.println(msg.get(0));
				
				
				ResultSet rs1 = stmt.executeQuery("SELECT * FROM book  WHERE BookId = '"+msg.get(0)+"';");
			if(rs1.next()) {
				bookCopies=rs1.getInt(6);
				
			}
			bookCopies=-1*bookCopies;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 			LocalDate localDate = LocalDate.now();
 			Thread.currentThread().sleep(200);
			ResultSet rs = stmt.executeQuery("SELECT * FROM book  WHERE BookId = '"+msg.get(0)+"';");
				if(rs.next())
	 			{	
		 			stmt.executeUpdate(String.format("INSERT INTO bookshistoryactions(BookID, BookQuantity,Action,historyDate) VALUES('%s','%s','%s','%s')",msg.get(0),bookCopies,"Remove",dtf.format(localDate)));

	 				
	 				stmt.executeUpdate("DELETE FROM book  WHERE BookId = '"+msg.get(0)+"';"); 					 				 			
					
						Thread.currentThread().sleep(200);
					
					rs.close();
					return "Removed";														
	 			}
				
				
	 			
				
				
		 			
		 			
		 			rs.close();
		 						 			
			} catch (SQLException |InterruptedException e) {
				e.printStackTrace();}		
			
			
			return "NotRemoved";	 
 }
  
  
  
  
  
  public static void EditInformationLibrarian (ArrayList<String> editInformationLibrarian) throws SQLException {
	  Statement stmt;
	  stmt = conn.createStatement();
	  ResultSet rs = stmt.executeQuery("SELECT * FROM librarian  WHERE idLibrarian = '"+editInformationLibrarian.get(0)+"';");
		if(rs.next())
 		{
 			stmt.executeUpdate("UPDATE librarian SET LibrarianName ='"+editInformationLibrarian.get(1)+"' WHERE idLibrarian = '"+editInformationLibrarian.get(0)+"';");
 			stmt.executeUpdate("UPDATE librarian SET LibrarianAddress ='"+editInformationLibrarian.get(2)+"' WHERE idLibrarian = '"+editInformationLibrarian.get(0)+"';");
 			stmt.executeUpdate("UPDATE librarian SET LibrarianEmail ='"+editInformationLibrarian.get(3)+"' WHERE idLibrarian = '"+editInformationLibrarian.get(0)+"';");
 			stmt.executeUpdate("UPDATE librarian SET LibrarianPhoneNumber ='"+editInformationLibrarian.get(4)+"' WHERE idLibrarian = '"+editInformationLibrarian.get(0)+"';");
 		
 		}
		else{
		 rs.close();
		}
		
	  
  }
  

  public static void EditInformationSubscriber(ArrayList<String> editInformationSubscriber) throws SQLException {
	  Statement stmt;
	  stmt = conn.createStatement();
	  ResultSet rs = stmt.executeQuery("SELECT * FROM subscriber  WHERE IdSubscriber = '"+editInformationSubscriber.get(0)+"';");
		if(rs.next())
 		{
 			stmt.executeUpdate("UPDATE subscriber SET NameSubscriber ='"+editInformationSubscriber.get(1)+"' WHERE IdSubscriber = '"+editInformationSubscriber.get(0)+"';");
 			stmt.executeUpdate("UPDATE subscriber SET SubscriberAddress ='"+editInformationSubscriber.get(2)+"' WHERE IdSubscriber = '"+editInformationSubscriber.get(0)+"';");
 			stmt.executeUpdate("UPDATE subscriber SET EmailSubscriber ='"+editInformationSubscriber.get(3)+"' WHERE IdSubscriber = '"+editInformationSubscriber.get(0)+"';");
 			stmt.executeUpdate("UPDATE subscriber SET PhoneNumber ='"+editInformationSubscriber.get(4)+"' WHERE IdSubscriber = '"+editInformationSubscriber.get(0)+"';");
 		
 		}
		else{
		 rs.close();
		}
		
	  
  }
  
  
  
  
  
  
  
  public void AddNewBook(ArrayList<String> msg) {
	  Statement stmt;
	   String BookID;	
		BookID = msg.get(0);
		String PDF = msg.get(11); 
		try 
		{
			stmt = conn.createStatement();	 			 		
	 			ResultSet rs = stmt.executeQuery("SELECT * FROM book  WHERE BookId = '"+msg.get(0)+"';");
	 			if(!(rs.next()))
	 					{
	 				try {
	 					System.out.println();
	 			stmt.executeUpdate(String.format("INSERT INTO book(BookId, BookName, BookAuthorName, BookGenre,BookDescription,bookCopies,BookInShelf,LocationBook,printDate,catalogNumber,purchaseDate,numberOfOrders) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d')",msg.get(0),msg.get(1),msg.get(2),msg.get(3),msg.get(4),msg.get(5),msg.get(6),msg.get(7),msg.get(8),msg.get(9),msg.get(10),0));
	 			Thread.currentThread().sleep(200);
	 			
	 			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 			LocalDate localDate = LocalDate.now();
	 			
	 			
	 			
	 			stmt.executeUpdate(String.format("INSERT INTO numberofborrowing(BookId, NumberOfBorrowings) VALUES('%s','%d')",msg.get(0),0));
	 		
	 			stmt.executeUpdate(String.format("INSERT INTO bookshistoryactions(BookID, BookQuantity,Action,historyDate) VALUES('%s','%s','%s','%s')",msg.get(0),msg.get(5),"Add",dtf.format(localDate)));
	 			
	 		
	 			
	 			String sql = "UPDATE book SET pdf = ? where BookId=?";
	       		PreparedStatement ps = conn.prepareStatement(sql);
	    		byte[] by_new = Base64.getDecoder().decode(PDF);
	    		java.sql.Blob blob = conn.createBlob();
	    		blob.setBytes(1, by_new);
	    		ps.setBlob(1, blob);
	    		ps.setString(2, BookID);
	    		ps.executeUpdate();
	 			System.out.println("the book added!");
					Thread.currentThread().sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		
	 			}
	 			stmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();
			
			
			
		
		
		}	
	  
	
	
}


public String CheckBookInLib(ArrayList<String> msg) throws InterruptedException {
	  Statement stmt;		 			 						
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM book  WHERE BookId = '"+msg.get(0)+"';");
			System.out.println(msg.get(0));
			System.out.println("maaa3");
	 			if(rs.next())
	 			{	 					 				 			
					Thread.currentThread().sleep(200);	
					rs.close();
					return "founded";										
	 			}
	 			stmt.close();
	 			rs.close();
	 			
	 			
		} catch (SQLException e) {
			
		
			}			
		
		return "not founded";
}


public void UpdateInfoSub(ArrayList<String> msg) {
	  Statement stmt;
	  
	
	  try {
		  stmt = conn.createStatement();
		  String StatusSubscriber=null;
			ResultSet rs = stmt.executeQuery("SELECT SubscriberStatus FROM subscriber  WHERE IdSubscriber = '"+msg.get(1)+"';");
			if(rs.next()) {
				StatusSubscriber=rs.getString(1);				
			}
			if(StatusSubscriber.equals("Lock")) {
				if(msg.get(5).equals("Active")) {
					stmt.executeUpdate("UPDATE subscriber SET delayTime ='"+0+"' WHERE IdSubscriber = '"+msg.get(1)+"';");

				}
			}

		  if(!(StatusSubscriber.equals(msg.get(5)))) {
			  
			  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 			LocalDate localDate = LocalDate.now();
			  
				stmt.executeUpdate("INSERT INTO statushistory (SubscriberID,status,date) VALUES('"+msg.get(1)+"','"+msg.get(5)+"','"+dtf.format(localDate)+"')");

			  
		  }
		  
		  
		  
		  
		  
		stmt.executeUpdate("UPDATE subscriber SET NameSubscriber ='"+msg.get(0)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		stmt.executeUpdate("UPDATE subscriber SET IdSubscriber ='"+msg.get(1)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		stmt.executeUpdate("UPDATE subscriber SET EmailSubscriber ='"+msg.get(2)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		stmt.executeUpdate("UPDATE subscriber SET PhoneNumber ='"+msg.get(3)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		stmt.executeUpdate("UPDATE subscriber SET SubscriberAddress ='"+msg.get(4)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		stmt.executeUpdate("UPDATE subscriber SET SubscriberStatus ='"+msg.get(5)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		stmt.executeUpdate("UPDATE subscriber SET subscriberNumber ='"+msg.get(6)+"' WHERE IdSubscriber = '"+msg.get(1)+"';");
		System.out.println("UPDATE subscriber SET SubscriberStatus ="+msg.get(5));

	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	
}

 
  public static boolean NewSubscriber(ArrayList<String> NewSubscriber) {
	  Statement stmt;
	
		
		try 
		{
			stmt = conn.createStatement();	 			 		
	 			ResultSet rs = stmt.executeQuery("SELECT * FROM subscriber  WHERE IdSubscriber = '"+NewSubscriber.get(1)+"';");
	 			if(!(rs.next()))
	 					{
	 				
	 			stmt.executeUpdate(String.format("INSERT INTO subscriber(NameSubscriber, IdSubscriber, EmailSubscriber, PhoneNumber,SubscriberAddress,SubscriberStatus,SubscriberPassword,subscriberNumber,delayTime) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')",NewSubscriber.get(0),NewSubscriber.get(1),NewSubscriber.get(2),NewSubscriber.get(3),NewSubscriber.get(4),NewSubscriber.get(5),NewSubscriber.get(6),NewSubscriber.get(7),NewSubscriber.get(8)));
	 			ResultSet rs1 = stmt.executeQuery("SELECT * FROM subscriber  WHERE IdSubscriber = '"+NewSubscriber.get(1)+"';");
                if(rs1.next()) {
                	return true;
                }
                	
	 			
	 			try {
					Thread.currentThread().sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		
	 			}
	 			stmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();}	
		return false;
  }

  
  
public static void  UpdateSubscriberPassword (String SubscriberId,String NewPassword) {
	
	Statement stmt;
	
	try 
	{
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM subscriber  WHERE IdSubscriber = '"+SubscriberId+"';");
		
 		if(rs.next())//if the student is existing 
 		{
 			stmt.executeUpdate("UPDATE subscriber SET SubscriberPassword ='"+NewPassword+"' WHERE IdSubscriber = '"+SubscriberId+"';");

 		
 		}
		
 		 else
		  {
			   rs.close();
	 			
		  }
	} catch (SQLException e) {
		e.printStackTrace();}
}
	



public static ArrayList<Book> FreeFillsearch(String FreeFill)
{
	Statement stmt;
	  ArrayList<Book>BookList = new ArrayList<Book>();
	  try {
		stmt = conn.createStatement();
		 ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE BookDescription LIKE '%" + FreeFill + "%'");
		 while(rs.next()) 
			  BookList.add(new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getInt(10),rs.getString(12),rs.getString(11),rs.getInt(13)));	  
		    rs.close();
		    System.out.println(BookList);
		    return BookList;
		   
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	return BookList;
	
	
	
}













public static   ArrayList<Book> PrintBookData(String SearchBy,String ColName){
	  
	  Statement stmt;
	  ArrayList<Book>BookList = new ArrayList<Book>();
	  try 
		{
	  stmt = conn.createStatement();
	  switch(SearchBy) {
	  case "AuthorName":
		  ResultSet rs = stmt.executeQuery("SELECT * FROM book  WHERE BookAuthorName =  '"+ColName+"';");
		  while(rs.next()) 
			  BookList.add(new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getInt(10),rs.getString(12),rs.getString(11),rs.getInt(13)));	  
		  rs.close();
		  System.out.println(BookList);
		  return BookList;
		  
	  case "BookName":
		  ResultSet rs1 = stmt.executeQuery("SELECT * FROM book  WHERE BookName =  '"+ColName+"';");
		  while(rs1.next()) 
			  BookList.add(new Book(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),rs1.getInt(6),rs1.getInt(7),rs1.getString(8),rs1.getString(9),rs1.getInt(10),rs1.getString(12),rs1.getString(11),rs1.getInt(13)));	  
		  rs1.close();
		  return BookList;
		  
	  case "BookGenre":
		  ResultSet rs2 = stmt.executeQuery("SELECT * FROM book  WHERE BookGenre =  '"+ColName+"';");
		  while(rs2.next()) 
			  BookList.add(new Book(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(4),rs2.getString(5),rs2.getInt(6),rs2.getInt(7),rs2.getString(8),rs2.getString(9),rs2.getInt(10),rs2.getString(12),rs2.getString(11),rs2.getInt(13)));	  
		  rs2.close();
		  return BookList;
		  
	  
		  
		  
		  
	  }
	
		}catch (SQLException e)
		
		{
			 
			e.printStackTrace();
		}
	
	 
	 
	  return BookList;

	  
}




	


  
  
  
 
  
  public static ArrayList<String>printCUserData1(String SubscriberID) throws SQLException {
	
	  Statement stmt;
		ArrayList<String>SusbcriberInfo = new ArrayList<String>();
	  stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Subscriber  WHERE IdSubscriber = '"+SubscriberID+"';");
	

		try 
		{
		if(rs.next())
		{
			SusbcriberInfo.add("GetData_Subscriber");
			SusbcriberInfo.add(rs.getString(1));/**Get Name Subscriber from database*/
			SusbcriberInfo.add(rs.getString(2));/**Get Id Subscriber from database*/
			SusbcriberInfo.add(rs.getString(3));/**Get Email Subscriber from database*/
			SusbcriberInfo.add(rs.getString(4));/**Get Phone Subscriber from database*/
			SusbcriberInfo.add(rs.getString(5));/**Get Address Subscriber from database*/
			SusbcriberInfo.add(rs.getString(6));/**Get Status Subscriber from database*/
			SusbcriberInfo.add(rs.getString(8));/**Get Number Subscriber from database*/
			
		stmt.close();
			return SusbcriberInfo;
		}
		}
	catch (SQLException e)
	
	{
		 
		e.printStackTrace();
	}
		SusbcriberInfo.add("not Found");
		stmt.close();
		
		return SusbcriberInfo;
	  
  }
  
				
  public static ArrayList<String> printCUserData(String SubscriberID,String SubscriberPassword)
	{
		Statement stmt;
		ArrayList<String>SusbcriberInfo = new ArrayList<String>();
	
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Subscriber  WHERE IdSubscriber = '"+SubscriberID+"' AND SubscriberPassword='"+ SubscriberPassword+"';");
			if(rs.next())
	 		{
	 			SusbcriberInfo.add("GetData_Subscriber");
	 			SusbcriberInfo.add(rs.getString(1));
	 			SusbcriberInfo.add(rs.getString(2));
	 			SusbcriberInfo.add(rs.getString(3));
	 			SusbcriberInfo.add(rs.getString(4));
	 			SusbcriberInfo.add(rs.getString(5));
	 			SusbcriberInfo.add(rs.getString(6));
	 			SusbcriberInfo.add(rs.getString(7));
	 			SusbcriberInfo.add(rs.getString(8));
	 			
	 			
	 			return SusbcriberInfo;
			}
	 		
	 				  		  
			 
			    rs = stmt.executeQuery("SELECT * FROM librarian  WHERE idLibrarian = '"+SubscriberID+"' AND LibrarianPassword='"+SubscriberPassword+"';");
				if(rs.next())
		 		{
					SusbcriberInfo.add("GetData_Librarian");
		 			SusbcriberInfo.add(rs.getString(1));
		 			SusbcriberInfo.add(rs.getString(2));
		 			SusbcriberInfo.add(rs.getString(3));
		 			SusbcriberInfo.add(rs.getString(4));
		 			SusbcriberInfo.add(rs.getString(5));
		 			SusbcriberInfo.add(rs.getString(6));
		 			SusbcriberInfo.add(rs.getString(7));
		 			System.out.println(SusbcriberInfo);
		 			
		 			return SusbcriberInfo;
				}
				 rs = stmt.executeQuery("SELECT * FROM manager  WHERE idManager = '"+SubscriberID+"' AND ManagerPassword='"+SubscriberPassword+"';");
					if(rs.next())
			 		{
						SusbcriberInfo.add("GetData_Manager");
			 			SusbcriberInfo.add(rs.getString(1));
			 			SusbcriberInfo.add(rs.getString(2));
			 			SusbcriberInfo.add(rs.getString(3));
			 			SusbcriberInfo.add(rs.getString(4));
			 			SusbcriberInfo.add(rs.getString(5));
			 			SusbcriberInfo.add(rs.getString(6));
			 			SusbcriberInfo.add(rs.getString(7));
			 			System.out.println(SusbcriberInfo);
			 			
			 			return SusbcriberInfo;
					}
				SusbcriberInfo.add("NotFound");
			   rs.close();
			   
	 			return SusbcriberInfo ;
		  
		}
		catch (SQLException e)
		
		{
			 
			e.printStackTrace();
		}
		
		return SusbcriberInfo;
	}
  
  
  public static ArrayList<String> sortDate(String Des)  throws SQLException 
  {
  ArrayList<String> sortDate =new ArrayList<String> ();
  Statement stmt;
   stmt = conn.createStatement();
  ResultSet rs = stmt.executeQuery("SELECT * FROM iteminloan WHERE BookID = '"+Des+ "';");
  int min =999999  , diffDays ;
  String returnDate =null;
  while(rs.next()) {
          SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
      Calendar c1 = Calendar.getInstance();
      c1.setTime(new Date()); // Now use today date.
      String outputcurrentDate = currentDate.format(c1.getTime());
      System.out.println("toays is"+outputcurrentDate);

       try {
  String date1 = outputcurrentDate;
  String date2 = rs.getString(5);//return day ;
  String format = "dd/MM/yyyy";

  SimpleDateFormat sdf = new SimpleDateFormat(format);

  Date dateObj1 = sdf.parse(date1);
  Date dateObj2 = sdf.parse(date2);
  System.out.println(dateObj1);
  System.out.println(dateObj2 + "\n");


  // getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
  long diff = dateObj2.getTime() - dateObj1.getTime();

    diffDays = (int) (diff / (24 * 60 * 60 * 1000));
    if(diffDays < min && diffDays >0)
    {
    min =diffDays;
    returnDate=date2;
    }
  System.out.println("difference between days: " +diffDays);
       } catch (Exception e) {
  e.printStackTrace();
  }
       }
    rs.close();
    if(returnDate!=null) 
    	sortDate.add(returnDate);
    System.out.println(sortDate);
    return sortDate;
  }
  
  
  public int NumberOfDelays(String date1,String searchDate) throws SQLException, ParseException
  {
  	String date2;
  	int countDelay =0;
  	Statement stmt=null;
  	stmt = conn.createStatement();
  	ResultSet rs=stmt.executeQuery("SELECT *FROM iteminloan2");
  	while(rs.next())
  	{
  		date2 = rs.getString(7);//return the book 
  		 try {
  				String date11 =date1;
  				String date22 = date2;
  				String format = "dd/MM/yyyy";
  	
  				SimpleDateFormat sdf = new SimpleDateFormat(format);
  	
  				Date dateObj1 = sdf.parse(date11);
  				Date dateObj2 = sdf.parse(date22);
  				System.out.println(dateObj1);
  				System.out.println(dateObj2 + "\n");
  	
  				DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
  	
  				// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
  				long diff = dateObj2.getTime() - dateObj1.getTime();
  	
  				 int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
  				 System.out.println("difference between days: " + diffDays);
  				 
  				 if(diffDays <=0) {
  					    try {
  						String date111 =rs.getString(7);;
  						String date222 = rs.getString(6);;
  						String format1 = "dd/MM/yyyy";
  			
  						SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
  			
  						Date dateObj11 = sdf.parse(date111);
  						Date dateObj22 = sdf.parse(date222);
  						System.out.println(dateObj11);
  						System.out.println(dateObj22 + "\n");
  			
  						DecimalFormat crunchifyFormatter1 = new DecimalFormat("###,###");
  			
  						// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
  						long diff1 = dateObj22.getTime() - dateObj11.getTime();
  			
  						 int diffDays1 = (int) (diff1 / (24 * 60 * 60 * 1000));
  						 System.out.println("difference between days: " + diffDays1);
  						 
  						 if(diffDays1<0) //delay
  							 countDelay++;
  			
  					} catch (Exception e) {
  						e.printStackTrace();
  					}
  				 }
  	
  			} catch (Exception e) {
  				e.printStackTrace();
  			} 
  	}
  	
      return countDelay;
  }

  public static int copiesNumber(String Date) throws SQLException
  {
  	int copyNumber =0;
  	Statement stmt =null;
  	stmt = conn.createStatement();
  	ResultSet rs = stmt.executeQuery("SELECT * FROM bookshistoryactions ");
       while(rs.next())
       {
      	 try {
  				String date11 =Date;
  				String date22 = rs.getString(4);
  				String format = "dd/MM/yyyy";
  	
  				SimpleDateFormat sdf = new SimpleDateFormat(format);
  	
  				Date dateObj1 = sdf.parse(date11);
  				Date dateObj2 = sdf.parse(date22);
  				System.out.println(dateObj1);
  				System.out.println(dateObj2 + "\n");
  	
  				DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
  	
  				// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
  				long diff = dateObj2.getTime() - dateObj1.getTime();
  	
  				 int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
  				 System.out.println("difference between days: " + diffDays);
  				 
  				 if(diffDays <=0) //NumberCopies
  					 copyNumber = copyNumber+Integer.parseInt(rs.getString(2));
  	
  			} catch (Exception e) {
  				e.printStackTrace();
  			} 
       }
  	
  	return copyNumber;
  }
  public static ArrayList<String> ActiveLockedFrozenSubscribersNumber(String Date) throws SQLException
  {
  	ArrayList<String> statusList = new ArrayList<String>();
  	int ActiveSubscriberNumber=0 , LockedSubscriberNumber =0 , FrozenNumber =0 ,MaxDay =3700;
  	String status = null  , ID =null;
  	Statement stmt =null;
  	stmt = conn.createStatement();
  	ResultSet rs = stmt.executeQuery("SELECT * FROM statushistory ");
       while(rs.next())
       {
      	 do {
      	 MaxDay = 3700;
      	 ID=rs.getString(1);	
           try {
      		String date1 = Date;
      		String date2 = rs.getString(3);//day ;
      		String format = "dd/MM/yyyy";

      		SimpleDateFormat sdf = new SimpleDateFormat(format);

      		Date dateObj1 = sdf.parse(date1);
      		Date dateObj2 = sdf.parse(date2);
      		System.out.println(dateObj1);
      		System.out.println(dateObj2 + "\n");

      		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

      		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
      		long diff = dateObj2.getTime() - dateObj1.getTime();

      		  int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
      		  if(diffDays < MaxDay && diffDays <=0 )
      		  {
      			  MaxDay =diffDays;
      			  status =rs.getString(2);
      		  }
      			System.out.println("difference between days: " +diffDays);
           } catch (Exception e) {
      			e.printStackTrace();
      		}
       }while(rs.next() && ID.equals(rs.getString(1)));
      	
      	 if(status.equals("Active")) ActiveSubscriberNumber++;
      	 if(status.equals("Locked")) LockedSubscriberNumber++;
      	 if(status.equals("Frozen")) FrozenNumber++ ;
      	 
     rs.previous();
   //  rs.previous();
  }
       statusList.add(""+ActiveSubscriberNumber); 
       statusList.add(""+LockedSubscriberNumber);
       statusList.add(""+FrozenNumber);
  	return statusList;
  }
  public static String RequestActionReport(String s1,String s2 ,String s3,String s4 ,String s5 ,String s6) throws SQLException 
  {
  	String Request =null;
     Statement stmt = null;
     try {
  	stmt = conn.createStatement();
  	stmt.executeUpdate("INSERT INTO reportaction (activeSubscribers ,frozenSubscribers,lockedSubscribers,copiesNumber,delayReurning,reportDate ) VALUES('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"')");
  	 Request="Suscceded";
  } catch (SQLException e) {
  	// TODO Auto-generated catch block
  	e.printStackTrace();
  }
     
     return Request ;
  }
    
  
  
  
  
  
  private boolean CheckIfRequest(String BookId) {
	  boolean res = false;
	  try {
		  
		  Statement stmt;
			ArrayList<Integer>NumberOfBorrowingsBookArray = new ArrayList<Integer>();
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT NumberOfBorrowings FROM numberofborrowing");
		
		
		while(rs.next()) {
		
			NumberOfBorrowingsBookArray.add(rs.getInt(1));
		}
		ResultSet rs2 = stmt.executeQuery("SELECT NumberOfBorrowings FROM numberofborrowing  WHERE BookId = '"+BookId+"';");
		int NumberOfBorrowingsBook = 0;
		if(rs2.next())
			NumberOfBorrowingsBook=	rs2.getInt(1);
		
		  Collections.sort(NumberOfBorrowingsBookArray);
		int median=NumberOfBorrowingsBookArray.get(NumberOfBorrowingsBookArray.size()/2);
		
		if(NumberOfBorrowingsBook<=median) 
			res=true;
		else
			res=false;
		
		
		
	} catch (SQLException e) {
	
		e.printStackTrace();
	}
		
		
		
		return res;
	}
  private ArrayList<ReportsActions> GetAllReports() {
		// TODO Auto-generated method stub
		Statement stmt;
		ArrayList<ReportsActions> ReportsList =new ArrayList<ReportsActions>(); 
		  try {
			stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM reportaction");
			 while(rs.next()) 
				 ReportsList.add(new ReportsActions(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7)));	  
			    rs.close();
			    System.out.println(ReportsList);
			    return ReportsList;
			   
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return ReportsList;
  }

  
  
  public static ArrayList<NormalLending> DurationLendingNormal() throws SQLException
  {
  	ArrayList<NormalLending> r = new  ArrayList<NormalLending>();
  	ArrayList<Integer> Duration =new ArrayList<Integer>();
  	Statement st = conn.createStatement();
      st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT *FROM iteminloan2");
      String date1 ,date2;
      while(rs.next())
      {
      	 date2 = rs.getString(7);//return date ;
           date1 =  rs.getString(5);//loan date
           if(date2==null) System.out.println("Item is not in loan");
           else {
   		    try {
        		String format = "dd/MM/yyyy";

        		SimpleDateFormat sdf = new SimpleDateFormat(format);

        		Date dateObj1 = sdf.parse(date1);
        		Date dateObj2 = sdf.parse(date2);
        		System.out.println(dateObj1);
        		System.out.println(dateObj2 + "\n");

        		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

        		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
        		long diff = dateObj2.getTime() - dateObj1.getTime();

        		  int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        		  if(diffDays >0 && RequestBook(rs.getString(1))==0 )// if(diffDays < min && diffDays >0)
        		  {
        			 Duration.add(diffDays);
        		  }
        			System.out.println("difference between days: " +diffDays);
        		
   		   } catch (Exception e) {
     			e.printStackTrace();
     		}
          }
      }
      Collections.sort(Duration);//Sort list
      
      int x  ,count ;
      if(Duration.size()>0)
      {
      	x = Duration.get(0);
      	count = 1;
      
      for(int i=1;i<Duration.size();i++)
      {
      	if(Duration.get(i)!=x)
      	{
      		r.add(new NormalLending(""+x,""+count));
      		x =Duration.get(i) ;
      		count =0;
      	}
      	else
      	count++ ;
      }
      r.add(new NormalLending(""+x,""+(count+1)));
      }
      System.out.println("yesssssss Normal");
      System.out.println(r);
      return r ;  
  }
  
  public static ArrayList<RequestLending> DurationLendingRequest() throws SQLException
  {
  	ArrayList<RequestLending> r = new  ArrayList<RequestLending>();
  	ArrayList<Integer> Duration =new ArrayList<Integer>();
  	Statement st = conn.createStatement();
      st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT *FROM iteminloan2");
      String date1 ,date2;
      while(rs.next())
      {
      	 date2 = rs.getString(7);//return date ;
           date1 =  rs.getString(5);//loan date
           if(date2==null) System.out.println("Item is not in loan");
           else {
   		    try {
        		String format = "dd/MM/yyyy";

        		SimpleDateFormat sdf = new SimpleDateFormat(format);

        		Date dateObj1 = sdf.parse(date1);
        		Date dateObj2 = sdf.parse(date2);
        		System.out.println(dateObj1);
        		System.out.println(dateObj2 + "\n");

        		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

        		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
        		long diff = dateObj2.getTime() - dateObj1.getTime();

        		  int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        		  if(diffDays >0 && RequestBook(rs.getString(1))==1 )// if(diffDays < min && diffDays >0)
        		  {
        			 Duration.add(diffDays);
        		  }
        			System.out.println("difference between days: " +diffDays);
        		
   		   } catch (Exception e) {
     			e.printStackTrace();
     		}
          }
      }
      Collections.sort(Duration);//Sort list
      int x  ,count ;
      if(Duration.size()>0)
      {
      x = Duration.get(0) ;
      count = 1;
      for(int i=1;i<Duration.size();i++)
      {
      	if(Duration.get(i)!=x)
      	{
      		r.add(new RequestLending(""+x,""+count));
      		x =Duration.get(i) ;
      		count =0 ;
      	}
      	else
      	count++ ;
      }
      r.add(new RequestLending(""+x,""+(count+1)));
      }
      System.out.println("yesssssss Request");
      System.out.println(r);
      return r ;  
  }
  
  
  
  
  public static int RequestBook(String bookid) throws SQLException
  {
  	int flag =0;
  	Statement st = conn.createStatement();
  	st = conn.createStatement();
  	ResultSet rs = st.executeQuery("SELECT *FROM book WHERE BookId = '"+bookid+"' ;");
  	if(rs.next())
  		if(rs.getInt(13)>=rs.getInt(6)/2.0) flag =1 ;
  	return flag ;
  }
  
  public static ArrayList<Integer> NumberOfDelays() throws SQLException
  {
  	ArrayList<Integer> Duration =new ArrayList<Integer>();
  	Statement st = conn.createStatement();
      st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT *FROM subscriber");
   
      while(rs.next())
          Duration.add(rs.getInt(9));
      
      Collections.sort(Duration);//Sort list
      return Duration ;  
  }
  public static ArrayList<Integer> DurationDelays() throws SQLException
  {
  	ArrayList<Integer> Duration =new ArrayList<Integer>();
  	Statement st = conn.createStatement();
      st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT *FROM iteminloan2");
      String date1 ,date2;
      while(rs.next())
      {
      	 date2 = rs.getString(7);//return date ;
           date1 =  rs.getString(6);//loan date
           if(date2==null) System.out.println("Item is not in loan");
           else {
   		    try {
        		String format = "dd/MM/yyyy";

        		SimpleDateFormat sdf = new SimpleDateFormat(format);

        		Date dateObj1 = sdf.parse(date1);
        		Date dateObj2 = sdf.parse(date2);
        		System.out.println(dateObj1);
        		System.out.println(dateObj2 + "\n");

        		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

        		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
        		long diff = dateObj2.getTime() - dateObj1.getTime();

        		  int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        		  if(diffDays >=0)// if(diffDays < min && diffDays >0)
        		  {
        			 Duration.add(diffDays);
        		  }
        			System.out.println("difference between days: " +diffDays);
        		
   		   } catch (Exception e) {
     			e.printStackTrace();
     		}
          }
      }
      Collections.sort(Duration);//Sort list
      return Duration ;  
  }
  
  public static ArrayList<String> PDFOpen(String BookID) throws SQLException
  {
	    ArrayList<String> book =new ArrayList<String>();
		Statement st = conn.createStatement();
	    st = conn.createStatement();
	    ResultSet rs = st.executeQuery("SELECT * FROM book WHERE bookID = '"+BookID+"';");
	    if(rs.next()) {
	    byte[] by_new = rs.getBytes("pdf");
	    book.add(Base64.getEncoder().encodeToString(by_new));
	    }
	    return book;  
  }
   

	
}
//End of EchoServer class

