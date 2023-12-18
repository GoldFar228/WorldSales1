package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private List<Sale> sales = new ArrayList<>();

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
    public void setSalesId(List<Sale> sales) {this.sales = sales;}
}