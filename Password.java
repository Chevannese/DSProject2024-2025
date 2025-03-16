/*
Created by Josan Williams
ID#: 2304917
Date: 02/16/2025
*/

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.security.*;

public class Password {
	public 	static 	Scanner scanner				= new Scanner(System.in);
	private static	String HashedPasswordsFile  = "HashedPasswords.txt";
    private	String password;
    
    //Default Constructor
    public Password() {
        password = "";
    }
    
    //Primary Constructor
    public Password(String password){
        this.password = password;
    }

    //Getters & Setters
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
/*------------------------------------------------------------------
    				|Password||Methods|
------------------------------------------------------------------*/
    //Method to enter the password from the user
    public void enterPassword() 
    {
        do {
            System.out.println("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
        }while (!checkPasswordStrength(password) || !checkPasswordAvailability(password));
        PasswordDetailsHashToFile(getPassword());
    }

    //Method to check password strength
    public boolean checkPasswordStrength(String password)
    {
        if (password.length() < 8) {
            System.err.println("Password must be at least 8 characters long.");
            return false;
        }

        boolean hasUppercase    = false;
        boolean hasLowercase    = false;
        boolean hasDigit        = false;
        boolean hasSpecialChar  = false;

        for (char c : password.toCharArray()) 
                {if (Character.isUpperCase(c))          {hasUppercase   = true;} 
            else if (Character.isLowerCase(c))          {hasLowercase   = true;}
            else if (Character.isDigit(c))              {hasDigit       = true;}
            else if (!Character.isLetterOrDigit(c))     {hasSpecialChar = true;}}

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
            System.err.println("Password failed requirements.");
            return false;
        }
        return true;
    }

    //Method to check password availability
    public boolean checkPasswordAvailability(String password) 
    {
        try (RandomAccessFile raf = new RandomAccessFile(HashedPasswordsFile, "r")) {
            String line;
            String hashedPassword = hashString(password);
            while ((line = raf.readLine()) != null) {
                if (line.equals(hashedPassword)) {
                    System.err.println("Password already exists.");
                    return false; // password is already in use
            }}
            return true; // password is available
        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to open file " + HashedPasswordsFile);
            return false;
        } catch (IOException e) {
            System.err.println("Error reading from file " + HashedPasswordsFile);
            return false;
        }
    }

    // Method to change password
    public void changePassword() 
    {
        char choice;
        String newPassword;
        String oldPassword = getPassword();
        do  {
                System.out.print("\nAre you sure you want to change your password?\n[Y] / [N] : ");
                choice = scanner.next().charAt(0);
        }while  (choice != 'Y' && choice != 'y' && choice != 'N' && choice != 'n');
        
        if (choice == 'N' || choice == 'n') {
                System.out.println("\nPassword change cancelled.");
                return;
        }else if (choice == 'Y' || choice == 'y') {

            do  {
                    System.out.println("\nPassword must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
                    System.out.print("Enter Your New Password: ");
                    scanner.nextLine();
                    newPassword = scanner.nextLine();

                    if (oldPassword.equals(newPassword)){
                        System.out.println("New password cannot be the same as old password");
                        do  {
                                System.out.println("\nDo you want to continue changing your password?\n[Y] / [N] : ");
                                choice = scanner.next().charAt(0);
                        }while  (choice != 'Y' && choice != 'y' && choice != 'N' && choice != 'n');
                            
                        if (choice == 'N' || choice == 'n') {
                                System.out.println("\nPassword change cancelled.");
                                return;
                        }

                        // Check if the new password meets the requirements
                        if (!checkPasswordStrength(newPassword)) {
                            System.out.println("Error: New password does not meet the requirements.");
                        }

                        // Check if the new password meets the requirements
                        if (!checkPasswordStrength(newPassword)) {
                            System.out.println("Error: New password does not meet the requirements.");
                        }
            
                        // Check if the new password is already in use
                        if (!checkPasswordAvailability(newPassword)) {
                            System.out.println("Error: New password is already in use.");
                        }
                    }
                }while(oldPassword.equals(newPassword) || !checkPasswordStrength(newPassword) || !checkPasswordAvailability(newPassword));

            // Implementing password change after checks
            PasswordDetailsHashToFile(newPassword);
            System.out.println("Password changed successfully.");
        }
    }
        
    // Method to hash the password details to the file
    public void PasswordDetailsHashToFile(String password) 
    {
            // Implementing password hashing after checks
            String hashedPassword = hashString(password);
        try (RandomAccessFile raf = new RandomAccessFile(HashedPasswordsFile, "rw")){
            raf.seek(raf.length());
            raf.writeBytes(hashedPassword + "\n");
            raf.close();
            System.out.println("Details saved to file");
        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to open file " + HashedPasswordsFile);
        } catch (IOException e) {
            System.err.println("Error: Unable to write to file " + HashedPasswordsFile);
        }
    }
   
    // Method to hash a string using SHA-256
   public static String hashString(String input) 
    {
            try {MessageDigest digest = MessageDigest.getInstance("SHA-256");
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
        
        //public static void main(String[] args) {
	    //Password user = new Password();
        //user.enterPassword();
        //user.changePassword();
	//}
}
