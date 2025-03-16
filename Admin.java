package pack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Jada Johnson (2209658), 2/14/25, Admin.java

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Book> books;
	private LinkedList<Patron> patrons;

	//Default Constructor
	public Admin() 
	{
		books = new LinkedList<>();
		patrons = new LinkedList<>();
	}
	
	//Primary Constructor
	public Admin(LinkedList<Book> books, LinkedList<Patron> patrons)
	{
		this.books = books;
		this.patrons = patrons;
	}
	
	//Copy Constructor
	public Admin(Admin ad)
	{
		this.books = ad.books;
		this.patrons = ad.patrons;
	}
	
	

	// Adds Book into the system
	public void addBook() {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;

		while (!validInput) {
			try {
				LinkedList <Book> bookList = new LinkedList<Book>();
				Book addBook = new Book();
				String isbn, title, authorFname, authorLname;
				boolean status;

				System.out.print("\nEnter book's ISBN: ");
				isbn = scanner.nextLine();
				addBook.setISBN(isbn);

				System.out.print("\nEnter book's title: ");
				title = scanner.nextLine();
				addBook.setTitle(title);

				System.out.print("\nEnter book's author first name: ");
				authorFname = scanner.nextLine();
				addBook.setAuthorFname(authorFname);

				System.out.print("\nEnter book's author last name: ");
				authorLname = scanner.nextLine();
				addBook.setAuthorLname(authorLname);

				System.out.print("\nEnter book's status (put \"1\" if book is available, \"0\" if not): ");
				status = scanner.nextInt() == 1;
				addBook.setStatus(status);
				scanner.nextLine(); // Clear the newline character from the input buffer

				System.out.println("---Book Details---");
				System.out.println("Book ISBN: " + addBook.getISBN());
				System.out.println("Book Title: " + addBook.getTitle());
				System.out.println("Book Author: " + addBook.getAuthorFName() + " " + addBook.getAuthorLName());
				System.out.println("Book Status: " + (addBook.getStatus() ? "SYSTEM: Available" : "SYSTEM: Checked Out"));
				System.out.println();

				books.append(addBook); // Add the book to the list
				validInput = true; // Set to true if all inputs are valid
				
				//Save to xml file
				
				
				File file = new File("BookRecords.xml");
				if(file.exists()) 
				{ 
					AppendToBookData("BookRecords.xml", books);
				}
				else
				{
					WriteBookData("BookRecords.xml",books);
				}
				
				
			} catch (Exception e) {
				System.out.print("SYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
				String response = scanner.nextLine();

				if (!response.equalsIgnoreCase("Y")) {
					break; // Exit the loop if the user does not want to try again
				}

				System.out.println();
			}
		}
	}
	
	//Writes book to xml file - chev
	public void WriteBookData(String fileName, LinkedList<Book> parameterlist)
	{
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
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the patron info.");
            e.printStackTrace();
        }
	}
	
	//If Book XML file exists this method will append new data
	private static void AppendToBookData(String fileName, LinkedList<Book> parameterList) {
        try {
            // Parse the existing XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));

            // Get the root element
            Element root = doc.getDocumentElement();

            
                parameterList.forEach(book -> {
                    // Create a <Book> element
                    Element bookElement = doc.createElement("Book");

                    // Add child elements for Book attributes
                    appendChildElement(doc, bookElement, "ISBN", book.getISBN());
                    appendChildElement(doc, bookElement, "title", book.getTitle());
                    appendChildElement(doc, bookElement, "authorFName", book.getAuthorFName());
                    appendChildElement(doc, bookElement, "authorLName", book.getAuthorLName());
                    appendChildElement(doc, bookElement, "status", String.valueOf(book.getStatus()));

                    // Append the <Book> element to the <Collection>
                    root.appendChild(bookElement);
                });
           
            

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            System.out.println("Data appended to file successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while appending the patron info.");
            e.printStackTrace();
        }
    }
	
	//Methods used to append the child elements of Books - Chev
	private static void appendChildElement(Document doc, Element parent, String tagName, String textContent) {
        Element child = doc.createElement(tagName);
        child.setTextContent(textContent);
        parent.appendChild(child);
    }

	// Updates Book in the system
	public void updateBook() {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;

		while (!validInput) {
			try {
				String isbn, title, authorLname, authorFname;
				boolean status;

				System.out.println("\nSYSTEM: Note that you CANNOT update a book's ISBN, because that new ISBN might be already in the system.");
				System.out.print("\nEnter the book's ISBN to update: ");
				isbn = scanner.nextLine();

				for (Book book : books) {
					if (book.getISBN().equals(isbn)) {
						System.out.print("\nUpdate book's title: ");
						title = scanner.nextLine();
						book.setTitle(title);

						System.out.print("\nUpdate book's author first name: ");
						authorFname = scanner.nextLine();
						book.setAuthorFname(authorFname);

						System.out.print("\nUpdate book's author last name: ");
						authorLname = scanner.nextLine();
						book.setAuthorLname(authorLname);

						System.out.print("\nUpdate book's status (put \"1\" if book is available, \"0\" if not): ");
						status = scanner.nextInt() == 1;
						book.setStatus(status);
						scanner.nextLine(); // Clear the newline character from the input buffer

						System.out.println("---New Book Details---");
						System.out.println("Book ISBN: " + book.getISBN());
						System.out.println("Book Title: " + book.getTitle());
						System.out.println("Book Author: " + book.getAuthorFname() + book.getAuthorLname());
						System.out.println("Book Status: " + (book.getStatus() ? "SYSTEM: Available" : "SYSTEM: Checked Out"));
						System.out.println();

						validInput = true; // Set to true if all inputs are valid
						break;
					}
				}

				if (!validInput) {
					System.out.println("SYSTEM: No book found with ISBN: " + isbn);
				}

			} catch (Exception e) {
				System.out.print("SYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
				String response = scanner.nextLine();

				if (!response.equalsIgnoreCase("Y")) {
					break; // Exit the loop if the user does not want to try again
				}

				System.out.println();
			}
		}
	}

	// Views Book on the system
	public void viewBook() {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;

		while (!validInput) {
			try {
				String isbn;

				System.out.print("\nEnter book's ISBN to view: ");
				isbn = scanner.nextLine();

				for (Book book : books) {
					if (book.getISBN().equals(isbn)) {
						System.out.println("---Book Details---");
						System.out.println("Book ISBN: " + book.getISBN());
						System.out.println("Book Title: " + book.getTitle());
						System.out.println("Book Author: " + book.getAuthorFName() + " " + book.getAuthorLName());
						System.out.println("Book Status: " + (book.getStatus() ? "SYSTEM: Available" : "SYSTEM: Checked Out"));
						System.out.println();
						validInput = true; // Set to true if all inputs are valid
						break;
					}
				}

				if (!validInput) {
					System.out.println("SYSTEM: No book found with ISBN: " + isbn);
				}

			} catch (Exception e) {
				System.out.print("SYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
				String response = scanner.nextLine();

				if (!response.equalsIgnoreCase("Y")) {
					break; // Exit the loop if the user does not want to try again
				}

				System.out.println();
			}
		}
	}

	// Removes Book from the system
	public void removeBook() {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;
		LinkedList<Book> removeBook = new LinkedList<Book>();
		while (!validInput) {
			try {
				String isbn;

				System.out.print("\nEnter book's ISBN to delete: ");
				isbn = scanner.nextLine();

				Book bookToRemove = null;
				removeBook.forEach(book ->{
					if (removeBook.getISBN().equals(isbn)) {
						bookToRemove = book;
						break;
					})
					
				}

				if (bookToRemove != null) {
					books.Remove(bookToRemove);
					System.out.println("SYSTEM: Book with ISBN: " + isbn + " has been removed.");
				} else {
					System.out.println("SYSTEM: No book found with ISBN: " + isbn);
				}

				validInput = true; // Set to true if all inputs are valid
			} catch (Exception e) {
				System.out.print("SYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
				String response = scanner.nextLine();

				if (!response.equalsIgnoreCase("Y")) {
					break; // Exit the loop if the user does not want to try again
				}

				System.out.println();
			}
		}
	}

	// Views member details of the library
	public void viewPatron() {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;

		while (!validInput) {
			try {
				System.out.print("\nEnter patron's ID to view: ");
				String viewID = scanner.nextLine();
				boolean found = false;

				for (Patron patron : patrons) {
					if (viewID.equals(patron.getUserID())) {
						patron.Display();
						found = true;
						break;
					}
				}

				if (!found) {
					System.out.println("SYSTEM: No patron found with ID: " + viewID);
				}

				validInput = true; // Set to true if all inputs are valid
			} catch (Exception e) {
				System.out.print("SYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
				String response = scanner.nextLine();

				if (!response.equalsIgnoreCase("Y")) {
					break; // Exit the loop if the user does not want to try again
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		Admin test = new Admin();
		test.addBook();
	}
}
