/*
Written By : Robert Watkin
Date Created : 14/10/2019
*/
package ProductSystemAgile;

import javax.swing.*;
import java.awt.*;
import java.awt.event. ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends JPanel implements ActionListener {
    // VARIABLE DECLARATION
    private JTextField unField;
    private JTextField pwField;

    private HashMap<String, ArrayList<String>> accountValues = new HashMap<>();

    // END OF VARIABLE DECLARATION


    // Constructor
    public LoginScreen (){
        // sets JPanel size
        setPreferredSize(new Dimension(350,250));
        initLoginComponents();  // calls function to initialise components
    }

    // function to initialise panel components
    private void initLoginComponents() {

        // init test accounts
        ArrayList<String> customerAccountValues = new ArrayList<String>();
        ArrayList<String> adminAccountValues = new ArrayList<String>();
        ArrayList<String> staffAccountValues = new ArrayList<String>();

        customerAccountValues.add("Password1");
        customerAccountValues.add("customer");

        adminAccountValues.add("Password1");
        adminAccountValues.add("admin");

        staffAccountValues.add("Password1");
        staffAccountValues.add("staff");

        accountValues.put("customer", customerAccountValues);
        accountValues.put("admin", adminAccountValues);
        accountValues.put("staff", staffAccountValues);
        // end if test account initialization

        //
        // UI INITIALIZATION
        //
        this.setBackground(Window.backgroundColor);
        // sets the layout of the JPanel
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        // two new JPanels are created to hold the components - used for the layout
        JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel innerPanel = new JPanel(new GridLayout(3,2,15,20));

        // creates new label for the menu title
        // VARIABLE DECLARATION
        JPanel p = new JPanel();
        p.setBackground(getBackground());
        JLabel title = new JLabel("Login Page");
        title.setFont(new Font("Arial", Font.PLAIN, 22));
        p.add(title);
        add(p);

        // creates labels to indicate which date the user needs to input into boxes
        JLabel unLabel = new JLabel("Username : ");
        JLabel pwLabel = new JLabel("Password : ");

        // creates to fields to take the users input
        unField = new JTextField();
        pwField = new JPasswordField();

        // sets all to visible
        unLabel.setVisible(true);
        pwLabel.setVisible(true);
        unField.setVisible(true);
        pwField.setVisible(true);

        // sets size of the username label - due to being in a grid layout, all components are set to this size
        unLabel.setPreferredSize(new Dimension(150,20));

        // adds all components to the innerpanel
        innerPanel.add(unLabel);
        innerPanel.add(unField);
        innerPanel.add(pwLabel);
        innerPanel.add(pwField);
        // adds two blank JPanels for spacing
        JPanel e1 = new JPanel();
        JPanel e2 = new JPanel();
        e1.setBackground(Window.backgroundColor);
        e2.setBackground(Window.backgroundColor);
        innerPanel.add(e1);
        innerPanel.add(e2);

        // new JButton is created to log the user in
        JButton loginButton = new JButton("Login");
        loginButton.setActionCommand("login");  // sets the buttons command which will be used when the button is pressed
        loginButton.addActionListener(this);    // adds this class as an actionlistener for the button
        loginButton.setPreferredSize(new Dimension(250, 50));

        // adds both buttons to the innerpanel


        // sets the panels background color based on the global static variable in the Window class
        outerPanel.setBackground(Window.backgroundColor);
        innerPanel.setBackground(Window.backgroundColor);

        // adds the innerpanel to the outerpanel
        outerPanel.add(innerPanel);
        add(outerPanel);    // adds the outerpanel to the overall class which extends JPanel - therefore the class is a JPanel
        add(loginButton);
    }


    private void login(){
        String username = unField.getText();    // set username to the text within the unField
        String password = pwField.getText();    // sets password to the text within the pwField

        System.out.println("Username - " + username + "; Password - " + password);

        accountValues.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
            if (username.equalsIgnoreCase(key)){
                ArrayList<String> passwordAndAccountType = value;
                System.out.println(passwordAndAccountType);
                if (password.equals(passwordAndAccountType.get(0))){
                    if ("customer".equals(passwordAndAccountType.get(1))) {
                        System.out.println("Customer logging in...");
                        Window.loggedIn = true;
                        Window.isCustomer = true;
                        Window.isAdmin = false;
                        Window.isStaff = false;
                    } else if ("admin".equals(passwordAndAccountType.get(1))) {
                        System.out.println("Admin logging in...");
                        Window.loggedIn = true;
                        Window.isCustomer = false;
                        Window.isAdmin = true;
                        Window.isStaff = false;
                    } else if ("staff".equals(passwordAndAccountType.get(1))) {
                        System.out.println("Staff logging in...");
                        Window.loggedIn = true;
                        Window.isCustomer = false;
                        Window.isAdmin = false;
                        Window.isStaff = true;
                    } else {
                        System.out.println("Invalid Input Detected");
                    }
                }
            }
        });
        if (Window.loggedIn){
            Window.startMainMenu();
            return;
        }

        JOptionPane.showMessageDialog(this, "Username or Password is Incorrect... Please Try Again!");
    }

    /*
    The function below is called when any button is pressed
    The function takes an actionevent which is essentially the button which has been pressed
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // if statements used to check the buttons command
        // this helps the program know which button has been pressed
        if("login".equals(actionEvent.getActionCommand())){
            // if the login button has been pressed
            login();
        }
    }
}
