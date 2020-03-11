/*
Written By : Robert Watkin
Date Created : 11/03/2020
*/
package ProductSystemAgile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import JTable.JTableButtonRenderer;
import JTable.JTableButtonMouseListener;
import database.AddProduct;

public class ViewBasket extends JPanel implements ActionListener {

    ArrayList<Product> basketList;
    ArrayList<Product> productList;
    DefaultTableModel model;
    Object[][] data;
    JLabel totalPrice;

    public ViewBasket(ArrayList<Product> productList){
        basketList = Basket.getBasket();
        this.productList = productList;
        initViewBasket();
    }

    private void initViewBasket() {
        this.removeAll();
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

        // creates a new button to return to the menu
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setActionCommand("Purchase");
        purchaseButton.addActionListener(this);   // adds this class as an ActionListener
        purchaseButton.setPreferredSize(new Dimension(250,80));   // sets the size for the button
        purchaseButton.setVisible(true);  // sets the buttons visibility to true


        initTable();

        totalPrice = new JLabel("");
        updatePrice();

        this.add(totalPrice);

        this.add(purchaseButton);
        this.add(returnButton);
    }

    private void updatePrice(){
        float tp = 0;
        if (basketList.size() == 0) {
            totalPrice.setText("Total Price : 0");
            return;
        }
        for (Product p : basketList){
            tp += p.getPrice() * p.getStockAmount();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        totalPrice.setText("Total Price : £" + df.format(tp));
    }

    private void initTable() {
        String[] headers = {"Product", "Price", "Quantity", "Remove Item"};


        // for loop goes through each row in the database
        updateData();

        model = new DefaultTableModel( data, headers)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }

            @Override public Object getValueAt(final int row, final int column) {
                if (column == 3){
                    final JButton button = new JButton("Remove Item (-1)");
                    button.setFont(new Font("Arial", Font.PLAIN, 12));
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            System.out.println("===================\n" +
                                    "Button clicked for productID " + basketList.get(row).getProductID());

                            basketList.get(row).setStockAmount(basketList.get(row).getStockAmount() - 1);
                            boolean removed = false;
                            if (basketList.get(row).getStockAmount() == 0){
                                basketList.remove(row);
                                removed = true;
                            }
                            updateTable(row, removed);

                        }
                    });
                    return button;
                }
                try {
                    return data[row][column];
                } catch (Exception e){
                    return "";
                }
            }
        };

        // tables is initialised with rows and headers
        JTable table = new JTable(model);
        // disables automatic resizing of columns

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Remove Item").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));


        // The table is added to a scroll pane incase of too many results for the set height of the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650,400));

        add(scrollPane, BorderLayout.CENTER);        // adds the scroll area (with the text area inside) ot the JPanel

    }

    private void updateData() {
        data = new Object[basketList.size()][4];
        for (int i=0; i < basketList.size(); i++){
            //String imgURL = productList.
            data[i][0] = basketList.get(i).getProduct();
            data[i][1] = basketList.get(i).getPrice();
            data[i][2] = basketList.get(i).getStockAmount();
            data[i][3] = "BUTTON PLACEHOLDER";
        }
    }

    private void updateTable(int row, boolean removed) {
        updateData();
        if (removed)
            model.removeRow(row);
        model.fireTableDataChanged();
        updatePrice();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if ("Return".equals(actionEvent.getActionCommand())){
            Window.startViewProductScreen();
        }
        if ("Purchase".equals(actionEvent.getActionCommand())){
            purchase();
        }
    }

    private void purchase() {
        for (Product p : basketList){
            int newQuantity = productList.get(p.getProductID()-1).getStockAmount() - p.getStockAmount();
            if (newQuantity <= 0)
                p.setAvailability(0);
            AddProduct.update(p.getProductID(), p.getProduct(),newQuantity, p.getPrice(), p.getAvailability(), p.getImage());
        }
        basketList = null;
        Basket.emptyBasket();
        JOptionPane.showMessageDialog(this, "Purchase Successful! your items should arrive within 2 weeks");
        Window.startMainMenu();
    }
}
