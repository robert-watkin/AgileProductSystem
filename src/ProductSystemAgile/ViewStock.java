package ProductSystemAgile;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class ViewStock extends JPanel implements ActionListener {

    public ViewStock(){
        initViewproducts();
    }

    private void initViewproducts() {
        this.setBackground(Window.backgroundColor);
        // sets the size of the window
        setPreferredSize(new Dimension(700,500));
        setLayout(new FlowLayout(FlowLayout.CENTER));   // sets the layout of the window

        // creates a new button to return to the menu
        JButton returnButton = new JButton("Return");
        returnButton.setActionCommand("Return");
        returnButton.addActionListener(this);   // adds this class as an ActionListener
        returnButton.setPreferredSize(new Dimension(250,80));   // sets the size for the button
        returnButton.setVisible(true);  // sets the buttons visibility to true

        int cols;
        if (Window.isCustomer)
            cols = 5;
        else
            cols = 7;

        // new vector to hold the header titles for the table
        String[] headers = new String[cols];

        if (!Window.isCustomer) {
            headers[0] = "ID";
            headers[1] = "Item";
            headers[2] = "Stock";
            headers[3] = "Price";
            headers[4] = "Availability";
            headers[5] = "Image";
            headers[6] = "Reviews";
        } else{
            headers[0] = "Item";
            headers[1] = "Price";
            headers[2] = "Availability";
            headers[3] = "Image";
            headers[4] = "Reviews";
        }

        // array list holds all of the products in the database
        ArrayList<Product> productList = getProducts();

        // a new table model from the table above is created

        // object array is created to hold the values within each row of the database


        Object[][] data = new Object[productList.size()][cols];
        // for loop goes through each row in the database
        for (int i=0; i < productList.size(); i++){
            //String imgURL = productList.

//            JButton reviewButton = new JButton("Reviews");
//            reviewButton.setActionCommand(Integer.toString(productList.get(i).getProductID()));

            ImageIcon finalImage = new ImageIcon();
            String imgUrl = productList.get(i).getImage();
            if (imgUrl != null && !imgUrl.equals("")) {
                try {


                    BufferedImage img = ImageIO.read(new File(imgUrl));
                    float mult = img.getWidth() / 100;
                    float height = img.getHeight() / mult;
                    Image resizedImg = img.getScaledInstance(100, (int) height, Image.SCALE_SMOOTH);
                    finalImage = new ImageIcon(resizedImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (Window.isCustomer) {
                data[i][0] = productList.get(i).getProduct();
                data[i][1] = productList.get(i).getPrice();
                data[i][2] = (productList.get(i).getAvailability() == 1) ? "Available" : "Not Available";
                data[i][3] = finalImage;
            }
            else {
                // cell data from the current row is added to the row array
                data[i][0] = productList.get(i).getProductID();
                data[i][1] = productList.get(i).getProduct();
                data[i][2] = productList.get(i).getStockAmount();
                data[i][3] = productList.get(i).getPrice();
                data[i][4] = (productList.get(i).getAvailability() == 1) ? "Available" : "Not Available";
                data[i][5] = finalImage;
            }
        }

        DefaultTableModel model = new DefaultTableModel( data, headers)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }

            @Override public Object getValueAt(final int row, final int column) {
                if (Window.isCustomer && column == 4 || !Window.isCustomer && column == 6){
                    final JButton button = new JButton("Reviews");
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            System.out.println("===================\n" +
                                    "Button clicked for productID " + productList.get(row).getProductID());
                            Window.startViewReviewsScreen(productList.get(row).getProductID());

                        }
                    });
                    return button;
                }
                return data[row][column];
            }

        };

        // tables is initialised with rows and headers
        JTable table = new JTable(model);
        // disables automatic resizing of columns

        table.setRowHeight(100);

        // The table is added to a scroll pane incase of too many results for the set height of the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650,400));

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Reviews").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));


        add(scrollPane, BorderLayout.CENTER);        // adds the scroll area (with the text area inside) ot the JPanel
        add(returnButton);  // adds the return button to the JPanel
    }



    // the function below is used to gather all entries in the products database and return them
    private ArrayList<Product> getProducts(){
        // an arraylist for the products is created
        ArrayList<Product> productList = new ArrayList<>();
        // try catch block in case of SQL exception
        try{
            // connect to the database
            Connection conn = DriverManager.getConnection(Window.getUrl());

            // query to get all data from the products table
            String query1 = "SELECT * FROM products";
            Statement stmt =  conn.createStatement();   // new statment is created for the table
            ResultSet rs = stmt.executeQuery(query1);   // the statement executes the query and adds the data to a result set

            // uninitialised product object is created
            Product product;

            // while there is a next row in the database
            while(rs.next()){
                // create a new product object based on the current data
                product = new Product(rs.getInt("productID"), rs.getString("product"), rs.getInt("stockAmount"), rs.getFloat("price"), rs.getInt("availability"), rs.getString("image"));
                productList.add(product);   // add that product to the in-memory list
            }
        }
        catch (SQLException e){
            // in case of SQL exception, the error will be printed out.
            System.out.println(e.getMessage());
        }
        // return the list of products
        return productList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // return to menu when a button is pressed
        if ("Return".equals(actionEvent.getActionCommand()))
            Window.startMainMenu();
        else {
            Window.startViewReviewsScreen(Integer.parseInt(actionEvent.getActionCommand()));
        }
    }

    public class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            int row    = e.getY()/table.getRowHeight();

            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton)value).doClick();
                }
            }
        }
    }
    public static class JTableButtonRenderer implements TableCellRenderer {
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            return button;
        }
    }
}
