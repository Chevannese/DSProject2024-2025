//Chevannese Ellis, 2301109, March 18, 2025, BookSearch.java

//package pack;

import java.util.Iterator;

import java.io.Serializable;

public class BookSearch implements Serializable {
	private static final long serialVersionUID = 1L;
    private TreeNode root;
    private int option; // Determines sorting order

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

    public void insert(Book book) 
    {
        root = insertRecursive(root, book);
    }
    
    private TreeNode insertRecursive(TreeNode root, Book book) {
        if (root == null) {
            return new TreeNode(book, option);
        }

        int compareResult = compare(root.book, book, option);

        if (compareResult < 0) {
            root.left = insertRecursive(root.left, book);
        } else if (compareResult > 0) {
            root.right = insertRecursive(root.right, book);
        } else {
          
            root.book = book;
        }

        return root;
    }

    private int compare(Book book1, Book book2, int option) {
        try {
            switch (option) {
                case 1: // Sort by ISBN
                    return book1.getISBN().compareTo(book2.getISBN());
                case 2: // Sort by Title
                    return book1.getTitle().compareTo(book2.getTitle());
                case 3: // Sort by Author First Name
                    return book1.getAuthorFName().compareTo(book2.getAuthorFName());
                case 4: // Sort by Author Last Name
                    return book1.getAuthorLName().compareTo(book2.getAuthorLName());
                default:
                    return book1.getISBN().compareTo(book2.getISBN()); // Default to ISBN sorting
            }
        } catch (Exception e) {
            System.err.println("IO Error: " + e.getMessage());
            return 0;
        }
    }

    public Book search(String searchValue, int option) {
        return searchRecursive(root, searchValue, option);
    }

    private Book searchRecursive(TreeNode root, String searchTerm, int option) {
        if (root == null) {
            return null;
        }
        //Makes comparison based on option provided by user
        
        //Determines what specific attribute of Book to compare with term entered by User
        int compareResult;
        switch (option) {
            case 1://Compares with ISBN
                compareResult = searchTerm.compareToIgnoreCase(root.book.getISBN());
                break;
            case 2://Compares with Title 
                compareResult = searchTerm.compareToIgnoreCase(root.book.getTitle());
                break;
            case 3://Compares with AuthorFName
                compareResult = searchTerm.compareToIgnoreCase(root.book.getAuthorFName());
                break;
            case 4://Compares with AuthorLName
                compareResult = searchTerm.compareToIgnoreCase(root.book.getAuthorLName());
                break;
            default://Activated when the value entered is not equal to above
                compareResult = searchTerm.compareToIgnoreCase(root.book.getISBN());
                break;
        }
        
        //Compares with higher values of BinarySearchTree if less than zero
        if (compareResult < 0) {
        	//Calls back searchRecursive to search the sub nodes of right side of BST
            return searchRecursive(root.right, searchTerm, option);
        } else if (compareResult > 0) {
        	//Compares with lesser values of BinarySearchTree if greater than zero
        	//Calls back searchRecursive to search the sub nodes of right side of BST
            return searchRecursive(root.left, searchTerm, option);
        } else {
            return root.book; // Found the book
        }
    }

    //Displays all Books
    public void displayTree() {
        displayHelper(root);
    }
    
    //Helper Method that determines the display order of Books
   
    private void displayHelper(TreeNode root) {
        if (root != null) {
        	//Root Right on top means it will be in ascending order
            displayHelper(root.right);
       
            System.out.println(root.book);
            
          //Root Left on top means it will be in descending order
            displayHelper(root.left);
        }
    }
    
    
}

class TreeNode {
    public Book book;
    public TreeNode left, right;
    public int option;

    public TreeNode(Book book, int option) {
        this.book = book;
        this.option = option;
        this.left = null;
        this.right = null;
    }
}

