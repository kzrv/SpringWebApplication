package cz.kzrv.library.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Person {
    private int id;
    @NotEmpty(message = "Person name should not be empty")
    private String name;
    @Min(value = 1900, message = "year of born should be more than 1900")
    @Max(value = 2022, message = "year of born should be less than 2022")
    private int year;

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
