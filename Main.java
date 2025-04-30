/*
  Project Name: Library Management System
  Group Members:
  Jada Johnson (2209658)
  Jonathan Blackwood (2306822)
  Jevana Grant (2302670)
  Josan Williams (2304917)
  Chevannese Ellis (2301109)
  
  admin login credentials:
  username: admin
  password: admin

*/
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Main {

	public static volatile long lastInputTime = System.currentTimeMillis();
	public static final long TIMEOUT_MILLIS = 3 * 60 * 1000; // Inactivity Timer Set to 3 minutes. The first number determines the number of minutes
	public static volatile boolean running = true;
	public static Scanner input = new Scanner(System.in); // shared scanner


	public static void main(String[] args) {
		LinkedList<Book> books = new LinkedList<Book>();// List to hold all books in the library.
		LinkedList<Patron> patrons = new LinkedList<Patron>();// List to hold all patrons
		Queue<Patron> patronsQueue = new Queue<Patron>(); // Queue to manage patrons waiting to check out books. (Jada)
		//Chev: Define file names for book and patron data.
		File booksFileName = new File("BookData.xml");
		File patronsFileName = new File("PatronRecords.xml");
		Scanner input = new Scanner(System.in);

		// Start the inactivity timeout thread
		new Thread(() -> {
			while (running) {
				if (System.currentTimeMillis() - lastInputTime > TIMEOUT_MILLIS) {
					System.out.println("\nSYSTEM: Session timed out due to inactivity. Auto-logging out...");
					input.close();
					System.exit(0);
				}
				try {
					Thread.sleep(1000); // check every second
				} catch (InterruptedException e) {
					// ignore
				}
			}
		}).start();

		// ************************** Default Books ******************************

    //Jevana / Jada: Initial book data.
		String[][] bookData = {
			{ "The Great Gatsby", "F. Scott", "Fitzgerald", "true" },
			{ "Cinderella", "John", "Brown", "true" },
			{ "Introduction to Psychology", "James W.", "Kalat", "true" },
			{ "Principles of Economics", "N. Gregory", "Mankiw", "true" },
			{ "Organic Chemistry", "Paula", "Bruice", "true" },
			{ "World History: Patterns of Interaction", "Roger B.", "Beck", "true" },
			{ "Anatomy and Physiology", "Elaine N.", "Marieb", "true" },
			{ "Calculus: Early Transcendentals", "James", "Stewart", "true" },
			{ "Introduction to Philosophy", "John", "Perry", "true" },
			{ "Marketing Management", "Philip", "Kotler", "true" },
			{ "Principles of Accounting", "Jerry J.", "Weygandt", "true" },
			{ "Introduction to Sociology", "Anthony", "Giddens", "true" },
			{ "Harry Potter", "J.K.", "Rowling", "true" },
			{ "The Hobbit", "J.R.R.", "Tolkien", "true" },
			{ "Moby Dick", "Herman", "Melville", "true" },
			{ "The Lord of the Rings", "J.R.R.", "Tolkien", "true" },
			{ "Pride and Prejudice", "Jane", "Austen", "true" },
			{ "To Kill a Mockingbird", "Harper", "Lee", "true" },
			{ "1984", "George", "Orwell", "true" },
			{ "Brave New World", "Aldous", "Huxley", "true" },
			{ "The Catcher in the Rye", "J.D.", "Salinger", "true" },
			{ "One Hundred Years of Solitude", "Gabriel", "Garcia", "Marquez", "true" },
			{ "From Behind the Counter", "Easton", "Lee", "true" },
			{ "Citizenship Under Pressure", "Rachel", "L. Mordecai", "true" },
			{ "Don Quixote", "Miguel", "de Cervantes", "true" },
			{ "Alice's Adventures in Wonderland", "Lewis", "Carroll", "true" },
			{ "War and Peace", "Leo", "Tolstoy", "true" },
			{ "Crime and Punishment", "Fyodor", "Dostoyevsky", "true" },
			{ "The Brothers Karamazov", "Fyodor", "Dostoyevsky", "true" },
			{ "Madame Bovary", "Gustave", "Flaubert", "true" },
			{ "Great Expectations", "Charles", "Dickens", "true" },
			{ "Jane Eyre", "Charlotte", "Bronte", "true" },
			{ "Wuthering Heights", "Emily", "Bronte", "true" },
			{ "The Picture of Dorian Gray", "Oscar", "Wilde", "true" },
			{ "Frankenstein", "Mary", "Shelley", "true" },
			{ "Dracula", "Bram", "Stoker", "true" },
			{ "The Count of Monte Cristo", "Alexandre", "Dumas", "true" },
			{ "Les Miserables", "Victor", "Hugo", "true" },
			{ "Anna Karenina", "Leo", "Tolstoy", "true" },
			{ "Middlemarch", "George", "Eliot", "true" },
			{ "David Copperfield", "Charles", "Dickens", "true" },
			{ "The Adventures of Huckleberry Finn", "Mark", "Twain", "true" },
			{ "The Scarlet Letter", "Nathaniel", "Hawthorne", "true" },
			{ "The Sun Also Rises", "Ernest", "Hemingway", "true" },
			{ "A Farewell to Arms", "Ernest", "Hemingway", "true" },
			{ "The Old Man and the Sea", "Ernest", "Hemingway", "true" },
			{ "Catch-22", "Joseph", "Heller", "true" },
			{ "Slaughterhouse-Five", "Kurt", "Vonnegut", "true" },
			{ "The Bell Jar", "Sylvia", "Plath", "true" },
			{ "The Color Purple", "Alice", "Walker", "true" }
		};

		// Jevana / Jada: Populate the book list with the initial book data.
		for (String[] data : bookData) {
			Book book = new Book();
			book.setTitle(data[0]);
			book.setAuthorFname(data[1]);
			book.setAuthorLname(data[2]);
			book.setStatus(Boolean.parseBoolean(data[3]));
			books.append(book);
		}
		if (!booksFileName.exists()) {
			WriteBookData("BookData.xml", books);
		} else {
			books = loadBookData("BookData.xml");
		}
    //Chev: Checks if patronFile exists
		if (!patronsFileName.exists()) {

			// Jevana / Jada: Initial patron data.
			String[][] patronData = {
				{ "Chevannese", "Ellis", "chev", "0", "Chev@8888" },
				{ "Jada", "Johnson", "jada.j", "1", "Jada@5678" },
				{ "Josan", "Williams", "jos.will", "2", "Josan@9012" },
				{ "James", "Anderson", "jamesa", "3", "James@4321" },
				{ "Johnathan", "Blackwood", "john.b", "4", "Lisa@3344" },
				{ "Jevana", "Grant", "jev.g", "5", "Kevin@7765" },
				{ "Sophie", "Turner", "soph.t", "6", "Sophie@4567" },
				{ "Michael", "Smith", "mike.s", "7", "Michael@9832" },
				{ "Ava", "Brooks", "ava.b", "8", "Ava@1122" },
				{ "Ethan", "Clark", "ethan.c", "9", "Ethan@7788" }
			};

			int i = 0;

			// Jevana / Jada: Populate the patron list with the initial patron data.
			for (String[] data : patronData) {
				Patron patron = new Patron();

				patron.setFname(data[0]);
				patron.setLname(data[1]);
				patron.setUsername(data[2]);
				patron.getPassword().setPassword(hashPasscode(data[4]));
				patron.getPassword().setOTP(false);
				try (FileWriter filer = new FileWriter("HashedPasswords.txt", true)) {
					filer.append(patron.getUsername() + ": " + patron.getPassword().getPassword());
					filer.append("\n");
					filer.close();
				} catch (Exception e) {
					System.out.println("Error writing default patron to HashedFile : " + e.getMessage());
				}
				patron.setBooks(new LinkedList<Book>());
				patrons.insert(patron, i);
				writeUsernameToFile(data[2], "Usernames.txt");

				i++;
			}

			// Jada: Assign books to patrons and add them to the checkout queue.
			
			patrons.get(0).getBooks().append(books.get(0));
			books.get(0).setStatus(false);

			patrons.get(3).getBooks().append(books.get(5));
			books.get(5).setStatus(false);

			patrons.get(6).getBooks().append(books.get(10));
			books.get(10).setStatus(false);

			patrons.get(1).getBooks().append(books.get(1));
			books.get(1).setStatus(false);

			patrons.get(2).getBooks().append(books.get(2));
			books.get(2).setStatus(false);

			patrons.get(3).getBooks().append(books.get(6));
			books.get(6).setStatus(false);

			patrons.get(4).getBooks().append(books.get(7));
			books.get(7).setStatus(false);

			patrons.get(5).getBooks().append(books.get(11));
			books.get(11).setStatus(false);

			patrons.get(6).getBooks().append(books.get(12));
			books.get(12).setStatus(false);

			patrons.get(7).getBooks().append(books.get(15));
			books.get(15).setStatus(false);

      //Chev: Writes default patron data to file if file does not exist
			WritePatronData("PatronRecords.xml", patrons);
			WriteBookData("BookData.xml", books);
		} else {
		  //Chev: Loads patron if the file exists and assign it to the LinkedList of patrons
			patrons = loadPatronData("PatronRecords.xml");
		}
    
    //Jevana / Jada: Create an admin use
		Admin admin1 = new Admin("Oral", "Robinson", "admin", new Password("admin", false), books, patrons);

		System.out.println("===== Welcome to the Library Management System (LMS) =====");
		System.out.println(
		    "\nThis system uses a variety of users, please identify yourself and select the correct information");


		// Main menu loop - Jevana
		while (running) {
			try {
				System.out.print(
				    "\n---LMS Main Menu--- \n[1] Admin/Patron Login \n[2] New Patron Registration (Sign Up)" +
				    "\n[3] Exit System \nSelect option (1-3): ");
				lastInputTime = System.currentTimeMillis(); // reset before waiting
				int choice = getValidIntegerInput(1, 3);

				switch (choice) {
				case 1:
					String username = timedInput("\nEnter your username: ");
					String pwd = timedInput("Enter your password: ");

					// Chev: Check if the user is an admin or a patron.
					if (username.equals("admin") && pwd.equals("admin")) {
						adminMenu(username, pwd, admin1, patrons,books, "BookData.xml",
						          "PatronRecords.xml");
					} else {
						patronMenu(username, pwd, patrons, books);//Displays Patron Menu to patron
					}
					break;
				case 2:
					addPatron(patrons); // Adding new patron via Patron class
					break;

				case 3:
					System.out.println("\nSYSTEM: Logging out of Main Menu... Goodbye! :)");
					running = false;
					break;
				default:
					System.out.println("\nSYSTEM: Invalid input!");
				}

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // clears buffer - Jada
			}
		}

		input.close();
	}

	// ******************** File Management ************************

	
	// Writes all patron information to xml file - Chev
	public static void WritePatronData(String fileName, LinkedList<Patron> parameterlist) {
		// Write the CustomLinkedList to an XML file
		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write("<Patrons>\n");

			for (Patron patron : parameterlist) {
				try {
					writer.write(patron.toXML());
					writer.write("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			writer.write("</Patrons>");
			System.out.println("Data written to file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while saving the patron info.");
			e.printStackTrace();
		}
	}

	
	// Writes all book information to xml file - Chev
	private static void WriteBookData(String fileName, LinkedList<Book> parameterlist) {
		// Write the CustomLinkedList to an XML file
		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write("<Books>\n");
			for (Book book : parameterlist) {
				try {
					writer.write(book.toXML());
					writer.write("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			writer.write("</Books>");
			System.out.println("Data written to file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while saving the patron info.");
			e.printStackTrace();
		}
	}

	
	// Read and Retrieve All Patron Data from the xml file - Chev
	private static LinkedList<Patron> loadPatronData(String filePath) {
		LinkedList<Patron> patrons = new LinkedList<Patron>();

		try {
			// Load the XML file
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// Get all Patron elements
			NodeList patronList = doc.getElementsByTagName("Patron");
			for (int i = 0; i < patronList.getLength(); i++) {
				Node patronNode = patronList.item(i);
				if (patronNode.getNodeType() == Node.ELEMENT_NODE) {
					Element patronElement = (Element) patronNode;

					// Extract Patron attributes
					String userID = patronElement.getElementsByTagName("LibraryNumber").item(0).getTextContent();
					String fName = patronElement.getElementsByTagName("FirstName").item(0).getTextContent();
					String lName = patronElement.getElementsByTagName("LastName").item(0).getTextContent();
					String username = patronElement.getElementsByTagName("Username").item(0).getTextContent();
					String password = patronElement.getElementsByTagName("Password").item(0).getTextContent();
					boolean otp = Boolean
					              .parseBoolean(patronElement.getElementsByTagName("OTP").item(0).getTextContent());

					// Extract Books
					LinkedList<Book> books = new LinkedList<Book>();
					NodeList bookList = patronElement.getElementsByTagName("Book");
					for (int j = 0; j < bookList.getLength(); j++) {
						Element bookElement = (Element) bookList.item(j);
						String ISBN = bookElement.getElementsByTagName("ISBN").item(0).getTextContent();
						String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
						String authorFName = bookElement.getElementsByTagName("authorFname").item(0).getTextContent();
						String authorLName = bookElement.getElementsByTagName("authorLname").item(0).getTextContent();
						boolean status = Boolean
						                 .parseBoolean(bookElement.getElementsByTagName("status").item(0).getTextContent());

						// Create a Book object and add it to the LinkedList
						books.append(new Book(ISBN, title, authorFName, authorLName, status));
					}

					// Create a Patron object and add it to the LinkedList
					patrons.append(new Patron(userID, fName, lName, username, new Password(password, otp), books));

				}
			}

			System.out.println("XML file loaded successfully.");
		} catch (Exception e) {
			System.out.println("An error occurred while loading the XML file.");
			e.printStackTrace();
		}

		return patrons;
	}

	// Read and Retrieve All Book Data from the xml file - Chev
	
	private static LinkedList<Book> loadBookData(String filePath) {
		LinkedList<Book> books = new LinkedList<>();

		try {
			// Load the XML file
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// Get all Book elements
			NodeList bookList = doc.getElementsByTagName("Book");

			for (int j = 0; j < bookList.getLength(); j++) {
				Node bookNode = bookList.item(j);

				if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
					Element bookElement = (Element) bookNode;

					// Extract data from XML
					String ISBN = bookElement.getElementsByTagName("ISBN").item(0).getTextContent();
					String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
					String authorFName = bookElement.getElementsByTagName("authorFname").item(0).getTextContent();
					String authorLName = bookElement.getElementsByTagName("authorLname").item(0).getTextContent();
					boolean status = Boolean
					                 .parseBoolean(bookElement.getElementsByTagName("status").item(0).getTextContent());

					// Create Book object (ISBN is auto-generated)
					books.append(new Book(ISBN, title, authorFName, authorLName, status));
				}
			}
			System.out.println("Book data loaded successfully.");
		} catch (Exception e) {
			System.out.println("An error occurred while loading the book XML file.");
			e.printStackTrace();
		}

		return books;
	}

	// ******************** User Management ************************
	// Method to write username to file - Jada
	public static boolean writeUsernameToFile(String username, String fileName) {
		if (username == null || fileName == null) {
			System.out.println("\nSYSTEM: Invalid username or filename.");
			return false;
		}

		try {
			Set<String> existingUsernames = new HashSet<>();

			File file = new File(fileName);

			// Read existing usernames
			if (file.exists()) {
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					String line;
					while ((line = reader.readLine()) != null) {
						existingUsernames.add(line.trim());
					}
				}
			}

			// Check if the username already exists
			if (!existingUsernames.contains(username)) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
					writer.write(username);
					writer.newLine();
					return true;
				}
			} else {
				return true;
			}

		} catch (IOException e) {
			System.err.println("\nSYSTEM: Error writing to file: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // Clears buffer - Jada
			return false;
		}
	}

	// ------------------------ Input Validation ------------------------ Jevana
	public static int getValidIntegerInput(int min, int max) {
		Scanner input = new Scanner(System.in);
		int choice = -1;

		choice = input.nextInt();

		while (true) {
			try {
				if (choice >= min && choice <= max)
					break;
				else {
					System.out.printf("\nSYSTEM: Invalid input!\n");
					System.out.printf("Enter a number between %d and %d: ", min, max);
					lastInputTime = System.currentTimeMillis(); // reset before waiting
					choice = input.nextInt();
				}
			} catch (NumberFormatException e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // clears buffer - Jada
			}
		}
		return choice;
	}

	// ***************Patron Management *******************


	// Returning Books - Jada
	public static void CheckIn(LinkedList<Book> books, LinkedList<Patron> patrons, String patronID) {
		Scanner input = new Scanner(System.in);
		boolean found = false;
		Patron patron = null;
		Book book = null;



		for (Patron p : patrons) {
			if (p.getUserID().equals(patronID)) {
				patron = p;
				break;
			}
		}

		boolean returnMenu = true;

		while (returnMenu) {
			viewBorrowedBooks(patron.getBooks());

			if (patron.getBooks().len() == 0) {
				System.out.print("\nReturning to Patron's Book Information Menu...\n");
				break;
			}

			String bookReturning = timedInput("\nWhich book are you returning? (Enter book's ISBN): ");


			boolean hasBook = false;
			//Check if Patron has book
			for (Book checkedOutBook : patron.getBooks()) {
				if (checkedOutBook.getISBN().equalsIgnoreCase(bookReturning)) {
					hasBook = true;
					book = checkedOutBook;
					break;
				}
			}


			if (!hasBook) {
				System.out.println("\nSYSTEM: You did not check out this book.");
			}

			if (book == null) {
				System.out.println("\nSYSTEM: Book not found.");
				return;
			}


			// Update book status in both patron's list and main books list
			for (Book b : books) {
				if (b.getISBN().equalsIgnoreCase(bookReturning)) {
					b.setStatus(true);
					WriteBookData("BookData.xml", books);
					break;
				}
			}

			// Remove from patron's list
			patron.getBooks().Remove(book);

			// Write updates to files
			WritePatronData("PatronRecords.xml", patrons);

			System.out.printf("\nSYSTEM: Book returned successfully by %s %s.\n", patron.getFname(), patron.getLname());
			System.out.printf("\nSYSTEM: Book with %s has been returned to library", book.getISBN());

			returnMenu = getYesNoResponse("\nSYSTEM: Would you like to return another book? (yes/no): ");

		}
	}

	// Borrowing Books - Jada
	public static void CheckOut(LinkedList<Book> bookList, LinkedList<Patron> patronList, String patronID) {
		Scanner input = new Scanner(System.in);
		Stack<Book> bookStack = new Stack<Book>(); // Stack to hold selected books
		LinkedList<Book> selectedBooks = new LinkedList<Book>(); // List to hold selected books for confirmation

		Patron patron = null;

		for (Patron p : patronList) {
			if (p.getUserID().equals(patronID)) {
				patron = p;
				break;
			}
		}

		System.out.println("\n---Books Currently In The System---");
		System.out.println(bookList);

		boolean shopping = true;

		while (shopping) {
			// Display current stack - Jada
			if (!bookStack.isEmpty()) {
				System.out.println("\n---Currently Selected Books---");

				for (Book b : bookStack)
					System.out.printf("%s: %s by %s %s\n", b.getISBN(), b.getTitle(), b.getAuthorFname(), b.getAuthorLname());

			}

			String bookISBN = timedInput("\nEnter the ISBN of the book you want to borrow \n(or 'done' to finish or 'remove' to remove a selected book): ");

			if (bookISBN.equalsIgnoreCase("done") || bookISBN.equalsIgnoreCase("finish")) {
				shopping = false;
				break;
			}

			if (bookISBN.equalsIgnoreCase("remove")) {
        
        /* Pop function
				if (bookStack.isEmpty()) {
					System.out.println("\nSYSTEM: Your book list is empty.");
					continue;
				}

				Stack<Book> tempStack = new Stack<>();
				boolean removed = false;

				Book tempBook = bookStack.pop();
			
				continue;*/
				
				System.out.print("Enter ISBN of book to remove: ");
				String removeISBN = input.nextLine();

				Stack<Book> tempStack = new Stack<>();
				boolean removed = false;

				while (!bookStack.isEmpty()) {
					Book tempBook = bookStack.pop();

					if (tempBook.getISBN().equals(removeISBN)) {
						System.out.printf("\nSYSTEM: %s removed from your list.\n", tempBook.getTitle());
						removed = true;
					} else
						tempStack.push(tempBook);
				}

				while (!tempStack.isEmpty())
					bookStack.push(tempStack.pop());

				if (!removed)
					System.out.println("\nYSTEM: Book not found in your list.");

				continue;

			}

			Book book = null;
			boolean bookFoundAndAvailable = false;

			for (Book b : bookList) {
				if (b.getISBN().equals(bookISBN)) {
					book = b;
					if (b.getStatus()) {
						bookFoundAndAvailable = true;
						break;
					} else
						System.out.println("\nSYSTEM: Sorry, but the book you're trying to select is currently borrowed.");
				}
			}

			if (!bookFoundAndAvailable) {
				if (book == null)
					System.out.println("\nSYSTEM: Book not found.");
				continue;
			}

			boolean patronHasBook = false;

			for (Book b : patron.getBooks()) {
				if (b.getISBN().equals(bookISBN)) {
					System.out.println("\nSYSTEM: You already have this book.");
					patronHasBook = true;
					break;
				}
			}

			if (patronHasBook) {
				continue;
			}

			bookStack.push(book);
			selectedBooks.append(book);
			System.out.printf("\nSYSTEM: %s by %s %s added to your list.\n", book.getTitle(), book.getAuthorFname(), book.getAuthorLname());
		}

		// Confirmation Message - Jada
		if (!bookStack.isEmpty()) {
			System.out.println("\n---Final Selected Books---");

			for (Book b : bookStack) {
				System.out.printf("%s by %s %s\n", b.getTitle(), b.getAuthorFname(), b.getAuthorLname());
			}

			if (getYesNoResponse("\nSYSTEM: Confirm checkout? (yes/no): ")) {
				Stack<Book> tempStack = new Stack<Book>();
				Stack<Book> copyStack = bookStack;
				Stack<Book> copyTemp = new Stack<Book>();

				for (Book book : bookStack) {
					for (Book b : bookList) {
						if (b.getISBN().equals(book.getISBN())) {
							b.setStatus(false);
						}
					}
				}

				WriteBookData("BookData.xml", bookList);


				for(Patron p : patronList)
				{
					if(patron.getUserID().equals(p.getUserID()))
					{
						while (!bookStack.isEmpty())
						{
							tempStack.push(bookStack.pop());
						}

						while (!tempStack.isEmpty()) {
							Book book = tempStack.pop();
							book.setStatus(false);
							patron.getBooks().append(book);

							System.out.printf("SYSTEM: Yay! You checked out %s by %s %s! Happy reading!\n", book.getTitle(), book.getAuthorFname(), book.getAuthorLname());
						}
						WritePatronData("PatronRecords.xml",patronList);
						break;
					}
				}

				System.out.println("SYSTEM: All books checked out successfully.");
			} else
				System.out.println("SYSTEM: Checkout cancelled.");
		} else
			System.out.println("SYSTEM: No books selected.");
	}




	public static void viewPatron(LinkedList<Patron> patrons, String viewID) {
		Scanner scanner = new Scanner(System.in);

		boolean validInput = false;

		while (!validInput) {
			try {
				boolean found = false;

				for (Patron patron : patrons) {
					if (patron.getUserID().equals(viewID)) {
						System.out.println("SYSTEM: Note that your password is hashed for security reasons...");
						System.out.println(patron.toString());
						validInput = true; // Set to true if all inputs are valid
						found = true;
						break;
					}
				}

				if (!found) {
					System.out.println("SYSTEM: No patron found with ID: " + viewID);
					validInput = getYesNoResponse(
					                 "\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
				}

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // clears buffer - Jada
			}
		}
	}

	public static void patronMenu(String username, String pwd, LinkedList<Patron> patrons, LinkedList<Book> books) {
		Scanner input = new Scanner(System.in);
		boolean responseFlag = false;
		boolean found = false;
		Patron loggedInPatron = null;

		String hashPWD = hashPasscode(pwd);
		try {
			for (Patron p : patrons) {
				if (p.getUsername().equals(username) && p.getPassword().getPassword().equals(hashPWD)

				   ) {
					System.out.println("\nSYSTEM: Login successful!\n");
					found = true;
					loggedInPatron = p;
					break;
				}
			}

			if (found) {
				System.out.printf("Welcome patron %s %s, how may we assist you today?\n", loggedInPatron.getFname(),
				                  loggedInPatron.getLname());
				responseFlag = true;

				if (loggedInPatron.getPassword().isOTP() == true) {
					String choice, newPassword;

					System.out.print("\nSYSTEM: Please change your password to ensure your account's safety.\n");

					boolean passwordChanged = changePassword(loggedInPatron, patrons);

					if (passwordChanged)
						System.out.println("\nSYSTEM: Password changed successfully.");

					// Update file here
					else
						System.out.println("\nSYSTEM: Password change failed.");
				}

				while (responseFlag) {
					System.out.println("\n---Patron Menu---");
					System.out.println("[1] Book Management Menu");
					System.out.println("[2] Search / Sort for Book by Author or Title");
					System.out.println("[3] Change Password");
					System.out.println("[4] View Your Account Details");
					System.out.println("[5] Exit Menu");
					System.out.print("\nPlease select an option: ");

					try {
						lastInputTime = System.currentTimeMillis();
						int option = getValidIntegerInput(1, 5);

						switch (option) {
						case 1: {// Book Management
							boolean bookManagementFlag = true; // Separate flag for the book management menu

							while (bookManagementFlag) {
								System.out.println("\n---Patron's Book Management Menu---");
								System.out.println("[1] Return a Book");
								System.out.println("[2] Borrow a Book");
								System.out.println("[3] View Your Currently Borrowed Books");
								System.out.println("[4] Exit Menu");
								System.out.print("\nPlease select an option: ");

								lastInputTime = System.currentTimeMillis();
								int choice = getValidIntegerInput(1, 4);

								switch (choice) {
								case 1:
									CheckIn(books, patrons, loggedInPatron.getUserID());
									break;
								case 2:
									CheckOut(books, patrons, loggedInPatron.getUserID());
									break;
								case 3:
									viewBorrowedBooks(loggedInPatron.getBooks());

									break;
								case 4:
									System.out.println("\nSYSTEM: Exiting book management menu...");
									bookManagementFlag = false;
									break;
								default:
									System.out.println("\nSYSTEM: Invalid input...");
								}
							}
						}
						break;

						case 2:
							// Book Search Tree
							searchBooks(books);
							break;

						case 3:
							// Change loggedInPatron password and update to xml file
							boolean passwordChanged = changePassword(loggedInPatron, patrons);

							if (passwordChanged)
								System.out.println("\nSYSTEM: Password changed successfully.");

							else
								System.out.println("\nSYSTEM: Password change failed.");
							break;

						case 4:
							System.out.println("\n---Viewing Your Details---\n");
							viewPatron(patrons, loggedInPatron.getUserID());
							break;

						case 5:
							System.out.println("\nSYSTEM: Exiting patron menu...");
							responseFlag = false;
							break;

						default:
							String response = timedInput("\nSYSTEM: Invalid input. Try again (Y/N)? ");
							if (!response.equalsIgnoreCase("Y")) {
								responseFlag = false;
							}
						}

					} catch (InputMismatchException e) {
						System.err.println("\nERROR: " + e.getMessage());
						e.printStackTrace();
						System.out.println("\n"); // clears buffer - Jada
					}
				}
			} else {
				System.out.println("\nERROR: Invalid patron credentials or patron not found.");
			}

		} catch (Exception e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // clears buffer - Jada
		}
	}// End of Patron Menu

	// So that it loops with response == "yes" or "y" - Jada
	protected static boolean getYesNoResponse(String prompt) {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;
		boolean response = false;

		try {
			while (!validInput) {
				lastInputTime = System.currentTimeMillis();
				System.out.print(prompt);
				String input = scanner.nextLine().trim().toLowerCase();

				if (input.equals("yes") || input.equals("y")) {
					response = true;
					validInput = true;
				} else if (input.equals("no") || input.equals("n")) {
					response = false;
					validInput = true;
				} else {
					System.out.println("\nSYSTEM: Invalid input. Please enter 'Yes' or 'No'.");
				}
			}
		} catch (Exception e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // clears buffer - Jada
		}

		return response;
	}




	// *************************** Book Management ******************************

	public static void searchBooks(LinkedList<Book> books) {
		Scanner input = new Scanner(System.in);
		int option = 0;
		boolean BSTMenu = false;

		System.out.println("\n---Search / Sort for a Book by ISBN, Title, or Author---");
		System.out.print("Do you want to \n[1] Sort \n[2] Search \n[3] Exit Menu \nEnter an option: ");
    lastInputTime = System.currentTimeMillis();
		int BSToption = getValidIntegerInput(1, 3);

		try {
			while (!BSTMenu) {

				if (BSToption == 1) {
					boolean sortAgain = true; // Flag to control the search loop

					while (sortAgain) {
						System.out.println("\n---Sort for a Book by ISBN, Title, or Author---\n");
						System.out.println("[1] - Sort by ISBN");
						System.out.println("[2] - Sort by Title");
						System.out.println("[3] - Sort by Author First Name");
						System.out.println("[4] - Sort by Author Last Name");
						System.out.println("[5] - Exit Search Menu\n");
						System.out.print("Choose sorting option: ");
            lastInputTime = System.currentTimeMillis();
						option = getValidIntegerInput(1, 5);

						if (option == 5) {
							BSTMenu = true;
							continue; // Exit the sort loop and the BSToption == 1 block.
						}

						BookSearch sortBook = new BookSearch(books, option);

						System.out.println("\nSYSTEM: Books Sorted!\n");
						sortBook.displayTree();

						sortAgain = getYesNoResponse("Do you want to sort again? (yes/no): ");
					}

				} else if (BSToption == 2) {
					System.out.print("\nSYSTEM: Books are sorted by ISBN for your comfort!\n");
					System.out.print("\n----Library Books----\n");

					BookSearch previewBooks = new BookSearch(books, 1);
					previewBooks.displayTree();

					boolean searchAgain = true; // Flag to control the search loop

					while (searchAgain) {
						System.out.println("\n---Search for a Book by ISBN, Title, or Author---\n");
						System.out.println("[1] - Search by ISBN");
						System.out.println("[2] - Search by Title");
						System.out.println("[3] - Search by Author First Name");
						System.out.println("[4] - Search by Author Last Name");
						System.out.println("[5] - Exit Search Menu");
						System.out.print("\nEnter search term option (1-5): ");
            lastInputTime = System.currentTimeMillis();
						option = getValidIntegerInput(1, 5);

						if (option == 5) {
							BSTMenu = true;
							break; // Exit the search loop and the BSToption == 2 block.
						}

						BookSearch searchBook = new BookSearch(books, option);

						String searchValue = timedInput("Enter the desired term: ");

						searchBook.prefixSearch(searchValue, option);

						searchAgain = getYesNoResponse("Do you want to search again? (yes/no): ");

					}

				} else if (BSToption == 3) {
					BSTMenu = true;
					System.out.println("\nSYSTEM: Exiting Search/Sort Menu...");
				}

				if (!BSTMenu) {
					System.out.print(
					    "\n---Search / Sort for a Book by ISBN, Title, or Author--- \n[1] Sort \n[2] Search \n[3] Exit Menu \nEnter an option: ");
					BSToption = getValidIntegerInput(1, 3);
				}
			}

		} catch (Exception e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // clears buffer - Jada
		}
	}

	// Views Book on the system - Jada
	public static void viewBook(LinkedList<Book> books) {
		Scanner scanner = new Scanner(System.in);
		boolean found = false;
		boolean continueViewing = true; // Flag to control viewBook method.

		while (continueViewing) {
			try {
				System.out.print("\n---Viewing Book---\n");
				System.out.print("\nSYSTEM: The first book's ISBN start with 50000" +
				                 " and the following ones increment by 4\n");
				String isbn = timedInput(("\nEnter book's ISBN to view: ")); // Trim input

				for (Book book : books) {
					if (book.getISBN().equals(isbn)) {
						found = true;
						System.out.println("\n---Book Details---");
						System.out.println(book.toString());
						break;
					}
				}

				if (!found) {
					System.out.println("SYSTEM: No book found with ISBN: " + isbn);
					continueViewing = getYesNoResponse("Would you like to view another book? (Yes/No): ");
				} else
					continueViewing = getYesNoResponse("\nSYSTEM: Book found with ISBN " + isbn
					                                   + "\nWould you like to view another book? (Yes/No): ");

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // Clears buffer
			}
		}
	}

	// Adds Book into the system - Jada
	public static void addBook(LinkedList<Book> books, String filePath) {
		boolean continueAdding = true;
		boolean validInput;

		Scanner scanner = new Scanner(System.in);
		while (continueAdding) {
			try {
				System.out.print("\n---Adding A Book---\n");
				Book b = new Book();

				System.out.print("\nSYSTEM: A book's ISBN is assigned automatically.\n");
				b.generateISBN();

				b.setTitle(timedInput("\nEnter book's title: "));

				b.setAuthorFname(timedInput("Enter book's author first name: "));

				b.setAuthorLname(timedInput("Enter book's author last name: "));

				System.out.print("\nSYSTEM: A new book's availability status is automatically set to 'true'.\n");
				b.setStatus(true);

				System.out.println("\n---Book Details---");
				System.out.println(b.toString());

				books.append(b);
				WriteBookData(filePath, books);

				validInput = false; // Reset validInput for each prompt

				if (!validInput)
					continueAdding = getYesNoResponse("\nWould you like to add another book? (yes/no): ");
				else
					continueAdding = getYesNoResponse(
					                     "\nSYSTEM: The information that you have entered is invalid. Would you like to add another book? (yes/no): ");

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n");
			}
		}
	}

	// Removes Book from the system - Jada
	public static void removeBook(LinkedList<Book> books, LinkedList<Patron> patrons, String bookfileName) {
		boolean found = false;
		boolean continueRemoving = true;

		Scanner scanner = new Scanner(System.in);
		while (continueRemoving) {
			try {
				System.out.print("\n---Removing Book---\n");
				String isbn = timedInput("\nEnter book's ISBN to delete: ");

				Book bookToRemove = null;

				for (Book book : books) {
					if (book.getISBN().equals(isbn)) {
						found = true;
						bookToRemove = book;
						break;
					}
				}

				if (bookToRemove != null) {
					boolean isBookCheckedOut = false;

					for (Patron patron : patrons) {
						for (Book patronBook : patron.getBooks()) {
							if (bookToRemove.getISBN().equals(patronBook.getISBN())) {
								if (patronBook.getStatus() == false) {
									isBookCheckedOut = true;
									break;
								}
							}
						}
						if (isBookCheckedOut) {
							break;
						}
					}

					if (isBookCheckedOut) {
						System.out.println("\nSYSTEM: Book with ISBN " + isbn + " is currently checked out by a patron and cannot be deleted.");
					} else {
						books.Remove(bookToRemove);
						WriteBookData(bookfileName, books);
						System.out.println("\nSYSTEM: Book with ISBN " + isbn + " has been removed.");
					}

					continueRemoving = getYesNoResponse("\nWould you like to remove another book? (yes/no): ");

				} else {
					System.out.println("SYSTEM: No book found with ISBN: " + isbn);
					continueRemoving = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				}

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); //clears buffer
			}
		}
	}

	// Updates Book in the system - Jada
	public static void updateBook(LinkedList<Book> books, String filePath) {
		boolean validInput = false; // Flag to control input validation.
		boolean responseFlag = false;
		boolean found = false;

		boolean continueUpdating = true; // More descriptive variable name

		Scanner scanner = new Scanner(System.in);

		System.out.print("\n---Updating a Book---\n");
		System.out.println(
		    "\nSYSTEM: Note that you CANNOT update a book's ISBN, because that new ISBN might be already in the system.");
		System.out.println("SYSTEM: Note that you also CANNOT update a book's status, as a patron could be using it.");

		while (continueUpdating) {
			try {

				String isbn = timedInput("\nEnter the book's ISBN to update: "); // Trim input

				for (Book book : books) {
					if (book.getISBN().equals(isbn)) {
						found = true;

						System.out.println("\n--- Current Book Details ---");
						System.out.println(book.toString());

						System.out.print("\nWhat book information would you like to update?\n");
						System.out.println("[1] Title");
						System.out.println("[2] Author");
						System.out.print("Please enter your choice (1-2): ");
            lastInputTime = System.currentTimeMillis();
						int updateChoice = getValidIntegerInput(1, 2); // Get a valid integer input.

						switch (updateChoice) {
						case 1: // Title
							book.setTitle(timedInput("\nUpdate book's title: ")); // Update the title.
							responseFlag = true;
							break;
						case 2: // Author
							book.setAuthorFname(timedInput("\nUpdate book's author first name: ")); // Update the author's first name.
							book.setAuthorLname(timedInput("Update book's author last name: ")); // Update the author's last name.
							responseFlag = true;
							break;
						default:
							System.out.println("\nSYSTEM: Invalid update option.");
							break;
						}

						System.out.println("\n---Updated Book Details---");
						System.out.println(book.toString());

						WriteBookData(filePath, books); // Update the book data in the file.

						validInput = true; // Set to true if all inputs are valid
						break;
					}
				}

				if (!found) {
					System.out.println("\nSYSTEM: No book found with ISBN: " + isbn);
					continueUpdating = getYesNoResponse("\nDo you want to try again (Yes/No)?  ");
				} else
					continueUpdating = getYesNoResponse("\nWould you like to update another book? (yes/no): ");

			} catch (InputMismatchException e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // Clears buffer
			}
		}
	}

	// **********Password Management*************

	public static String hashPasscode(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// Method to check username availability - Josan
	public static boolean checkUsernameAvailability(String username, String fileName) {
		if (username == null || fileName == null) {
			System.err.println("\nSYSTEM: Invalid username or filename.");
			return false; // Or throw an IllegalArgumentException
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals(username.trim())) { // Usernames are stored in plain text
					return false; // Username is taken
				}
			}

			System.out.println("\nSYSTEM: Good news! Username is available.");
			return true;
		} catch (IOException e) {
			System.err.println("\nSYSTEM: Error reading file: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // clears buffer - Jada
			return false;
		}
	}

	// Method to change password - Josan
	public static boolean changePassword(Patron loggedInUser, LinkedList<Patron> patrons) {
		Scanner input = new Scanner(System.in);
		String choice, newPassword;

		try {
			System.out.println("\nSYSTEM: Password must contain at least one uppercase letter, one " +
			                   "lowercase letter, one digit, and one special character.");

			boolean passwordValid = false;

			while (!passwordValid) {
				newPassword = timedInput("Enter new password: ");

				if (!checkPasswordStrength(newPassword))
					continue;
				else if (loggedInUser.getPassword().getPassword().equals(hashPasscode(newPassword))) {
					System.out.println("\nSYSTEM: New password cannot be the same as old password! Try again.");
					continue;
				} else {
					do {
						choice = timedInput("\nAre you sure you want to change your password?\n[Y] / [N] : ");
					} while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("n") && !choice.equalsIgnoreCase("no"));

					if (choice.equalsIgnoreCase("n")) {
						System.out.println("\nSYSTEM: Password change cancelled.");
						return false; // Return false if cancelled
					}

					loggedInUser.getPassword().setPassword(hashPasscode(newPassword));
					loggedInUser.getPassword().setOTP(false);
					passwordValid = true;

					for (Patron patron : patrons) {
						if (patron.getUsername().equals(loggedInUser.getUsername())) {
							patron.getPassword().setPassword(loggedInUser.getPassword().getPassword());
							patron.getPassword().setOTP(false);
							WritePatronData("PatronRecords.xml", patrons);
							updatePasswordDetailsHashToFile("HashedPasswords.txt", patron.getUsername(), patron.getPassword().getPassword());
							break;
						}
					}

					return true; // Return true if successful
				}
			}

			return false; // Return false if loop exits without changing password
		} catch (Exception e) {
			System.err.println("\nSYSTEM: An error occurred during password change: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n");
			return false; // Return false if an exception occurs
		}
	}

	// Method to check password strength - Josan
	public static boolean checkPasswordStrength(String password) {
		if (password.length() < 8) {
			System.out.println("\nSYSTEM: Password must be at least 8 characters long.");
			return false;
		} else if (password.length() > 30) {
			System.out.println("\nSYSTEM: Password must be at most 30 characters long.");
			return false;
		}

		boolean hasUppercase = false;
		boolean hasLowercase = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;

		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUppercase = true;
			} else if (Character.isLowerCase(c)) {
				hasLowercase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (!Character.isLetterOrDigit(c)) {
				hasSpecialChar = true;
			}
		}

		if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
			System.err.println(
			    "\nSYSTEM: Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
			return false;
		}

		return true;
	}

	// Chev - Update the user's password and reflect it in HashedPasswords.txt
	public static void updatePasswordDetailsHashToFile(String filePath, String username, String newHash)
	throws IOException {
		List<String> updatedLines = new ArrayList<>(); // Declare updatedLines list
		boolean updated = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;

			// Read all lines and update if needed
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(username + ":")) {
					updatedLines.add(username + ": " + newHash); // Consistent format
					updated = true;
				} else {
					updatedLines.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
			throw e; // Re-throw the exception after handling
		}

		if (!updated) {
			throw new IllegalArgumentException("Username not found: " + username);
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String l : updatedLines) {
				writer.write(l);
				writer.newLine();
			}
		}

		catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
			throw e; // Re-throw the exception after handling
		}
	}

	public static boolean checkUsernameStrength(String username) {
		if (username.length() < 3 || username.length() > 30) {
			System.out.println("\nSYSTEM: Username should be between 3 to 30 characters long.");
			return false;
		}
		// Check if username contains any special characters or spaces
		for (int i = 0; i < username.length(); i++) {
			if (!Character.isLetterOrDigit(username.charAt(i)) && username.charAt(i) != '_'
			        && username.charAt(i) != ' ') {
				System.out.println("\nSYSTEM: Username should not contain any special characters.");
				return false;
			}
		}
		return true;
	}


	// *************************Admin Management*****************************
	// adminMenu: Displays the admin's main menu and handles user choices.
	public static void adminMenu(String username, String pwd, Admin admin, LinkedList<Patron> patrons, LinkedList<Book> books, String bookfileName, String fileName) {
		Scanner input = new Scanner(System.in); // Scanner for user input within the menu.

		boolean responseFlag = false; // Flag to control the menu loop.

		try {
			System.out.println("\nSYSTEM: Admin login successful!\n");
			responseFlag = true;

			System.out.printf("Welcome admin %s %s, how may we assist you today?\n", admin.getFname(),
			                  admin.getLname());

			while (responseFlag) {
				System.out
				.print("\n---Admin Controls Menu---\n[1] Book Information Menu \n[2] Patron Information Menu" +
				       " \n[3] Check Library Status \n[4] Exit Menu \nPlease select an option: ");
        lastInputTime = System.currentTimeMillis();
				int choice = getValidIntegerInput(1, 4); // Get a valid integer input.

				try {
					// Explores each option - Jada
					switch (choice) {
					// Shows options for book information - Jada
					case 1: {
						System.out.println("\n---Books Currently In The System---");
						System.out.println(books);

						boolean bookMenu = true; // Flag to control the book menu loop.

						while (bookMenu) {
							System.out.print(
							    "\n---Admin's Book Information Menu---\n[1] Add a Book \n[2] Update a Book \n[3] View a Book"
							    +
							    "\n[4] Remove a Book \n[5] Search/Sort for Book by Author or Title \n[6] Exit Menu "
							    +
							    "\nPlease select an option: ");
							lastInputTime = System.currentTimeMillis();
							int bookOption = getValidIntegerInput(1, 6); // Get a valid integer input.

							try {
								switch (bookOption) {
								case 1:
									addBook(books, "BookData.xml");
									break;
								case 2:
									updateBook(books, "BookData.xml"); // Update a book.
									break;
								case 3:
									viewBook(books); // View a book.
									break;
								case 4:
									removeBook(books, patrons, "BookData.xml"); // Remove a book.
									break;
								case 5: {
									searchBooks(books); // Search for books.
									break;
								}
								case 6: {
									System.out.println(
									    "\nSYSTEM: Logging out of Admin's Book Information Menu...");
									bookMenu = false; // Exit the book menu.
									break;
								}
								default: {
									System.out.println(
									    "\nSYSTEM: Invalid input... Please enter the value again: \n");
									bookMenu = false;
								}
								}
							} catch (InputMismatchException e) {
								System.err.println("\nERROR: " + e.getMessage());
								e.printStackTrace();
								System.out.println("\n"); // Clears buffer - Jada
							}
						}
						break;
					}

					// Shows options for patron information - Jada
					case 2: {
						boolean patronMenu = true; // Flag to control the patron menu loop.

						while (patronMenu) {
							System.out.println("\n---Patrons Currently In The System---");
							System.out.println(patrons);

							System.out.print(
							    "\n---Admin's Patron Information Menu---\n[1] Add a Patron \n[2] Update a Patron"
							    +
							    "\n[3] View a Patron's Details \n[4] Remove a Patron \n[5] Exit Menu " +
							    "\nPlease select an option: ");
              lastInputTime = System.currentTimeMillis();
							int patronOption = getValidIntegerInput(1, 5); // Get a valid integer input.

							try {
								switch (patronOption) {
								case 1:
									boolean continueAdd = true;
									do {
										addPatron(patrons); // Add a patron

										String response = timedInput("\nSYSTEM: Would you like to add another patron? (yes/no): "); // Added trim() to remove
										// whitespace

										if (response.equalsIgnoreCase("yes")
										        || response.equalsIgnoreCase("y")) {

										} else if (response.equalsIgnoreCase("no")
										           || response.equalsIgnoreCase("n")) {
											continueAdd = false;
										}
									} while (continueAdd);

									break;
								case 2:
									updatePatron(patrons, "PatronRecords.xml"); // Update a patron.
									break;
								case 3:
									boolean continueAdd2 = true;
									while (continueAdd2) // Changed condition to run while continueAdd2 is true
									{
										System.out.println("\n---Viewing Patron's Details---\n");
										String userID = timedInput("Please enter user library card number : ");
										viewPatron(patrons, userID);

										String response = timedInput("\nSYSTEM: Would you like to view another patron? (yes/no): ");

										if (!response.equalsIgnoreCase("Y")
										        && !response.equalsIgnoreCase("Yes")) {
										} else if (response.equalsIgnoreCase("no")
										           || response.equalsIgnoreCase("n")) {
											continueAdd2 = false; // Exit the loop
										}
									}
									break;
								case 4:
									removePatron(patrons); // Remove a patron.
									break;
								case 5:
									System.out.println(
									    "\nSYSTEM: Logging out of Admin's Patron Information Menu...");
									patronMenu = false; // Exit the patron menu.
									break;
								default: {
									System.out.println("\nSYSTEM: Invalid option entered.\n");
									String response = timedInput(
									                      "\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");

									patronMenu = false;

									if (!response.equalsIgnoreCase("Y") || !response.equalsIgnoreCase("Yes"))
										break; // Exit the loop if the user does not want to try again
									else if (!response.equalsIgnoreCase("n")
									         || !response.equalsIgnoreCase("no"))
										patronMenu = true;
									else {
										response = timedInput(
										               "\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");

										if (!response.equalsIgnoreCase("Y")
										        || !response.equalsIgnoreCase("Yes"))
											break; // Exit the loop if the user does not want to try again
										else
											patronMenu = true;
									}
								}
								}
							} catch (InputMismatchException e) {
								System.err.println("\nERROR: " + e.getMessage());
								e.printStackTrace();
								System.out.println("\n"); // Clears buffer
							}
						}
						break;
					}

					// Checks library status
					case 3:
						viewLibraryStats(books, patrons); // View library statistics.
						break;

					// Exits menu - Jada
					case 4:
						System.out.print("\nSYSTEM: Logging out of Admin Menu...\n");
						responseFlag = false; // Exit the admin menu.
						break;

					default:
						System.out.println("\nSYSTEM: Invalid option entered.\n");
						String response = timedInput("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");

						if (!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("Yes")) {
							break; // Exit the loop if the user does not want to try again
						} else if (response.equalsIgnoreCase("N") || response.equalsIgnoreCase("No")) {
							responseFlag = false;
							break;
						} else {
							responseFlag = true; // Continue the loop if the user wants to try again
						}
					}
				} catch (InputMismatchException e) {
					System.err.println("\nERROR: " + e.getMessage());
					e.printStackTrace();
					System.out.println("\n"); // Clears buffer
				}
			}
		} catch (InputMismatchException e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // Clears buffer
		} catch (NullPointerException e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // Clears buffer
		} catch (IndexOutOfBoundsException e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // Clears buffer - Jada
		} catch (NumberFormatException e) {
			System.err.println("\nERROR: " + e.getMessage());
			e.printStackTrace();
			System.out.println("\n"); // Clears buffer - Jada
		}
	}

	public static void addPatron(LinkedList<Patron> patrons) {

		System.out.println(
		    "\nWelcome new patron to our Library Management System (LMS), please fill out the following information to get started!\n");

		boolean responseFlag = false;
		boolean continueAdding = true;

		Patron p = new Patron();
		System.out.print("\n---Adding New Patron---\n");

		System.out.print("\nSYSTEM: A patron's ID is assigned automatically by the system\n");

		p.setFname(timedInput("\nEnter patron's first name: "));

		p.setLname(timedInput("Enter patron's last name: "));

		boolean usernameAvailable = false;
		boolean weakUsername = false;
		String pUsername = null;

		while (!weakUsername && !usernameAvailable) {
			p.setUsername(timedInput("Enter patron's username: "));

			if (!checkUsernameStrength(p.getUsername())) {
				System.out.print("\nSYSTEM: Weak username! Try again.\n");
				continue; // Loops again if username is weak
			} else if (!checkUsernameAvailability(p.getUsername(), User.UsernamesFile)) {
				System.out.print("\nSYSTEM: Username in use! Try again.\n");
				continue; // Loops again if username is taken
			} else {
				// If both conditions pass, set the username

				weakUsername = true;
				usernameAvailable = true;
			}
		}

		boolean validInput = true;

		String generatedPassword = p.getPassword().generatePassword(8); // Generate the password
		p.getPassword().setPassword(hashPasscode(generatedPassword)); // Set the generated password
		p.getPassword().setOTP(true);

		System.out.print("\nSYSTEM: Patron registration is successful! Here are the details: \n");

		System.out.print("\n---Patron Details---\n");
		System.out.println(p.toString());

		System.out.printf(
		    "\nSYSTEM: Every time a new patron is added to the LMS, they are give a default password, this one is: %s\n",
		    generatedPassword);

		patrons.append(p);
		try (FileWriter file = new FileWriter("HashedPasswords.txt", true)) {
			file.append(p.getUsername() + ": " + p.getPassword().getPassword());
			file.append("\n");
			file.close();
		} catch (Exception e) {
			System.out.println("Error writing to file : " + e.getMessage());
		}

		writeUsernameToFile(p.getUsername(), User.UsernamesFile);
		WritePatronData("PatronRecords.xml", patrons);
	}

	// Updates member details - Jada
	public static void updatePatron(LinkedList<Patron> patrons, String filePath) {
		boolean continueUpdating = true;
		while (continueUpdating) {
			try {
				System.out.print("\n---Updating Patron---\n");
				String updateID = timedInput("\nEnter patron's Library Number to update: ");

				boolean found = false;

				for (Patron patron : patrons) {
					if (patron.getUserID().equals(updateID)) {
						found = true;
						System.out.print("\n---Current Patron Details---\n");
						System.out.println(patron.toString());
						System.out.println(
						    "\nSYSTEM: Note that you cannot update a patron's Library Number or their booklist.\n");

						System.out.println("SYSTEM: Which information about this patron would you like to update?");
						System.out.println("[1] First Name");
						System.out.println("[2] Last Name");
						System.out.println("[3] Username");
						System.out.println("[4] Password");
						System.out.println("[5] Exit Updating Patron");
						System.out.print("Enter your choice: ");

						lastInputTime = System.currentTimeMillis();
						int choice = getValidIntegerInput(1, 5);

						switch (choice) {
						case 1:
							patron.setFname(timedInput("\nUpdate patron's first name: "));
							break;

						case 2:
							patron.setLname(timedInput("\nUpdate patron's last name: "));
							break;
						case 3: {
							String pUsername = null;
							while (true) {
								pUsername = timedInput("Enter patron's username: ");

								if (!checkUsernameStrength(pUsername)) {
									System.out.print("\nSYSTEM: Weak username! Try again.\n");
									continue;
								} else {
									boolean usernameAvailable = true;
									for (Patron p : patrons) {
										if (p != patron && p.getUsername().equals(pUsername)) {
											usernameAvailable = false;
										}
									}
									if (!usernameAvailable) {
										System.out.print("\nSYSTEM: Username in use! Try again.\n");
										continue;
									}
									updateUsernameFile("Usernames.txt", patron.getUsername(), pUsername);
									updateHashedFileUsername("HashedPasswords.txt", patron.getUsername(), pUsername);
									patron.setUsername(pUsername);
									break;
								}
							}
						}
						break;

						case 4:
							changePassword(patron, patrons);
							break;

						case 5:
							break;

						default:
							System.out.println("SYSTEM: Invalid choice.");
							continue; // Go back to the menu
						}

						System.out.print("\nSYSTEM: Patron details updated! Here are the results!\n");
						System.out.print("\n---Updated Patron Details---\n");
						System.out.println(patron.toString());
						WritePatronData("PatronRecords.xml", patrons);

						break; // break out of the for loop after processing a valid update
					}
				}

				if (!found) {
					System.out.println("SYSTEM: No patron found with Library Number: " + updateID);
					lastInputTime = System.currentTimeMillis();
					continueUpdating = getYesNoResponse(
					                       "\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else
					lastInputTime = System.currentTimeMillis();
				continueUpdating = getYesNoResponse("\nWould you like to update another patron? (yes/no): ");

			} catch (Exception e) {
				System.err.println("\nAn error occurred while updating patron.\n" + e.getMessage());
				e.printStackTrace();
				System.out.println("\n");
			}
		}
	}


	// Remove member from the library system - Jada
	public static void removePatron(LinkedList<Patron> patrons) {
		Scanner scanner = new Scanner(System.in); // Initialize scanner here
		boolean continueRemoving = true;

		while (continueRemoving) {
			try {
				System.out.print("\n---Removing Patron---\n");
				String deleteID = timedInput("\nEnter patron's Library Number to remove: ");

				Patron patronToRemove = null;
				boolean found = false;

				for (Patron patron : patrons) {
					if (patron.getUserID().equals(deleteID)) {
						patronToRemove = patron;
						found = true;
						break;
					}
				}

				if (found) {
					if (patronToRemove.getBooks().len() != 0) {
						System.out.println("\nSYSTEM: Patron with library number: " + deleteID + " is in a queue. Please have them return any checked out books before removing them.");
					} else {
						patrons.Remove(patronToRemove);
						WritePatronData("PatronRecords.xml", patrons);
						removeUsernameFromFile("Usernames.txt",patronToRemove.getUsername());
						removeHashedUsernameFromFile("HashedPasswords.txt",patronToRemove.getUsername());

						System.out.println("\nSYSTEM: Patron with library number: " + deleteID + " has been removed.");
					}

				}

				if (!found) {
					System.out.println("SYSTEM: No patron found with Library Number: " + deleteID);
					continueRemoving = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else
					continueRemoving = getYesNoResponse("\nWould you like to remove another patron? (yes/no): ");

			} catch (Exception e) {
				System.err.println("\nAn error occurred while removing patron.\n" + e.getMessage());
				e.printStackTrace();
				System.out.println("\n");
			}
		}
	}


// Method to view currently borrowed books - Jada
	public static void viewBorrowedBooks(LinkedList<Book> borrowedBooks) {
		if (borrowedBooks.len() == 0)
			System.out.println("\nSYSTEM: No books have been borrowed.");
		else {
			System.out.println("\n---Your Current Borrowed Books---");

			for (Book book : borrowedBooks) {
				if (book.getStatus() == false)
					System.out.println(book.toString());
			}
		}
	}

	//Chev: Changes a username in the Usernames.txt
	public static void updateUsernameFile(String filePath, String oldUsername, String newUsername)
	{
		List<String> usernames = new ArrayList<>();

		// Read usernames from file
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals(oldUsername)) {
					usernames.add(newUsername);
				} else {
					usernames.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
			return;
		}

		// Write updated usernames back to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String username : usernames) {
				writer.write(username);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}

		System.out.println("Username updated successfully.");
	}

	//Chev: Removes a username from file
	public static void removeUsernameFromFile(String filePath, String deleteUser) {
		List<String> usernames = new ArrayList<>();

		// Read usernames from file
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.equals(deleteUser)) {  // Only add if it's not the user to delete
					usernames.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
			return;
		}

		// Write updated usernames back to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String username : usernames) {
				writer.write(username);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}

		System.out.println("Username deleted successfully.");
	}


	public static void updateHashedFileUsername(String filePath, String oldUsername, String newUsername)
	{
		List<String> updatedLines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(oldUsername + ":")) {
					// Keep the hash part unchanged
					String hash = line.substring(line.indexOf(":") + 1).trim();
					updatedLines.add(newUsername + ": " + hash);
				} else {
					updatedLines.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
			return;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : updatedLines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}

		System.out.println("Username changed from " + oldUsername + " to " + newUsername);
	}

	public static void removeHashedUsernameFromFile(String filePath, String deleteUser)
	{
		List<String> updatedLines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith(deleteUser + ":")) {
					updatedLines.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
			return;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : updatedLines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}

	}


