package org.example.models.person;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Full name should not be empty")
    @Size(min = 3, max = 100, message = "Full name should be between 3 and 100 characters")
    private String fullName;

    @Min(value = 1900, message = "Not correct year of birth")
    private int yearOfBirth;

    public Person() {

    }

    public Person(int id, String fullName, int yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", fullName='" + fullName + '\'' +
               ", yearOfBirth=" + yearOfBirth +
               '}';
    }
}
