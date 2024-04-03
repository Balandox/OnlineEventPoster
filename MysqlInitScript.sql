CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    role VARCHAR(30) NOT NULL,
    isEnabled BOOLEAN DEFAULT FALSE
);

CREATE TABLE ConfirmationToken (
    id INT PRIMARY KEY AUTO_INCREMENT,
    confirmation_token VARCHAR(255) UNIQUE,
    createdDate TIMESTAMP NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL UNIQUE,
    img_name VARCHAR(50),
    date TIMESTAMP NOT NULL,
    price INT,
    category VARCHAR(30) NOT NULL,
    amount INT CHECK (amount >= 0) DEFAULT 0,
    rating DECIMAL(5,2) CHECK (rating >= 0.00) DEFAULT 0.00
);

CREATE TABLE Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    order_num INT NOT NULL UNIQUE,
    creation_time TIMESTAMP NOT NULL,
    total_sum INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Order_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    order_id INT,
    quantity INT,
    price INT NOT NULL,
    total_sum INT,
    FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE NO ACTION,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE
);

CREATE TABLE Bucket (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Bucket_Product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bucket_id INT,
    product_id INT,
    FOREIGN KEY (bucket_id) REFERENCES Bucket(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE CASCADE
);


INSERT INTO Users (email, name, password, role)
VALUES ('admin@mail.ru', 'admin', '$2a$10$gbjqnweL85ZTmL5IFjR.vO4SnQQLnWxWGeB5DAfJ21T.SRvGPv4hC', 'ADMIN');

INSERT INTO Product(title, img_name, date, price, amount, category) VALUES
        ('Концерт Travis Scott', 'https://i.postimg.cc/MZPkMvhD/travis.jpg', '2022-12-27 18:00', 6000, 200 , 'CONCERT'),
        ('Концерт Scorpions', 'scorpions.jpg', '2022-12-30 18:00', 700, 3000, 'CONCERT'),
        ('Концерт Playboi Carti', 'https://i.postimg.cc/8kHvhVHw/playboi-Carti.jpg' , '2022-12-28 17:00', 7500, 1200, 'CONCERT'),
        ('Футбольный матч Зенит - Ростов', 'zenit-rostov.jpg' , '2022-12-28 19:00', 1500, 15000, 'SPORT'),
        ('Баскетбольный матч Зенит - Цска', 'zenit-cska.jpg' , '2023-01-15 18:00', 1500, 5000, 'SPORT');

INSERT INTO Product(title, img_name, date, price, amount, category) VALUES
    ('Опера щелкунчик', 'nutcracker.jpg', '2022-12-28 18:00', 1700, 3, 'THEATRE');

UPDATE Product SET img_name='https://i.postimg.cc/BnYy3BLb/scorpions.jpg' WHERE title='Концерт Scorpions';
UPDATE Product SET img_name='https://i.postimg.cc/gkKQSWGv/zenit-rostov.jpg' WHERE title='Футбольный матч Зенит - Ростов';
UPDATE Product SET img_name='https://i.postimg.cc/tTPmh0rn/zenit-cska.jpg' WHERE title='Баскетбольный матч Зенит - Цска';
UPDATE Product SET img_name='https://i.postimg.cc/jSd9BFjB/nutcracker.jpg' WHERE title='Опера щелкунчик';
UPDATE Product SET img_name='https://i.postimg.cc/nVB6mm5S/avatar.jpg' WHERE title='Фильм Аватар';
