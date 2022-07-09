package items;
import clients.*;
import items.*;
import server.*;
import users.*;
import authentication.*;

public class AuctionItem
{
    private final double startPrice;
    private final String itemDescription;
    private final double reservePrice;
    private double currentPrice;
    private final int uniqueID;
    private final Sellers seller;
    private Buyers buyer;

    public AuctionItem(double startPrice, String itemDescription, double reservePrice, int uniqueID, Sellers seller) {
        this.startPrice = startPrice;
        this.itemDescription = itemDescription;
        this.reservePrice = reservePrice;
        this.currentPrice = startPrice;
        this.uniqueID = uniqueID;
        this.seller = seller;
    }

    public Sellers getSeller() {
        return seller;
    }

    public void setBuyers(Buyers buyer){
        this.buyer = buyer;
    }

    public Buyers getBuyer(){
        return buyer;
    }

    public double getCurrentPrice(){
        return currentPrice;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getReservePrice() {
        return reservePrice;
    }

    public int getUniqueID()
    {
        return uniqueID;
    }
}