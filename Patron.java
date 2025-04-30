// Jonathan Blackwood (2306822), 20/2/2025, Patron.java

import java.util.*;

public class Patron extends User {
	private LinkedList<Book> books;

	//Default Constructor
	public Patron() {
		super(); // Calls the constructor of the superclass User
		books = new LinkedList<>(); // Initialize the LinkedList
	}

	//Primary Constructor 1
	//This constructor is used when retrieving all attributes from xml file to prevent null Nodelist error
	public Patron(String userID, String fname, String lname, String username, Password password, LinkedList<Book> books) {
		super(userID, fname, lname, username, password); // Calls the constructor of the superclass User
		this.books = books;
	}


	//Primary Constructor 2
	//This constructor is used to automatically generateISBN with other manually inputted attributes
	public Patron(String fname, String lname, String username, Password password, LinkedList<Book> books) {
		super(fname, lname, username, password); // Calls the constructor of the superclass User
		this.books = books;
	}



	public Patron(Patron obj) {
		super(obj); // Calls the constructor of the superclass User
		this.books = obj.books;
	}

	public LinkedList<Book> getBooks() {
		return books;
	}

	public void setBooks(LinkedList<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		String result;

		result = "\nLibrary Number  : " + getUserID() + "\n" +
		         "First Name      : " + getFname() + "\n" +
		         "Last Name       : " + getLname() + "\n" +
		         "Username        : " + getUsername() + "\n" +
		         "Password        : " + getPassword().getPassword() + "\n";
		result += "Books           : " + (books.len() == 0 ? "No books in your list at this time\n" : "\n" + books.toString());

		return result; // Returns the final string
	}

  // toXML Method - Returns an XML representation of the Patron.	
	public String toXML() {
		String xmlString = "    <Patron>\n" +
		                   "        <LibraryNumber>" + getUserID() + "</LibraryNumber>\n" +
		                   "        <FirstName>" + getFname() + "</FirstName>\n" +
		                   "        <LastName>" + getLname() + "</LastName>\n" +
		                   "        <Username>" + getUsername() + "</Username>\n" +
		                   "        <Password>" + getPassword().getPassword() + "</Password>\n" +
		                   "        <OTP>" + getPassword().isOTP() + "</OTP>\n" +
		                   "        <Books>\n";
		LinkedList<Book> books = getBooks();

		if (books != null) {
			for (Book book : books) {
				xmlString += "            <Book>\n" +
				             "                <ISBN>" + book.getISBN() + "</ISBN>\n" +
				             "                <title>" + book.getTitle() + "</title>\n" +
				             "                <authorFname>" + book.getAuthorFname() + "</authorFname>\n" +
				             "                <authorLname>" + book.getAuthorLname() + "</authorLname>\n" +
				             "                <status>" + book.getStatus() + "</status>\n" +
				             "            </Book>\n";
			}
		}

		xmlString += "        </Books>\n" +
		             "    </Patron>";
		return xmlString;
	}

}
