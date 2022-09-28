package com.periodicals.dao.mysql;

class Queries {
    //LOCALE
    public static final String CREATE_LOCALE = "INSERT INTO locale values (?, ?, ?, ?)";
    public static final String GET_ALL_LOCALES = "SELECT * FROM locale";
    public static final String GET_LOCALE_BY_ID = "SELECT * FROM locale WHERE short_name_id=?";
    public static final String UPDATE_LOCALE = "UPDATE locale SET lang_name_original=?, currency=?, flag_icon_url=? WHERE short_name_id=?";
    public static final String DELETE_LOCALE = "DELETE FROM locale WHERE short_name_id=?";

    //USER
    public static final String CREATE_USER = "INSERT INTO user values (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    public static final String UPDATE_USER = "UPDATE user SET locale_id=?, firstname=?, lastname=?, password=?, " +
            "email=?, role=?, balance=?, blocking_status=? WHERE id=?";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";

}
