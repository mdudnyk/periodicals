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
    public static final String GET_TOPICS_WITH_TRANSLATES_BY_LOCALE = """
            SELECT id, COALESCE(
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?),
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?)
                    ) AS name
                FROM topic
                         JOIN topic_translate ON id = topic_id
                GROUP BY id
                ORDER BY name;
                """;
    public static final String GET_TOPICS_WITH_TRANSLATES_BY_LOCALE_PAGINATION_ASC = """
            SELECT id, COALESCE(
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?),
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?)
                    ) AS name
                FROM topic
                         JOIN topic_translate ON id = topic_id
                GROUP BY id
                ORDER BY name
                LIMIT ? OFFSET ?;
                """;
    public static final String GET_TOPICS_WITH_TRANSLATES_BY_LOCALE_PAGINATION_DESC = """
            SELECT id, COALESCE(
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?),
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?)
                    ) AS name
                FROM topic
                         JOIN topic_translate ON id = topic_id
                GROUP BY id
                ORDER BY name DESC
                LIMIT ? OFFSET ?;
                """;
    public static final String GET_TOPICS_WITH_TRANSLATES_BY_NAME_AND_LOCALE_PAGINATION_ASC = """
            SELECT id, COALESCE(
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?),
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?)
                    ) AS name
                FROM topic
                         JOIN topic_translate ON id = topic_id
                WHERE name LIKE CONCAT( '%',?,'%')
                GROUP BY id
                ORDER BY name
                LIMIT ? OFFSET ?;
                """;
    public static final String GET_TOPICS_WITH_TRANSLATES_BY_NAME_AND_LOCALE_PAGINATION_DESC = """
            SELECT id, COALESCE(
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?),
                        (SELECT name
                        FROM topic_translate
                        WHERE topic_id=id AND locale_id=?)
                    ) AS name
                FROM topic
                         JOIN topic_translate ON id = topic_id
                WHERE name LIKE CONCAT( '%',?,'%')
                GROUP BY id
                ORDER BY name DESC
                LIMIT ? OFFSET ?;
                """;
    public static final String GET_TOPIC_BY_ID = "SELECT * FROM topic WHERE id=?";
    public static final String GET_TOPIC_BY_NAME = """
            SELECT id
            FROM topic
                    JOIN topic_translate on topic_id=id
            WHERE name=?;
            """;
    public static final String GET_TOPICS_COUNT = "SELECT COUNT(*) FROM topic";
    public static final String GET_TOPICS_COUNT_SEARCH_MODE = """
            SELECT COUNT(*)
            FROM topic
                     JOIN topic_translate ON id = topic_id
            WHERE name LIKE CONCAT( '%',?,'%');
            """;
    public static final String DELETE_TOPIC = "DELETE FROM topic WHERE id=?";


    //TOPIC_TRANSLATE
    public static final String CREATE_TOPIC_TRANSLATE = "INSERT INTO topic_translate values (?, ?, ?)";
    public static final String GET_ALL_TOPIC_TRANSLATES = "SELECT * FROM topic_translate WHERE topic_id=?";
    public static final String GET_TOPIC_TRANSLATE_BY_LOCALE = "SELECT * FROM topic_translate WHERE topic_id=? AND locale_id=?";
    public static final String UPDATE_TOPIC_TRANSLATE = "UPDATE topic_translate SET name=? WHERE topic_id=? AND locale_id=?";
    public static final String DELETE_TOPIC_TRANSLATE = "DELETE FROM user WHERE topic_id=? AND locale_id=?";
    public static final String TOPIC_TRANSLATE_EXISTS = "SELECT EXISTS(SELECT * FROM topic_translate WHERE topic_id=? AND locale_id=?)";

    //PERIODICAL
    public static final String CREATE_PERIODICAL = "INSERT INTO periodical values (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_PERIODICAL_BY_ID = "SELECT * FROM periodical WHERE id=?";
    public static final String GET_PERIODICALS_FOR_TABLE_PAGINATION_ASC = """
        SELECT DISTINCT id, title, COALESCE(
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?),
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?)
                                    ) AS topic_name, price, status
        FROM periodical
            LEFT JOIN topic_translate ON topic_translate.topic_id=periodical.topic_id
        ORDER BY ?, 2
        LIMIT ? OFFSET ?;
                """;
    public static final String GET_PERIODICALS_FOR_TABLE_PAGINATION_DESC = """
        SELECT DISTINCT id, title, COALESCE(
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?),
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?)
                                    ) AS topic_name, price, status
        FROM periodical
            LEFT JOIN topic_translate ON topic_translate.topic_id=periodical.topic_id
        ORDER BY ? DESC, 2
        LIMIT ? OFFSET ?;
                """;
    public static final String GET_PERIODICALS_FOR_TABLE_BY_TITLE_PAGINATION_ASC = """
        SELECT DISTINCT id, title, COALESCE(
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?),
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?)
                                    ) AS topic_name, price, status
        FROM periodical
            LEFT JOIN topic_translate ON topic_translate.topic_id=periodical.topic_id
        WHERE title LIKE CONCAT( '%',?,'%')
        ORDER BY ?, 2
        LIMIT ? OFFSET ?;
                """;
    public static final String GET_PERIODICALS_FOR_TABLE_BY_TITLE_PAGINATION_DESC = """
        SELECT DISTINCT id, title, COALESCE(
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?),
                                    (SELECT name
                                    FROM topic_translate
                                    WHERE topic_id=periodical.topic_id AND locale_id=?)
                                    ) AS topic_name, price, status
        FROM periodical
            LEFT JOIN topic_translate ON topic_translate.topic_id=periodical.topic_id
        WHERE title LIKE CONCAT( '%',?,'%')
        ORDER BY ? DESC, 2
        LIMIT ? OFFSET ?;
                """;
    public static final String DELETE_PERIODICAL = "DELETE FROM periodical WHERE id=?";
    public static final String GET_PERIODICALS_COUNT = "SELECT COUNT(*) FROM periodical";
    public static final String GET_PERIODICALS_COUNT_SEARCH_MODE = "SELECT COUNT(*) FROM periodical WHERE " +
            "title LIKE CONCAT( '%',?,'%')";
    public static final String GET_IS_PERIODICAL_EXISTS = "SELECT EXISTS(SELECT * FROM periodical WHERE title=? LIMIT 1)";

    //PERIODICAL_TRANSLATE
    public static final String CREATE_PERIODICAL_TRANSLATE = "INSERT INTO periodical_translate values (?, ?, ?, ?, ?)";
    public static final String GET_PERIODICAL_TRANSLATION_BY_PERIODICAL_ID =
            "SELECT * FROM periodical_translate WHERE periodical_id=?";

    //PERIODICAL_RELEASE_CALENDAR
    public static final String CREATE_PERIODICAL_RELEASE_CALENDAR = "INSERT INTO release_calendar values (?, ?, ?)";
    public static final String GET_PERIODICAL_RELEASE_CALENDAR_BY_PERIODICAL_ID =
            "SELECT * FROM release_calendar WHERE periodical_id=?";
}