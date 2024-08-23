CREATE DATABASE shoppingcart;

use shoppingcart;

CREATE TABLE user (
    id int NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL UNIQUE,
    last_name varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    area_of_interest varchar(250) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE product (
    id int NOT NULL AUTO_INCREMENT,
    product_name varchar(255) NOT NULL,
    total_products_inventory int NOT NULL,
    price double NOT NULL,
    image varchar(255),
    product_description long,
    product_status boolean NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO user VALUES(1,"john@company.com","Smith","John","Finance");
INSERT INTO user VALUES(2,"noah@cruise.com","Cruise","Noah","Marketing");
INSERT INTO user VALUES(3,"jennd@mail.com","Diaz","Jennifer","Science");
INSERT INTO user VALUES(4,"ana_lively@mail.com","Lively","Ana","Technology");

INSERT INTO product VALUES(1,"Corn Flakes",1000,5.29,"https://tse2.mm.bing.net/th/id/OIP.GCrlzp8ZfJCtuyJGEil02AHaHa?rs=1&pid=ImgDetMain","Plain and simple",true);
INSERT INTO product VALUES(2,"Pop Tarts",850,2.99,"https://i5.walmartimages.com/asr/d5611139-3dc1-4fba-b126-291dea5b2cd4_1.1b3c5151429481535f3c771956d0e3a9.jpeg","S'mores and chocolate",true);
INSERT INTO product VALUES(3,"Sprite",615,3.00,"https://tse2.mm.bing.net/th/id/OIP.530med_Dd7gfd-l3faxXKgHaHa?rs=1&pid=ImgDetMain","Regular flavor",true);
INSERT INTO product VALUES(4,"Tic Tac",30,0.99,"https://tse3.mm.bing.net/th/id/OIP.o9ME7l9YErVdnHkHrvO9bgHaHa?rs=1&pid=ImgDetMain","Minty",true);
INSERT INTO product VALUES(5,"Mac and Cheese",20,1.19,"https://i5.walmartimages.com/asr/4472819b-978e-4294-a082-0841907de72d.c3d2d2a81f7c54207719665284b582e7.jpeg","Krafty",true);
