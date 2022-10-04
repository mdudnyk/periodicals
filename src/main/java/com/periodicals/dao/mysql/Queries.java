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

    //TOPIC
    public static final String CREATE_TOPIC = "INSERT INTO topic values (DEFAULT)";
    public static final String GET_ALL_TOPICS = "SELECT * FROM topic";
    public static final String DELETE_TOPIC = "DELETE FROM topic WHERE id=?";

    //TOPIC_TRANSLATE
    public static final String CREATE_TOPIC_TRANSLATE = "INSERT INTO topic_translate values (?, ?, ?)";
    public static final String GET_ALL_TOPIC_TRANSLATES = "SELECT * FROM topic_translate WHERE topic_id=?";
    public static final String GET_TOPIC_TRANSLATE_BY_LOCALE = "SELECT * FROM topic_translate WHERE topic_id=? AND locale_id=?";
    public static final String UPDATE_TOPIC_TRANSLATE = "UPDATE topic_translate SET name=? WHERE topic_id=? AND locale_id=?";
    public static final String DELETE_TOPIC_TRANSLATE = "DELETE FROM user WHERE topic_id=? AND locale_id=?";
}