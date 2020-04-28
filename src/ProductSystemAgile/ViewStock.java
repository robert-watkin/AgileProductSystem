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
import JTable.JTableButtonRenderer;
import JTable.JTableButtonMouseListener;

public class ViewStock extends JPanel implements ActionListener {

    public ViewStock(){
        initViewProducts();
    }

    private void initViewProducts() {
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

        // creates a new button to show the basket
        JButton viewBasketButton = new JButton("View Basket");
        viewBasketButton.setActionCommand("View Basket");
        viewBasketButton.addActionListener(this);   // adds this class as an ActionListener
        viewBasketButton.setPreferredSize(new Dimension(250,80));   // sets the size for the button
        viewBasketButton.setVisible(true);  // sets the buttons visibility to true

        int cols;
        if (Window.isCustomer)
            cols = 6;
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
            headers[5] = "Add to Basket";
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
                data[i][5] = "";
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
                    button.setFont(new Font("Arial", Font.PLAIN, 12));
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            System.out.println("===================\n" +
                                    "Button clicked for productID " + productList.get(row).getProductID());
                            Window.startViewReviewsScreen(productList.get(row).getProductID());

                        }
                    });
                    return button;
                }
                else if (Window.isCustomer && column == 5) {
                    final JButton button = new JButton("Add to Basket");
                    button.setFont(new Font("Arial", Font.PLAIN, 12));
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            System.out.println("===================\n" +
                                    "Button clicked for to add product ID '" + productList.get(row).getProductID() + "'to basket ");
                            while (true) {
                                if (productList.get(row).getAvailability() == 0){
                                    JOptionPane.showMessageDialog(null, "Sorry! This item is currently unavailable");
                                    break;
                                }
                                if (Basket.getBasket().contains(productList.get(row))){
                                    JOptionPane.showMessageDialog(null, "Sorry! This product is already in your basket");
                                    break;
                                }
                                try {
                                    String quantityS = JOptionPane.showInputDialog("How many would you like to add to basket");
                                    if (quantityS.isEmpty())
                                        break;
                                    int quantity = Integer.parseInt(quantityS);
                                    if (quantity > productList.get(row).getStockAmount()) {
                                        JOptionPane.showMessageDialog(null, "There are not that many " + productList.get(row).getProduct() + "'s available at the moment.");
                                    } else {
                                        Basket.addToBasket(productList.get(row), quantity);
                                        JOptionPane.showMessageDialog(null, productList.get(row).getProduct() + " added successfully");
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "ERROR! Please enter a valid number.");
                                }
                            }
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
        if (Window.isCustomer)
            table.getColumn("Add to Basket").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));

        int c = (Window.isCustomer) ? 2 : 1;

        JPanel buttons = new JPanel(new GridLayout(1, c, 15,0));
        buttons.setPreferredSize(new Dimension(200 * c, 80));
        buttons.setBackground(Window.backgroundColor);

        if (Window.isCustomer)
            buttons.add(viewBasketButton);
        buttons.add(returnButton);

        add(scrollPane, BorderLayout.CENTER);        // adds the scroll area (with the text area inside) ot the JPanel
        add(buttons);  // adds the return button to the JPanel
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
        else if ("View Basket".equals(actionEvent.getActionCommand())){
            Window.startViewBasketScreen(getProducts());
        }
        else {
            Window.startViewReviewsScreen(Integer.parseInt(actionEvent.getActionCommand()));
        }
    }
}
