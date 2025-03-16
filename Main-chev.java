package pack;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Main {
	
	
	
	public static void main(String[] args) 
	{
		String filePatron = "PatronRecords.xml";
		LinkedList<Book> bookie = new LinkedList<Book>();
		Book d1 = new Book("123","Cinderella","John","Brown",false);
		Book d2 = new Book(d1);
		Book d3 = new Book(d1);
		Book d4 = new Book(d1);
		Book d5 = new Book(d1);
		
		bookie.append(d1);
		bookie.append(d2);
		bookie.append(d3);
		bookie.append(d4);
		bookie.append(d5);
		Password pass = new Password("Comedypoo","byebye");
		pass.setPassword(hashString());
		
		LinkedList<Patron> pat = new LinkedList<Patron>();
		Patron chev = new Patron("2303617","Shenna","Miracles","what",pass, "keyrus1",bookie);
		Patron jon = new Patron(chev);
		Patron jada = new Patron(chev);
		Patron josan = new Patron(chev);
				
		System.out.print("Patron List: ");
		pat.append(chev);
		pat.append(jon);
		pat.append(jada);
		pat.append(josan);
		pat.Display();
		
		System.out.println("Patron Chev Info: ");
		chev.Display();
		
		
		File file = new File("PatronRecords.xml");
		if(file.exists()) 
		{ 
			AppendToPatronData("PatronRecords.xml", pat);
		}
		else
		{
			WritePatronData("PatronRecords.xml",pat);
		}
		
    
    
		LinkedList<Patron> patrons = loadPatronData("PatronRecords.xml");

        // Display the loaded Patron objects
        System.out.println("Loaded Patrons:");
        patrons.forEach(Patron::Display);
        
        
    }
        
	
	//Methods
	
	//Writes all patron information to xml file - Chev
		private static void WritePatronData(String fileName, LinkedList<Patron> parameterlist)
		{
			// Write the CustomLinkedList to an XML file
	        try (FileWriter writer = new FileWriter(fileName)) {
	            writer.write("<Patrons>\n");
	            parameterlist.forEach(patron -> {
	                try {
	                    writer.write(patron.toXML());
	                    writer.write("\n");
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            });
	            writer.write("</Patrons>");
	            System.out.println("Data written to file successfully.");
	        } catch (IOException e) {
	            System.out.println("An error occurred while saving the patron info.");
	            e.printStackTrace();
	        }
		}
		
		//If Patron XML file exists this method will append new data
		
		private static void AppendToPatronData(String fileName, LinkedList<Patron> parameterList) {
	        try {
	            // Parse the existing XML file
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(new File(fileName));

	            // Get the root element
	            Element root = doc.getDocumentElement();

	            // Append new Patron elements
	            parameterList.forEach(patron -> {
	                // Create a new <Patron> element
	                Element patronElement = doc.createElement("Patron");

	                // Add child elements for Patron attributes
	                appendChildElement(doc, patronElement, "userID", patron.getUserID());
	                appendChildElement(doc, patronElement, "fName", patron.getfName());
	                appendChildElement(doc, patronElement, "lName", patron.getlName());
	                appendChildElement(doc, patronElement, "Username1", patron.getUsername());
	                appendChildElement(doc, patronElement, "Username2", patron.getPassword().getUsername());
	                appendChildElement(doc, patronElement, "password", patron.getPassword().getPassword());
	                appendChildElement(doc, patronElement, "lNumb", patron.getlNumb());

	                // Create a <Collection> element for books
	                Element collectionElement = doc.createElement("Collection");
	                
	                LinkedList<Book> books = patron.getBooks();
	                books.forEach(book -> {
	                    // Create a <Book> element
	                    Element bookElement = doc.createElement("Book");

	                    // Add child elements for Book attributes
	                    appendChildElement(doc, bookElement, "ISBN", book.getISBN());
	                    appendChildElement(doc, bookElement, "title", book.getTitle());
	                    appendChildElement(doc, bookElement, "authorFName", book.getAuthorFName());
	                    appendChildElement(doc, bookElement, "authorLName", book.getAuthorLName());
	                    appendChildElement(doc, bookElement, "status", String.valueOf(book.getStatus()));

	                    // Append the <Book> element to the <Collection>
	                    collectionElement.appendChild(bookElement);
	                });

	                // Append the <Collection> element to the <Patron>
	                patronElement.appendChild(collectionElement);

	                // Append the <Patron> element to the root
	                root.appendChild(patronElement);
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
		
		//Methods used to append the child elements of Patron - Chev
		private static void appendChildElement(Document doc, Element parent, String tagName, String textContent) {
	        Element child = doc.createElement(tagName);
	        child.setTextContent(textContent);
	        parent.appendChild(child);
	    }
		
		//Read and Retrieve All Patron Data from the xml file - Chev
		
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
	                    String userID = patronElement.getElementsByTagName("userID").item(0).getTextContent();
	                    String fName = patronElement.getElementsByTagName("fName").item(0).getTextContent();
	                    String lName = patronElement.getElementsByTagName("lName").item(0).getTextContent();
	                    String username = patronElement.getElementsByTagName("Username1").item(0).getTextContent();
	                    String passwordUsername = patronElement.getElementsByTagName("Username2").item(0).getTextContent();
	                    String password = patronElement.getElementsByTagName("password").item(0).getTextContent();
	                    String lNumb = patronElement.getElementsByTagName("lNumb").item(0).getTextContent();

	                    // Create a Password object
	                    Password pass = new Password(passwordUsername, password);

	                    // Extract Books
	                    LinkedList<Book> books = new LinkedList<Book>();
	                    NodeList bookList = patronElement.getElementsByTagName("Book");
	                    for (int j = 0; j < bookList.getLength(); j++) {
	                        Element bookElement = (Element) bookList.item(j);
	                        String ISBN = bookElement.getElementsByTagName("ISBN").item(0).getTextContent();
	                        String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
	                        String authorFName = bookElement.getElementsByTagName("authorFName").item(0).getTextContent();
	                        String authorLName = bookElement.getElementsByTagName("authorLName").item(0).getTextContent();
	                        boolean status = Boolean.parseBoolean(bookElement.getElementsByTagName("status").item(0).getTextContent());

	                        // Create a Book object and add it to the LinkedList
	                        books.append(new Book(ISBN, title, authorFName, authorLName, status));
	                    }

	                    // Create a Patron object and add it to the LinkedList
	                    patrons.append(new Patron(userID, fName, lName, username, pass, lNumb, books));
	                }
	            }

	            System.out.println("XML file loaded successfully.");
	        } catch (Exception e) {
	            System.out.println("An error occurred while loading the XML file.");
	            e.printStackTrace();
	        }

	        return patrons;
	    }
		
		private static LinkedList<Patron> SearchByPatronID (String filePath,String userIDToFind)
		{
			LinkedList<Patron> patrons = new LinkedList<Patron>();
			File file = new File(filePath);
			if (file.exists())
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				try
				{
					DocumentBuilder builder = factory.newDocumentBuilder();
					
					Document document = builder.parse(file);
					
					document.getDocumentElement().normalize();
					
					NodeList node = document.get
				}
				
			}
		}
		/*
		 Find Patron ID in XML
		 Notify that Patron ID = ... was found
		 Use Stacks to UNDO books

		
		*/
		
		
	}

