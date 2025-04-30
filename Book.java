import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
Name: Chevannese Ellis
ID: 2301109
Date: February 21, 2025
Book.java
*/

public class Book implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Attributes
	private String ISBN;
	private String title;
	private String authorFname;
	private String authorLname;
	private boolean status;

	// Default Constructor
	public Book() {
		ISBN = generateISBN();
		title = "";
		authorFname = "";
		authorLname = "";
		status = false;
	}

	// Primary Constructor 1
	//This constructor is used when retrieving all attributes from xml file to prevent null Nodelist error
	
	public Book(String ISBN, String title, String authorFname, String authorLname, boolean status) {
		this.ISBN = ISBN;
		this.title = title;
		this.authorFname = authorFname;
		this.authorLname = authorLname;
		this.status = status;
	}

	// Primary Constructor 2
	//This constructor is used to automatically generateISBN with other manually inputted attributes
	// Updated by Chev
	public Book(String title, String authorFname, String authorLname, boolean status) {
		this.ISBN = generateISBN();
		this.title = title;
		this.authorFname = authorFname;
		this.authorLname = authorLname;
		this.status = status;
	}

	// Copy Constructor
	public Book(Book bo) {
		this.title = bo.title;
		this.authorFname = bo.authorFname;
		this.authorLname = bo.authorLname;
		this.ISBN = bo.ISBN;
		this.status = bo.status;
	}

	// Getters and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorFname() {
		return authorFname;
	}

	public void setAuthorFname(String authorFname) {
		this.authorFname = authorFname;
	}

	public String getAuthorLname() {
		return authorLname;
	}

	public void setAuthorLname(String authorLname) {
		this.authorLname = authorLname;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	// Display Method
	public String toString() {

		return "\nThe ISBN of the book is : " + ISBN + "\n" +
		       "The title of the book is  : " + title + "\n" +
		       "The author of the book is : " + authorFname + " " + authorLname + "\n" +
		       "The status of the book is :" + (status ? " Available" : " Checked Out") + "\n";
	}

  // toXML Method - Returns an XML representation of the book.	
  
  public String toXML() {
		return "    <Book>\n" +
		       "      <ISBN>" + ISBN + "</ISBN>\n" +
		       "      <title>" + title + "</title>\n" +
		       "      <authorFname>" + authorFname + "</authorFname>\n" +
		       "      <authorLname>" + authorLname + "</authorLname>\n" +
		       "      <status>" + status + "</status>\n" +
		       "    </Book>";
	}

	//Method to save the ISBN number to the file
	public static void SaveISBNCount(String filePath,int x)
	{
		try (FileWriter writer = new FileWriter(filePath))
		{
			writer.write(String.valueOf(x));

		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the counter");
			e.printStackTrace();
		}
	}

	
	//Method to read or create a file that contains ISBN number
	public static int LoadISBNCount(String filePath)
	{
		File file = new File(filePath);
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String line = reader.readLine();
				if (line != null) {
					return Integer.parseInt(line);
				}
			} catch (IOException e) {
				System.out.println("An error occurred while reading the file.");
				e.printStackTrace();
			}
		} else {
			//If file does not exist it will create a new file
			try {
				if (file.createNewFile()) {
					System.out.println("File created: " + file.getName());
					//Assigns initial value of isbn 50000
					SaveISBNCount("ISBNCount.txt", 50000);
				}
			} catch (IOException e) {
				System.out.println("An error occurred while creating the file.");
				e.printStackTrace();
			}
		}

		return 50000; // Return default value if the file was not found

	}

	//creates random ISBN for book
	public static String generateISBN()
	{
		int isbnCounter;
		isbnCounter = LoadISBNCount("ISBNCount.txt"); //reads ISBN number from file
		String newISBN = String.valueOf(isbnCounter); // Assign initial value from counter
		isbnCounter += 4; // Increment counter for next book
		SaveISBNCount("ISBNCount.txt", isbnCounter);
		return newISBN;
	}
}
