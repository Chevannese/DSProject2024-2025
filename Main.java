/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/

import java.io.*;
import java.util.*;

public class Main {

    private Scanner input = new Scanner(System.in);
    private Admin admin1;
    private User user1;
    private List<Patron> patronsList; // List to manage patrons
    private LinkedList<Book> booksList; // LinkedList to manage books

    // Constructor
    public Main() {
        patronsList = new ArrayList<>();
        booksList = new LinkedList<>();
        admin1 = new Admin(); 
        user1 = new User(patronsList);  // Pass patron list to User for management
    }

    public static void main(String[] args) {
        Main system = new Main(); // Create instance of Main class
        system.run(); // Start system
    }

    public void run() {
        System.out.println("===== Welcome to the Library Management System (LMS) =====");
        //System.out.println("NOTICE: Default Admin Login - Username: 'admin', Password: 'admin'");
        //System.out.println("Please ensure to change your password once logged in.\n");

        boolean running = true;

        while (running) {
            try {
                System.out.println("\nPlease identify yourself:");
                System.out.println("[1] Admin Login");
                System.out.println("[2] Patron Login");
                System.out.println("[3] New Patron Registration");
                System.out.println("[4] Exit System");
                System.out.print("Select option (1-4): ");

                int choice = getValidIntegerInput(1, 4);

                switch (choice) {
                    case 1:
                        if (adminLogin()) adminMenu();
                        break;
                    case 2:
                        Patron patron = patronLogin();
                        if (patron != null) patronMenu(patron);
                        break;
                    case 3:
                        user1.addPatron(); // Adding new patron via User class
                        break;
                    case 4:
                        System.out.println("\n Logging out... Goodbye!");
                        running = false;
                        break;
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                input.nextLine(); // Clear buffer
            }
        }

        input.close(); // Close Scanner
    }

    // ------------------------ Admin Menu ------------------------
    public void adminMenu() {
        boolean active = true;
        while (active) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("[1] Book Management");
            System.out.println("[2] Patron Management");
            System.out.println("[3] View Library Statistics");
            System.out.println("[4] Change Password");
            System.out.println("[5] Logout");
            System.out.print("Select option (1-5): ");

            int option = getValidIntegerInput(1, 5);

            switch (option) {
                case 1: bookManagementMenu(); 
                break;
                case 2: patronManagementMenu(); 
                break;
                case 3: viewLibraryStats(); 
                break;
                case 4: admin1.getPassword().changePassword(); 
                break;
                case 5:
                    System.out.println("\nAdmin logged out.");
                    active = false;
                    break;
            }
        }
    }

    // ------------------------ Book Management Menu (Admin) ------------------------
    public void bookManagementMenu() {
        boolean active = true;
        while (active) {
            System.out.println("\n--- Book Management ---");
            System.out.println("[1] Add Book");
            System.out.println("[2] View Book");
            System.out.println("[3] Update Book");
            System.out.println("[4] Remove Book");
            System.out.println("[5] Search Book (To be implemented - BST)");
            System.out.println("[6] Back to Admin Menu");
            System.out.print("Select option (1-6): ");

            int option = getValidIntegerInput(1, 6);

            switch (option) {
                case 1: admin1.addBook(); 
                break;
                case 2: admin1.viewBook(); 
                break;
                case 3: admin1.updateBook(); 
                break;
                case 4: admin1.removeBook(); 
                break;
                case 5: 
                    System.out.println("\nBook search feature to be implemented using BST.");
                    // TODO: Implement BookBST class and searching mechanism
                    break;
                case 6: active = false; break;
            }
        }
    }

    // ------------------------ Patron Management Menu (Admin) ------------------------
    public void patronManagementMenu() {
        boolean active = true;
        while (active) {
            System.out.println("\n--- Patron Management ---");
            System.out.println("[1] Add Patron (By Admin)");
            System.out.println("[2] View Patron Details");
            System.out.println("[3] Back to Admin Menu");
            System.out.print("Select option (1-3): ");

            int option = getValidIntegerInput(1, 3);

            switch (option) {
                case 1:
                    user1.addPatron(); 
                    break;
                case 2: 
                    admin1.viewPatron(); // Admin views patron details
                    break;
                case 3: 
                    active = false;
                    break;
            }
        }
    }

    // ------------------------ View Library Statistics ------------------------
    public void viewLibraryStats() {
        System.out.println("\n--- Library Statistics ---");
        System.out.println("Total Books: " + booksList.len()); // length of LinkedList
        System.out.println("Total Patrons: " + patronsList.size());
        
        //Count number of currently checked-out books
        System.out.println("Books Checked Out: (To be implemented)");
    }

