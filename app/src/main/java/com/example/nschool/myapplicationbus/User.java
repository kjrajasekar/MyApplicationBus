package com.example.nschool.myapplicationbus;

/**
 * Class used as Getter Setter
 * this class used get the values from/to Database
 * and set the values from/to Android pages
 */
public class User {
    private int id;             //variable for user id
    private String name;        //variable for user name
    private String email;       //variable for user email
    private String password;    //variable for user password

    //getter for id
     public int getId() {
        return id;
    }
    //setter for id
    public void setId(int id) {
        this.id = id;
    }
    //getter for name
    public String getName() {
        return name;
    }
    //setter for name
    public void setName(String name) {
        this.name = name;
    }
    //getter for email
    public String getEmail() {
        return email;
    }
    //setter for email
    public void setEmail(String email) {
        this.email = email;
    }
    //getter for password
    public String getPassword() {
        return password;
    }
    //setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}
