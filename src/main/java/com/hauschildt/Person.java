/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt;

/**
 *
 * @author k0519415
 */
public class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;
    private String picture;
    
    public Person() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.picture = "";
    }

    public Person(String firstName, String lastName, String picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    @Override
    public int compareTo(Person other) {
        int result = this.lastName.compareToIgnoreCase(other.lastName);
        if(result == 0) {
            result = this.firstName.compareToIgnoreCase(other.firstName);
        }
        return result;
    }
}
