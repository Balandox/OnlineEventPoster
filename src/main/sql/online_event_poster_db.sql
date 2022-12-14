CREATE TABLE Users
(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    email varchar(50) NOT NULL UNIQUE,
    role varchar(30) NOT NULL
);

CREATE TABLE Product(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title varchar(50) NOT NULL UNIQUE,
    img_name varchar(50),
    date timestamp with time zone NOT NULL,
    price int,
    category varchar(30) NOT NULL
);

ALTER TABLE Product
    ADD amount int CHECK (amount >= 0) DEFAULT 0;

CREATE TABLE Orders
(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id int REFERENCES Users(id) ON DELETE CASCADE,
    order_num int NOT NULL UNIQUE,
    creation_time timestamp with time zone NOT NULL,
    total_sum int NOT NULL
);

CREATE TABLE Order_details(
        id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
        product_id int REFERENCES Product(id) ON DELETE NO ACTION,
        order_id int REFERENCES Orders(id),
        quantity int,
        price int NOT NULL,
        total_sum int
);

CREATE TABLE Bucket(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id int REFERENCES Users (id) ON DELETE CASCADE UNIQUE
);

CREATE TABLE Bucket_Product(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    bucket_id int REFERENCES Bucket(id) ON DELETE CASCADE,
    product_id int REFERENCES Product(id) ON DELETE CASCADE
);

DROP TABLE bucket_product;
DROP TABLE bucket;
DROP TABLE order_details;
DROP TABLE product;
DROP TABLE Orders;
DROP TABLE Users;

ALTER TABLE order_details
    DROP CONSTRAINT order_details_product_id_fkey;

ALTER TABLE order_details
    ADD CONSTRAINT order_details_product_id_fkey
    FOREIGN KEY (product_id)
    REFERENCES product(id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

INSERT INTO Users (id, email, name, password, role)
VALUES (1, 'admin@mail.ru', 'admin', '$2a$10$45XS11YmDmJ4qjlXNdlVuuApgNjm.S7QTlTLmC4GKeMCcyYIMB0zC', 'ADMIN');

INSERT INTO Product(title, img_name, date, price, amount, category) VALUES
        ('?????????????? Travis Scott', 'https://i.postimg.cc/MZPkMvhD/travis.jpg', '2022-12-27 18:00', 6000, 200 , 'CONCERT'),
        ('?????????????? Scorpions', 'scorpions.jpg', '2022-12-30 18:00', 700, 3000, 'CONCERT'),
        ('?????????????? Playboi Carti', 'https://i.postimg.cc/8kHvhVHw/playboi-Carti.jpg' , '2022-12-28 17:00', 7500, 1200, 'CONCERT'),
        ('???????????????????? ???????? ?????????? - ????????????', 'zenit-rostov.jpg' , '2022-12-28 19:00', 1500, 15000, 'SPORT'),
        ('?????????????????????????? ???????? ?????????? - ????????', 'zenit-cska.jpg' , '2023-01-15 18:00', 1500, 5000, 'SPORT');

INSERT INTO Product(title, img_name, date, price, amount, category) VALUES
    ('?????????? ??????????????????', 'nutcracker.jpg', '2022-12-28 18:00', 1700, 3, 'THEATRE');

UPDATE Product SET img_name='https://i.postimg.cc/BnYy3BLb/scorpions.jpg' WHERE title='?????????????? Scorpions';
UPDATE Product SET img_name='https://i.postimg.cc/gkKQSWGv/zenit-rostov.jpg' WHERE title='???????????????????? ???????? ?????????? - ????????????';
UPDATE Product SET img_name='https://i.postimg.cc/tTPmh0rn/zenit-cska.jpg' WHERE title='?????????????????????????? ???????? ?????????? - ????????';
UPDATE Product SET img_name='https://i.postimg.cc/jSd9BFjB/nutcracker.jpg' WHERE title='?????????? ??????????????????';
UPDATE Product SET img_name='https://i.postimg.cc/nVB6mm5S/avatar.jpg' WHERE title='?????????? ????????????';


DELETE FROM Product WHERE title = '?????????? ??????????????????';
DELETE FROM Users WHERE id = 1;


SELECT * FROM Users;

DELETE FROM Product WHERE id >= 1;
DELETE FROM Users WHERE id >= 1;
DELETE FROM Bucket WHERE id >= 1;
SELECT * FROM Product;

SELECT u.name,  P.title from Users u
JOIN Bucket B on u.id = B.user_id
JOIN Bucket_Product BP on B.id = BP.bucket_id
JOIN Product P on P.id = BP.product_id;

ALTER SEQUENCE users_id_seq RESTART WITH 2;
ALTER SEQUENCE product_id_seq RESTART WITH 1;
ALTER SEQUENCE bucket_id_seq RESTART WITH 1;

SET timezone = 'Europe/Moscow';




