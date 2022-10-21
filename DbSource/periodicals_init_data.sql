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


INSERT INTO periodical values (DEFAULT, 1, 'Top Gear', DEFAULT, 12300, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 1, 'Autosport', DEFAULT, 15400, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 1, 'Evo', DEFAULT, 12460, '{"amount": 1, "period": "month"}', 1, false);
INSERT INTO periodical values (DEFAULT, 1, 'Classic Driver', DEFAULT, 10590,'{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 2, 'Forbes', DEFAULT, 13090,'{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 2, 'The Insider', DEFAULT, 13100, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 2, 'Fast Company', DEFAULT, 18460, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 2, 'Business Traveler', DEFAULT, 23999, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 3, 'Science', DEFAULT, 35700, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 3, 'Popular Science', DEFAULT, 29999, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 3, 'Android Advisor', DEFAULT, 22360, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 3, 'Macworld', DEFAULT, 18320, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 4, 'The Knitter', DEFAULT, 19999, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 4, 'Mollie', DEFAULT, 19099, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 4, 'Crochet', DEFAULT, 9960, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 4, 'Simply Sewing', DEFAULT, 16620, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 5, 'ARQ', DEFAULT, 34999, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 5, 'Frankie', DEFAULT, 20000, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 5, 'Designlines', DEFAULT, 26010, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 5, 'Interior Design', DEFAULT, 40590, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 6, 'Good Food', DEFAULT, 34010, '{"amount": 1, "period": "month"}', 1, false);
INSERT INTO periodical values (DEFAULT, 6, 'Clean Eating', DEFAULT, 20599, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 6, 'Beer & Brewing', DEFAULT, 18000, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 6, 'Gourmet', DEFAULT, 32910, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 7, 'Retro Gamer', DEFAULT, 32099, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 7, 'Level', DEFAULT, 20900, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 7, 'Minecraft', DEFAULT, 9999, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 7, 'Sjakk', DEFAULT, 10090, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 8, 'Men\'s Health', DEFAULT, 39000, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 8, 'MindFood', DEFAULT, 8900, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 8, 'Yoga', DEFAULT, 12099, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 8, 'Anatomica', DEFAULT, 9000, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 9, 'Canadian Geographic', DEFAULT, 31010, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 9, 'All About Space', DEFAULT, 30900, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 9, 'Storica', DEFAULT, 29999, '{"amount": 1, "period": "month"}', 1, false);
INSERT INTO periodical values (DEFAULT, 9, 'National Geographic', DEFAULT, 50090, '{"amount": 1, "period": "month"}', 1, true);

INSERT INTO periodical values (DEFAULT, 11, 'The Guardian', DEFAULT, 5099, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 11, 'The Daily Telegraph', DEFAULT, 7900, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 11, 'El Universo', DEFAULT, 7120, '{"amount": 1, "period": "month"}', 1, true);
INSERT INTO periodical values (DEFAULT, 11, 'New York Daily News', DEFAULT, 4070, '{"amount": 1, "period": "month"}', 1, true);


SELECT id, title, title_img, price FROM periodical WHERE topic_id=1 AND NOT status=0 ORDER BY id DESC LIMIT 4