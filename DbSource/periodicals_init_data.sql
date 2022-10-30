INSERT INTO periodicals_db.locale values('ua', 'українська', 'грн', '/img/ua_flag.png');
INSERT INTO periodicals_db.locale values('en', 'english', 'uah', '/img/en_flag.png');



INSERT INTO periodicals_db.user values(1, 'ua', 'admin', 'admin', 'qwerty123', 'admin@gmail.com', 'ADMIN', 100000, false);
INSERT INTO periodicals_db.user values(DEFAULT, 'ua', 'Myroslav', 'Dudnyk', 'qwerty123', 'mdudnyk.sps@gmail.com', 'CUSTOMER', 0, false);
INSERT INTO periodicals_db.user values(DEFAULT, 'ua', 'Petro', 'Bamper', 'qwerty123', 'petro_bamper@gmail.com', 'CUSTOMER', 1000, false);
INSERT INTO periodicals_db.user values(DEFAULT, 'en', 'John', 'Redneck', 'qwerty123', 'johny_boy_from_texas@gmail.com', 'CUSTOMER', 1000, true);
INSERT INTO periodicals_db.user values(DEFAULT, 'en', 'Бабушка', 'Міша', 'qwerty123', 'babushka_misha@gmail.com', 'CUSTOMER', 2340, false);



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
INSERT INTO periodicals_db.topic_translate values (10, 'en', 'Fashion & Lifestyle');
INSERT INTO periodicals_db.topic_translate values (10, 'ua', 'Мода та стиль життя');

INSERT INTO periodicals_db.topic_translate values (11, 'en', 'News');
INSERT INTO periodicals_db.topic_translate values (11, 'ua', 'Новини');
INSERT INTO periodicals_db.topic_translate values (12, 'en', 'Traveling');
INSERT INTO periodicals_db.topic_translate values (12, 'ua', 'Подорожі');



