INSERT INTO locale values('uk', 'українська', 'грн', '/img/ua_flag.png');
INSERT INTO locale values('en', 'english', 'uah', '/img/en_flag.png');


INSERT INTO user values(DEFAULT, 'uk', 'admin', 'admin', 'qwerty123', 'admin@gmail.com', 'ADMIN', 1000000, false);
INSERT INTO user values(DEFAULT, 'en', 'Myroslav', 'Dudnyk', 'qwerty123', 'mdudnyk.sps@gmail.com', 'CUSTOMER', 500000, false);


INSERT INTO topic values(1);
INSERT INTO topic values(2);


INSERT INTO topic_translate values (1, 'en', 'Automotive');
INSERT INTO topic_translate values (1, 'uk', 'Автомобілі');
INSERT INTO topic_translate values (2, 'en', 'Business');
INSERT INTO topic_translate values (2, 'uk', 'Бізнес та фінанси');


INSERT INTO periodical values (DEFAULT, 1, 'Top Gear', '166643252774218.jpeg', 16300, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 1, 'Autosport', '166637602575348.jpeg', 15400, '{"amount": 4, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 2, 'Forbes', '166638127316649.jpeg', 25090,'{"amount": 6, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 2, 'Агробізнес сьогодні', '166643194368971.jpeg', 11000, '{"amount": 12, "period": "year"}', true);


INSERT INTO release_calendar values (1, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (1, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (2, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (2, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (3, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (3, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (4, 2022, '[true, false, true, false, true, false, true, false, true, false, true, false]');
INSERT INTO release_calendar values (4, 2023, '[true, false, true, false, true, false, true, false, true, false, true, false]');


INSERT INTO periodical_translate VALUES (1, 'en', 'Great Britain', 'English', 'BBC Top Gear is the UK’s best-selling car magazine.');
INSERT INTO periodical_translate VALUES (1, 'uk', 'Велика Британія', 'Англійська', 'BBC Top Gear — це найпопулярніший автомобільний журнал у Великій Британії.');

INSERT INTO periodical_translate VALUES (2, 'en', 'Great Britain', 'English', 'Autosport is the number one magazine for fans of all things motorsport.');
INSERT INTO periodical_translate VALUES (2, 'uk', 'Велика Британія', 'Англійська', 'Autosport — це журнал номер один для любителів автоспорту.');

INSERT INTO periodical_translate VALUES (3, 'en', 'Great Britain', 'English', 'evo is the world’s leading sports, performance and premium car magazine brand.');
INSERT INTO periodical_translate VALUES (3, 'uk', 'Велика Британія', 'Англійська', 'evo — провідний у світі бренд журналу про спорт і автомобілі преміум-класу.');

INSERT INTO periodical_translate VALUES (4, 'en', 'New Zealand', 'English', 'Founded in 1998, Classic Driver is a leading international marketplace and magazine.');
INSERT INTO periodical_translate VALUES (4, 'uk', 'Нова Зеландія', 'Англійська', 'Classic Driver, заснований у 1998 році, є провідним міжнародним ринком і журналом.');