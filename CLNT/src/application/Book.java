
/**
 * 
 * In this class we save the details of the books 
 * to use in another classes
 * 
 * All the variables in private case with getter s and setters
 * 
 * @author  Adham Shahwan
 * 
 * */




package application;

import java.io.Serializable;

import javafx.scene.control.Button;


public class Book implements Serializable  {
	private String BookId;
	private String EditionNumber;
	private String PrintingnDate;
	private String BookName;
	private String BookAuthorName;
	private String BookSubject;
	private String BookDescription;
	private int CatalogNumber;
	private int CopiesNumber;
	private int BookInShelf;
	private String LocationBook;

	private String pdfPath;
	private String purchaseDate;
	private int numberOfOrders;
	
	public int getCopiesNumber() {
		return CopiesNumber;
	} 
	public void setCopiesNumber(int copiesNumber) {
		CopiesNumber = copiesNumber;
	}
	
	public String getEditionNumber() {
		return EditionNumber;
	}
	public void setEditionNumber(String editionNumber) {
		EditionNumber = editionNumber;
	}
	public String getPrintingnDate() {
		return PrintingnDate;
	}
	public void setPrintingnDate(String printingnDate) {
		PrintingnDate = printingnDate;
	}
	public int getCatalogNumber() {
		return CatalogNumber;
	}
	public void setCatalogNumber(int catalogNumber) {
		CatalogNumber = catalogNumber;
	}
	public String getBookId() {
		return BookId;
	}
	public void setBookId(String bookId) {
		BookId = bookId;
	}
	public String getBookName() {
		return BookName;
	}
	public void setBookName(String bookName) {
		BookName = bookName;
	}
	public String getBookAuthorName() {
		return BookAuthorName;
	}
	public void setBookAuthorName(String bookAuthorName) {
		BookAuthorName = bookAuthorName;
	}
	public String getBookSubject() {
		return BookSubject;
	}
	public void setBookSubject(String bookSubject) {
		BookSubject = bookSubject;
	}
	public String getBookDescription() {
		return BookDescription;
	}
	public void setBookDescription(String bookDescription) {
		BookDescription = bookDescription;
	}
	public Book(String bookId, String bookName, String bookAuthorName, String bookSubject, String bookDescription,int copiesNumber,int bookInShelf,String locationBook,String printingnDate,int catalogNumber,String pdfpath,String purchasedate,int numberoforders) {
		super();
		BookId = bookId;
		BookName = bookName;
		BookAuthorName = bookAuthorName;
		BookSubject = bookSubject;
		BookDescription = bookDescription;
		CatalogNumber=catalogNumber;
		PrintingnDate=printingnDate;  
		CopiesNumber=copiesNumber;
		BookInShelf=bookInShelf;
		LocationBook=locationBook;
		
		
		setPdfPath(pdfpath);
		setPurchaseDate(purchasedate);
		setNumberOfOrders(numberoforders);
					
	}	

public String toString()
{
	return BookId+","+BookName+","+BookAuthorName+","+BookSubject+","+BookDescription+","+PrintingnDate+","+EditionNumber+","+CatalogNumber+","+CopiesNumber;	
}
public int getBookInShelf() {
	return BookInShelf;
}
public void setBookInShelf(int bookInShelf) {
	BookInShelf = bookInShelf;
}
public String getLocationBook() {
	return LocationBook;
}
public void setLocationBook(String locationBook) {
	LocationBook = locationBook;
}
public String getPdfPath() {
	return pdfPath;
}
public void setPdfPath(String pdfPath) {
	this.pdfPath = pdfPath;
}
public String getPurchaseDate() {
	return purchaseDate;
}
public void setPurchaseDate(String purchaseDate) {
	this.purchaseDate = purchaseDate;
}
public int getNumberOfOrders() {
	return numberOfOrders;
}
public void setNumberOfOrders(int numberOfOrders) {
	this.numberOfOrders = numberOfOrders;
}

	

}
