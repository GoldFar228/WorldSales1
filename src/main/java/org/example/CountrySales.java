package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CountrySales extends JFrame {

    private Date convertDate(String str) {
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
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Sale.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("1.csv"));
            bufferedReader.readLine();
            session.beginTransaction();
            Set<String> existingCountries = new HashSet<>(); // Создаем множество для хранения уже существующих стран
            for (var s: bufferedReader.lines().collect(Collectors.toList())) {
                String[] strings = s.split(",");
                String countryName = strings[1];
                Country country = null;
                if (existingCountries.contains(countryName)) {
                    // Если страна уже существует, пропускаем эту запись
                    Query query = session.createQuery("from Country where countryName = :name", Country.class);
                    query.setParameter("name", countryName);
                    List<Country> existingCountryList = query.getResultList();
                    if (!existingCountryList.isEmpty()) {
                        country = existingCountryList.get(0);
                    }
                } else {
                    // Если страна не существует, создаем новый объект Country и добавляем его в множество уже существующих
                    country = new Country(strings[0], countryName);
                    existingCountries.add(countryName);
                }
                Sale sale = new Sale(strings[2], strings[3], strings[4], convertDate(strings[5]),
                        Integer.parseInt(strings[6]), Float.parseFloat(strings[7]), country);
                session.save(country);
                session.save(sale);
            }
            session.getTransaction().commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            factory.close();
        }
    }
    public void firstTask(){
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Sale.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            List<Object[]> results = session.createQuery("select c.region, sum(s.unitsSold) as totalQuantity " +
                            "from Sale s " +
                            "join s.country c " +
                            "group by c.region " +
                            "order by totalQuantity desc", Object[].class)
                    .getResultList();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] result : results) {
                String region = (String) result[0];
                long totalQuantity = (long) result[1];
                dataset.addValue(totalQuantity, region, region);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Продажи по регионам",
                    "Регионы",
                    "Продажи",
                    dataset);
            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeGridlinePaint(Color.BLACK);
            ChartFrame frame = new ChartFrame("Bar chart", chart);
            frame.setVisible(true);
            frame.setSize(800, 400);
        } finally {
            factory.close();
        }

    }
    public void secondTask(){
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Sale.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            List<Object[]> results = session.createQuery("select c.countryName, max(s.totalProfit) as maxIncome " +
                            "from Sale s " +
                            "join s.country c " +
                            "where c.region in ('Europe', 'Asia') " +
                            "group by c.countryName " +
                            "order by maxIncome desc", Object[].class)
                    .setMaxResults(1)
                    .getResultList();
            if (!results.isEmpty()) {
                Object[] result = results.get(0);
                String countryName = (String) result[0];
                float totalProfit = (float) result[1];
                System.out.println("Страна с самым высоким общим доходом среди регионов Европы и Азии: " + countryName + " с общим доходом: " + totalProfit);
            }
        } finally {
            factory.close();
        }
    }

    public void thirdTask(){
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Sale.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            List<Object[]> results = session.createQuery(
                            "select c.countryName, max(s.totalProfit) as totalIncome " +
                                    "from Sale s " +
                                    "join s.country c " +
                                    "where c.region in ('Middle East and North Africa', 'Sub-Saharan Africa') and s.totalProfit > 42000 and s.totalProfit < 44000 " +
                                    "group by c.countryName " +
                                    "order by totalIncome desc", Object[].class)
                    .setMaxResults(1)
                    .getResultList();
            if (!results.isEmpty()) {
                Object[] result = results.get(0);
                String countryName = (String) result[0];
                float totalIncome = (float) result[1];
                System.out.println("Страна с самым высоким общим доходом в заданном диапазоне: " + countryName);
                System.out.println("Её доход: " + totalIncome);
            }
        } finally {
            factory.close();
        }
    }
}
