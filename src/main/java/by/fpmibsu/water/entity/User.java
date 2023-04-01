package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

public abstract class User implements Identified<Integer> {
    private Integer userId;
    private String userLogin;
    private String userHashPassword;
    private Contacts friends;
}
