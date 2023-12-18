package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CountryId")
    private int id;

    @Column(name = "Region")
    private String region;

    @Column(name = "Country")
    private String countryName;

    @OneToMany(mappedBy = "country")
    private List<Sales> sales = new ArrayList<>();

    @Override
    public String toString() {
        return "Country{" +
                "region='" + region + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public Country() {
    }
    public Country(String region, String countryName) {
        this.region = region;
        this.countryName = countryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public void setSalesId(List<Sales> sales) {this.sales = sales;}
//    public List<Sales> getSales() {return sales;}
    public void loadCSVInTable() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Sales.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("1.csv"));
            bufferedReader.readLine();
            session.beginTransaction();
            for (var s : bufferedReader.lines().collect(Collectors.toList())) {
                Country country = new Country();
                String[] strings = s.split(",");
                country.setRegion(strings[0]);
                country.setCountryName(strings[1]);
                session.save(country);
            }
            session.getTransaction().commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
        factory.close();
    }
    }
}