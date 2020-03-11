/*
Written By : Robert Watkin
Date Created : 11/03/2020
*/
package ProductSystemAgile;

import database.AddReview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateReview extends JPanel implements ActionListener {
    private JTextField nameField;
    private JTextArea reviewField;

    private int productID;
    // END OF VARIABLE DECLARATION

    // Constructor
    public CreateReview(int productID) {
        this.productID = productID;
        // sets size of window
        setPreferredSize(new Dimension(500, 400));
        initCreateBooking();    // initialises the create booking


    }

    // the function below initialises all components of the createBooking window
    private void initCreateBooking() {
        this.setBackground(Window.backgroundColor);
        // sets the layout for the class (extends JPanel) to a new flowlayout
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel title = new JLabel("Create New Review");
        title.setFont(new Font("Arial", Font.PLAIN, 22));
        title.setPreferredSize(new Dimension(500, 100));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title);

        JPanel innerPanel = new JPanel();
        innerPanel.setPreferredSize(new Dimension(500, 200));
        innerPanel.setBackground(Window.backgroundColor);
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        JPanel productNamePanel = new JPanel();
        productNamePanel.setBackground(Window.backgroundColor);
        productNamePanel.setLocation(100,100);
        productNamePanel.setLayout(new GridLayout(1, 2, 15, 0));

        JPanel productReviewPanel = new JPanel();
        productReviewPanel.setBackground(Window.backgroundColor);
        productReviewPanel.setLayout(new GridLayout(1, 2, 15, 0));

        // ---------------NAME---------------
        // creates new JLabel to identify the name field
        JLabel nameLabel = new JLabel("Your Name : ");
        nameLabel.setHorizontalAlignment(JLabel.RIGHT);                 // sets alignment for the JLabel
        nameLabel.setPreferredSize(new Dimension(50, 25));// sets the size of the JLabel
        nameField = new JTextField();                                   // creates a new JTextField for the name
        nameField.setPreferredSize(new Dimension(200, 25));// sets the size of the JLabel

        // --------------REVIEW---------------
        JLabel reviewLabel = new JLabel("Review : ");
        reviewLabel.setHorizontalAlignment(JLabel.RIGHT);                 // sets alignment for the JLabel
        reviewLabel.setPreferredSize(new Dimension(50, 25));// sets the size of the JLabel
        reviewField = new JTextArea();                                   // creates a new JTextField for the name
        reviewField.setPreferredSize(new Dimension(200, 100));// sets the size of the JLabel


        // ---------------BUTTONS---------------
        // creates two buttons (confirm, return)
        JButton addReviewButton = new JButton("Add Review");
        JButton returnButton = new JButton("Return");
        // adds this class as an ActionListener for the two buttons
        addReviewButton.addActionListener(this);
        returnButton.addActionListener(this);
        // sets ActionCommands for the buttons so they can be differentiated
        addReviewButton.setActionCommand("Add Review");
        returnButton.setActionCommand("return");


        // new panel to hold the buttons is created
        JPanel buttons = new JPanel(new GridLayout(1, 2, 30, 0));
        buttons.setBackground(Window.backgroundColor);
        buttons.setPreferredSize(new Dimension(300, 50));
        buttons.setAlignmentX(CENTER_ALIGNMENT);

        // buttons are added to the panel
        buttons.add(addReviewButton);
        buttons.add(returnButton);

        // adds name details
        productNamePanel.add(nameLabel);
        productNamePanel.add(nameField);

        // adds duration details
        productReviewPanel.add(reviewLabel);
        productReviewPanel.add(reviewField);

        innerPanel.add(productNamePanel);
        innerPanel.add(productReviewPanel);



        // inner panel is added to this class (extends JPanel)
        this.add(innerPanel);
        buttons.setAlignmentX(CENTER_ALIGNMENT);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if ("Add Review".equals(actionEvent.getActionCommand())) {
            // if the confirm button has been pressed

            AddReview.add(productID, nameField.getText(), reviewField.getText());
            JOptionPane.showMessageDialog(this, "Review Successfully Added!");
            Window.startViewReviewsScreen(productID); // return to the main menu

        } else if ("return".equals(actionEvent.getActionCommand())) {
            // if the return button has been pressed
            Window.startMainMenu(); // return to the mainmenu
        }
    }
}
