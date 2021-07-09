package org.ptmk.app;

import org.ptmk.app.entity.Person;
import org.ptmk.app.manager.PersonManager;

import javax.swing.text.DateFormatter;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class myApp {

    public static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    public static String[] generateRandomWords( boolean allFs)
    {
        String[] randomStrings = new String[3];
        Random random = new Random();
        for(int i = 0; i < 3; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            if(!allFs)
            word[0] = (char)('A' + random.nextInt(26));
            else
            word[0] = 'F';
            for(int j = 1; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    public static void main(String[] args) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            getConnection();
            switch (args[0]){
                case "1": PersonManager.createTable();
                break;
                case "2": {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(args[2]);
                    format.applyPattern("yyyy-MM-dd");
                    int i = 7211/365;
                    System.out.println(i);
                    PersonManager.addPerson(new Person(args[1], date, args[3].charAt(0)));
                }
                break;
                case "3":{
                    PersonManager.getAll().forEach(System.out::println);
                }
                break;
                case "4":{
                    Random r = new Random();
                    for (int i = 0; i < 1000000; i++)
                        PersonManager.addPerson( new Person(String.join(" ",Arrays.stream(generateRandomWords(false)).toList()), between(new Date(-1877084666), new Date()), r.nextBoolean() ? 'M' : 'F'));
                    for (int i = 0; i < 100; i++)
                        PersonManager.addPerson( new Person(String.join(" ",Arrays.stream(generateRandomWords(true)).toList()), between(new Date(-1877084666), new Date()), 'M'));
                }
                break;
                case "5":{
                    long start = System.currentTimeMillis();
                    PersonManager.getFiltered().forEach(System.out::println);
                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    System.out.println(timeElapsed + " миллисекунд");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Error connecting to database!");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/testapp", "root", "1234");
    }

}
