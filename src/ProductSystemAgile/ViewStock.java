package ProductSystemAgile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ViewStock extends JPanel implements ActionListener {

    public ViewStock(){
        initViewBookings();
    }

    private void initViewBookings() {
        this.setBackground(Window.backgroundColor);
        // sets the size of the window
        setPreferredSize(new Dimension(700,500));
        setLayout(new FlowLayout(FlowLayout.CENTER));   // sets the layout of the window

        // creates a new button to return to the menu
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(this);   // adds this class as an ActionListener
        returnButton.setPreferredSize(new Dimension(250,80));   // sets the size for the button
        returnButton.setVisible(true);  // sets the buttons visibility to true

        // new EMPTY vector to set the tables rows
        Vector rows = new Vector();
        int cols = 0;
        if (Window.isCustomer)
            cols = 4;
        else
            cols = 6;

        // new vector to hold the header titles for the table
        String[] headers = new String[cols];

        if (!Window.isCustomer) {
            headers[0] = "ID";
            headers[1] = "Item";
            headers[2] = "Stock";
            headers[3] = "Price";
            headers[4] = "Availability";
            headers[5] = "Image";
        } else{
            headers[0] = "Item";
            headers[1] = "Price";
            headers[2] = "Availability";
            headers[3] = "Image";
        }

        // array list holds all of the bookings in the database
        ArrayList<Product> productList = bookingList();

        // a new table model from the table above is created

        // object array is created to hold the values within each row of the database


        Object[] row = new Object[cols];
        Object[][] data = new Object[productList.size()][cols];
        // for loop goes through each row in the database
        for (int i=0; i < productList.size(); i++){
            //String imgURL = productList.
            ImageIcon finalImage = new ImageIcon();
            try {
                BufferedImage img = ImageIO.read(new File("src/assets/mug.png"));
                float mult = img.getWidth() / 100;
                float height = img.getHeight() / mult;
                Image resizedImg = img.getScaledInstance(100, (int) height, Image.SCALE_SMOOTH);
                finalImage = new ImageIcon(resizedImg);
            } catch (Exception e){
                e.printStackTrace();
            }

            if (Window.isCustomer) {
                row[0] = productList.get(i).getProduct();
                row[1] = productList.get(i).getPrice();
                row[2] = (productList.get(i).getAvailability() == 1) ? "Available" : "Not Available";
                row[3] = finalImage;
            }
            else {
                // cell data from the current row is added to the row array
                row[0] = productList.get(i).getProductID();
                row[1] = productList.get(i).getProduct();
                row[2] = productList.get(i).getStockAmount();
                row[3] = productList.get(i).getPrice();
                row[4] = (productList.get(i).getAvailability() == 1) ? "Available" : "Not Available";
                row[5] = finalImage;
            }
            data[i] = row;  // this row is added to the table model
        }

        DefaultTableModel model = new DefaultTableModel( data, headers)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                System.out.println("Column : " + column);
                return getValueAt(0, column).getClass();

            }
        };

        // tables is initialised with rows and headers
        JTable table = new JTable(model);
        // disables automatic resizing of columns

        table.setRowHeight(100);

        // The table is added to a scroll pane incase of too many results for the set height of the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650,300));

        add(scrollPane, BorderLayout.CENTER);        // adds the scroll area (with the text area inside) ot the JPanel
        add(returnButton);  // adds the return button to the JPanel
    }

    // the function below is used to gather all entries in the bookings database and return them
    private ArrayList<Product> bookingList(){
        // an arraylist for the bookings is created
        ArrayList<Product> productList = new ArrayList<>();
        // try catch block in case of SQL exception
        try{
            // connect to the database
            Connection conn = DriverManager.getConnection(Window.getUrl());

            // query to get all data from the bookings table
            String query1 = "SELECT * FROM products";
            Statement stmt =  conn.createStatement();   // new statment is created for the table
            ResultSet rs = stmt.executeQuery(query1);   // the statement executes the query and adds the data to a result set

            // uninitialised booking object is created
            Product product;

            // while there is a next row in the database
            while(rs.next()){
                // create a new booking object based on the current data
                product = new Product(rs.getInt("productID"), rs.getString("product"), rs.getInt("stockAmount"), rs.getFloat("price"), rs.getInt("availability"));
                productList.add(product);   // add that booking to the in-memory list
            }
        }
        catch (SQLException e){
            // in case of SQL exception, the error will be printed out.
            System.out.println(e.getMessage());
        }
        // return the list of bookings
        return productList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // return to menu when a button is pressed
        // no need for an action command as there is only one button on this window
        Window.startMainMenu();
    }
}
