drop table if exists products;

create table products(
    product_id varchar(50) primary key,
    product_name varchar(20) not null ,
    category varchar(50) not null ,
    price bigint not null ,
    description varchar(500) default null ,
    created_at datetime(6) not null,
    updated_at datetime(6) default null
);
drop table if exists orders;

create table orders(
    order_id varchar(50) primary key ,
    email varchar(50) not null ,
    phone_number varchar(200) not null ,
    bell_number varchar(200) not null ,
    order_status varchar(50) not null ,
    created_at datetime(6) not null,
    updated_at datetime(6) default null
);

drop table if exists order_items;

CREATE TABLE order_items
(
    seq        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   varchar(50)  NOT NULL,
    product_id varchar(50)  NOT NULL,
    category   VARCHAR(50) NOT NULL,
    price      bigint      NOT NULL,
    quantity   int         NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);
