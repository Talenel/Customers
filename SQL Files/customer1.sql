
Drop table if exists address;
drop table if exists isEmployee;
Drop table if exists customer;
Drop table if exists companies;


#alter table customers add CustID int not null auto_increment primary key;



Create table address as
(
Select CustID, StreetAddress, City, State, ZipCode
from customers 

);

#Select count(*),StreetAddress, City, State, ZipCode from address group by StreetAddress, City, State, ZipCode order by count(*) desc;

#Select * from address where StreetAddress='497 Rollins Road';

alter table address add addID int not null auto_increment primary key;

Create table customer as
(
Select * from customers);

#must set CustID as primary key in order to use it as a foreign key
alter table customer add primary key(CustID);
alter table customer add addID int;
alter table customer change CustID CustID int(11) not null auto_increment, auto_increment=4068;


update customer c set c.addID = (select t.addID from
address t where t.CustID=c.CustID);

#Brute Force methodology to delete the duplicate  
update customer set addID='513' where CustID=3508;
delete from address where CustID='3508';
alter table address drop column CustID;
alter table customer drop column FullName;



Create table companies as
(
Select distinct company
from customer 

);
alter table companies add compID int not null auto_increment primary key;

drop table if exists isEmployee;
create table isEmployee as
(
Select CustID, company, position
from customer


);

alter table isEmployee add compID int;


update isEmployee e set e.compID = (select c.compID from
companies c where c.company=e.company);

#Have to add both primary keys at the same time
alter table isEmployee add primary key(CustID, compID);


alter table isEmployee add foreign key (CustID) references customer(CustID);
alter table isEmployee add foreign key (compID) references companies(compID);

alter table customer drop column StreetAddress, drop column City, drop column State, drop column ZipCode, drop column company, drop column position;
alter table isEmployee drop column company;

