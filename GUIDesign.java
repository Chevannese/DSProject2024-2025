package pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUIDesign 
{
	public static void main(String[] args)
	{
		LaunchPage run = new LaunchPage();
		
    
	}
}

class NewWindow
{
	JFrame frame = new JFrame();
	JLabel label = new JLabel("Hello");
	
	NewWindow(String title) {
        label.setText(title);
        label.setFont(new Font(null, Font.PLAIN, 25));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }
	
}


	class LaunchPage implements ActionListener {
		
	    JFrame frame = new JFrame();
	    JPanel buttonPanel = new JPanel();
	    String[] buttonNames = {"Library", "Rent", "Return", "User", "Add Staff"};
	    JButton logoutButton = new JButton("Logout");
	    JButton[] buttons = new JButton[5];

	    
	    LaunchPage() {
	        frame.setTitle("Library Management System");
	        frame.setSize(600, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());

	        // Set background panel with image
	        BackgroundPanel backgroundPanel = new BackgroundPanel("jojo.jpg"); // Change to your image path
	        backgroundPanel.setLayout(new BorderLayout());
	        frame.setContentPane(backgroundPanel);

	        // Button panel settings
	        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
	        buttonPanel.setOpaque(false);

	        // Create buttons and add action listeners
	        for (int i = 0; i < buttonNames.length; i++) 
	        {
	            buttons[i] = new JButton(buttonNames[i]);
	            buttons[i].setBackground(new Color(0,128,0));
	            buttons[i].setForeground(new Color(255,255,255));
	            buttons[i].setFocusable(false);
	            buttons[i].addActionListener(this); // Attach action listener
	            buttonPanel.add(buttons[i]);
	        }

	        logoutButton.setBackground(new Color(20,54,164));
	        logoutButton.setForeground(new Color(255,255,255));
	     // Add panel to the left side
	        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
	        // Add panel to the left side
	        frame.add(buttonPanel, BorderLayout.WEST);
	        
	        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align right
	        logoutButton.addActionListener(e -> System.exit(0)); // Close program
	        logoutPanel.add(logoutButton);
	        frame.add(logoutPanel, BorderLayout.SOUTH);
	        frame.setVisible(true);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        for (int i = 0; i < buttonNames.length; i++) {
	            if (e.getSource() == buttons[i]) {
	            	
	            	frame.dispose();
	                new NewWindow(buttonNames[i]); // Open new window with respective title
	            }
	        }
	    }
	}
	
	//This class is used to create custom backgrounds
	class BackgroundPanel extends JPanel {
	    private Image backgroundImage;

	    public BackgroundPanel(String imagePath) {
	        backgroundImage = new ImageIcon(imagePath).getImage();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	    }
	}
	
	
	
	

