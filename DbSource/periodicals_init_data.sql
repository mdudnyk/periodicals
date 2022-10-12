INSERT INTO periodicals_db.locale values('ua', 'українська', 'грн', '/img/ua_flag.png');
INSERT INTO periodicals_db.locale values('en', 'english', 'uah', '/img/en_flag.png');

INSERT INTO periodicals_db.user values(1, 'ua', 'admin', 'admin', 'qwerty123', 'press_reader_admin@gmail.com', 'ADMIN', 100000, 'NOT_BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'ua', 'Myroslav', 'Dudnyk', 'qwerty123', 'mdudnyk.sps@gmail.com', 'CUSTOMER', 0, 'NOT_BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'ua', 'Petro', 'Bamper', 'qwerty123', 'petro_bamper@gmail.com', 'CUSTOMER', 1000, 'BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'en', 'John', 'Redneck', 'qwerty123', 'johny_boy_from_texas@gmail.com', 'CUSTOMER', 1000, 'NOT_BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'en', 'Бабушка', 'Міша', 'qwerty123', 'babushka_misha@gmail.com', 'CUSTOMER', 2340, 'NOT_BLOCKED');

INSERT INTO periodicals_db.topic values(1);
INSERT INTO periodicals_db.topic values(2);
INSERT INTO periodicals_db.topic values(3);
INSERT INTO periodicals_db.topic values(4);
INSERT INTO periodicals_db.topic values(5);
INSERT INTO periodicals_db.topic values(6);
INSERT INTO periodicals_db.topic values(7);
INSERT INTO periodicals_db.topic values(8);
INSERT INTO periodicals_db.topic values(9);
INSERT INTO periodicals_db.topic values(10);
INSERT INTO periodicals_db.topic values(11);
INSERT INTO periodicals_db.topic values(12);

INSERT INTO periodicals_db.topic_translate values (1, 'en', 'Automotive');
INSERT INTO periodicals_db.topic_translate values (1, 'ua', 'Автомобілі');
INSERT INTO periodicals_db.topic_translate values (2, 'en', 'Business');
INSERT INTO periodicals_db.topic_translate values (2, 'ua', 'Бізнес та фінанси');
INSERT INTO periodicals_db.topic_translate values (3, 'en', 'Computers & Technology');
INSERT INTO periodicals_db.topic_translate values (3, 'ua', 'Комп`ютери та технології');
INSERT INTO periodicals_db.topic_translate values (4, 'en', 'Crafts & Hobbies');
INSERT INTO periodicals_db.topic_translate values (4, 'ua', 'Хоббі');
INSERT INTO periodicals_db.topic_translate values (5, 'en', 'Design');
INSERT INTO periodicals_db.topic_translate values (5, 'ua', 'Дизайн');
INSERT INTO periodicals_db.topic_translate values (6, 'en', 'Food & Drinks');
INSERT INTO periodicals_db.topic_translate values (6, 'ua', 'Кулінарія');
INSERT INTO periodicals_db.topic_translate values (7, 'en', 'Gaming');
INSERT INTO periodicals_db.topic_translate values (7, 'ua', 'Ігри');
INSERT INTO periodicals_db.topic_translate values (8, 'en', 'Health & Fitness');
INSERT INTO periodicals_db.topic_translate values (8, 'ua', 'Здоров`я та фітнес');
INSERT INTO periodicals_db.topic_translate values (9, 'en', 'History & Science');
INSERT INTO periodicals_db.topic_translate values (9, 'ua', 'Історія та наука');
INSERT INTO periodicals_db.topic_translate values (10, 'en', 'Magazines');
INSERT INTO periodicals_db.topic_translate values (10, 'ua', 'Журнали');
INSERT INTO periodicals_db.topic_translate values (11, 'en', 'News');
INSERT INTO periodicals_db.topic_translate values (11, 'ua', 'Новини');
INSERT INTO periodicals_db.topic_translate values (12, 'en', 'Traveling');
INSERT INTO periodicals_db.topic_translate values (12, 'ua', 'Подорожі');


SELECT id, COALESCE(
        (SELECT name
         FROM topic_translate
         WHERE topic_id=id AND locale_id='ua'),
        (SELECT name
         FROM topic_translate
         WHERE topic_id=id AND locale_id='en')
    ) AS name
FROM topic
         JOIN topic_translate ON id = topic_id
WHERE name LIKE CONCAT( '%',?,'%')
GROUP BY id
ORDER BY name ASC
LIMIT 10 OFFSET 0;

SELECT id, name
FROM topic
        JOIN topic_translate on topic_id = id
WHERE name LIKE ?;

SELECT DISTINCT id, title, COALESCE(
        (SELECT name
         FROM topic_translate
         WHERE topic_id=id AND locale_id=?),
        (SELECT name
         FROM topic_translate
         WHERE topic_id=id AND locale_id=?)
    ) AS topic_name, price, status
FROM periodical
         JOIN topic_translate ON id = topic_translate.topic_id
ORDER BY ? ASC
LIMIT 10 OFFSET 0;

SELECT DISTINCT id, title, COALESCE(
        (SELECT name
         FROM topic_translate
         WHERE topic_id=id AND locale_id='ua'),
        (SELECT name
         FROM topic_translate
         WHERE topic_id=id AND locale_id='en')
    ) AS topic_name, price, status
FROM periodical
         JOIN topic_translate ON id = topic_translate.topic_id
ORDER BY price DESC
LIMIT 10 OFFSET 0;