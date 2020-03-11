/*
Written By : Robert Watkin
Date Created : 11/03/2020
*/
package ProductSystemAgile;

import java.util.ArrayList;

public class Basket {
    private static ArrayList<Product> basket = new ArrayList<Product>();

    public static void addToBasket(Product p, int quantity){
        p.setStockAmount(quantity);
        basket.add(p);
    }

    public static void removeFromBasket(){
        // TODO remove from basket
    }

    public static ArrayList<Product> getBasket(){
        return basket;
    }

    public static void emptyBasket(){
        basket = new ArrayList<Product>();
    }
}
