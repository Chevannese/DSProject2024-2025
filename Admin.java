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

}
