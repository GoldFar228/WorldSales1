package org.example;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "ItemType")
    private String itemType;

    @Column(name = "SalesChannel")
    private String salesChannel;

    @Column(name = "OrderPriority")
    private String orderPriority;

    @Column(name = "OrderDate")
    private Date orderDate;

    @Column(name = "UnitsSold")
    private int unitsSold;

    @Column(name = "TotalProfit")
    private float totalProfit;

    @ManyToOne()
    @JoinColumn(name = "CountryId")
    private Country country;

    public Sale(){
    }

    public Sale(String itemType, String salesChannel, String orderPriority, Date orderDate, int unitsSold, float totalProfit, Country country){
        this.itemType = itemType;
        this.salesChannel = salesChannel;
        this.orderPriority = orderPriority;
        this.orderDate = orderDate;
        this.unitsSold = unitsSold;
        this.totalProfit = totalProfit;
        this.country = country;
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(String orderPriority) {
        this.orderPriority = orderPriority;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }
}
