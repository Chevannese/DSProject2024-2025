/*
Created by Josan Williams
ID#: 2304917
Date: 02/16/2025
*/

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class Password implements java.io.Serializable {
	private String password;
	private boolean isOTP; //OTP means One Time Password

	private static String HashedPasswordsFile = "HashedPasswords.txt";

	// Default Constructor
	public Password() {
		password = "";
		isOTP = false; // for temp password - Jada / Jonathan
	}

	// Primary Constructor
	public Password(String password, boolean isOTP) {
		this.password = password;
		this.isOTP = isOTP;
	}
	// Getters & Setters
	public boolean isOTP() {
		return isOTP;
	}

	// Setter for isOTP
	public void setOTP(boolean isOTP) {
		this.isOTP = isOTP;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Method to display password details
	public String toString() {
		return "Password: " + password + "\nOTP: " + isOTP;
	}


	//generates random password for the user - Jada
	public String generatePassword(int len) {

		// A user MUST have a strong password containing upper and lower characters,
		// numeric value and symbols. So we created strings of
		// each to generate for their password
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$%^&*_=+-/.?<>)";

		//put all of the strings together
		String values = Capital_chars + Small_chars + numbers + symbols;

		// Using random method
		Random rand = new Random();

		// Using StringBuilder method for better traversal
		StringBuilder password = new StringBuilder(len);

		//loops thru password array to ensure that each char meets eligibility
		for (int i = 0; i < len; i++) {
			// Use of charAt() method: to get character value
			// Use of nextInt() as it is scanning the value as int
			password.append(values.charAt(rand.nextInt(values.length())));
		}

		setOTP(true);

		return password.toString();
	}

	
}
