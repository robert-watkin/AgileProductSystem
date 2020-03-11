/*
Written By : Robert Watkin
Date Created : 11/03/2020
*/
package ProductSystemAgile;

public class Review {
    private int reviewID;
    private int productID;
    private String name;
    private String Review;

    public Review(int reviewID, int productID, String name, String review) {
        this.reviewID = reviewID;
        this.productID = productID;
        this.name = name;
        Review = review;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }
}
