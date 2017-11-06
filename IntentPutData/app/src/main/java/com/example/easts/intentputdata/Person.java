package com.example.easts.intentputdata;

import java.io.Serializable;

/**
 * Created by easts on 2017/11/1.
 */

public class Person implements Serializable{
    private String username;
    private String password;
    private String sex;

    public Person(String username,String password,String sex){
        this.username = username;
        this.password = password;
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {

        this.sex = sex;
    }
}
