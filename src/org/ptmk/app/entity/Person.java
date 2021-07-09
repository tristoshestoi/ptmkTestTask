package org.ptmk.app.entity;

import java.util.Date;

public class Person {

    private int ID;
    private String fullName;
    private Date birthDate;
    private char sex;
    private int age;

    public Person(int ID, String fullName, Date birthDate, char sex, int age) {
        this.ID = ID;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.age = age;
    }
    public Person(int ID, String fullName, Date birthDate, char sex) {
        this.ID = ID;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.sex = sex;

    }




    public Person(String fullName, Date birthDate, char sex) {
        this.ID = -1;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public int getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return String.valueOf(sex);
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return
                 fullName + ' ' +
                 birthDate + ' ' +
                 sex + ' ' +
                 age
                ;
    }
}
