package pack;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;
//Patron.java

public class Patron extends User
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lNumb;
	private LinkedList<Book> books;

//Default Constructor
 public Patron() 
 {
     super(); // Calls the constructor of the superclass User
     lNumb = "";
     books = new LinkedList<>(); // Initialize the LinkedList
 }
 
 //Primary Constructor
 public Patron(String userID, String fname, String lname, String username, Password password,String lNumb, LinkedList<Book> books)
 {
	 super(userID,fname,lname,username,password);
	 this.lNumb = lNumb;
	 this.books = books;
 }
 
 //Copy Constructor
 public Patron(Patron pat)
 {
	 super(pat);
	 this.lNumb = pat.lNumb;
	 this.books = pat.books;
 }

 public String getlNumb() {
     return lNumb;
 }

 public void setlNumb(String lNumb) {
     this.lNumb = lNumb;
 }

 public LinkedList<Book> getBooks() {
     return books;
 }

 public void setBooks(LinkedList<Book> books) {
     this.books = books;
 }

 public void Display() 
 {
	 super.Display();
     System.out.println("Library Number:  " + lNumb);
     System.out.print("Books: ");
     books.Display();
     System.out.println("");
 }
 
 public String toXML()
 {
	 StringBuilder xml = new StringBuilder();
	    xml.append("<Patron>\n");
	    xml.append("  <userID>").append(userID).append("</userID>\n");
	    xml.append("  <fName>").append(fName).append("</fName>\n");
	    xml.append("  <lName>").append(lName).append("</lName>\n");
	    xml.append("  <Username1>").append(username).append("</Username1>\n");
	    //I don't know how to hash the password - Chev
	    xml.append("  <Username2>").append(password.getUsername()).append("</Username2>\n");
	    xml.append("  <password>").append(password.getPassword()).append("</password>\n");
	    xml.append("  <lNumb>").append(lNumb).append("</lNumb>\n");
	    xml.append("  <Collection>\n");
	    books.forEach(book -> xml.append(book.toXML()).append("\n"));
	    xml.append("  </Collection>\n");
	    xml.append("</Patron>");
	   
	    
	    return xml.toString();
}

 
// a book is available to borrow
public void CheckIn() 
 {
	Scanner scanner = new Scanner(System.in);
	String input;
	System.out.println("Please enter the ISBN of book");
	input = scanner.next();
	if(input.equals(book.getISBN))
	{
		//book.setStatus(false);
		//Update book records and remove it there
		
		System.out.println("Book with ISBN" + input + "has been checked in");
		System.out.println("Book Details");
		System.out.println("-----------------------------------");
		//book.Display();
		
	}
	else
	{
		System.out.println("Book that contains ISBN " + input + " was not found");
	}
	
	
	
 }
 
//it's currently borrowed by another patron. 
//Method that undo checkouts
 public void CheckOut() 
 {
	int option;
	Scanner scanner = new Scanner(System.in);
	Patron patronIDToSearch = new Patron();
	System.out.println("****Welcome to Checkout Menu****");
	System.out.print("Please enter User ID: ");
	patronIDToSearch.setUserID(scanner.next());
	if (patronIDToSearch.getUserID().equals(searchPatronByID(patronIDToSearch)))
	{
		while(true)
		{
			/*if (books == null)
			 * {
			 * 		System.out.println("All books have been checked out or booklist is empty");
			 * 		break;
			 * }*/
			System.out.println("[1] - Return Book ");
			System.out.println("[2] - Exit checkout");
			System.out.print("Enter option: ");
			option = scanner.nextInt();
			while(option <= 0 || option >2)
			{
				System.out.println("Invalid option entered");
				System.out.println("[1] - Return Book ");
				System.out.println("[2] - Exit checkout");
				System.out.print("Enter option: ");
				option = scanner.nextInt();
			}
			if(option == 1)
			{
				
				
				book.setStatus(true);
			
			}
			else
			{
				System.out.println("Exitted checkout menu");
				break;
			}
		}
		
	 }//endwhile
	}//endif
	
 
}