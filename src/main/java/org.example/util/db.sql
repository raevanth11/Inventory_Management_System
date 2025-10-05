create database Inventory_Management_System;
use Inventory_Management_System;
create table Products(
   id int primary key,
   name varchar(100) not null,
   category varchar(100) not null,
   quantity int not null check(quantity > 0),
   price double not null check(price > 0)
);