    // ------------------------ Patron Menu ------------------------
    public void patronMenu(Patron patron) {
        boolean active = true;
        while (active) {
            System.out.printf("\nWelcome Patron %s %s, please choose an option:\n", patron.getFname(), patron.getLname());
            System.out.println("[1] Search Book (To be implemented )");
            System.out.println("[2] Check Out Book (To be implemented)");
            System.out.println("[3] Return Book (To be implemented )");
            System.out.println("[4] View Borrowed Books");
            System.out.println("[5] Change Password");
            System.out.println("[6] Logout");
            System.out.print("Select option (1-6): ");

            int option = getValidIntegerInput(1, 6);

            switch (option) {
                case 1: 
                    System.out.println("\nSearch not yet here (BST).");
                    break;
                case 2:
                    System.out.println("\n: Checkout feature not yet.");
                    break;
                case 3:
                    System.out.println("\nReturn not yet");
                    break;
                case 4:
                    patron.getBooks().display(); // List of borrowed books
                    break;
                case 5:
                    patron.getPassword().changePassword();
                    break;
                case 6:
                    System.out.println("\nPatron logged out.");
                    active = false;
                    break;
            }
        }
    }

    // ------------------------ Admin Login ------------------------
   public boolean adminLogin() {
    System.out.println("\nNOTICE: Default Admin Login - Username: 'admin', Password: 'admin'");
    System.out.print("\nEnter Admin Username: ");
    String username = input.nextLine().trim();

    System.out.print("Enter Admin Password : ");
    String pwd = input.nextLine().trim();

    // Check if they typed admin but in wrong case
    if ((username.equalsIgnoreCase("admin") && !username.equals("admin")) || 
        (pwd.equalsIgnoreCase("admin") && !pwd.equals("admin"))) {
        System.out.println("Please use lowercase 'admin' for both username and password.");
        return false;
    }

    // Check if they are fully correct
    if (username.equals("admin") && pwd.equals("admin")) {
        System.out.println(" Admin login successful.");
        return true;
    }

    // If completely wrong
    System.out.println(" Invalid admin credentials.");
    return false;
}

    // ------------------------ Patron Login ------------------------
    public Patron patronLogin() {
        System.out.print("\nEnter Patron Username: ");
        String username = input.nextLine().trim();

        System.out.print("Enter Patron Password: ");
        String pwd = input.nextLine().trim();

        for (Patron p : patronsList) {
            if (p.getUsername().equals(username) && p.getPassword().getPassword().equals(pwd)) {
                System.out.println("Patron login successful.");
                return p;
            }
        }
        System.out.println("Invalid patron credentials.");
        return null;
    }

    // ------------------------ Input Validation ------------------------
    public int getValidIntegerInput(int min, int max) {
        int choice = -1;
        while (true) {
            try {
                choice = Integer.parseInt(input.nextLine());
                if (choice >= min && choice <= max) break;
                else System.out.printf("Invalid input! Enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid input! Enter a number between %d and %d: ", min, max);
            }
        }
        return choice;
    }
}




		//     // Removing a book
		//     admin1.removeBook(); // Call the method to remove a book

		//     // User input for username and password
		//     user1.enterUsername();
		//     user1.enterPassword();

		//     // user1.checkPasswordStrength(user1.getPassword());
		//     // user1.checkUsernameAvailability(user1.getUsername());
		//     user1.passwordDetailsHashToFile(user1.getUsername(), user1.getPassword());
		//     user1.display();

		//     Node<Integer> n1 = new Node(3);
		//     Node<String> n2 = new Node("joe");
		//     print("Joe");
		//     print(69);
		// }

		// public static <T> void print(T thing) {
		//     System.out.println(thing);
		// }

		//put functionality to accept user input for patron or admin
	/*	while() {
			try {
				//verify the user by prompting them for input (patron or admin or a new patron) - Jevana

				//menu method for either the patron or admin - Jevana
				if() { //patron's functionality

				} else if () {//admin's functionality

				} else if () { //new patron's functionality

				} else if () { //logs out of System
					System.out.println("\nSYSTEM: Logging out...\n");
					System.exit(0);
				} else { //error message for invalid input

				}
			} catch () { //do error handling/endless menu prompting here

			}

			//file saving method - Chev
		}

	}

	//implement the diff menus - Jada
	/*public static void Menu() {

		public void patronMenu() {
			System.out.printf("Welcome patron %s %s, how may we assist you today? ", patron1.getFname(), patron1.getLname());
			System.out.println("\n[1] Check Number of Books \n[2] Search for Book by Author, Title or ISBN " +
			                   " \n[3] Change Password \n[4] Exit Menu \nPlease select an option: ");

			try {
				int option = input.nextInt();
				input.nextLine(); //consumes newline character - Jada

				//explores each option - Jada
				switch(option) {
				case 1: { //check number of books outstanding for patron - Jada
					break;
				}
				case 2: { //Search for Book using BST - Jada
					break;
				}
				case 3: { //Changes patron password - Jada
					if(userPass.equals(patron1.getPassword())) {
                        patron1.getPassword().changePassword();
					}
					break;
				}
				case 4: { //exits menu - Jada
					System.out.printf("\nSYSTEM: Logging out...\n");
					System.exit(0);
					break;
				}
				default: {

				}
				}
			} catch () {
			    
			}

		}
		
		public void newPatronMenu() {
		    System.out.println("\nWelcome patron to our Library Management System (LMS), please fill out the following information to get started!");
		    
		    patron1.addPatron();
		}

		public void adminMenu() {
			//see library stats
		}

	}
	
	input.close();

}*/
