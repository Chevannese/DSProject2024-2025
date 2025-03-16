package pack;

/*
Created by Josan Williams
ID#: 2304917
Date: 02/16/2025
*/

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;

public class Password {
    private String username;
    private String password;

    private static String UsernamesFile = "User names.txt";
    private static String HashedUserNamesFile = "HashedUserNames.txt";
    private static String PasswordsFile = "Passwords.txt";
    private static String HashedPasswordsFile = "HashedPasswords.txt";

    // Default Constructor
    public Password() {
        username = "";
        password = "";
    }

    // Primary Constructor
    public Password(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*------------------------------------------------------------------
                        |Methods|
  ------------------------------------------------------------------*/
    /*------------------------------------------------------------------
                        |User names|
  ------------------------------------------------------------------*/
    // Method to enter the username from the user
    public static void enterUsernameAndPassword(Password input) {
    
    	
        System.out.print("Enter Username: ");
        Scanner scanner = new Scanner(System.in);
        input.setUsername(scanner.nextLine());
        
        System.out.print("Enter Password: ");
        input.setPassword(scanner.nextLine());
        
        
        input.display();
    }

    // Method to check username strength
    public boolean checkUsernameStrength(String username) {
        if (username.length() < 3 || username.length() > 30) {
            System.err.println("Username should be between 3 to 30 characters long.");
            return false;
        }
        // Check if username contains any special characters or spaces
        for (int i = 0; i < username.length(); i++) {
            if (!Character.isLetterOrDigit(username.charAt(i)) && username.charAt(i) != '_' && username.charAt(i) != ' ') {
                System.out.println("Username should not contain any special characters.");
                return false;
            }
        }
        return true;
    }

    // Method to check username availability
    public boolean checkUsernameAvailability(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HashedUserNamesFile))) {
            String line;
            String hashedUsername = hashString(username);
            while ((line = reader.readLine()) != null) {
                if (line.equals(hashedUsername)) {
                    return false;
                }
            }
            System.out.println("Username is available.");
            return true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    // Method to change username
    public void changeUsername(String newUsername) {
        // Check if the new username meets the requirements
        if (!checkUsernameStrength(newUsername)) {
            System.err.println("Error: New username does not meet the requirements.");
            return;
        }
        // Check if the new username is already in use
        if (!checkUsernameAvailability(newUsername)) {
            System.err.println("Error: New username is already in use.");
            return;
        }
        // Implementing username change after checks
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UsernamesFile, true))) {
            writer.write(newUsername);
            writer.newLine();
            System.out.println("Username changed successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /*------------------------------------------------------------------
  ------------------------------------------------------------------*/

    /*------------------------------------------------------------------
                        |Passwords|
  ------------------------------------------------------------------*/
    // Method to enter the password from the user
    public void enterPassword() {
        System.out.print("Enter Password: ");
        Scanner scanner = new Scanner(System.in);
        password = scanner.nextLine();
    }

    // Method to check password strength
    public boolean checkPasswordStrength(String password) {
        if (password.length() < 8) {
            System.err.println("Password must be at least 8 characters long.");
            return false;
        }
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
            System.err.println("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
            return false;
        }

        return true;
    }

    // Method to check password availability
    public boolean checkPasswordAvailability(String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HashedPasswordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(password)) {
                    return false; // password is already in use
                }
            }
            return true; // password is available
        } catch (IOException e) {
            System.err.println("Error: Unable to open file " + HashedPasswordsFile);
            return false;
        }
    }

    // Method to change password
    public void changePassword(String newPassword) {
        // Check if the new password meets the requirements
        if (!checkPasswordStrength(newPassword)) {
            System.out.println("Error: New password does not meet the requirements.");
            return;
        }
        // Check if the new password is already in use
        if (!checkPasswordAvailability(newPassword)) {
            System.out.println("Error: New password is already in use.");
            return;
        }

        // Implementing password change after checks
        PasswordDetailsHashToFile(username, newPassword);
        System.out.println("Password changed successfully.");
    }

    // Method to hash the password details to the file
    public void PasswordDetailsHashToFile(String username, String password) {
        // Implementing password hashing after checks
        String hashedUsername = hashString(username);
        String hashedPassword = hashString(password);
        System.out.println("Hashed Username : " + hashedUsername);
        System.out.println("Hashed Password : " + hashedPassword);
    }

    // Method to display password details
    public void display() {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    // Method to hash a string using SHA-256
    public String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Method Overloading 
    public String hashString() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static void main(String[] args)
    {
    	Password test = new Password();
    	enterUsernameAndPassword(test);
    }
}