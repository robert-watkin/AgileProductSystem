/*
Written By : Robert Watkin
Date Created : 11/03/2020
*/
package ProductSystemAgile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class ViewReviews extends JPanel implements ActionListener {

    private int productID;

    public ViewReviews(int productID) {
        this.productID = productID;
        initViewReviews();
    }

    private void initViewReviews() {
        this.setBackground(Window.backgroundColor);
        // sets the size of the window
        setPreferredSize(new Dimension(700,500));
        setLayout(new FlowLayout(FlowLayout.CENTER));   // sets the layout of the window

        // creates a new button to return to the menu
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(this);   // adds this class as an ActionListener
        returnButton.setPreferredSize(new Dimension(250,80));   // sets the size for the button
        returnButton.setVisible(true);  // sets the buttons visibility to true

        // creates a new button to add a review
        JButton createReview = new JButton("New Review");
        createReview.setActionCommand("Create Review");
        createReview.addActionListener(this);
        createReview.setVisible(true);

        JPanel buttons = new JPanel(new GridLayout(2,1, 20,0));
        buttons.setPreferredSize(new Dimension(400, 80));
        buttons.add(createReview);
        buttons.add(returnButton);

        initTable();
        add(buttons);  // adds the return button to the JPanel
    }



    private void initTable() {
        String[] headers = {"Name", "Review"};

        ArrayList<Review> reviewList = getReviews();

        Object[][] data = new Object[reviewList.size()][2];
        // for loop goes through each row in the database
        for (int i=0; i < reviewList.size(); i++){
            //String imgURL = productList.
                data[i][0] = reviewList.get(i).getName();
                data[i][1] = reviewList.get(i).getReview();
        }

        DefaultTableModel model = new DefaultTableModel( data, headers)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        // tables is initialised with rows and headers
        JTable table = new JTable(model);
        // disables automatic resizing of columns

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(500);

        // The table is added to a scroll pane incase of too many results for the set height of the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650,400));

        add(scrollPane, BorderLayout.CENTER);        // adds the scroll area (with the text area inside) ot the JPanel

    }



    private ArrayList<Review> getReviews() {
        // an arraylist for the products is created
        ArrayList<Review> reviewList = new ArrayList<>();
        // try catch block in case of SQL exception
        try {
            // connect to the database
            Connection conn = DriverManager.getConnection(Window.getUrl());

            // query to get all data from the products table
            String query1 = "SELECT * FROM reviews WHERE productID = '" + productID + "'";
            Statement stmt = conn.createStatement();   // new statment is created for the table
            ResultSet rs = stmt.executeQuery(query1);   // the statement executes the query and adds the data to a result set

            // uninitialised product object is created
            Review review;

            // while there is a next row in the database
            while (rs.next()) {
                // create a new product object based on the current data
                review = new Review(rs.getInt("reviewID"), rs.getInt("productID"), rs.getString("name"), rs.getString("review"));
                reviewList.add(review);   // add that product to the in-memory list
            }
        }
        catch (SQLException e){
            // in case of SQL exception, the error will be printed out.
            System.out.println(e.getMessage());
        }
        // return the list of products
        return reviewList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if ("Return".equals(actionEvent.getActionCommand())){
            Window.startViewProductScreen();
        }
        if ("Create Review".equals(actionEvent.getActionCommand())){
            Window.startCreateReviewScreen(productID);
        }
    }
}
