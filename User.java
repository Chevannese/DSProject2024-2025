package pack;

//Created by Jevana Grant on February 14, 2025
//ID No: 2302670
//User.java

import java.io.Serializable;

public class User implements Serializable {
  /**
	 * 
	 */
private static final long serialVersionUID = 1L;
protected String userID;
  protected String fName;
  protected String lName;
  protected String username;
  protected Password password;

  // Default Constructor
  public User() {
      userID = "";
      fName = "";
      lName = "";
      username = "";
      password = new Password();
  }

  // Primary Constructor
  public User(String userID, String fName, String lName, String username, Password password) {
      this.userID = userID;
      this.fName = fName;
      this.lName = lName;
      this.username = username;
      this.password = password;
  }

  // Copy Constructor
  public User(User obj) {
      this.userID = obj.userID;
      this.fName = obj.fName;
      this.lName = obj.lName;
      this.username = obj.username;
      this.password = obj.password;
  }

  // Getters and Setters
  public String getUserID() {
      return userID;
  }

  public void setUserID(String userID) {
      this.userID = userID;
  }

  public String getfName() {
      return fName;
  }

  public void setfName(String fName) {
      this.fName = fName;
  }

  public String getlName() {
      return lName;
  }

  public void setlName(String lName) {
      this.lName = lName;
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
      this.password = password; // Corrected to assign the parameter to the instance variable
  }

  // Display method
  public void Display() {
      System.out.println("User's ID       : " + userID);
      System.out.println("First Name      : " + fName);
      System.out.println("Last Name       : " + lName);
      System.out.println("Username        : " + username);
      System.out.println("Password        : " + password);
  }
}
