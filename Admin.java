// Jada Johnson (2209658), 2/14/25, Admin.java

import java.io.*;
import java.util.*;
import java.security.*;

import javax.xml.parsers.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static javax.swing.GroupLayout.*;
import static javax.swing.GroupLayout.Alignment.*;

public class Admin extends User {
	private LinkedList<Book> books; // List of books managed by the admin.
	private LinkedList<Patron> patrons; // List of patrons managed by the admin.

	// Default Constructor - Jada: Creates an Admin with empty book and patron lists.
	public Admin() {
		super(); // Call the User class's default constructor.
		books = new LinkedList<Book>(); // Initialize the books list.
		patrons = new LinkedList<Patron>(); // Initialize the patrons list.
	}

	// Primary Constructor - Jada: Creates an Admin with given details.
	public Admin(String fname, String lname, String username, Password password, LinkedList<Book> books, LinkedList<Patron> patrons) {
		super(fname, lname, username, password); // Call the User class's primary constructor.
		this.books = books; // Set the books list.
		this.patrons = patrons; // Set the patrons list.
	}

	// Copy Constructor - Jada: Creates an Admin by copying an existing Admin.
	public Admin(Admin ad) {
		super(ad); // Call the User class's copy constructor.
		this.books = ad.books; // Copy the books list.
		this.patrons = ad.patrons; // Copy the patrons list.
	}

	Scanner scanner = new Scanner(System.in); // Scanner for user input.

