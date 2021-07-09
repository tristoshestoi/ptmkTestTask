package org.ptmk.app.manager;


import org.ptmk.app.entity.Person;
import org.ptmk.app.myApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {


    public static void createTable(){
        try {
            Connection c = myApp.getConnection();
            Statement statement = c.createStatement();
            statement.executeUpdate("CREATE TABLE `person` (\n" +
                    "  `ID` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `Full Name` varchar(100) NOT NULL,\n" +
                    "  `BirthDate` datetime NOT NULL,\n" +
                    "  `Sex` enum('M','F') NOT NULL,\n" +
                    "  PRIMARY KEY (`ID`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addPerson(Person person){
        String sql = "INSERT INTO `person` (`FullName`, `BirthDate`, `Sex`) VALUES (?, ?, ?);";
        try {
            Connection c = myApp.getConnection();
            PreparedStatement preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getFullName());
            preparedStatement.setTimestamp(2, new Timestamp(person.getBirthDate().getTime()));
            preparedStatement.setString(3, person.getSex());
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                person.setID(resultSet.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public static List<Person> getAll(){

        List<Person> persons = new ArrayList<>();
        try {
            Connection c = myApp.getConnection();
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *,  datediff(CURRENT_TIMESTAMP, BirthDate) FROM testapp.person group by CONCAT(FullName, BirthDate) order by FullName;");
            while (resultSet.next()){
                persons.add(new Person(resultSet.getInt("ID"),
                        resultSet.getString("FullName"),
                        resultSet.getDate("BirthDate"),
                        resultSet.getString("Sex").charAt(0),
                        resultSet.getInt(5)/365));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return persons;
    }

    public static List<Person> getFiltered(){

        List<Person> persons = new ArrayList<>();
        try {
            Connection c = myApp.getConnection();
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *, datediff(CURRENT_TIMESTAMP, BirthDate) FROM testapp.person where FullName like 'F%' and Sex = 'M'");
            while (resultSet.next()){
                persons.add(new Person(resultSet.getInt("ID"),
                        resultSet.getString("FullName"),
                        resultSet.getDate("BirthDate"),
                        resultSet.getString("Sex").charAt(0),
                        resultSet.getInt(5)/365
                        ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return persons;
    }


}
