show databases;
use productdatabase;
show tables;
select * from category;
select * from product;

select name from product;
select * from product where name = "Basota";

select category.name,category.description,product.name,product.description,product.price
from category inner join product where category.id = product.category_id;
select * from product where price>10000 And name ="IQOO Z6 PRO";
