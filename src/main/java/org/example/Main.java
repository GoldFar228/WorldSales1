package org.example;

public class Main {
    public static void main(String[] args) {
        CountrySales countrySales = new CountrySales();
//        countrySales.loadCSVInTable();
        countrySales.firstTask();
        countrySales.secondTask();
        countrySales.thirdTask();
//        SessionFactory factory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(Country.class)
//                .addAnnotatedClass(Sales.class)
//                .buildSessionFactory();
//        Session session = null;
//        try {
//            session = factory.openSession();
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("1.csv"));
//            bufferedReader.readLine();
//            session.beginTransaction();
//            List<Country> res = session.createQuery(
//                    "from Country " + "where region = 'Europe' OR region = 'Asia'").getResultList();
//            for (Country c: res) {
//                System.out.println(c);
//            }

        //           session.beginTransaction();
//            for (var s: bufferedReader.lines().collect(Collectors.toList())) {
//                Country country = new Country();
//                String[] strings = s.split(",");
//                country.setRegion(strings[0]);
//                country.setCountryName(strings[1]);
//                session.save(country);
//            }
//            session.getTransaction().commit();

//            session.beginTransaction();
//            for (var s: bufferedReader.lines().collect(Collectors.toList())) {
//                Sales sale = new Sales();
//                String[] strings = s.split(",");
//                sale.setItemType(strings[2]);
//                sale.setSalesChannel(strings[3]);
//                sale.setOrderPriority(strings[4]);
//                String input = strings[5];
//                input = input.replace(".", "/");
//                SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
//                Date date = null;
//                try {
//                    date = parser.parse(input);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                String formattedDate = formatter.format(date);
//                date = formatter.parse(formattedDate);
//                sale.setOrderDate(date);
//                sale.setUnitsSold(Integer.parseInt(strings[6]));
//                sale.setTotalProfit(Float.parseFloat(strings[7]));
//                session.save(sale);
//            }
//            session.getTransaction().commit();
//
//        } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        finally {
//            factory.close();
//        }
    }
}