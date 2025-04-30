//Created by Jevana Grant on February 14, 2025
//ID No: 2302670
//User.java

import java.io.*;

public class User implements java.io.Serializable {
	protected final String userID; // Unique ID for each user, final as it's assigned once.
	protected static int patronIDCounter = 1000; // Counter to generate unique patron IDs, starts at 1000.
	protected String fname; // User's first name.
	protected String lname; // User's last name.
	protected String username; // User's username.
	protected Password password; // User's password object.

	protected static String UsernamesFile = "Usernames.txt"; // File to store usernames.

	// Default Constructor - Initializes a User with default values.
	public User() {
		userID = assignID(); // Assigns a unique ID.
		fname = ""; // Default first name.
		lname = ""; // Default last name.
		password = new Password(); // Creates a new Password object.
		username = ""; // Default username.
	}


  //Primary Constructor 1
	//This constructor is used when retrieving all attributes from xml file to prevent null Nodelist error
	public User(String userID, String fname, String lname, String username, Password password) {
		this.userID = userID; 
		this.fname = fname; // Sets the first name.
		this.lname = lname; // Sets the last name.
		this.username = username; // Sets the username.
		this.password = password; // Sets the password.
	}
	
	// Primary Constructor 2
	//This constructor is used to automatically generateISBN with other manually inputted attributes
	public User(String fname, String lname, String username, Password password) {
		this.userID = assignID(); // Assigns a unique ID.
		this.fname = fname; // Sets the first name.
		this.lname = lname; // Sets the last name.
		this.username = username; // Sets the username.
		this.password = password; // Sets the password.
	}
	
	// Copy Constructor - Creates a new User by copying an existing User.
	public User(User obj) {
		this.userID = obj.assignID(); // Assigns a unique ID.
		this.fname = obj.fname; // Copies the first name.
		this.lname = obj.lname; // Copies the last name.
		this.username = obj.username; // Copies the username.
		this.password = obj.password; // Copies the password.
	}

	// Getters and Setters - Methods to access and modify user attributes.
	public String getUserID() {
		return userID;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password; // Assigns the parameter to the instance variable.
	}

	// Display method - Returns a string representation of the User.
	public String toString() {
		return "\nUser 's ID    : " + userID + "\n" +
		       "    First Name   : " + fname + "\n" +
		       "    Last Name    : " + lname + "\n" +
		       "    Username    : " + username + "\n" +
		       "    Password    : " + password + "\n";
	}

  //Updated by Chev
	//Method to save the User ID to the file
	public static void SaveUserIDCount(String filePath,int x)
	{
		try (FileWriter writer = new FileWriter(filePath))
		{
			writer.write(String.valueOf(x));

		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the counter");
			e.printStackTrace();
		}
	}

	//Updated by Chev
	//Method to read or create a file that contains UserID
	public static int LoadUserIDCount(String filePath)
	{
		File file = new File(filePath);
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String line = reader.readLine();
				if (line != null) {
					return Integer.parseInt(line);
				}
			} catch (IOException e) {
				System.out.println("An error occurred while reading the file.");
				e.printStackTrace();
			}
		} else {
			//If file does not exist it will create a new file
			try {
				if (file.createNewFile()) {
					System.out.println("File created: " + file.getName());
					//Assigns initial value
					SaveUserIDCount("UserIDCount.txt", 1000);
				}
			} catch (IOException e) {
				System.out.println("An error occurred while creating the file.");
				e.printStackTrace();
			}
		}

		return 1000; // Return default value if the file was not found

	}

	//Updated by Chev
	//creates random UserID of user
	public static String assignID()
	{
		int userIDCounter;
		userIDCounter = LoadUserIDCount("UserIDCount.txt"); //reads ISBN number from file
		String newUserID = String.valueOf(userIDCounter); // Assign initial value from counter
		userIDCounter += 4; // Increment counter for next book
		SaveUserIDCount("UserIDCount.txt", userIDCounter);
		return newUserID;
	}
	
	


}
