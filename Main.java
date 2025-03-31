// Main.java

import java.io.*;
import java.util.*;
import java.security.*;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		//Chev: Initialize lists for books and patrons, and a queue for patron checkout requests.
		LinkedList<Book> bookie = new LinkedList<Book>(); // List to hold all books in the library.
		LinkedList<Patron> pat = new LinkedList<Patron>(); // List to hold all patrons.
		Queue<Patron> patQue = new Queue<>(); // Queue to manage patrons waiting to check out books. (Jada)

		//Chev: Define file names for book and patron data.
		String booksFileName = "BookData.xml";
		String patronsFileName = "PatronRecords.xml";

		//Test Data - Jevana / Jada: Create an admin user and populate the library with initial data.
		Admin admin1 = new Admin("Oral", "Robinson", "admin", new Password("admin", false), bookie, pat); // Create an admin user.

		//Jevana / Jada: Initial book data.
		String[][] bookData = {
			{"The Great Gatsby", "F. Scott", "Fitzgerald", "true"},
			{"Cinderella", "John", "Brown", "true"},
			{"Introduction to Psychology", "James W.", "Kalat", "true"},
			{"Principles of Economics", "N. Gregory", "Mankiw", "true"},
			{"Organic Chemistry", "Paula", "Bruice", "true"},
			{"World History: Patterns of Interaction", "Roger B.", "Beck", "false"},
			{"Anatomy and Physiology", "Elaine N.", "Marieb", "true"},
			{"Calculus: Early Transcendentals", "James", "Stewart", "true"},
			{"Introduction to Philosophy", "John", "Perry", "true"},
			{"Marketing Management", "Philip", "Kotler", "true"},
			{"Principles of Accounting", "Jerry J.", "Weygandt", "true"},
			{"Introduction to Sociology", "Anthony", "Giddens", "false"},
			{"Harry Potter", "J.K.", "Rowling", "false"},
			{"The Hobbit", "J.R.R.", "Tolkien", "true"},
			{"Moby Dick", "Herman", "Melville", "false"},
			{"The Lord of the Rings", "J.R.R.", "Tolkien", "false"},
			{"Pride and Prejudice", "Jane", "Austen", "true"},
			{"To Kill a Mockingbird", "Harper", "Lee", "true"},
			{"1984", "George", "Orwell", "true"},
			{"Brave New World", "Aldous", "Huxley", "true"},
			{"The Catcher in the Rye", "J.D.", "Salinger", "false"},
			{"One Hundred Years of Solitude", "Gabriel", "Garcia", "Marquez", "true"},
			{"The Odyssey", "Homer", " ", "true"},
			{"The Iliad", "Homer", " ", "true"},
			{"Don Quixote", "Miguel", "de Cervantes", "true"},
			{"Alice's Adventures in Wonderland", "Lewis", "Carroll", "true"},
			{"War and Peace", "Leo", "Tolstoy", "false"},
			{"Crime and Punishment", "Fyodor", "Dostoyevsky", "true"},
			{"The Brothers Karamazov", "Fyodor", "Dostoyevsky", "false"},
			{"Madame Bovary", "Gustave", "Flaubert", "true"},
			{"Great Expectations", "Charles", "Dickens", "true"},
			{"Jane Eyre", "Charlotte", "Bronte", "false"},
			{"Wuthering Heights", "Emily", "Bronte", "true"},
			{"The Picture of Dorian Gray", "Oscar", "Wilde", "true"},
			{"Frankenstein", "Mary", "Shelley", "true"},
			{"Dracula", "Bram", "Stoker", "false"},
			{"The Count of Monte Cristo", "Alexandre", "Dumas", "true"},
			{"Les Miserables", "Victor", "Hugo", "true"},
			{"Anna Karenina", "Leo", "Tolstoy", "false"},
			{"Middlemarch", "George", "Eliot", "true"},
			{"David Copperfield", "Charles", "Dickens", "true"},
			{"The Adventures of Huckleberry Finn", "Mark", "Twain", "false"},
			{"The Scarlet Letter", "Nathaniel", "Hawthorne", "true"},
			{"The Sun Also Rises", "Ernest", "Hemingway", "true"},
			{"A Farewell to Arms", "Ernest", "Hemingway", "false"},
			{"The Old Man and the Sea", "Ernest", "Hemingway", "true"},
			{"Catch-22", "Joseph", "Heller", "true"},
			{"Slaughterhouse-Five", "Kurt", "Vonnegut", "false"},
			{"The Bell Jar", "Sylvia", "Plath", "true"},
			{"The Color Purple", "Alice", "Walker", "true"}
		};

		//Jevana / Jada: Populate the book list with the initial book data.
		for (String[] data : bookData) {
			Book book = new Book();
			book.setTitle(data[0]);
			book.setAuthorFname(data[1]);
			book.setAuthorLname(data[2]);
			book.setStatus(Boolean.parseBoolean(data[3]));
			bookie.append(book);
		}

		//Jevana / Jada: Initial password data for patrons.
		String[] passwordStrings = {
			"Chev@8888", "Jada@5678", "Josan@9012", "James@4321", "Lisa@3344",
			"Kevin@7765", "Sophie@4567", "Michael@9832", "Ava@1122", "Ethan@7788", "fucku"
		};

		Password[] passwords = new Password[passwordStrings.length];

		//Jevana / Jada: Create Password objects for each password string.
		for (int i = 0; i < passwordStrings.length; i++) {
			passwords[i] = new Password(passwordStrings[i], false);
		}

		//Jevana / Jada: Initial patron data.
		String[][] patronData = {
			{"Chevannese", "Ellis", "chev", "0"},
			{"Jada", "Johnson", "jada.j", "1"},
			{"Josan", "Williams", "jos.will", "2"},
			{"James", "Anderson", "jamesa", "3"},
			{"Johnathan", "Blackwood", "john.b", "4"},
			{"Jevana", "Grant", "jev.g", "5"},
			{"Sophie", "Turner", "soph.t", "6"},
			{"Michael", "Smith", "mike.s", "7"},
			{"Ava", "Brooks", "ava.b", "8"},
			{"Ethan", "Clark", "ethan.c", "9"}
		};

		int i = 0;

		//Jevana / Jada: Populate the patron list with the initial patron data.
		for (String[] data : patronData) {
			Patron patron = new Patron();

			patron.setFname(data[0]);
			patron.setLname(data[1]);
			patron.setUsername(data[2]);
			patron.setPassword(passwords[Integer.parseInt(data[3])]);
			patron.setBooks(new LinkedList<Book>());
			pat.insert(patron, i);
			patron.writeUsernameToFile(data[2], Patron.UsernamesFile);
			Password.PasswordDetailsHashToFile(data[2], passwordStrings[Integer.parseInt(data[3])]);
			i++;
		}

		//Jada: Assign books to patrons and add them to the checkout queue.
		pat.get(0).getBooks().append(bookie.get(0));
		bookie.get(0).setStatus(false);
		patQue.enqueue(pat.get(0));

		pat.get(3).getBooks().append(bookie.get(5));
		bookie.get(5).setStatus(false);
		patQue.enqueue(pat.get(3));

		pat.get(6).getBooks().append(bookie.get(10));
		bookie.get(10).setStatus(false);
		patQue.enqueue(pat.get(6));

		pat.get(1).getBooks().append(bookie.get(1));
		bookie.get(1).setStatus(false);
		patQue.enqueue(pat.get(1));

		pat.get(2).getBooks().append(bookie.get(2));
		bookie.get(2).setStatus(false);
		patQue.enqueue(pat.get(2));

		pat.get(3).getBooks().append(bookie.get(5));
		bookie.get(5).setStatus(false);
		patQue.enqueue(pat.get(3));

		pat.get(4).getBooks().append(bookie.get(7));
		bookie.get(7).setStatus(false);
		patQue.enqueue(pat.get(4));

		pat.get(5).getBooks().append(bookie.get(10));
		bookie.get(10).setStatus(false);
		patQue.enqueue(pat.get(5));

		pat.get(6).getBooks().append(bookie.get(12));
		bookie.get(12).setStatus(false);
		patQue.enqueue(pat.get(6));

		pat.get(7).getBooks().append(bookie.get(15));
		bookie.get(15).setStatus(false);
		patQue.enqueue(pat.get(7));

		// Jada: Assign books to patrons who already have books.
		for (Book book : bookie) {
			if (!book.getStatus()) { // If the book's status is false
				boolean assigned = false; // Flag to track if the book was assigned

				for (Patron patron : pat) {
					if (!patron.getBooks().isEmpty() && patron.getBooks().len() < 5) { // If patron has books and less than 5
						patron.getBooks().append(book);
						patQue.enqueue(patron);
						assigned = true;
						break; // Book assigned, move to the next book
					}
				}

				if (!assigned) {
					System.out.println("\nSYSTEM: No patrons with available slots to assign book: " + book.getTitle());
				}
			}
		}

		// Chev: Add a new patron to the system.
		Patron chev = new Patron("Jevana", "Grant-Brown", "what", new Password("fucku", false), new LinkedList<Book>());
		pat.append(chev);
		chev.writeUsernameToFile(chev.getUsername(), User.UsernamesFile);
		chev.getPassword().PasswordDetailsHashToFile(chev.getUsername(), chev.getPassword().getPassword());

		// Chev / Jada: Write patron data to XML file.
		admin1.WritePatronData(patronsFileName, pat);
		admin1.loadPatronData(patronsFileName, pat);

		// Write book data to XML file if needed.
		admin1.WriteBookData(booksFileName, bookie);
		admin1.loadBookData(booksFileName, bookie);

		System.out.println("===== Welcome to the Library Management System (LMS) =====");
		System.out.println("\nThis system uses a variety of users, please identify yourself and select the correct information");

		boolean running = true;

		// Main menu loop - Jevana
		while (running) {
			try {
				System.out.print("\n---LMS Main Menu--- \n[1] Admin/Patron Login \n[2] New Patron Registration (Sign Up)" +
				                 "\n[3] Exit System \nSelect option (1-3): ");

				int choice = getValidIntegerInput(1, 3);

				switch (choice) {
				case 1:
					System.out.print("\nEnter your username: ");
					String username = input.nextLine().trim();

					System.out.print("Enter your password: ");
					String pwd = input.nextLine().trim();

					//Chev: Check if the user is an admin or a patron.
					if (username.equals("admin") && pwd.equals("admin")) {
						Admin.adminMenu(username, pwd, admin1, pat, patQue, bookie, booksFileName, patronsFileName);
					} else {
						Patron.patronMenu(username, pwd, pat, patQue, bookie, patronsFileName);
					}
					break;
				case 2:
					Patron.newPatronMenu(pat, patronsFileName); // Adding new patron via Patron class
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
				System.out.println("\n"); //clears buffer - Jada
			}
		}
		
		input.close();
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
					choice = input.nextInt();
				}
			} catch (NumberFormatException e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); //clears buffer - Jada
			}
		}
		return choice;
	}
}
