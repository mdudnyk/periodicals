INSERT INTO periodicals_db.locale values('ua', 'українська', 'UAH', '/img/ua_flag.png');
INSERT INTO periodicals_db.locale values('en', 'english', 'USD', '/img/en_flag.png');

INSERT INTO periodicals_db.user values(1, 'ua', 'admin', 'admin', 'qwerty123', 'press_reader_admin@gmail.com', 'ADMIN', 100000, 'NOT_BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'ua', 'Myroslav', 'Dudnyk', 'qwerty123', 'mdudnyk.sps@gmail.com', 'CUSTOMER', 0, 'NOT_BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'ua', 'Petro', 'Bamper', 'qwerty123', 'petro_bamper@gmail.com', 'CUSTOMER', 1000, 'BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'en', 'John', 'Redneck', 'qwerty123', 'johny_boy_from_texas@gmail.com', 'CUSTOMER', 1000, 'NOT_BLOCKED');
INSERT INTO periodicals_db.user values(DEFAULT, 'en', 'Бабушка', 'Міша', 'qwerty123', 'babushka_misha@gmail.com', 'CUSTOMER', 2340, 'NOT_BLOCKED');

