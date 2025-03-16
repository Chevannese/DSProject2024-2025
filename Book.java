//package pack;

/*
Name: Chevannese Ellis
ID: 2301109
Date: February 21, 2025
Book.java
*/

public class Book {
    // Attributes
    private String ISBN;
    private String title;
    private String authorFName;
    private String authorLName;
    private boolean status;

    // Default Constructor
    public Book() {
    	ISBN = "";
        title = "";
        authorFName = "";
        authorLName = "";
        status = false;
    }

    // Primary Constructor
    public Book(String ISBN,String title, String authorFName, String authorLName, boolean status) {
    	this.ISBN = ISBN;
    	this.title = title;
        this.authorFName = authorFName;
        this.authorLName = authorLName;
        this.status = status;
    }

    // Copy Constructor
    public Book(Book bo) {
        this.title = bo.title;
        this.authorFName = bo.authorFName;
        this.authorLName = bo.authorLName;
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

    public String getAuthorFName() {
        return authorFName;
    }

    public void setAuthorFname(String authorFName) {
        this.authorFName = authorFName;
    }

    public String getAuthorLName() {
        return authorLName;
    }

    public void setAuthorLname(String authorLName) {
        this.authorLName = authorLName;
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
    public void Display() {
    	System.out.println("Book ISBN: " + ISBN);
	System.out.println("Book Title: " + title);
	System.out.println("Book Author: " + authorFName + " " + authorLName;
	System.out.println("Book Status: " + (status ? "SYSTEM: Available" : "SYSTEM: Checked Out"));
    }

	//XML Method
    public String toXML() {
    	return "    <Book>\n" +
    	           "      <ISBN>" + ISBN + "</ISBN>\n" +
    	           "      <title>" + title + "</title>\n" +
    	           "      <authorFName>" + authorFName + "</authorFName>\n" +
    	           "      <authorLName>" + authorLName + "</authorLName>\n" +
    	           "      <status>" + status + "</status>\n" +
    	           "    </Book>";
    	}
	//toString Method
	@Override
	public String toString() {
		return "Book [ISBN=" + ISBN + ", title=" + title + ", authorFName=" + authorFName + ", authorLName="
				+ authorLName + ", status=" + status + "]";
	}
    
    
    
    
}
