//Created by Jevana Grant on February 14, 2025
//ID No: 2302670
//User.java


import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class User {
    private String userID;
    private String fname;
    private String lname;
    private String username;
    private Password password;
    private final String HashedUserNamesFile = "HashedUserNames.txt";
    private final String UsernamesFile = "Usernames.txt";
    private List<Patron> patrons;

    // -------------------- Constructors -----------------------

    // Default Constructor
    public User() {
        userID = "";
        fname = "";
        lname = "";
        username = "";
        password = new Password();
        patrons = new ArrayList<>();
    }

    // Constructor with patrons list
    public User(List<Patron> patrons) {
        this.patrons = patrons;
    }

    // Primary Constructor
    public User(String userID, String fname, String lname, String username, Password password) {
        this.userID = userID;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
    }

    // Copy Constructor
    public User(User obj) {
        this.userID = obj.userID;
        this.fname = obj.fname;
        this.lname = obj.lname;
        this.username = obj.username;
        this.password = obj.password;
    }

    // -------------------- Getters and Setters -----------------------

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }
    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Password getPassword() { return password; }
    public void setPassword(Password password) { this.password = password; }

    // -------------------- Display -----------------------

    public void display() {
        System.out.println("User's ID       : " + userID);
        System.out.println("First Name      : " + fname);
        System.out.println("Last Name       : " + lname);
        System.out.println("Username        : " + username);
        System.out.println("Password        : " + password.getPassword());
    }

    // -------------------- Username Methods -----------------------

    public boolean checkUsernameStrength(String username) {
        if (username.length() < 3 || username.length() > 30) {
            System.err.println("Username should be between 3 to 30 characters long.");
            return false;
        }
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != ' ') {
                System.out.println("Username should not contain any special characters.");
                return false;
            }
        }
        return true;
    }

    public boolean checkUsernameAvailability(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HashedUserNamesFile))) {
            String line;
            String hashedUsername = Password.hashString(username);
            while ((line = reader.readLine()) != null) {
                if (line.equals(hashedUsername)) {
                    System.out.println("SYSTEM: Username is already in use.");
                    return false;
                }
            }
            System.out.println("SYSTEM: Username is available.");
            return true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    public void changeUsername(String newUsername) {
        if (!checkUsernameStrength(newUsername) || !checkUsernameAvailability(newUsername)) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UsernamesFile, true))) {
            writer.write(newUsername);
            writer.newLine();
            System.out.println("Username changed successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // -------------------- Add Patron Method -----------------------

    public void addPatron() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("\nEnter Patron's First Name: ");
                String pFName = scanner.nextLine();

                System.out.print("Enter Patron's Last Name: ");
                String pLName = scanner.nextLine();

                String pUName;
                do {
                    System.out.print("Enter Patron's Username: ");
                    pUName = scanner.nextLine();
                } while (!checkUsernameStrength(pUName) || !checkUsernameAvailability(pUName));

                String defaultPassword = "Password@123";
                System.out.printf("\nSYSTEM: Your default password is: %s\n", defaultPassword);

                // Create Password and Patron objects
                Password patronPassword = new Password(defaultPassword);
                Patron newPatron = new Patron("P" + (patrons.size() + 1), pFName, pLName, pUName, "LN" + (patrons.size() + 1), patronPassword);

                // Add to patrons list
                patrons.add(newPatron);

                // Save username and password (hashed)
                saveHashedUsername(pUName);
                patronPassword.PasswordDetailsHashToFile(defaultPassword);

                System.out.println("\nSYSTEM: Patron registration successful! Here are the details: ");
                newPatron.Display();

                System.out.println("\nSYSTEM: Please change your password upon first login!");
                
                validInput = true;

            } catch (Exception e) {
                System.out.print("SYSTEM: Invalid input. Try again? (Y/N): ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("Y")) break;
            }
        }
    }

    private void saveHashedUsername(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HashedUserNamesFile, true))) {
            writer.write(Password.hashString(username));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving username: " + e.getMessage());
        }
    }
}
