package com.periodicals.dao.mysql;

class Queries {

    //USER
    public static final String CREATE_USER = "INSERT INTO user values (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    public static final String CHECK_IF_EMAIL_EXISTS = "SELECT EXISTS (SELECT email FROM user WHERE email=?)";
    public static final String UPDATE_USER = "UPDATE user SET locale_id=?, firstname=?, lastname=?, password=?, " +
            "email=?, role=?, balance=?, blocking_status=? WHERE id=?";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";


}
