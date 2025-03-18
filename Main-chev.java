package pack;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.*;
import java.util.*;
import javax.swing.*;
import static javax.swing.GroupLayout.*;
import static javax.swing.GroupLayout.Alignment.*;
import java.awt.*;
import java.awt.event.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class Main {
	private static Scanner input = new Scanner(System.in);
	private static LinkedList<Book> books;
	private static LinkedList<Patron> patrons;
	private static LinkedList<Admin> admins;
	private static JFrame window;
	
	public static <T extends Serializable> void save(String file, T data) throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
		output.writeObject(data);
	}

	public static <T extends Serializable> T read(String file) throws IOException, ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
		return (T) input.readObject();
	}

	
	
	public static void main(String[] args) 
	{
		 Password pass = new Password("Comedypoo","byebye");
			LinkedList<Patron> patrons = new LinkedList<Patron>();
			LinkedList<Patron> patrons2 = null;
			LinkedList<Book> books1 = new LinkedList<Book>();
			LinkedList<Book> books2 = new LinkedList<Book>();
			LinkedList<Book> books3 = null;
			books1.append(new Book("123","Cinderella","John","Brown",false));
			books2.append(new Book("456789", "uheruhq", "ierioeaj", "ojeorea", true));
			books2.append(new Book("yegra", "Amulet", "ijeire", "joe", false));
			
			patrons.append(new Patron("1","Shenna","Miracles","what",pass, "keyrus1",books1));
			patrons.append(new Patron("2","Adam","Rose","AR",pass, "captionbakery",books2));
			patrons.append(new Patron("3","Steve","Watts","Stevie",pass, "keyrus1",books1));
			patrons.append(new Patron("4","Shenna","Miracles","what",pass, "keyrus1",books1));

			try {
				save("patrons.txt", patrons);
				patrons2 = read("patrons.txt");
			}
			catch (IOException e) {
				System.err.println("io:" + e.getMessage());
				patrons2 = new LinkedList<Patron>();
			}
			catch (ClassNotFoundException e) {
				System.err.println("Class not found");
				patrons2 = new LinkedList<Patron>();
			}
			
			for (Patron run: patrons2) 
			{
				run.Display();
		    }

			Book book1 = new Book("111-222-333", "The Great Gatsby", "F. Scott", "Fitzgerald", false);
			Book book2 = new Book("444-555-666", "To Kill a Mockingbird", "Harper", "Lee", false);
			Book book3 = new Book("777-888-999", "1984", "George", "Orwell", false);
			Book book4 = new Book("123-456-789", "Pride and Prejudice", "Jane", "Austen", false);
			Book book5 = new Book("987-654-321", "Moby-Dick", "Herman", "Melville", false);
			
			Stack<Book> bookstore = new Stack<Book>();
			
			bookstore.push(book1);
			bookstore.push(book2);
			bookstore.push(book3);
			bookstore.push(book4);
			bookstore.push(book5);
			
			/*System.out.println("Original Books");
			for (Book books: bookstore)
			{
				books.Display();
			}
			
			bookstore.undo();
			
			System.out.println("Books after pop");
			for (Book books: bookstore)
			{
				books.Display();
			}*/
			
			BST<Book> tree = new BST<Book>();
			tree.insert(book1, book1);
	        tree.insert(book2, book2);
	        tree.insert(book3, book3);
	        tree.insert(book4, book4);

	        System.out.println("Books in alphabetical order:");
	        tree.displayTree();;
			
			
		}
	
	public static <T extends Serializable> void save(String file, T data) throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
		output.writeObject(data);
	}

	public static <T extends Serializable> T read1(String file) throws IOException, ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
		return (T) input.readObject();
	}
		
	}

