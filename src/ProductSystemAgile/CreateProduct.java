package ProductSystemAgile;

import database.AddProduct;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateProduct extends JPanel implements ActionListener {
    private JPanel innerPanel;
    private JTextField nameField;
    private JTextField stockPriceField;
    private JTextField stockAmountField;
    private JButton selectImage;
    private JFileChooser chooser;
    private String imageUrl = "";
    private String imageButtonText = "Select Image";

    private String mode;
    // END OF VARIABLE DECLARATION

    // Constructor
    public CreateProduct(String mode) {
        this.mode = mode;
        // sets size of window
        setPreferredSize(new Dimension(500, 400));
        initCreateProduct();    // initialises the create booking


    }

    // the funciton below initialises all components of the createBooking window
    private void initCreateProduct() {
        this.setBackground(Window.backgroundColor);
        // sets the layout for the class (extends JPanel) to a new flowlayout
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        String pageTitle = (mode.equalsIgnoreCase("add")) ? "Add Product" : "Update Product";

        JLabel title = new JLabel(pageTitle);
        title.setFont(new Font("Arial", Font.PLAIN, 22));
        title.setPreferredSize(new Dimension(500, 100));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title);

        innerPanel = new JPanel();
        innerPanel.setBackground(Window.backgroundColor);
        innerPanel.setLayout(new GridLayout(5, 2, 15, 20));

        // ---------------NAME---------------
        // creates new JLabel to identify the name field
        JLabel nameLabel = new JLabel("Product Name : ");
        nameLabel.setHorizontalAlignment(JLabel.RIGHT);                 // sets alignment for the JLabel
        nameLabel.setPreferredSize(new Dimension(50, 25));// sets the size of the JLabel
        nameField = new JTextField();                                   // creates a new JTextField for the name
        nameField.setPreferredSize(new Dimension(200, 25));// sets the size of the JLabel



        // ---------------Stock Amount---------------
        // creates new JLabel to identify the duration field
        JLabel stockAmountLabel = new JLabel("Amount : ");
        stockAmountLabel.setHorizontalAlignment(JLabel.RIGHT); // sets the alignment of the JLabel
        stockAmountField = new JTextField();   // initialises the duration combobox with the options in durations



        // ---------------STOCK PRICE---------------
        JLabel stockPriceLabel = new JLabel("Stock Price :");
        stockPriceLabel.setHorizontalAlignment(JLabel.RIGHT);
        stockPriceField = new JTextField();   // initialises the duration combobox with the options in durations


        // ---------------BUTTONS---------------
        // creates two buttons (confirm, return)
        JButton addProductButton = new JButton(pageTitle);
        JButton returnButton = new JButton("Return");
        // set the size of the buttons
//        addProductButton.setPreferredSize(new Dimension(100, 50));
//        returnButton.setPreferredSize(new Dimension(100, 50));
        // adds this class as an ActionListener for the two buttons
        addProductButton.addActionListener(this);
        returnButton.addActionListener(this);
        // sets ActionCommands for the buttons so they can be differentiated
        addProductButton.setActionCommand(pageTitle);
        returnButton.setActionCommand("return");

        // --------------Image-----------------
        JLabel imageLabel = new JLabel("Select Product Image : ");
        imageLabel.setHorizontalAlignment(JLabel.RIGHT);
        selectImage = new JButton(imageButtonText);
        selectImage.setActionCommand("Select Image");
        selectImage.addActionListener(this);


        // new panel to hold the buttons is created

        JPanel buttons = new JPanel(new GridLayout(0, 2, 30, 0));
        buttons.setBackground(Window.backgroundColor);
        buttons.setPreferredSize(new Dimension(300, 50));
        buttons.setAlignmentX(CENTER_ALIGNMENT);

        // buttons are added to the panel
        buttons.add(addProductButton);
        buttons.add(returnButton);

        // adds name details
        innerPanel.add(nameLabel);
        innerPanel.add(nameField);

        // adds duration details
        innerPanel.add(stockAmountLabel);
        innerPanel.add(stockAmountField);

        innerPanel.add(stockPriceLabel);
        innerPanel.add(stockPriceField);

        innerPanel.add(imageLabel);
        innerPanel.add(selectImage);




        // FILE CHOOSER
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);




        // inner panel is added to this class (extends JPanel)
        this.add(innerPanel);
        buttons.setAlignmentX(CENTER_ALIGNMENT);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if ("Select Image".equals(actionEvent.getActionCommand())){
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                System.out.println("File selected successfully...");
                imageButtonText = chooser.getSelectedFile().getName();
                imageUrl = chooser.getSelectedFile().getAbsolutePath();
                selectImage.setText(imageButtonText);
            } else {
                System.out.println("Image selection cancelled...");
            }
        }
        if ("Add Product".equals(actionEvent.getActionCommand())) {
            // if the confirm button has been pressed

            try {

                int availability = ( Integer.parseInt(stockAmountField.getText()) > 0 ) ? 1 : 0;
                AddProduct.add(nameField.getText(), (int) Integer.parseInt(stockAmountField.getText()), Float.parseFloat(stockPriceField.getText()), availability, imageUrl);
                JOptionPane.showMessageDialog(this, "Product Successfully Added!");
                Window.startMainMenu(); // return to the main menu
            } catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error has occurred.. please check all values have been entered correctly");
                System.out.println("An error has occurred.. please check all values have been entered correctly");
            }
        } else if ("Update Product".equals(actionEvent.getActionCommand())) {
            try {
                int availability = ( Integer.parseInt(stockAmountField.getText()) > 0 ) ? 1 : 0;
                int id = Integer.parseInt(JOptionPane.showInputDialog("What is the ID of the product you wish to change?"));
                AddProduct.update(id, nameField.getText(), (int) Integer.parseInt(stockAmountField.getText()), Float.parseFloat(stockPriceField.getText()), availability, imageUrl);
                JOptionPane.showMessageDialog(this, "Product Successfully Added!");
                Window.startMainMenu(); // return to the main menu
            } catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error has occurred.. please check all values have been entered correctly");
                System.out.println("An error has occurred.. please check all values have been entered correctly");
            }
        }else if ("return".equals(actionEvent.getActionCommand())) {
            // if the return button has been pressed
            Window.startMainMenu(); // return to the mainmenu
        }
    }

}
