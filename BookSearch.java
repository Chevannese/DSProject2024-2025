//Chevannese Ellis, 2301109, March 18, 2025, BookSearch.java or BinarySearchTree

import java.util.Iterator;

public class BookSearch {
	private TreeNode root;
	private int option; // Determines sorting order

	// Initializes the BookSearch tree with a specified sorting option.
	public BookSearch(int option) {
		root = null;
		this.option = option; // Set the user-chosen sorting option
	}
	
	public BookSearch(Iterable<Book> items, int option) {
    	root = null;
        this.option = option;
    	for (Book book: items) {
    		this.insert(book);
    	}
    }

	// Method to insert a Book into the tree.
	public void insert(Book book) {
		root = insertRecursive(root, book);
	}

	// Recursive helper method to insert a Book into the tree.
	private TreeNode insertRecursive(TreeNode root, Book book) {
		if (root == null) {
			// If the current node is null, create a new TreeNode and return it.
			return new TreeNode(book, option);
		}

		// Compare the new Book with the current node's Book based on the sorting option.
		int compareResult = compare(root.book, book, option);

		if (compareResult < 0) {
			// If the new Book is less than the current node's Book, insert into the left subtree.
			root.left = insertRecursive(root.left, book);
		} else if (compareResult > 0) {
			// If the new Book is greater than the current node's Book, insert into the right subtree.
			root.right = insertRecursive(root.right, book);
		} else {
			// If the Books are equal, replace the existing Book with the new Book.
			root.book = book;
		}

		return root; // Return the updated root.
	}

	// Method to compare two Books based on the specified sorting option.
	private int compare(Book book1, Book book2, int option) {
		try {
			switch (option) {
			case 1: // Sort by ISBN
				return book1.getISBN().compareTo(book2.getISBN());
			case 2: // Sort by Title
				return book1.getTitle().compareTo(book2.getTitle());
			case 3: // Sort by Author First Name
				return book1.getAuthorFname().compareTo(book2.getAuthorFname());
			case 4: // Sort by Author Last Name
				return book1.getAuthorLname().compareTo(book2.getAuthorLname());
			default:
				return book1.getISBN().compareTo(book2.getISBN()); // Default to ISBN sorting
			}
		} catch (Exception e) {
			System.err.println("\nIO Error: " + e.getMessage());
			return 0;
		}
	}

	// Method to search for a Book based on a search term and sorting option.
	public Book search(String searchValue, int option) {
		return searchRecursive(root, searchValue, option);
	}

	// Recursive method to search for a Book.
	private Book searchRecursive(TreeNode root, String searchTerm, int option) {
		if (root == null)
			return null; // Book not found.

		// Determine which attribute of the Book to compare with the search term.
		int compareResult;
		switch (option) {
		case 1: // Compare with ISBN
			compareResult = searchTerm.compareToIgnoreCase(root.book.getISBN());
			break;
		case 2: // Compare with Title
			compareResult = searchTerm.compareToIgnoreCase(root.book.getTitle());
			break;
		case 3: // Compare with Author First Name
			compareResult = searchTerm.compareToIgnoreCase(root.book.getAuthorFname());
			break;
		case 4: // Compare with Author Last Name
			compareResult = searchTerm.compareToIgnoreCase(root.book.getAuthorLname());
			break;
		default: // Activated when the value entered is not equal to above
			compareResult = searchTerm.compareToIgnoreCase(root.book.getISBN());
		}

		if (compareResult > 0) {
			// If the search term is greater than the current node's Book, search the left subtree.
			return searchRecursive(root.left, searchTerm, option);
		} else if (compareResult < 0) {
			// If the search term is less than the current node's Book, search the right subtree.
			return searchRecursive(root.right, searchTerm, option);
		} else {
			return root.book; // Found the book.
		}
	}

	// Partial Search that uses the first few characters of the term
	public void prefixSearch(String prefix, int option) {

		LinkedList<Book> results = prefixSearchRec(root, prefix, option, new LinkedList<>());

		if (results.isEmpty()) {
			System.out.println("\nSYSTEM: No matching books found.\n");
		} else {
			System.out.println("\nSYSTEM: Matching Books: ");
			for (Book book : results) {
				System.out.println(book);
			}
		}

	}

	// Recursive search that uses the first few characters of the term
	private LinkedList<Book> prefixSearchRec(TreeNode root, String prefix, int option, LinkedList<Book> results) {
		// Recursive function to search for books with a given prefix in a binary tree.
		// root: The current node being examined.
		// prefix: The string to search for as a prefix.
		// option: Determines which book field to search (ISBN, title, first name, last name).
		// results: A linked list to store the matching books.

		if (root == null) // Base case: If the current node is null, we've reached the end of a branch.
			return results; // Return the current results (which may be empty).

		// Recursively search the left subtree.
		prefixSearchRec(root.left, prefix, option, results);

		String currentValue; // Store the value we're comparing the prefix against.

		// Determine which field of the book to search based on the 'option' parameter.
		switch (option) {
		case 1:
			currentValue = root.book.getISBN(); // Search by ISBN.
			break;
		case 2:
			currentValue = root.book.getTitle(); // Search by title.
			break;
		case 3:
			currentValue = root.book.getAuthorFname(); // Search by author's first name.
			break;
		case 4:
			currentValue = root.book.getAuthorLname(); // Search by author's last name.
			break;
		default:
			System.out.println("\nSYSTEM: Invalid option\n"); // If an invalid option is provided, print an error message.
			return results; // Return the results (without adding anything).
		}

		// Check if the current book's selected field contains the prefix.
		if (currentValue != null && currentValue.toLowerCase().contains(prefix.toLowerCase())) {
			results.append(root.book); // If it does, add the book to the results list.
		}

		// Recursively search the right subtree.
		prefixSearchRec(root.right, prefix, option, results);

		return results; // Return the updated results list.
	}

	// Public method to display all Books in the tree.
	public void displayTree() {
		displayHelper(root);
	}

	// Recursive method to display Books in the tree.
	private void displayHelper(TreeNode root) {
		if (root != null) {
			// Display right subtree (for ascending order).
			displayHelper(root.right);

			System.out.println(root.book); // Display the current Book.

			// Display left subtree (for ascending order).
			displayHelper(root.left);
		}
	}
}

// TreeNode class: Represents a node in the binary search tree.
class TreeNode {
	public Book book;
	public TreeNode left, right;
	public int option;

	// Constructor: Initializes a TreeNode with a Book and sorting option.
	public TreeNode(Book book, int option) {
		this.book = book;
		this.option = option;
		this.left = null;
		this.right = null;
	}
}
