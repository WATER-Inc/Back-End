package by.fpmibsu.water.dao;


import by.fpmibsu.water.dao.Group;
import by.fpmibsu.water.dao.Identified;

import java.util.Date;

/**
 * Объектное представление сущности Судент.
 */
public class Student implements Identified<Integer> {

    private Integer id = null;
    private String name;
    private String surname;
    private Date enrolmentDate;
    private Group group;

    public Integer getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(Date enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
