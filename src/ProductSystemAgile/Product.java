/*
Written By : Robert Watkin
Date Created : 04/11/2019
*/
package ProductSystemAgile;

public class Product {
    int productID;
    String product;
    int stockAmount;
    float price;
    int availability;
    String image;

    public Product(int productID, String product, int stockAmount, float price, int availability, String image) {
        this.productID = productID;
        this.product = product;
        this.stockAmount = stockAmount;
        this.price = price;
        this.availability = availability;
        this.image = image;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