	// adminMenu: Displays the admin's main menu and handles user choices.
	public static void adminMenu(String username, String pwd, Admin admin, LinkedList<Patron> patrons, Queue<Patron> queue, LinkedList<Book> books, String bookfileName, String fileName) {
		Scanner input = new Scanner(System.in); // Scanner for user input within the menu.

		boolean responseFlag = false; // Flag to control the menu loop.

		try {
			System.out.println("\nSYSTEM: Admin login successful!\n");
			responseFlag = true;

			System.out.printf("Welcome admin %s %s, how may we assist you today?\n", admin.getFname(), admin.getLname());

			while (responseFlag) {
				System.out.print("\n---Admin Controls Menu---\n[1] Book Information Menu \n[2] Patron Information Menu" +
				                 " \n[3] Check Library Status \n[4] Exit Menu \nPlease select an option: ");

				int choice = Main.getValidIntegerInput(1, 4); // Get a valid integer input.

				try {
					// Explores each option - Jada
					switch (choice) {
					// Shows options for book information - Jada
					case 1: {
						System.out.println("\n---Books Currently In The System---");
						System.out.println(books);

						boolean bookMenu = true; // Flag to control the book menu loop.

						while (bookMenu) {
							System.out.println("\n---Admin's Book Information Menu---\n[1] Add a Book \n[2] Update a Book \n[3] View a Book" +
							                   "\n[4] Remove a Book \n[5] Search/Sort for Book by Author or Title \n[6] Exit Menu " +
							                   "\nPlease select an option: ");
							int bookOption = Main.getValidIntegerInput(1, 6); // Get a valid integer input.

							try {
								switch (bookOption) {
								case 1:
									admin.addBook(books, bookfileName); // Add a book.
									break;
								case 2:
									admin.updateBook(books, bookfileName); // Update a book.
									break;
								case 3:
									admin.viewBook(books); // View a book.
									break;
								case 4:
									admin.removeBook(books, patrons, bookfileName); // Remove a book.
									break;
								case 5: {
									admin.searchBooks(books); // Search for books.
									break;
								}
								case 6: {
									System.out.println("\nSYSTEM: Logging out of Admin's Book Information Menu...");
									bookMenu = false; // Exit the book menu.
									break;
								}
								default: {
									System.out.println("\nSYSTEM: Invalid input... Please enter the value again: \n");
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

							System.out.print("\n---Admin's Patron Information Menu---\n[1] Add a Patron \n[2] Update a Patron" +
							                 "\n[3] View a Patron's Details \n[4] Remove a Patron \n[5] Exit Menu " +
							                 "\nPlease select an option: ");

							int patronOption = Main.getValidIntegerInput(1, 5); // Get a valid integer input.

							try {
								switch (patronOption) {
								case 1:
									admin.addPatron(patrons, fileName); // Add a patron.
									break;
								case 2:
									admin.updatePatron(patrons, fileName); // Update a patron.
									break;
								case 3:
									System.out.println("\n---Viewing Patron's Details---\n");
									System.out.println("SYSTEM: Note that the patron's password is hashed for security reasons...\n");
									admin.viewPatron(patrons); // View a patron.
									break;
								case 4:
									admin.removePatron(patrons, queue, fileName); // Remove a patron.
									break;
								case 5:
									System.out.println("\nSYSTEM: Logging out of Admin's Patron Information Menu...");
									patronMenu = false; // Exit the patron menu.
									break;
								default: {
									System.out.println("\nSYSTEM: Invalid option entered.\n");
									System.out.print("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
									String response = input.nextLine();

									patronMenu = false;

									if (!response.equalsIgnoreCase("Y") || !response.equalsIgnoreCase("Yes"))
										break; // Exit the loop if the user does not want to try again
									else if (!response.equalsIgnoreCase("n") || !response.equalsIgnoreCase("no"))
										patronMenu = true;
									else {
										System.out.print("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
										response = input.nextLine();

										if (!response.equalsIgnoreCase("Y") || !response.equalsIgnoreCase("Yes"))
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
						admin.viewLibraryStats(books, patrons); // View library statistics.
						break;

					// Exits menu - Jada
					case 4:
						System.out.print("\nSYSTEM: Logging out of Admin Menu...\n");
						responseFlag = false; // Exit the admin menu.
						break;

					default:
						System.out.println("\nSYSTEM: Invalid option entered.\n");
						System.out.print("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
						String response = input.nextLine();

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

	//-------------------------Book Section---------------------------------

	// Adds Book into the system - Jada
	public void addBook(LinkedList<Book> books, String filePath) {
		boolean continueAdding = true;
		boolean validInput;

		while (continueAdding) {
			try {
				System.out.print("\n---Adding A Book---\n");
				Book b = new Book();

				System.out.print("\nSYSTEM: A book's ISBN is assigned automatically.\n");
				b.generateISBN();

				System.out.print("\nEnter book's title: ");
				b.setTitle(scanner.nextLine());

				System.out.print("Enter book's author first name: ");
				b.setAuthorFname(scanner.nextLine());

				System.out.print("Enter book's author last name: ");
				b.setAuthorLname(scanner.nextLine());

				System.out.print("\nSYSTEM: A new book's availability status is automatically set to 'true'.\n");
				b.setStatus(true);

				System.out.println("---Book Details---");
				System.out.println("\nBook ISBN: " + b.getISBN());
				System.out.println("Book Title: " + b.getTitle());
				System.out.println("Book Author: " + b.getAuthorFname() + " " + b.getAuthorLname());
				System.out.println("Book Status: " + (b.getStatus() ? "Available" : "Checked Out"));

				books.append(b);
				b.toXML();
				WriteBookData(filePath, books);

				validInput = false; // Reset validInput for each prompt

				if (!validInput)
					continueAdding = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				else
					continueAdding = getYesNoResponse("\nWould you like to add another book? (yes/no): ");

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n");
			}
		}
	}

	// Updates Book in the system - Jada
	public void updateBook(LinkedList<Book> books, String filePath) {
		boolean validInput = false; // Flag to control input validation.
		boolean responseFlag = false;
		boolean found = false;

		boolean continueUpdating = true; // More descriptive variable name

		System.out.print("\n---Updating a Book---\n");
		System.out.println("\nSYSTEM: Note that you CANNOT update a book's ISBN, because that new ISBN might be already in the system.");
		System.out.println("SYSTEM: Note that you also CANNOT update a book's status, as a patron could be using it.");

		while (continueUpdating) {
			try {

				System.out.print("\nEnter the book's ISBN to update: ");
				String isbn = scanner.nextLine().trim(); // Trim input

				for (Book book : books) {
					if (book.getISBN().equals(isbn)) {
						found = true;

						System.out.println("\n--- Current Book Details ---");
						System.out.println(book.toString());

						System.out.print("\nWhat book information would you like to update?\n");
						System.out.println("[1] Title");
						System.out.println("[2] Author");
						System.out.print("Please enter your choice (1-2): ");

						int updateChoice = Main.getValidIntegerInput(1, 2); // Get a valid integer input.

						switch (updateChoice) {
						case 1: // Title
							System.out.print("\nUpdate book's title: ");
							book.setTitle(scanner.nextLine()); // Update the title.
							responseFlag = true;
							break;
						case 2: // Author
							System.out.print("\nUpdate book's author first name: ");
							book.setAuthorFname(scanner.nextLine()); // Update the author's first name.

							System.out.print("Update book's author last name: ");
							book.setAuthorLname(scanner.nextLine()); // Update the author's last name.
							responseFlag = true;
							break;
						default:
							System.out.println("\nSYSTEM: Invalid update option.");
							break;
						}

						System.out.println("\n---Updated Book Details---");
						System.out.println("\nBook ISBN: " + book.getISBN());
						System.out.println("Book Title: " + book.getTitle());
						System.out.println("Book Author: " + book.getAuthorFname() + " " + book.getAuthorLname());
						System.out.println("Book Status: " + (book.getStatus() ? "Available" : "Checked Out"));

						WriteBookData(filePath, books); // Update the book data in the file.

						validInput = true; // Set to true if all inputs are valid
						break;
					}
				}

				if (!found) {
					System.out.println("\nSYSTEM: No book found with ISBN: " + isbn);
					continueUpdating = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else
					continueUpdating = getYesNoResponse("\nWould you like to update another book? (yes/no): ");

			} catch (InputMismatchException e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // Clears buffer
			}
		}
	}

	// Views Book on the system - Jada
	public void viewBook(LinkedList<Book> books) {
		boolean found = false;
		boolean continueViewing = true; // Flag to control viewBook method.

		while (continueViewing) {
			try {
				System.out.print("\n---Viewing Book---\n");
				System.out.print("\nSYSTEM: The first book's ISBN start with 50000" +
				                 " and the following ones increment by 4\n");
				System.out.print("\nEnter book's ISBN to view: ");
				String isbn = scanner.nextLine().trim(); // Trim input

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
					continueViewing = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else
					continueViewing = getYesNoResponse("Would you like to view another book? (Yes/No): ");

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n"); // Clears buffer
			}
		}
	}

	// Removes Book from the system - Jada
	public void removeBook(LinkedList<Book> books, LinkedList<Patron> patrons, String bookfileName) {
		boolean found = false;
		boolean continueRemoving = true;

		while (continueRemoving) {
			try {
				System.out.print("\n---Removing Book---\n");
				System.out.print("\nEnter book's ISBN to delete: ");
				String isbn = scanner.nextLine();

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

	//-------------------------Patron Section---------------------------------

	// Add member to the library system - Jada
	public static void addPatron(LinkedList<Patron> patrons, String filePath) {
		Scanner scanner = new Scanner(System.in);

		boolean responseFlag = false;
		boolean validInput = true;
		boolean continueAdding = true;

		while (validInput) {
			try {
				Patron p = new Patron();
				System.out.print("\n---Adding New Patron---\n");

				System.out.print("\nSYSTEM: A patron's ID is assigned automatically by the system\n");
				p.assignID();

				System.out.print("\nEnter patron's first name: ");
				p.setFname(scanner.nextLine());

				System.out.print("Enter patron's last name: ");
				p.setLname(scanner.nextLine());

				boolean usernameAvailable = false;
				boolean weakUsername = false;
				String pUsername = null;

				while(!weakUsername && !usernameAvailable) {
					System.out.print("Enter patron's username: ");
					pUsername = scanner.nextLine().trim();

					if (!checkUsernameStrength(pUsername)) {
						System.out.print("\nSYSTEM: Weak username! Try again.\n");
						continue; // Loops again if username is weak
					} else if (!checkUsernameAvailability(pUsername, User.UsernamesFile)) {
						System.out.print("\nSYSTEM: Username in use! Try again.\n");
						continue; // Loops again if username is taken
					} else {
						// If both conditions pass, set the username
						p.setUsername(pUsername);
						weakUsername = true;
						usernameAvailable = true;
					}
				}

				String generatedPassword = p.getPassword().generatePassword(8); // Generate the password
				p.getPassword().setPassword(generatedPassword); // Set the generated password
				p.getPassword().setOTP(true);

				System.out.print("\nSYSTEM: Patron registration is successful! Here are the details: \n");

				System.out.print("\n---Patron Details---\n");
				System.out.println(p.toString());

				System.out.printf("\nSYSTEM: Every time a new patron is added to the LMS, they are give a default password, this one is: %s\n", generatedPassword);

				patrons.append(p);
				p.getPassword().PasswordDetailsHashToFile(pUsername, generatedPassword);
				p.writeUsernameToFile(p.getUsername(), User.UsernamesFile);
				p.toXML();
				WritePatronData(filePath, patrons);

				if (!validInput) {
					continueAdding = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else {
					continueAdding = getYesNoResponse("\nWould you like to add another patron? (yes/no): ");
				}

				break;

			} catch (InputMismatchException e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.println("\n");
			}
		}
	}

	// Views member details - Jada
	public void viewPatron(LinkedList<Patron> patrons) {
		Scanner scanner = new Scanner(System.in);

		boolean continueViewing = true;
		boolean validInput = false;

		while (!validInput) {
			try {
				System.out.print("\nSYSTEM: The first patron's ISBN start with 1000" +
				                 " and the following ones increment by 4\n");
				System.out.print("\nEnter patron's Library Number to view: ");
				String viewID = scanner.nextLine();
				boolean found = false;

				for(Patron patron : patrons) {
					if (patron.getUserID().equals(viewID)) {
						System.out.println(patron.toString());
						validInput = true; // Set to true if all inputs are valid
						found = true;
						break;
					}
				}

				if (!found) {
					System.out.println("SYSTEM: No patron found with Library Number: " + viewID);
					continueViewing = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else {
					continueViewing = getYesNoResponse("\nWould you like to view another patron? (yes/no): ");
				}

			} catch (Exception e) {
				System.err.println("\nERROR: " + e.getMessage());
				e.printStackTrace();
				System.out.print("\n");
			}
		}
	}

	// Updates member details - Jada
	public void updatePatron(LinkedList<Patron> patrons, String filePath) {
		boolean continueUpdating = true;

		while (continueUpdating) {
			try {
				System.out.print("\n---Updating Patron---\n");
				System.out.print("\nEnter patron's Library Number to update: ");
				String updateID = scanner.nextLine();

				boolean found = false;
				for (Patron patron : patrons) {
					if (patron.getUserID().equals(updateID)) {
						found = true;
						System.out.print("\n---Current Patron Details---\n");
						System.out.println(patron.toString());
						System.out.println("\nSYSTEM: Note that you cannot update a patron's Library Number or their booklist.\n");

						System.out.println("SYSTEM: Which information about this patron would you like to update?\n");
						System.out.println("[1] First Name");
						System.out.println("[2] Last Name");
						System.out.println("[3] Username");
						System.out.print("Enter your choice: ");

						int choice = Main.getValidIntegerInput(1, 3);

						switch (choice) {
						case 1:
							System.out.print("\nUpdate patron's first name: ");
							patron.setFname(scanner.nextLine());
							break;
						case 2:
							System.out.print("\nUpdate patron's last name: ");
							patron.setLname(scanner.nextLine());
							break;
						case 3: {
							String pUsername = null;
							while (true) {
								System.out.print("Enter patron's username: ");
								pUsername = scanner.nextLine().trim();

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
									patron.setUsername(pUsername);
									break;
								}
							}
						}
						break;
						
						default:
							System.out.println("SYSTEM: Invalid choice.");
							continue; // Go back to the menu
						}

						System.out.print("\nSYSTEM: Patron details updated! Here are the results!\n");
						System.out.print("\n---Updated Patron Details---\n");
						System.out.println(patron.toString());
						WritePatronData(filePath, patrons);

						break; // break out of the for loop after processing a valid update
					}
				}

				if (!found) {
					System.out.println("SYSTEM: No patron found with Library Number: " + updateID);
					continueUpdating = getYesNoResponse("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Yes/No)? ");
				} else {
					continueUpdating = getYesNoResponse("\nWould you like to update another patron? (yes/no): ");
				}

			} catch (Exception e) {
				System.err.println("\nAn error occurred while updating patron.\n" + e.getMessage());
				e.printStackTrace();
				System.out.println("\n");
			}
		}
	}

	// Remove member from the library system - Jada
	public void removePatron(LinkedList<Patron> patrons, Queue<Patron> paTqueue, String filePath) {
		Scanner scanner = new Scanner(System.in); // Initialize scanner here
		boolean continueRemoving = true;

		while (continueRemoving) {
			try {
				System.out.print("\n---Removing Patron---\n");
				System.out.print("\nEnter patron's Library Number to remove: ");
				String deleteID = scanner.nextLine();

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
					boolean patronInQueue = false;
					QueueIterator<Patron> iterator = paTqueue.iterator();

					while (iterator.hasNext()) {
						Patron queuePatron = iterator.next();
						if (queuePatron.getUserID().equals(patronToRemove.getUserID())) {
							patronInQueue = true;
							break;
						}
					}

					if (patronInQueue) {
						System.out.println("\nSYSTEM: Patron with library number: " + deleteID + " is in a queue. Please have them return any checked out books before removing them.");
					} else {
						patrons.Remove(patronToRemove);
						WritePatronData(filePath, patrons);
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

	//-------------------------Files Section---------------------------------

	// Writes book to xml file - Chev
	public static void WriteBookData(String fileName, LinkedList<Book> parameterlist) {
		// Write the CustomLinkedList to an XML file
		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write("<Books>\n");
			parameterlist.forEach(book -> {
				try {
					writer.write(book.toXML());
					writer.write("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			writer.write("</Books>");
			System.out.println("\nSYSTEM: Book Data written to file successfully.");
		} catch (IOException e) {
			System.err.println("\nAn error occurred while saving the patron info.");
			e.printStackTrace();
			System.out.print("\n");
		}
	}

	// Read and Retrieve All Book Data from the xml file - Chev
	public static void loadBookData(String filePath, LinkedList<Book> parameterlist) {
		try {
			// Load the XML file
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// Get all Book elements
			NodeList bookList = doc.getElementsByTagName("Book");

			for (int i = 0; i < bookList.getLength(); i++) {
				org.w3c.dom.Node bookNode = bookList.item(i);
				if (bookNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element bookElement = (Element) bookNode;

					// Extract Book data
					String ISBN = getTagValue("ISBN", bookElement);
					String title = getTagValue("Title", bookElement);
					String authorFName = getTagValue("AuthorFirstName", bookElement);
					String authorLName = getTagValue("AuthorLastName", bookElement);
					boolean status = Boolean.parseBoolean(getTagValue("AvailabilityStatus", bookElement));
				}
			}

			System.out.println("SYSTEM: Book XML file loaded successfully.\n");
		} catch (Exception e) {
			System.err.println("\nAn error occurred while loading the Book XML file.\n" + e.getMessage());
			e.printStackTrace();
			System.out.print("\n");
		}
	}

	// Methods used to append the child elements of Books - Jada
	public static void appendChildElement(Document doc, Element parent, String tagName, String textContent) {
		Element element = doc.createElement(tagName);
		element.appendChild(doc.createTextNode(textContent));
		parent.appendChild(element);
	}

	// Writes all patron information to xml file - Chev
	public static void WritePatronData(String fileName, LinkedList<Patron> parameterlist) {
		// Write the CustomLinkedList to an XML file
		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write("<Patrons>\n"); // Start the XML document with the root element "Patrons"

			parameterlist.forEach(patron -> { // Loop through each patron in the list
				try {
					writer.write(patron.toXML()); // Convert the Patron object to XML and write it to the file
					writer.write("\n"); // Add a new line for better readability
					writer.flush(); // Flush after writing each patron
				} catch (IOException e) {
					e.printStackTrace(); // Print the stack trace if an error occurs while writing
				}
			});

			writer.write("</Patrons>"); // Close the root element "Patrons"
			writer.flush(); // Flush after writing each patron
			System.out.println("SYSTEM: Patron Data written to file successfully."); // Inform the user that the operation was successful
		} catch (IOException e) {
			System.err.println("\nERROR: An error occurred while saving the patron info." + e.getMessage()); // Print an error message if an exception occurs
			e.printStackTrace(); // Print the stack trace
			System.out.println("\n"); // Add a new line for better formatting
		}
	}

	// Read and Retrieve All Patron Data from the xml file - Chev
	public static void loadPatronData(String filePath, LinkedList<Patron> patrons) {
		try {
			// Load the XML file
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile); // Parse the XML file into a Document object
			doc.getDocumentElement().normalize(); // Normalize the document to remove empty text nodes

			// Get all Patron elements
			NodeList patronList = doc.getElementsByTagName("Patron"); // Get a list of all "Patron" elements
			
			for (int i = 0; i < patronList.getLength(); i++) { // Loop through each Patron element
				org.w3c.dom.Node patronNode = patronList.item(i);
				if (patronNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) { // Check if the node is an element
					Element patronElement = (Element) patronNode;

					// Extract Patron attributes
					String userID = getTagValue("LibraryNumber", patronElement); // Get the LibraryNumber
					String fName = getTagValue("FirstName", patronElement); // Get the FirstName
					String lName = getTagValue("LastName", patronElement); // Get the LastName
					String username = getTagValue("Username", patronElement); // Get the Username
					String password = getTagValue("Password", patronElement); // Get the Password

					// Extract Books
					LinkedList<Book> books = new LinkedList<>(); // Create a list to store books
					NodeList bookList = patronElement.getElementsByTagName("Book"); // Get a list of all "Book" elements

					for (int j = 0; j < bookList.getLength(); j++) { // Loop through each Book element
						Element bookElement = (Element) bookList.item(j);
						String ISBN = getTagValue("ISBN", bookElement); // Get the ISBN
						String title = getTagValue("Title", bookElement); // Get the Title
						String authorFName = getTagValue("AuthorFirstName", bookElement); // Get the AuthorFirstName
						String authorLName = getTagValue("AuthorLastName", bookElement); // Get the AuthorLastName
						boolean status = Boolean.parseBoolean(getTagValue("AvailabilityStatus", bookElement)); // Get the AvailabilityStatus

						// Create a Book object and add it to the LinkedList
						books.append(new Book(title, authorFName, authorLName, status)); // Add the book to the list
					}
				}
			}
			
			System.out.println("SYSTEM: Patron XML file loaded successfully."); // Inform the user that the file was loaded successfully
			
		} catch (Exception e) {
			System.err.println("\nAn error occurred while loading the Patron XML file.\n" + e.getMessage()); // Print an error message if an exception occurs
			e.printStackTrace(); // Print the stack trace
			System.out.println("\n"); // Add a new line for better formatting
		}

		//return patrons; // This line seems to be commented out, so it's not doing anything.
	}

	// Method to extract tag values - Jada
	private static String getTagValue(String tag, Element element) {
		org.w3c.dom.NodeList nodeList = element.getElementsByTagName(tag); // Get a list of nodes with the given tag

		if (nodeList != null && nodeList.getLength() > 0) { // Check if the list is not null and not empty
			org.w3c.dom.Node node = nodeList.item(0); // Get the first node in the list

			if (node != null && node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) { // Check if the node is an element
				Element childElement = (Element) node;
				org.w3c.dom.NodeList textNodeList = childElement.getChildNodes(); // Get a list of child nodes

				if (textNodeList != null && textNodeList.getLength() > 0) { // Check if the list is not null and not empty
					org.w3c.dom.Node textNode = textNodeList.item(0); // Get the first child node
					if (textNode != null && textNode.getNodeType() == org.w3c.dom.Node.TEXT_NODE) { // Check if the node is a text node
						String value = textNode.getNodeValue(); // Get the text value
						return value; // Return the value
					}
				}
			}
		}
		return ""; // Return an empty string if the tag is not found or the value is empty
	}
}
