package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Table(name = "sales")
public class Sales {
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

//    @OneToOne(mappedBy = "sales")
//    private Country country;


    @ManyToOne()
    @JoinColumn(name = "CountryId")
    private Country country;

    public Sales(){
    }

    public Sales(String itemType, String salesChannel, String orderPriority, Date orderDate, int unitsSold, float totalProfit, Country country){
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

//    public int getCountryId(){
//        return country != null ? country.getId() : 1000;
//    }
//
//    public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }
    @Override
    public String toString() {
        return "Sales{" +
                "totalProfit='" + totalProfit + '\'' +
                '}';
    }
    public Date convertDate(String str){
        str = str.replace(".", "/");
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = parser.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        try {
            date = formatter.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
    public void loadCSVInTable(){
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Sales.class)
                .addAnnotatedClass(Country.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("1.csv"));
            bufferedReader.readLine();
            session.beginTransaction();
            for (var s: bufferedReader.lines().collect(Collectors.toList())) {
                String[] strings = s.split(",");
                Sales sales = new Sales(strings[2], strings[3], strings[4], convertDate(strings[5]),
                        Integer.parseInt(strings[6]), Float.parseFloat(strings[7]), country);
                session.save(sales);
            }
            session.getTransaction().commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            factory.close();
        }
    }
}