// ------------------------ View Library Statistics ------------------------ Jevana
	public static void viewLibraryStats(LinkedList<Book> books, LinkedList<Patron> parameterlist) {
		System.out.println("\n---Library Statistics---");

		// Gets the size of the LinkedList - Jada
		System.out.println("\n----Total Books----\n" + books.len());
		for (Book b : books) {
			System.out.println(b.toString());
		}

		// Gets the size of the parameterlist - Jada
		System.out.println("\n----Total Patrons----\n" + parameterlist.len());
		for (Patron p : parameterlist) {
			System.out.println(p.toString());
		}

		// Count number of currently checked-out books - Jada
		System.out.println("\n----Books Currently Checked Out----");
		boolean hasCheckedOutBooks = false; // Flag to check if there are any checked-out books

		for (Book b : books) {
			if (b.getStatus() == false) {
				System.out.println(b.toString()); // Print the book details if checked out - Jada
				hasCheckedOutBooks = true; // Set the flag to true if at least one book is checked out - Jada
			}
		}

		// If no books are checked out, print a message - Jada
		if (!hasCheckedOutBooks) {
			System.out.println("SYSTEM: No books are currently checked out.");
		}
	}

	// Chev: Method that resets the inactivity timer when a new prompt appears
	public static String timedInput(String prompt) {
		System.out.print(prompt);
		String line = input.nextLine().trim();
		lastInputTime = System.currentTimeMillis();
		return line;
	}

}

