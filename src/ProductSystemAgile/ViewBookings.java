package ProductSystemAgile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ViewBookings extends JPanel implements ActionListener {
    // VARIABLE DECLARATION
    private JTable table;
    // END OF VARIABLE DECLARATION

    public ViewBookings(){
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

        // new vector to hold the header titles for the table
        Vector headers = new Vector();
        headers.addElement("ID");
        headers.addElement("Name");
        headers.addElement("Duration");
        headers.addElement("Hotel Room");
        headers.addElement("Subtotal");
        headers.addElement("Room Price");
        headers.addElement("Date");

        // tables is initialised with rows and headers
        table = new JTable(rows, headers);
        // disables automatic resizing of columns
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // column width is manually set to ensure room for column content
        table.getColumnModel().getColumn(0).setPreferredWidth(35);
        table.getColumnModel().getColumn(1).setPreferredWidth(137);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(135);

        // The table is added to a scroll pane incase of too many results for the set height of the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650,300));

        // array list holds all of the bookings in the database
        ArrayList<Booking> bookingsList = bookingList();

        // a new table model from the table above is created
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        // object array is created to hold the values within each row of the database
        Object[] row = new Object[7];

        // for loop goes through each row in the database
        for (int i=0; i < bookingsList.size(); i++){
            // cell data from the current row is added to the row array
            row[0] = bookingsList.get(i).getBookingID();
            row[1] = bookingsList.get(i).getName();
            row[2] = bookingsList.get(i).getDuration();
            row[3] = bookingsList.get(i).getHotelRoom();
            row[4] = bookingsList.get(i).getSubtotal();
            row[5] = bookingsList.get(i).getRoomPrice();
            row[6] = bookingsList.get(i).getDate();
            model.addRow(row);  // this row is added to the table model
        }

        add(scrollPane, BorderLayout.CENTER);        // adds the scroll area (with the text area inside) ot the JPanel
        add(returnButton);  // adds the return button to the JPanel
    }

    // the function below is used to gather all entries in the bookings database and return them
    private ArrayList<Booking> bookingList(){
        // an arraylist for the bookings is created
        ArrayList<Booking> bookingList = new ArrayList<>();
        // try catch block in case of SQL exception
        try{
            // connect to the database
            Connection conn = DriverManager.getConnection(Window.getUrl());

            // query to get all data from the bookings table
            String query1 = "SELECT * FROM bookings";
            Statement stmt =  conn.createStatement();   // new statment is created for the table
            ResultSet rs = stmt.executeQuery(query1);   // the statement executes the query and adds the data to a result set

            // uninitialised booking object is created
            Booking booking;

            // while there is a next row in the database
            while(rs.next()){
                // create a new booking object based on the current data
                booking = new Booking(rs.getInt("bookingID"), rs.getString("name"), rs.getInt("holidayDuration"), rs.getString("hotelRoom"),rs.getFloat("subtotal"),rs.getFloat("roomPrice"), rs.getString("startDate"));
                bookingList.add(booking);   // add that booking to the in-memory list
            }
        }
        catch (SQLException e){
            // in case of SQL exception, the error will be printed out.
            System.out.println(e.getMessage());
        }
        // return the list of bookings
        return bookingList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // return to menu when a button is pressed
        // no need for an action command as there is only one button on this window
        Window.startMainMenu();
    }
}
