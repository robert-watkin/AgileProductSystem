/*
Written By : Robert Watkin
Date Created : 11/10/2019
*/
package ProductSystemAgile;

import database.AddProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GraphicalMainMenu extends JPanel implements ActionListener {

    // Constructor
    public GraphicalMainMenu (){
        initMainMenuButtons();
    }

    // The function below initialises the components within the main menu
    private void initMainMenuButtons() {


        this.setBackground(Window.backgroundColor);
        // Declares integer for the rows of the grid layout
        // This will change as there will be more buttons required for a loggedin user
        int rows = 0;
        if (Window.isCustomer)
            rows = 4;
        else if (Window.isAdmin)
            rows = 6;
        else if (Window.isStaff)
            rows = 5;
        // sets the size of the window
        this.setPreferredSize(new Dimension( 450,(60* rows) + 80));

        // creates the innerpanel with 'rows' as the number of rows
        JPanel innerPanel = new JPanel(new GridLayout((rows/2),2,15,40));
        innerPanel.setBackground(Window.backgroundColor);

        // sets relevent menu title depending on the user type
        JLabel title = new JLabel();

        if (Window.isCustomer)
            title = new JLabel("Store");
        else if (Window.isAdmin)
            title = new JLabel("Stock - Admin");
        else if (Window.isStaff)
            title = new JLabel("Stock - Staff");

        title.setFont(new Font("Arial", Font.PLAIN, 22));
        title.setPreferredSize(new Dimension( 450, 100));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title);

        // main menu buttons are declared and initialised
        JButton addNewProduct = new JButton();
        JButton updateProduct = new JButton();
        JButton viewStock = new JButton();
        JButton FAQButton = new JButton();
        JButton logout = new JButton();


        // action commands for all buttons are added - this lets the program differentiate each button
        addNewProduct.setActionCommand("add new product");
        updateProduct.setActionCommand("update product");
        viewStock.setActionCommand("view stock");
        FAQButton.setActionCommand("open FAQ");
        logout.setActionCommand("logout");

        // sets the size of the create booking button - due to being in a gridlayout, all components in the grid will have this layout
        logout.setPreferredSize(new Dimension(150,75));

        // button alignement is set so they are all centered
        addNewProduct.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateProduct.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        FAQButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);


        // text for all buttons is set
        addNewProduct.setText("Add New Product");
        updateProduct.setText("Update Product");
        FAQButton.setText("Open FAQ");
        logout.setText("Logout");

        // this class is set to all buttons ActionListener - this class implements ActionListener
        addNewProduct.addActionListener(this);
        updateProduct.addActionListener(this);
        viewStock.addActionListener(this);
        FAQButton.addActionListener(this);
        logout.addActionListener(this);


        addNewProduct.setVisible(true);
        updateProduct.setVisible(true);
        viewStock.setVisible(true);
        logout.setVisible(true);

        // if statement checks if the admin is logged in to determine which buttons should be shown
        if (Window.isCustomer){
            viewStock.setText("Browse Products");

        }else if (Window.isAdmin){
            viewStock.setText("View Stock");

            innerPanel.add(addNewProduct);
            innerPanel.add(updateProduct);
        } else if (Window.isStaff){
            viewStock.setText("View Stock");
            innerPanel.add(updateProduct);
        }

        innerPanel.add(viewStock);
        innerPanel.add(FAQButton);
        innerPanel.add(logout);

        // sets the layout of this class (JPanel) to a centered flowlayout
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(innerPanel);
    }

    /*
    the function below is called whenever a button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("===========================");
        System.out.println(actionEvent.getActionCommand());
        if ("add new product".equals(actionEvent.getActionCommand())) {
            // if the create booking button is pressed
            System.out.println("Opening 'Add new product' page...");   // console output for debuggin
            Window.startCreateProductScreen("add");      // create booking screen is called
        }
        if ("view stock".equals(actionEvent.getActionCommand())) {
            // if the view bookings button is pressed
            System.out.println("Opening 'view stock' page...");     // console output for debuggin
            Window.startViewProductScreen();        // view booking screen is called
        }
        if ("open FAQ".equals(actionEvent.getActionCommand())){
            File htmlFile = new File("src/FAQ.html");
            try {
                System.out.println("Opening 'FAQ' page externally...");
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ("logout".equals(actionEvent.getActionCommand())) {
            // if the logout button is pressed
            System.out.println("Logging out...");           // console output for debuggin
            Window.loggedIn = false;                // loggedIn variable is set to false
            Window.isStaff = false;
            Window.isAdmin = false;
            Window.isCustomer = false;
            Window.startLoginScreen();                 // main menu is called
        }
        if ("update product".equals(actionEvent.getActionCommand())){
            System.out.println("Opening 'Update Product' page...");
            Window.startCreateProductScreen("update");
        }
    }
}
