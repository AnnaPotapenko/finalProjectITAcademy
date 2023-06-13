CREATE TABLE my_film (
        my_film_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        my_film_name TEXT(255) NOT NULL,
        my_film_type TEXT(255) NOT NULL,
        my_film_price TEXT(255) NOT NULL,
        my_film_time TEXT(255) NOT NULL
        /*FOREIGN KEY (gender) REFERENCES gender(gender_id)*/
        );
        
CREATE TABLE my_person (
        my_person_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        my_person_login TEXT(255) NOT NULL,
        my_person_password TEXT(255) NOT NULL,
        my_person_money INTEGER(11) DEFAULT NULL,
        my_person_role TEXT(255) NOT NULL DEFAULT 'ORDINARY_USER'
        );
        
CREATE TABLE my_ticket (
        my_ticket_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        my_ticket_film_name TEXT(255),
        my_ticket_status TEXT(255) NOT NULL DEFAULT 'NOT_SOLD',
        my_film_price TEXT(255),
        my_film_time TEXT(255),
        my_film INTEGER(11),
        my_person INTEGER(11) DEFAULT NULL,
        FOREIGN KEY (my_film) REFERENCES my_film(my_film_id),
        FOREIGN KEY (my_person) REFERENCES my_person(my_person_id)
        );
       
INSERT INTO my_person ( my_person_id, my_person_login, my_person_password, my_person_money, my_person_role)
VALUES ("1", "MyAdmin", "1111", "1040", "ADMIN");      
       
INSERT INTO my_person ( my_person_id, my_person_login, my_person_password, my_person_role)
VALUES ("2", "MyManager", "2222", "MANAGER");

INSERT INTO my_person ( my_person_id, my_person_login, my_person_password, my_person_money)
VALUES ("3", "Anna", "3333", "15"); 

INSERT INTO my_person (my_person_login, my_person_password)
VALUES ("Superman", "4444"); 
       


INSERT INTO my_film (my_film_name, my_film_type, my_film_price, my_film_time)
VALUES ("Гарри Поттер и Философский камень", "Семейный, фентези", "20", "01-05-2023 14:00"); 

INSERT INTO my_film (my_film_name, my_film_type, my_film_price, my_film_time)
VALUES ("Аватар", "Боевик, фентези", "20", "23-04-2023 18:30"); 

INSERT INTO my_film (my_film_name, my_film_type, my_film_price, my_film_time)
VALUES ("Ёлки 2", "Комедия", "5", "20-04-2023 12:30"); 

INSERT INTO my_film (my_film_name, my_film_type, my_film_price, my_film_time)
VALUES ("Джон Уик 4", "Боевик", "15", "23-04-2023 20:30"); 


INSERT INTO my_film (my_film_name, my_film_type, my_film_price, my_film_time)
VALUES ("Пила", "Ужасы", "20", "23-04-2023 23:30"); 

INSERT INTO my_ticket (my_film_name, my_film_type, my_film_price, my_film_time)
VALUES ("Фильм к удалению", "Ужасы", "20", "23-04-2023 23:30"); 


INSERT INTO my_ticket (my_ticket_id,
        my_ticket_film_name,
        my_film_price,
        my_film_time,
        my_film,
        my_person)
VALUES ("1", "Ёлки 2", "5", "20-04-2023 12:30", "3", "3"); 

INSERT INTO my_ticket (my_ticket_id,
        my_ticket_film_name,
        my_film_price,
        my_film_time,
        my_film)
VALUES ("2", "Ёлки 2", "5", "20-04-2023 12:30", "3"); 

INSERT INTO my_ticket (
        my_ticket_film_name,
        my_film_price,
        my_film_time,
        my_film)
VALUES ("Ёлки 2", "5", "20-04-2023 12:30", "3"); 

       