/*
Written By : Robert Watkin
Date Created : 04/11/2019
*/
package ProductSystemAgile;

public class Booking {
    private int bookingID;
    private String name;
    private int duration;
    private String hotelRoom;
    private float subtotal, roomPrice;
    private String date;

    public Booking(int bookingID, String name, int duration, String hotelRoom, float subtotal, float roomPrice, String date) {
        this.bookingID = bookingID;
        this.name = name;
        this.duration = duration;
        this.hotelRoom = hotelRoom;
        this.subtotal = subtotal;
        this.roomPrice = roomPrice;
        this.date = date;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getHotelRoom() {
        return hotelRoom;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public float getRoomPrice() {
        return roomPrice;
    }

    public String getDate() {
        return date;
    }
}
