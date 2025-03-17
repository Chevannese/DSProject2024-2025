// Main.java
package main;

import java.io.*;
import java.util.*;
import javax.swing.*;
import static javax.swing.GroupLayout.*;
import static javax.swing.GroupLayout.Alignment.*;
import java.awt.*;
import java.awt.event.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class Main {
	private static Scanner input = new Scanner(System.in);
	private static LinkedList<Book> books;
	private static LinkedList<Patron> patrons;
	private static LinkedList<Admin> admins;
	private static JFrame window;

	public static void main(String[] args) {
	    LinkedList<Book> stuff = new LinkedList<Book>();
	    
	    stuff.append(new Book("joe", "mama", "frfr", "something", true));
	    stuff.append(new Book("123456789", "The Great Gatsby", "F. Scott", "Fitzgerald", true));
	    stuff.append(new Book("123","Cinderella","John","Brown",true));
	    
	    for (Book b: stuff) {
	        b.Display();
	    }
	    
        System.out.println("===== Welcome to the Library Management System (LMS) =====");
        //System.out.println("NOTICE: Default Admin Login - Username: 'admin', Password: 'admin'");
        //System.out.println("Please ensure to change your password once logged in.\n");
        
        try {
            books = read("book.txt");
            patrons = read("patrons.txt");
            admins = read("admins.txt");
        }
        catch (Exception e) {
        	books = new LinkedList<Book>();
        	patrons = new LinkedList<Patron>();
        	admins = new LinkedList<Admin>();
            JOptionPane.showMessageDialog(null, "Failed to read from files", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        window = new JFrame("Title or something I unno");
        JPanel pane = new JPanel();
        JLabel title = new JLabel("===== Welcome to the Library Management System (LMS) =====");
        JButton[] mainButtons = { new JButton("Admin Login"), new JButton("Patron login"), new JButton("Patron Registration"), new JButton("Exit") };
        JPanel buttonRow = new JPanel();
        
        Arrays.stream(mainButtons).forEach(buttonRow::add);
        
        //window.setSize(600, 500);
        title.setSize(100, 100);
        window.setMinimumSize(new Dimension(600, 500));
        pane.add(title);
        pane.add(buttonRow);
        pane.setAlignmentX(100);
        
        window.add(pane);
        
        window.setVisible(true);
        
        mainButtons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                JPanel login = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField username = new JTextField();
                JPasswordField password = new JPasswordField();
                
                label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
                label.add(new JLabel("Password", SwingConstants.RIGHT));
                controls.add(username);
                controls.add(password);
                login.add(label, BorderLayout.WEST);
                login.add(controls, BorderLayout.CENTER);

                int option = JOptionPane.showConfirmDialog(window, login, "Login", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                	String u = username.getText();
                	String p = new String(password.getPassword());
                	
                	if (u.length() == 0 || p.length() == 0) {
                		JOptionPane.showMessageDialog(window, "Fill in all fields", "ERROR", JOptionPane.WARNING_MESSAGE);
                	}
                	else {
                		JDialog popup = new JDialog(window, "Admin Login", true);
                		
                	}
                }
            	
                
                //"\n---Admin Controls Menu---\[1] Check Book Information \n[2] Check Patron Information " +
				  //                 " \n[3] Check Library Status \n[4] Exit Menu \nPlease select an option: ");
            }
        });
        
        mainButtons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog popup = new JDialog(window, "Patron Login", true);
            }
        });
        
        mainButtons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog popup = new JDialog(window, "Patron Registration", true);
                JPanel c = new JPanel();
                GroupLayout layout = new GroupLayout(c);
                JLabel[] names = { new JLabel("Name"), new JLabel("Username"), new JLabel("Pass"), new JLabel("Confirm") };
                JTextField[] inputs = { new JTextField(), new JTextField(), new JTextField(), new JPasswordField(), new JPasswordField() };
                JButton confirm = new JButton("Confirm");
                
                c.setLayout(layout);
                layout.setAutoCreateContainerGaps(true);
                layout.setAutoCreateGaps(true);

                layout.setHorizontalGroup(layout.createSequentialGroup()
                		.addGroup(layout.createParallelGroup()
                				.addComponent(names[0])
                        		.addComponent(names[1])
                        		.addComponent(names[2])
                        		.addComponent(confirm))
                        .addGroup(layout.createParallelGroup()
                        		.addGroup(layout.createSequentialGroup()
                        				.addComponent(inputs[0])
                        				.addComponent(inputs[1]))
                            	.addComponent(inputs[2])
                            	.addGroup(layout.createSequentialGroup()
                            			.addComponent(inputs[3])
                            			.addComponent(names[3])
                            			.addComponent(inputs[4]))));
                
                layout.setVerticalGroup(layout.createSequentialGroup()
                		.addGroup(layout.createParallelGroup(BASELINE)
                				.addComponent(names[0])
                				.addComponent(inputs[0])
                				.addComponent(inputs[1]))
                        .addGroup(layout.createParallelGroup(BASELINE)
                        		.addComponent(names[1])
                        		.addComponent(inputs[2]))
                        .addGroup(layout.createParallelGroup(BASELINE)
                        		.addComponent(names[2])
                        		.addComponent(inputs[3])
                        		.addComponent(names[3])
                        		.addComponent(inputs[4]))
                			.addComponent(confirm));
                
                confirm.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		if (Arrays.stream(inputs).anyMatch(t -> t.getText().length() == 0)) {
                			JOptionPane.showMessageDialog(popup, "Fill in all fields", "Bruh", JOptionPane.OK_OPTION);
                		}
                		else {
                			/*
                			Patron p = new Patron(input[0], inputs[1], inputs[2], inputs[3]);
                			patrons.append(p);
                			*/
                		}
                	}
                });
                
                popup.setContentPane(c);
                popup.setVisible(true);

                JOptionPane.showMessageDialog(window, "Patron Registration", "JOptionPane Example : ", JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        mainButtons[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(window, "Saving changes", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                
                try {
                	save("books.txt", books);
                }
                catch (Exception es) {
                	JOptionPane.showMessageDialog(window, "Error saving files", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
                return;
            }
        });
        
	
	}
	
	public static <T extends Serializable> void save(String file, Iterable<T> list) throws IOException {
	    ArrayList<T> items = new ArrayList<T>();
	    list.iterator().forEachRemaining(items::add);
	    
	    try {
	        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
	        output.writeObject(items);
	    }
	    catch (Exception e) {
	        throw e;
	    }
	}
	
	public static <T extends Serializable> LinkedList<T> read(String file) throws IOException, ClassNotFoundException {
	    ArrayList<T> list = new ArrayList<T>();
	    
	    ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
	    list = (ArrayList<T>) input.readObject();
	    
	    return new LinkedList<T>(list);
	}
		
	// ------------------------ Input Validation ------------------------ Jevana
    public int getValidIntegerInput(int min, int max) {
        int choice = -1;
        
        while (true) {
            try {
                choice = Integer.parseInt(input.nextLine());
                if (choice >= min && choice <= max) 
                    break;
                else 
                    System.out.printf("Invalid input! Enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid input! Enter a number between %d and %d: ", min, max);
            }
        }
        return choice;
    }
}

                

        	    /*
        		Admin admin1 = new Admin();
        		Patron patron1 = new Patron("", "", "", "", "", new Password());
        		Password user1 = new Password();
        		Book NB = new Book("123456789", "The Great Gatsby", "F. Scott", "Fitzgerald", true);
        		
        		LinkedList<Book> bookie = new LinkedList<Book>();
        		Book d1 = new Book("123","Cinderella","John","Brown",true);
        		Book d2 = new Book(d1);
        		Book d3 = new Book(d1);
        		Book d4 = new Book(d1);
        		Book d5 = new Book(d1);
        		
        		bookie.append(d1);
        		bookie.append(d2);
        		bookie.append(d3);
        		bookie.append(d4);
        		bookie.append(d5);
        		Password pass = new Password("Comedypoo","fucku");
        		
        		
        		LinkedList<Patron> pat = new LinkedList<Patron>();
        		Patron chev = new Patron("2301109","Chevannese","Ellis","what", pass, "keyrus1", bookie);
        		Patron jon = new Patron(chev);
        		Patron jada = new Patron(chev);
        		Patron josan = new Patron(chev);
        				
        		System.out.print("Patron List: ");
        		pat.append(chev);
        		pat.append(jon);
        		pat.append(jada);
        		pat.append(josan);
        		pat.Display();
        		
        		System.out.println("Patron Chev Info: ");
        		chev.Display();
        		
        		WritePatronData("PatronRecords.xml", pat);
            
            
        		LinkedList<Patron> patrons = loadPatronData("PatronRecords.xml");

                // Display the loaded Patron objects
                System.out.println("Loaded Patrons:");
                patrons.forEach(Patron::Display);

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
        		*/
                        /*
                        
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
                                        System.out.println("\nSYSTEM: Logging out... Goodbye!");
                                        running = false;
                                        break;
                                }

                            } catch (Exception e) {
                                System.out.println("ERROR: " + e.getMessage());
                                input.nextLine(); // Clear buffer
                            }
                        }

                        input.close(); // Close Scanner
                        */
                    
                    /*
                    // ------------------------ View Library Statistics ------------------------ Jevana
                    public void viewLibraryStats() {
                        System.out.println("\n--- Library Statistics ---");
                        System.out.println("Total Books: " + booksList.len()); // length of LinkedList
                        System.out.println("Total Patrons: " + patronsList.size());
                        
                        //Count number of currently checked-out books
                        System.out.println("Books Checked Out: (To be implemented)");
                    }

                	//implement the diff menus - Jada
                	public static void Menu() {

                		boolean responseFlag = false;

                		public void patronMenu() {
                			System.out.printf("Welcome patron %s %s, how may we assist you today? ", patron1.getFname(), patron1.getLname());

                			while(!responseFlag) {
                				System.out.println("\n---Patron Menu---\n[1] Checking In/Out Books \n[2] Search for Book by Author, Title or ISBN " +
                				                   " \n[3] Change Password \n[4]View Account Details \n[5] Exit Menu \nPlease select an option: ");

                				try {
                					int option = input.nextInt();
                					input.nextLine(); //consumes newline character - Jada

                					//explores each option - Jada
                					switch(option) {
                					case 1: { //checks books outstanding for patron - Jada
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
                					case 4: { //View Patron Details
                						for (Patron patron : patrons) {
                							patron.viewPatron();
                						}
                					}

                					case 5: { //exits menu - Jada
                						System.out.println("\nSYSTEM: Logging out...\n");
                						System.exit(0);
                						break;
                					}
                					default: {
                						System.out.println("\nSYSTEM: Incorrect option entered\n");
                					}
                					}
                				} catch (IOException e) {
                					System.out.print("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
                					String response = scanner.nextLine();
                					scanner.nextLine();

                					if (!response.equalsIgnoreCase("Y")) {
                						break; // Exit the loop if the user does not want to try again
                					}
                				}
                			}

                		}

                		public void newPatronMenu() {
                			System.out.println("\nWelcome new patron to our Library Management System (LMS), please fill out the following information to get started!");

                			for (Patron patron : patrons) {
                				patron.addPatron();
                			}
                		}

                		public void adminMenu() {

                			System.out.printf("Welcome admin %s %s, how may we assist you today? ", admin.getFname(), admin.getLname());

                			while(!responseFlag) {
                				System.out.println("\n---Admin Controls Menu---\n[1] Check Book Information \n[2] Check Patron Information " +
                				                   " \n[3] Check Library Status \n[4] Exit Menu \nPlease select an option: ");

                				int option = input.nextInt();
                				input.nextLine(); //consumes newline character

                				while(!responseFlag) {
                					try {
                						//explores each option - Jada
                						switch(option) {

                						//shows options for book information - Jada
                						case 1: {
                							while(!responseFlag) {
                								System.out.println("\n---Book Information Menu---\n[1] Add a Book \n[2] Update a Book \n[3] View a Book" +
                								                   "\n[4] Remove a Book \n[5] Search for Book by Author, ISBN or Title \n[6] Exit Menu " +
                								                   "\nPlease select an option: ");
                								int bookOption = input.nextInt();
                								input.nextLine(); //consumes newline character - Jada

                								switch(bookOption) {
                								case 1: {
                									for (Book book : books) {
                										books.addBook();
                									}
                								}
                								
                								case 2: {
                									for (Book book : books) {
                										books.updateBook();
                									}
                								}
                								
                								case 3: {
                									for (Book book : books) {
                										books.viewBook();
                									}
                								}
                								
                								case 4: {
                									for (Book book : books) {
                										books.removeBook();
                									}
                								}
                								
                								//BST
                								case 5: {
                									for (Book book : books) {

                									}
                								}
                								
                								case 6: {
                									System.out.println("\nSYSTEM: Logging out...\n");
                									System.exit(0);
                									break;
                								}
                								
                								default: {

                								}
                								
                								break;

                								}
                							}
                						}

                						//shows options for patron information - Jada
                						case 2: {
                							while(!responseFlag) {
                								System.out.println("\n---Patron Information Menu---\n[1] Add a Patron \n[2] Update a Patron" +
                								                   "\n[3] View Patron's Details \n[4] Remove a Patron \n[5] Exit Menu " +
                								                   "\nPlease select an option: ");
                								int patronOption = input.nextInt();
                								input.nextLine(); //consumes newline character - Jada

                								try {
                									switch(patronOption) {
                									case 1: {
                										for (Patron patron : patrons) {
                											patron.addPatron();
                										}
                									}
                									case 2: {
                										for (Patron patron : patrons) {
                											patron.updatePatron();
                										}
                									}
                									case 3: {
                										for (Patron patron : patrons) {
                											patron.viewPatron();
                										}
                									}
                									case 4: {
                										for (Patron patron : patrons) {
                											patron.removePatron();
                										}
                									}
                									case 5: {
                										System.out.println("\nSYSTEM: Logging out...\n");
                										System.exit(0);
                										break;
                									}
                									default: {
                										System.out.println("\nSYSTEM: Invalid option entered.\n");
                									}
                									break;

                									}

                								} catch (IOException e) {
                									System.out.print("SYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
                									String response = input.nextLine();
                									input.nextLine();

                									if (!response.equalsIgnoreCase("Y")) {
                										break; // Exit the loop if the user does not want to try again
                									}
                								}
                							}
                						}

                						//checks library status
                						case 3: {

                							break;
                						}

                						//exits menu - Jada
                						case 4: {
                							System.out.printf("\nSYSTEM: Logging out...\n");
                							System.exit(0);
                							break;
                						}

                						default: {
                							System.out.printf("\nSYSTEM: Incorrect option entered\n");
                						}

                						}
                					} catch (IOException e) {
                						System.out.print("\nSYSTEM: The information that you have entered is invalid. Do you want to try again (Y/N)? ");
                						String response = input.nextLine();
                						input.nextLine();

                						if (!response.equalsIgnoreCase("Y")) {
                							break; // Exit the loop if the user does not want to try again
                						}
                					}

                					//see library stats
                					input.close();
                				}
                			}

                		}

                	}

                	//Writes all patron information to xml file - Chev
                	public static void WritePatronData(String fileName, LinkedList<Patron> parameterlist) {
                		// Write the CustomLinkedList to an XML file
                		try (FileWriter writer = new FileWriter(fileName)) {
                			writer.write("<Patrons>\n");
                			parameterlist.forEach(patron -> {
                				try {
                					writer.write(patron.toXML());
                					writer.write("\n");
                				} catch (IOException e) {
                					e.printStackTrace();
                				}
                			});
                			writer.write("</Patrons>");
                			System.out.println("SYSTEM: Data written to file successfully.");
                		} catch (IOException e) {
                			System.out.println("ERROR: An error occurred while saving the patron info.");
                			e.printStackTrace();
                		}
                	}

                	//Read and Retrieve All Patron Data from the xml file - Chev
                	public static LinkedList<Patron> loadPatronData(String filePath) {
                		LinkedList<Patron> patrons = new LinkedList<Patron>();

                		try {
                			// Load the XML file
                			File xmlFile = new File(filePath);
                			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                			Document doc = dBuilder.parse(xmlFile);
                			doc.getDocumentElement().normalize();

                			// Get all Patron elements
                			NodeList patronList = doc.getElementsByTagName("Patron");
                			for (int i = 0; i < patronList.getLength(); i++) {
                				Node patronNode = patronList.item(i);
                				if (patronNode.getNodeType() == Node.ELEMENT_NODE) {
                					Element patronElement = (Element) patronNode;

                					// Extract Patron attributes
                					String userID = patronElement.getElementsByTagName("userID").item(0).getTextContent();
                					String fName = patronElement.getElementsByTagName("fName").item(0).getTextContent();
                					String lName = patronElement.getElementsByTagName("lName").item(0).getTextContent();
                					String username = patronElement.getElementsByTagName("Username1").item(0).getTextContent();
                					String passwordUsername = patronElement.getElementsByTagName("Username2").item(0).getTextContent();
                					String password = patronElement.getElementsByTagName("password").item(0).getTextContent();
                					String lNumb = patronElement.getElementsByTagName("lNumb").item(0).getTextContent();

                					// Create a Password object
                					Password pass = new Password(passwordUsername, password);

                					// Extract Books
                					LinkedList<Book> books = new LinkedList<Book>();
                					NodeList bookList = patronElement.getElementsByTagName("Book");
                					for (int j = 0; j < bookList.getLength(); j++) {
                						Element bookElement = (Element) bookList.item(j);
                						String ISBN = bookElement.getElementsByTagName("ISBN").item(0).getTextContent();
                						String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                						String authorFName = bookElement.getElementsByTagName("authorFName").item(0).getTextContent();
                						String authorLName = bookElement.getElementsByTagName("authorLName").item(0).getTextContent();
                						boolean status = Boolean.parseBoolean(bookElement.getElementsByTagName("status").item(0).getTextContent());

                						// Create a Book object and add it to the LinkedList
                						books.append(new Book(ISBN, title, authorFName, authorLName, status));
                					}

                					// Create a Patron object and add it to the LinkedList
                					patrons.append(new Patron(userID, fName, lName, username, pass, lNumb, books));
                				}
                			}

                			System.out.println("XML file loaded successfully.");
                		} catch (Exception e) {
                			System.out.println("An error occurred while loading the XML file.");
                			e.printStackTrace();
                		}

                		return patrons;
                	}
                	*/