INSERT INTO periodical values (DEFAULT, 1, 'Top Gear', '166643252774218.jpeg', 16300, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 1, 'Autosport', '166637602575348.jpeg', 15400, '{"amount": 4, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 1, 'Evo', '166638052794748.jpeg', 12460, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 1, 'Classic Driver', '166637753059175.jpeg', 17590,'{"amount": 6, "period": "year"}', true);

INSERT INTO periodical values (DEFAULT, 2, 'Forbes', '166638127316649.jpeg', 25090,'{"amount": 6, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 2, 'Агробізнес сьогодні', '166643194368971.jpeg', 11000, '{"amount": 12, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 2, 'Fast Company', '166638080272127.jpeg', 18460, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 2, 'Business Traveler', '166637683464287.jpeg', 14999, '{"amount": 1, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 3, 'Science Illustrated', '166642987402456.jpeg', 35700, '{"amount": 4, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 3, 'Popular Science', '166638645086891.jpeg', 29999, '{"amount": 4, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 3, 'Android Advisor', '166637543272311.jpeg', 22360, '{"amount": 6, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 3, 'Macworld', '166638435359194.jpeg', 18320, '{"amount": 1, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 4, 'The Knitter', '166643221996340.jpeg', 13999, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 4, 'Mollie Magazine', '166643397916850.jpeg', 19099, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 4, 'Crochet!', '166637946244915.jpeg', 7965, '{"amount": 4, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 4, 'Simply Sewing', '166643023414125.jpeg', 16620, '{"amount": 1, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 5, 'ARQ', '166637572894475.jpeg', 12099, '{"amount": 12, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 5, 'Frankie', '166638179703638.jpeg', 20685, '{"amount": 3, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 5, 'Designlines', '166637984667286.jpeg', 26010, '{"amount": 2, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 5, 'Commercial Interior Design', '166638246308754.jpeg', 40590, '{"amount": 6, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 6, 'Sale & Pepe', '166638214479864.jpeg', 14015, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 6, 'Taste', '166637841584057.jpeg', 9999, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 6, 'Beer & Brewing', '166637643666325.jpeg', 28000, '{"amount": 3, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 6, 'Gourmet', '166638287840873.jpeg', 32910, '{"amount": 3, "period": "year"}', true);

INSERT INTO periodical values (DEFAULT, 7, 'Retro Gamer', '166638390935291.jpeg', 12099, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 7, 'Level', '166638322133126.jpeg', 20900, '{"amount": 4, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 7, 'Play', '166638344958660.jpeg', 9999, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 7, 'Edge', '166638368728051.jpeg', 16090, '{"amount": 1, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 8, 'Men\'s Health', '166638534814490.jpeg', 19020, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 8, 'MindFood', '166638554298917.jpeg', 8900, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 8, '220 Triathlon', '166643291975888.jpeg', 12099, '{"amount": 6, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 8, 'Anatomica', '166637491521659.jpeg', 14000, '{"amount": 1, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 9, 'Canadian Geographic', '166637722828442.jpeg', 21010, '{"amount": 6, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 9, 'All About Space', '166637447978859.jpeg', 30900, '{"amount": 4, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 9, 'How It Works', '166643051183797.jpeg', 19999, '{"amount": 1, "period": "month"}', true);
INSERT INTO periodical values (DEFAULT, 9, 'National Geographic', '166638590539446.jpeg', 23035, '{"amount": 1, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 11, 'The Guardian', '166643122978015.jpeg', 5099, '{"amount": 7, "period": "week"}', true);
INSERT INTO periodical values (DEFAULT, 11, 'The Daily Telegraph', '166643085236514.jpeg', 4990, '{"amount": 7, "period": "week"}', true);
INSERT INTO periodical values (DEFAULT, 11, 'El Universo', '166638021593019.jpeg', 5820, '{"amount": 5, "period": "week"}', false);
INSERT INTO periodical values (DEFAULT, 11, 'The Week', '166638624034815.jpeg', 4070, '{"amount": 1, "period": "week"}', true);
INSERT INTO periodical values (DEFAULT, 11, 'Газета по-українськи', '166643359501537.jpeg', 5450, '{"amount": 4, "period": "month"}', true);

INSERT INTO periodical values (DEFAULT, 10, 'Elle Ukraine', '166643469288751.jpeg', 11900, '{"amount": 4, "period": "year"}', true);
INSERT INTO periodical values (DEFAULT, 10, 'Vogue UA', '166643550829927.jpeg', 15000, '{"amount": 1, "period": "month"}', true);



INSERT INTO release_calendar values (1, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (1, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (2, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (2, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (3, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (3, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (4, 2022, '[true, false, true, false, true, false, true, false, true, false, true, false]');
INSERT INTO release_calendar values (4, 2023, '[true, false, true, false, true, false, true, false, true, false, true, false]');

INSERT INTO release_calendar values (5, 2022, '[false, true, false, true, false, true, false, true, false, true, false, true]');
INSERT INTO release_calendar values (5, 2023, '[false, true, false, true, false, true, false, true, false, true, false, true]');

INSERT INTO release_calendar values (6, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (6, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (7, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (7, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (8, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (8, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (9, 2022, '[true, false, false, true, false, false, true, false, false, true, false, false]');
INSERT INTO release_calendar values (9, 2023, '[true, false, false, true, false, false, true, false, false, true, false, false]');

INSERT INTO release_calendar values (10, 2022, '[false, true, false, false, true, false, false, true, false, false, true, false]');
INSERT INTO release_calendar values (10, 2023, '[false, true, false, false, true, false, false, true, false, false, true, false]');


INSERT INTO release_calendar values (11, 2022, '[false, true, false, true, false, true, false, true, false, true, false, true]');
INSERT INTO release_calendar values (11, 2023, '[false, true, false, true, false, true, false, true, false, true, false, true]');

INSERT INTO release_calendar values (12, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (12, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (13, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (13, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (14, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (14, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (15, 2022, '[false, false, true, false, false, true, false, false, true, false, false, true]');
INSERT INTO release_calendar values (15, 2023, '[false, false, true, false, false, true, false, false, true, false, false, true]');

INSERT INTO release_calendar values (16, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (16, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (17, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (17, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (18, 2022, '[false, false, true, false, false, false, true, false, false, false, true, false]');
INSERT INTO release_calendar values (18, 2023, '[false, false, true, false, false, false, true, false, false, false, true, false]');

INSERT INTO release_calendar values (19, 2022, '[false, false, false, false, false, true, false, false, false, false, false, true]');
INSERT INTO release_calendar values (19, 2023, '[false, false, false, false, false, true, false, false, false, false, false, true]');

INSERT INTO release_calendar values (20, 2022, '[true, false, true, false, true, false, true, false, true, false, true, false]');
INSERT INTO release_calendar values (20, 2023, '[true, false, true, false, true, false, true, false, true, false, true, false]');


INSERT INTO release_calendar values (21, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (21, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (22, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (22, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (23, 2022, '[false, false, false, true, false, false, false, true, false, false, false, true]');
INSERT INTO release_calendar values (23, 2023, '[false, false, false, true, false, false, false, true, false, false, false, true]');

INSERT INTO release_calendar values (24, 2022, '[true, false, false, false, true, false, false, false, true, false, false, false]');
INSERT INTO release_calendar values (24, 2023, '[true, false, false, false, true, false, false, false, true, false, false, false]');

INSERT INTO release_calendar values (25, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (25, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (26, 2022, '[true, false, false, true, false, false, true, false, false, true, false, false]');
INSERT INTO release_calendar values (26, 2023, '[true, false, false, true, false, false, true, false, false, true, false, false]');

INSERT INTO release_calendar values (27, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (27, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (28, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (28, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (29, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (29, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (30, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (30, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');


INSERT INTO release_calendar values (31, 2022, '[true, false, true, false, true, false, true, false, true, false, true, false]');
INSERT INTO release_calendar values (31, 2023, '[true, false, true, false, true, false, true, false, true, false, true, false]');

INSERT INTO release_calendar values (32, 2022, '[true, false, true, false, true, false, true, false, true, false, true, false]');
INSERT INTO release_calendar values (32, 2023, '[true, false, true, false, true, false, true, false, true, false, true, false]');

INSERT INTO release_calendar values (33, 2022, '[true, false, true, false, true, false, true, false, true, false, true, false]');
INSERT INTO release_calendar values (33, 2023, '[true, false, true, false, true, false, true, false, true, false, true, false]');

INSERT INTO release_calendar values (34, 2022, '[false, true, false, false, true, false, false, true, false, false, true, false]');
INSERT INTO release_calendar values (34, 2023, '[false, true, false, false, true, false, false, true, false, false, true, false]');

INSERT INTO release_calendar values (35, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (35, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (36, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (36, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (37, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (37, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (38, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (38, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (39, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (39, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (40, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (40, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');


INSERT INTO release_calendar values (41, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (41, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');

INSERT INTO release_calendar values (42, 2022, '[false, true, false, false, true, false, false, true, false, false, true, false]');
INSERT INTO release_calendar values (42, 2023, '[false, true, false, false, true, false, false, true, false, false, true, false]');

INSERT INTO release_calendar values (43, 2022, '[true, true, true, true, true, true, true, true, true, true, true, true]');
INSERT INTO release_calendar values (43, 2023, '[true, true, true, true, true, true, true, true, true, true, true, true]');



INSERT INTO periodical_translate VALUES (1, 'en', 'Great Britain', 'English', 'BBC Top Gear is the UK’s best-selling car magazine and delivers innovative and exciting motoring news and views from across the globe every month. With the best writers Top Gear puts you behind the wheel of the greatest cars money can buy, and with the best automotive photographers, Top Gear is a visual treat every month');
INSERT INTO periodical_translate VALUES (1, 'ua', 'Велика Британія', 'Англійська', 'BBC Top Gear — це найпопулярніший автомобільний журнал у Великій Британії, який щомісяця надає інноваційні та захоплюючі автомобільні новини та погляди з усього світу. З найкращими сценаристами Top Gear дає вам змогу сісти за кермо найкращих автомобілів, які можна купити за гроші, а з найкращими автомобільними фотографами Top Gear — це візуальне задоволення щомісяця');

INSERT INTO periodical_translate VALUES (2, 'en', 'Great Britain', 'English', 'Autosport is the number one magazine for fans of all things motorsport, covering both national and international championships - providing the perfect mix of the latest news and comment, with exclusive interviews with the biggest names in motorsports, as well as insightful analysis of all the key races and events that matter.');
INSERT INTO periodical_translate VALUES (2, 'ua', 'Велика Британія', 'Англійська', 'Autosport — це журнал номер один для любителів автоспорту, який висвітлює як національні, так і міжнародні чемпіонати, забезпечуючи ідеальне поєднання останніх новин і коментарів, ексклюзивні інтерв’ю з найбільшими іменами автоспорту, а також глибокий аналіз усіх ключових гонки та події, які мають значення.');

INSERT INTO periodical_translate VALUES (3, 'en', 'Great Britain', 'English', 'evo is the world’s leading sports, performance and premium car magazine brand. evo is a global automotive performance and luxury media brand produced by the world’s finest writers and most talented automotive photographers in the world');
INSERT INTO periodical_translate VALUES (3, 'ua', 'Велика Британія', 'Англійська', 'evo — провідний у світі бренд журналу про спорт і автомобілі преміум-класу. evo — це глобальний автомобільний бренд, створений найкращими письменниками та найталановитішими автомобільними фотографами світу');

INSERT INTO periodical_translate VALUES (4, 'en', 'New Zealand', 'English', 'Founded in 1998, Classic Driver is a leading international marketplace and magazine for classic and performance cars and the associated lifestyle. The market offers an outstanding selection of cars, bikes, boats and other luxury items from sellers around the world. The magazine attracts passionate readers from more than 220 countries and is highly regarded for its entertainment and editorial quality. Our headquarters are located in Zurich and we have offices in London.');
INSERT INTO periodical_translate VALUES (4, 'ua', 'Нова Зеландія', 'Англійська', 'Classic Driver, заснований у 1998 році, є провідним міжнародним ринком і журналом про класичні та потужні автомобілі та пов’язаний із ними стиль життя. Ринок пропонує чудовий вибір автомобілів, велосипедів, човнів та інших предметів розкоші від продавців по всьому світу. Журнал приваблює пристрасних читачів з більш ніж 220 країн і високо цінується за розважальні та редакційні якості. Наша штаб-квартира розташована в Цюріху, а офіси у Лондоні.');

INSERT INTO periodical_translate VALUES (5, 'en', 'United States', 'English', 'Forbes Magazine is a business magazine that is focused on business news and financial information');
INSERT INTO periodical_translate VALUES (5, 'ua', 'США', 'Англійська', 'Журнал Forbes – це діловий журнал, який орієнтований на ділові новини та фінансову інформацію');

INSERT INTO periodical_translate VALUES (6, 'en', 'Ukraine', 'Ukrainian', 'Multimedia solutions of "Agrobusiness Today" allow farmers to use exclusive information as effectively as possible around the clock for profit. "Agrobusiness Today" has already become a three-in-one communication project, a multimedia platform that allows you to read, see, or listen to useful practical information in video or audio formats. The platform is constantly updated to respond to the challenges of time');
INSERT INTO periodical_translate VALUES (6, 'ua', 'Україна', 'Українська', 'Мультимедійні рішення «Agrobusiness Segodni» дозволяють аграріям цілодобово максимально ефективно користуватися ексклюзивною інформацією для отримання прибутку. «Agrobusiness Segodni» вже став триєдиним комунікаційним проектом, мультимедійною платформою, що дозволяє прочитати, побачити у відео- або послухати аудіоформатах корисну практичну інформацію. Платформа постійно оновлюється, щоб відповідати на викликам часу');

INSERT INTO periodical_translate VALUES (7, 'en', 'United States', 'English', 'Fast Company is the world’s leading business media brand, with an editorial focus on innovation in technology, leadership, world changing ideas, creativity, and design. Written for and about the most progressive business leaders, Fast Company inspires readers to think expansively, lead with purpose, embrace change, and shape the future of business');
INSERT INTO periodical_translate VALUES (7, 'ua', 'США', 'Англійська', 'Fast Company — провідний світовий бізнес-медіа-бренд, редактори якого зосереджуються на інноваціях у технологіях, лідерстві, ідеях, що змінюють світ, креативності та дизайні. Написаний для найпрогресивніших бізнес-лідерів і про них, Fast Company надихає читачів мислити експансивно, керувати цілеспрямовано, сприймати зміни та формувати майбутнє бізнесу');

INSERT INTO periodical_translate VALUES (8, 'en', 'Canada', 'English', 'Launched in the UK in 1976, Business Traveller has become the leading magazine around the world for the frequent corporate traveller. A consumer publication, it is aimed at entertaining business travellers, saving them money and making their travelling life easier. Each edition is packed with editorial on the latest news about airlines, airports, hotels and car rental.');
INSERT INTO periodical_translate VALUES (8, 'ua', 'Канада', 'Англійська', 'Започаткований у Великій Британії в 1976 році, Business Traveler став провідним журналом у всьому світі для тих, хто часто подорожує у справах. Журнал націлений на те, щоб розважити ділових мандрівників, заощадити їм гроші та полегшити життя під час подорожей. Кожен випуск наповнений редакційними статтями про останні новини про авіакомпанії, аеропорти, готелі та прокат автомобілів.');

INSERT INTO periodical_translate VALUES (9, 'en', 'Australia', 'English', 'Science Illustrated is one of the most authoritative, most accessible and most expansive magazines about science and the natural world. Science Illustrated is the magazine for intellectually curious men and women with a passion for science and discovery and adventure and a desire to share that passion with their families');
INSERT INTO periodical_translate VALUES (9, 'ua', 'Австралія ', 'Англійська', 'Science Illustrated — один із найавторитетніших, найдоступніших і найрозширеніших журналів про науку та світ природи. Science Illustrated — це журнал для інтелектуально допитливих чоловіків і жінок із пристрастю до науки, відкриттів і пригод і бажанням поділитися цією пристрастю зі своїми сім’ями');

INSERT INTO periodical_translate VALUES (10, 'en', 'United States', 'English', 'Discovery and innovation are reshaping the world around us, and Popular Science magazine makes even the most complex ideas entertaining and accessible. This is the most exciting time to be alive in history. Get Popular Science digital magazine subscription today and see why. By taking an upbeat, solutions-oriented look at today\'s most audacious science and revolutionary technology, we forecast what tomorrow will be like. We deliver the future now');
INSERT INTO periodical_translate VALUES (10, 'ua', 'США', 'Англійська', 'Відкриття та інновації змінюють світ навколо нас, а журнал Popular Science робить навіть найскладніші ідеї цікавими та доступними. Це найцікавіший час життя в історії. Отримайте підписку на цифровий журнал Popular Science сьогодні та дізнайтеся чому. Оптимістично дивлячись на найсміливішу науку та революційні технології сучасності, ми прогнозуємо, яким буде завтра. Ми забезпечуємо майбутнє зараз');


INSERT INTO periodical_translate VALUES (11, 'en', 'Australia', 'English', 'Android Advisor is your go-to source for everything Android, from smartphones, tablets and the best mobile apps to wearable and in-car tech, optimised for the small screen of your mobile device. Whether you\'re new to the Android operating system or a seasoned user, you\'re sure to find something you like in Android Advisor\'s wealth of analysis, reviews, features and tutorials. Android Advisor will not only help you to choose the best phone or tablet for your needs, but also to get more out of that device.');
INSERT INTO periodical_translate VALUES (11, 'ua', 'Австралія', 'Англійська', 'Android Advisor — це ваше найкраще джерело для всього що про Android, від смартфонів, планшетів і найкращих мобільних додатків до носимих і автомобільних технологій, оптимізованих для маленького екрана вашого мобільного пристрою. Незалежно від того, чи ви новачок у роботі з операційною системою Android, чи досвідчений користувач, ви обов’язково знайдете те, що вам сподобається в багатстві аналізу, оглядів, функцій і посібників Android Advisor. Android Advisor допоможе вам не тільки вибрати найкращий телефон або планшет для ваших потреб, але й отримати більше від цього пристрою.');

INSERT INTO periodical_translate VALUES (12, 'en', 'Australia', 'English', 'Macworld is the world’s biggest Mac magazine exclusively covering Apple’s ever-growing range of popular hardware and software products. Each month, Macworld brings readers the very latest news, reviews, tips and expert advice on everything Apple, the Mac and OS X');
INSERT INTO periodical_translate VALUES (12, 'ua', 'Австралія', 'Англійська', 'Macworld — це найбільший у світі журнал Mac, який ексклюзивно висвітлює постійно зростаючий асортимент популярних апаратних і програмних продуктів Apple. Щомісяця Macworld пропонує читачам найсвіжіші новини, огляди, поради та поради експертів щодо всього, що стосується Apple, Mac і OS X');

INSERT INTO periodical_translate VALUES (13, 'en', 'Australia', 'English', 'The Knitter caters for skilled knitters with more than 10 challenging patterns in each issue. The Knitter has beautiful, original patterns and inspiration from world-class designers. Our patterns aren’t just fabulous to look at, they’re enjoyable to make, with a few unusual techniques and intriguing ways with yarns for you to try');
INSERT INTO periodical_translate VALUES (13, 'ua', 'Австралія', 'Англійська', 'Knitter обслуговує досвідчених в’язальниць із більш ніж 10 складними візерунками в кожному випуску. В’язальниця має гарні оригінальні візерунки та натхнення від дизайнерів світового рівня. Наші візерунки не просто чудові на вигляд, їх приємно створювати, завдяки декільком незвичайним технікам і інтригуючим способам пряжі, які ви можете спробувати');

INSERT INTO periodical_translate VALUES (14, 'en', 'Great Britain', 'English', 'Discover the new Mollie magazine and be inspired to live a creative life. Find the joy in making and create a beautiful home with your own hands. Enjoy 14 modern trend-led projects per issue from influential designers and makers including old favourites such as sewing, knitting and crochet. Explore your creativity further through embroidery, punch needle, art, upcycling, DIY, macrame and clay PLUS delicious bakes to make & share');
INSERT INTO periodical_translate VALUES (14, 'ua', 'Велика Британія', 'Англійська', 'Відкрийте для себе новий журнал Mollie і отримуйте натхнення жити творчим життям. Знайдіть радість у виготовленні та створіть гарний будинок своїми руками. Насолоджуйтеся 14 сучасними трендовими проектами в кожному випуску від впливових дизайнерів і творців, включаючи старі фаворити, як-от шиття, в’язання та в’язання гачком. Досліджуйте свою творчість далі за допомогою вишивки, перфорації, мистецтва, переробки, DIY, макраме та глини PLUS смачної випічки, щоб зробити та поділитися');

INSERT INTO periodical_translate VALUES (15, 'en', 'United States', 'English', 'Crochet! magazine, featuring current trends in crochet design, was originally published as Crochet Home, beginning in 1988');
INSERT INTO periodical_translate VALUES (15, 'ua', 'США', 'Англійська', 'Crochet! -  журнал, який показує сучасні тенденції в дизайні в’язання гачком, спочатку видавався як Crochet Home, починаючи з 1988 року');

INSERT INTO periodical_translate VALUES (16, 'en', 'Great Britain', 'English', 'Simply Sewing is a practical magazine for makers who sew, or would like to start sewing. Its contemporary look and feel, fresh attitude and ideas, beautiful photography and inspiring projects will have you behind the sewing machine from the get-go!');
INSERT INTO periodical_translate VALUES (16, 'ua', 'Велика Британія', 'Англійська', 'Simply Sewing — практичний журнал для майстрів, які шиють або хотіли б почати шити. Його сучасний вигляд і відчуття, свіже ставлення та ідеї, прекрасна фотографія та надихаючі проекти змусять вас сісти за швейну машину з самого початку!');

INSERT INTO periodical_translate VALUES (17, 'en', 'Argentina', 'Spanish', 'ARQ publishes cutting-edge work covering all aspects of architectural endeavour. Contents include building design, urbanism, history, theory, environmental design, construction, materials, information technology, and practice. Other features include interviews, occasional reports, lively letters pages, book reviews and an end feature, Insight. Reviews of significant buildings are published at length and in a detail matched today by few other architectural journals. Elegantly designed, inspirational and often provocative, arq is essential reading for practitioners in industry and consultancy as well as for academic researchers.');
INSERT INTO periodical_translate VALUES (17, 'ua', 'Аргентина', 'Іспанська', 'ARQ публікує передові роботи, що охоплюють усі аспекти архітектурної діяльності. Зміст включає проектування будівель, урбанізм, історію, теорію, екологічний дизайн, будівництво, матеріали, інформаційні технології та практику. Інші функції включають інтерв’ю, випадкові звіти, живі сторінки з листами, огляди книг і кінцеву функцію, Insight. Огляди значних будівель публікуються довго й детально, з якими сьогодні можна порівняти лише в небагатьох інших архітектурних журналах. Елегантно оформлений, надихаючий і часто провокативний, arq є необхідним для читання для практиків у промисловості та консалтингу, а також для академічних дослідників.');

INSERT INTO periodical_translate VALUES (18, 'en', 'Australia', 'English', 'Frankie features are more than your average magazine articles – if you can imagine your idea running in another publication, then you’ll have to re-think your pitch. Our readers expect the unexpected every time they open a new issue');
INSERT INTO periodical_translate VALUES (18, 'ua', 'Австралія', 'Англійська', 'Frankie — це більше, ніж звичайні журнальні статті. Якщо ви можете уявити, що ваша ідея буде представлена в іншому австралійському виданні, вам доведеться переосмислити своє представлення. Наші читачі щоразу, відкриваючи новий номер, чекають несподіваного');

INSERT INTO periodical_translate VALUES (19, 'en', 'Canada', 'English', 'Published two times a year, Designlines is the ultimate guide to Toronto’s best contemporary furniture showrooms and decor shops, as well as the city’s most innovative residential projects');
INSERT INTO periodical_translate VALUES (19, 'ua', 'Канада', 'Англійська', 'Designlines, що виходить два рази на рік — це найкращий путівник по найкращим салонам сучасних меблів і магазинам декору в Торонто, а також по найбільш інноваційним житловим проектам міста.');

INSERT INTO periodical_translate VALUES (20, 'en', 'United Arab Emirates', 'English', 'Commercial Interior Design is the definitive print and digital B2B platform for the region’s design community. Now in its 18th year, it features news, interviews, case studies and market reports, making it the industry’s most sought after publication on interior design with a special focus on the commercial and contract sectors');
INSERT INTO periodical_translate VALUES (20, 'ua', 'ОАЕ', 'Англійська', 'Commercial Interior Design — це друкована та цифрова B2B платформа для дизайнерської спільноти. Уже 18-й рік випуску, він містить новини, інтерв’ю, тематичні дослідження та ринкові звіти, що робить його найпопулярнішим виданням про дизайн інтер’єру в галузі з особливим акцентом на комерційному та контрактному секторах');


INSERT INTO periodical_translate VALUES (21, 'en', 'Italy', 'Italian', 'For over 35 years Sale e Pepe has been Italy\'s best-loved food magazine, a monthly that\'s produced in its design capital, Milan. Sale e Pepe\'s delicious recipes and beautiful photography have inspired several generations of Italian cooks to widen their culinary horizons. We\'ve loved introducing them to the hidden gems of Italian gastronomy, the specialities that each region of this amazingly diverse country can boast');
INSERT INTO periodical_translate VALUES (21, 'ua', 'Італія', 'Італійська', 'Понад 35 років Sale e Pepe був найулюбленішим журналом про їжу в Італії, щомісячником, який виходить у столиці дизайну, Мілані. Смачні рецепти Sale e Pepe і чудові фотографії надихнули кілька поколінь італійських кухарів розширювати свої кулінарні горизонти. Нам подобалося знайомити їх із прихованими перлинами італійської гастрономії, особливостями, якими може похвалитися кожен регіон цієї надзвичайно різноманітної країни');

INSERT INTO periodical_translate VALUES (22, 'en', 'Australia', 'English', 'From Australia\'s favourite food and lifestyle comes an exciting new food experience - Taste magazine. The magazine brings to life all new and exclusive recipes that are a joy to make and eat, whether you\'re after a healthy midweek meal or something lusciously indulgent.');
INSERT INTO periodical_translate VALUES (22, 'ua', 'Австралія', 'Англійська', 'Улюблена їжа та австралійський стиль життя – журнал Taste. Журнал оживляє всі нові та ексклюзивні рецепти, які приємно готувати та їсти, незалежно від того, чи хочете ви здорово поїсти посеред тижня чи приготувати якусь ексклюзивну страву');

INSERT INTO periodical_translate VALUES (23, 'en', 'United States', 'English', 'Craft Beer & Brewing is a magazine for people who love to drink and make great beer');
INSERT INTO periodical_translate VALUES (23, 'ua', 'США', 'Англійська', 'Craft Beer & Brewing — це журнал для людей, які люблять пити та готувати чудове пиво');

INSERT INTO periodical_translate VALUES (24, 'en', 'Sweden', 'Swedish', 'We are the leading food magazine in Sweden. We highlight the diversity of chefs, sommeliers, bartenders, producers, breeders and gourmets who oversee and create food and drink around the world. Gourmet is constantly on the culinary scene. Welcome to Gourmet - Sweden\'s hottest water magazine!');
INSERT INTO periodical_translate VALUES (24, 'ua', 'Швеція', 'Швецька', 'Ми є провідним журналом про їжу в Швеції. Ми висвітлюємо різноманітність шеф-кухарів, сомельє, барменів, виробників, заводчиків і гурманів, які контролюють і виготовляють їжу та напої у всьому світі. Gourmet постійно на кулінарній арені. Ласкаво просимо до Gourmet - найгарячішого водного журналу Швеції!');

INSERT INTO periodical_translate VALUES (25, 'en', 'Great Britain', 'English', 'Retro Gamer offers an experience not available anywhere else in the world. Celebrating video games exciting past with style and purpose, it is both an intelligent and emotional rediscovery of the IPs that defined an industry. Famed for its informative and in-depth stories, access to legendary developers and its sheer enthusiasm for the games it covers, the award-winning Retro Gamer is a worldwide phenomenon universally loved by an entire industry');
INSERT INTO periodical_translate VALUES (25, 'ua', 'Велика Британія', 'Англійська', 'Retro Gamer пропонує досвід, недоступний більше ніде у світі. Відзначаючи захоплююче минуле відеоігор зі стилем і цілеспрямованістю, це інтелектуальне й емоційне повторне відкриття IP, які визначили індустрію. Відомий своїми інформативними й глибокими історіями, доступом до легендарних розробників і справжнім ентузіазмом щодо ігор, про які він розповідає, Retro Gamer, відзначений нагородами, є світовим явищем, яке любить ціла індустрія');

INSERT INTO periodical_translate VALUES (26, 'en', 'Turkey', 'Turkish', 'LEVEL is the best-selling gaming magazine in Turkey. We write news, special news, reviews and articles. We update every day. Not to mention the abundance of recent news');
INSERT INTO periodical_translate VALUES (26, 'ua', 'Туреччина', 'Турецька', 'LEVEL - найбільш продаваний ігровий журнал в Туреччині. Пишемо новини, спеціальні новини, огляди та статті. Ми оновлюємося кожен день. Не кажучи вже про велику кількість останніх новин');

INSERT INTO periodical_translate VALUES (27, 'en', 'Australia', 'English', 'The magazine formerly known as OPS is now PLAY Australia! It\'s Australia\'s number one source for all things PlayStation 5, PlayStation 4 and PlayStation VR');
INSERT INTO periodical_translate VALUES (27, 'ua', 'Австралія', 'Англійська', 'Журнал, раніше відомий як OPS, тепер називається PLAY Australia! Це джерело номер один в Австралії для PlayStation 5, PlayStation 4 і PlayStation VR');

INSERT INTO periodical_translate VALUES (28, 'en', 'Australia', 'English', 'Edge is the leading multi-format video game magazine, with masterfully written articles for all things video games. Edge dives into gaming’s 8-bit roots all the way to the behemoths of the current age. Beautifully designed, Edge magazine is renowned for its stunning artwork which is unrivalled by any other gaming magazine out there');
INSERT INTO periodical_translate VALUES (28, 'ua', 'Австралія', 'Англійська', 'Edge — це провідний багатоформатний журнал про відеоігри з майстерно написаними статтями про все, що стосується відеоігор. Edge занурюється в 8-бітне коріння ігор аж до гігантів сучасної епохи. Красиво оформлений журнал Edge відомий своїм приголомшливим малюнком, який не має собі рівних у жодному іншому ігровому журналі');

INSERT INTO periodical_translate VALUES (29, 'en', 'Great Britain', 'English', 'Bringing you the world of health and fitness, Men’s Health magazine is full of workout regimes, nutrition plans, celebrity interviews and much more. The guide to getting your muscles defined, Men’s Health is the UK’s number one fitness and wellbeing magazine for men. No matter what your fitness goal is, Men’s Health magazine will help you get there with their expert tips on building muscle, losing weight and beating stress');
INSERT INTO periodical_translate VALUES (29, 'ua', 'Велика Британія', 'Англійська', 'Представляючи вам світ здоров’я та фітнесу, журнал Men’s Health повний режимів тренувань, планів харчування, інтерв’ю зі знаменитостями та багато іншого. Посібник із формування м’язів, Men’s Health – це журнал номер один у Великій Британії про фітнес і добробут для чоловіків. Незалежно від того, яка ваша фітнес-ціль, журнал Men’s Health допоможе вам досягти її своїми експертними порадами щодо нарощування м’язів, схуднення та подолання стресу');

INSERT INTO periodical_translate VALUES (30, 'en', 'Australia', 'English', 'A mix of community, wellness, environment, culture, travel, fashion and food, Mindfood is smart, creative and very cool. Perfect for thinking men and women who crave intelligent, inspirational ideas and information');
INSERT INTO periodical_translate VALUES (30, 'ua', 'Австралія', 'Англійська', 'Mindfood – це поєднання спільноти, здоров’я, навколишнього середовища, культури, подорожей, моди та їжі. Mindfood розумний, креативний і дуже крутий. Ідеально підходить для мислячих чоловіків і жінок, які жадають розумних, надихаючих ідей та інформації');


INSERT INTO periodical_translate VALUES (31, 'en', 'Great Britain', 'English', '220 Triathlon Magazine is your indispensible triathlon training partner. Each month we feature technique and session advice from the world’s best coaches; test the latest gear to hit the shops; profile the greatest athletes; and hear from our exclusive columnist, Chrissie Wellington. Whether you are a beginner or Ironman, you will race better with 220 Triathlon');
INSERT INTO periodical_translate VALUES (31, 'ua', 'Велика Британія', 'Англійська', 'Журнал 220 Triathlon — ваш незамінний партнер у тренуваннях з триатлону. Щомісяця ми пропонуємо поради щодо техніки та тренувань від найкращих тренерів світу; випробувати найновіше обладнання, яке з’явиться в магазинах; профіль найбільших спортсменів; і послухайте нашого ексклюзивного оглядача Кріссі Веллінгтон. Незалежно від того, новачок ви чи Ironman, з 220 Triathlon ви будете краще брати участь у перегонах');

INSERT INTO periodical_translate VALUES (32, 'en', 'Uzbekistan', 'English', 'The main task of the magazine is to provide a platform for scientists of various humanitarian specialties to discuss the problems of culture and cultural studies. The magazine publishes materials related to the study of various cultural practices.');
INSERT INTO periodical_translate VALUES (32, 'ua', 'Узбекистан', 'Англійська', 'Головне завдання журналу – надати майданчик вченим різних гуманітарних спеціальностей для обговорення проблем культури та культурології. Журнал публікує матеріали, пов\'язані з вивчення різних культурних практик.');

INSERT INTO periodical_translate VALUES (33, 'en', 'Canada', 'English', 'Comprehensive, authoritative, delightfully readable and fabulously illustrated with colour photographs, maps, charts, diagrams and pull-out posters. As a means of understanding Canada’s history, people, places, culture, environment and wildlife, there is no other magazine like Canadian Geographic');
INSERT INTO periodical_translate VALUES (33, 'ua', 'Канада', 'Англійська', 'Вичерпний, авторитетний, чудово читабельний і неймовірно ілюстрований кольоровими фотографіями, картами, діаграмами, діаграмами та висувними плакатами. Як засіб розуміння історії Канади, людей, місць, культури, навколишнього середовища та дикої природи, немає іншого журналу, як Canadian Geographic');

INSERT INTO periodical_translate VALUES (34, 'en', 'Great Britain', 'English', '"All About Space"  is the premier periodical of space exploration, innovation and astronomy news, chronicling (and celebrating) humanity\'s ongoing expansion across the final frontier. We transport our visitors across the solar system and beyond through accessible, comprehensive coverage of the latest news and discoveries.');
INSERT INTO periodical_translate VALUES (34, 'ua', 'Велика Британія', 'Англійська', '«All About Space» — це провідне періодичне видання новин про дослідження космосу, інновації та астрономію, яке описує (і вшановує) триваючу експансію людства через останній рубіж. Ми переміщаємо наших відвідувачів по всій Сонячній системі та за її межами завдяки доступному, вичерпному висвітленню останніх новин і відкриттів.');

INSERT INTO periodical_translate VALUES (35, 'en', 'Great Britain', 'English', 'Welcome to How It Works, the magazine that explains everything you never knew you wanted to know about the world we live in. Loaded with fully illustrated guides and expert knowledge, and with sections dedicated to science, technology, transportation, space, history and the environment, no subject is too big or small for How It Works to explain');
INSERT INTO periodical_translate VALUES (35, 'ua', 'Велика Британія', 'Англійська', 'Ласкаво просимо до журналу How It Works, який пояснює все, що ви навіть не підозрювали, що хотіли знати про світ, у якому ми живемо. Він містить повністю ілюстровані посібники та експертні знання, а також розділи, присвячені науці, технологіям, транспорту, космосу, історії та середовища, жоден предмет не є занадто великим або малим, щоб пояснити How It Works');

INSERT INTO periodical_translate VALUES (36, 'en', 'Spain', 'Spanish', 'See how National Geographic History magazine inflames and quenches the curiosity of history buffs and informs and entertains anyone who appreciates that the truth indeed is stranger than fiction with a digital subscription today. And that history is not just about our forebears. It’s about us. It’s about you');
INSERT INTO periodical_translate VALUES (36, 'ua', 'Іспанія', 'Іспанська', 'Подивіться, як журнал National Geographic History розпалює та вгамовує цікавість любителів історії та інформує та розважає всіх, хто цінує, що правда справді дивніша за вигадку, завдяки цифровій підписці сьогодні. І ця історія стосується не лише наших предків. Це про нас. Це про вас');

INSERT INTO periodical_translate VALUES (37, 'en', 'Great Britain', 'English', 'Guardian Media Group is a global news organisation that delivers fearless, investigative journalism - giving a voice to the powerless and holding power to account. Our independent ownership structure means we are entirely free from political and commercial influence. Only our values determine the stories we choose to cover – relentlessly and courageously');
INSERT INTO periodical_translate VALUES (37, 'ua', 'Велика Британія', 'Англійська', 'Guardian Media Group — це глобальна новинна організація, яка займається безстрашною журналістикою-розслідуванням, надаючи голос безправним і притягаючи владу до відповідальності. Наша незалежна структура власності означає, що ми повністю вільні від політичного та комерційного впливу. Тільки наші цінності визначають історії, які ми вирішуємо висвітлювати – невпинно та сміливо');

INSERT INTO periodical_translate VALUES (38, 'en', 'Great Britain', 'English', 'The Telegraph is an award-winning, multimedia news brand that has been synonymous with quality, authority and credibility for more than 165 years. Over the years our journalists have reported on the events that have shaped the world, bearing witnesses to history. We continue to set the news agenda, spark debate, and serve our readers on a daily basis');
INSERT INTO periodical_translate VALUES (38, 'ua', 'Велика Британія', 'Англійська', 'The Telegraph — це нагороджений мультимедійний новинний бренд, який уже понад 165 років є синонімом якості, авторитету та довіри. Протягом багатьох років наші журналісти повідомляли про події, які сформували світ, стаючи свідками історії. Ми продовжуємо визначати порядок денний новин, розпалювати дебати та щоденно обслуговувати наших читачів');

INSERT INTO periodical_translate VALUES (39, 'en', 'Ecuador', 'Spanish', 'Clearly, honestly, impartially, truthfully and on time, to constantly inform about what is happening in the city, the country and the world. And also offer elements for reflection on current events from their editorial spaces, analysis, investigations and in-depth journalism');
INSERT INTO periodical_translate VALUES (39, 'ua', 'Еквадор', 'Іспанська', 'Чітко, чесно, неупереджено, правдиво і вчасно, постійно інформувати про те, що відбувається в місті, країні та світ. А також пропонувати елементи для роздумів про поточні події зі своїх редакційних просторів, аналізу, розслідувань і глибокої журналістики');

INSERT INTO periodical_translate VALUES (40, 'en', 'Great Britain', 'English', 'The Week brings the most relevant and important news from more than 200 trusted local and global sources to over 300,000 readers in one concise, informative and entertaining read. We provide all the facts you need to both confidently reflect on the last seven days and prepare for what’s coming next. The Week threads all sides of an argument together to offer a balanced perspective, because we understand that there are more than two sides to every story');
INSERT INTO periodical_translate VALUES (40, 'ua', 'Велика Британія', 'Англійська', 'The Week надає найактуальніші та важливі новини з більш ніж 200 надійних місцевих і глобальних джерел для більш ніж 300 000 читачів в одному короткому, інформативному та розважальному читанні. Ми надаємо всі факти, які вам потрібні, щоб впевнено поміркувати про останні сім днів і підготуватися до того, що буде далі. The Week об’єднує всі сторони суперечки, щоб запропонувати збалансовану перспективу, оскільки ми розуміємо, що кожна історія має більше ніж дві сторони');


INSERT INTO periodical_translate VALUES (41, 'en', 'Ukraine', 'Ukrainian', 'Newspaper for the whole family: about politics, money, scandals, famous people, sports, culture, home, health, food, gardening, life stories, useful tips, as well as horoscopes, crosswords and anecdotes. Published every Thursday');
INSERT INTO periodical_translate VALUES (41, 'ua', 'Україна', 'Українська', 'Газета для всієї родини: про політику, гроші, скандали, відомих людей, спорт, культуру, дім, здоров’я, їжу, сад-город, життєві історії, корисні поради, а також гороскоп, кросворди та анекдоти. Виходить щотижня у четвер');

INSERT INTO periodical_translate VALUES (42, 'en', 'Ukraine', 'Ukrainian', 'ELLE is the most popular fashion, beauty and lifestyle magazine. It is one of the three leaders of the international ELLE family, which has 46 editions. ELLE is a fashion gloss. However, our format is more than just fashion. To be precise, this concept goes beyond the usual framework and extends to the way of life as a whole. Basically, we can talk about anything. With one condition: an optimistic view of everything is important for us');
INSERT INTO periodical_translate VALUES (42, 'ua', 'Україна', 'Українська', 'ELLE – найпопулярніший журнал про моду, красу і стиль життя. Входить до трійки лідерів міжнародної родини ELLE, яка налічує 46 видань. ELLE – це модний глянець. Однак наш формат більший, ніж просто мода. Якщо бути точним, це поняття виходить за звичні рамки і поширюється на спосіб життя в цілому. По суті, ми можемо говорити про будь-які речі. З однією умовою: для нас важливий оптимістичний погляд на все');

INSERT INTO periodical_translate VALUES (43, 'en', 'Ukraine', 'Ukrainian', 'Vogue Ukraine is the Ukrainian edition of the American monthly fashion and lifestyle magazine Vogue. The magazine has been published since March 2013, becoming the twenty-first local edition of Vogue');
INSERT INTO periodical_translate VALUES (43, 'ua', 'Україна', 'Українська', 'Vogue Україна (англ. Vogue Ukraine) — українське видання американського щомісячного журналу про моду та стиль життя Vogue. Журнал виходить з березня 2013 року, ставши двадцять першим місцевим виданням Vogue');
