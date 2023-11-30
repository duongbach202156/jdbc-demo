drop database if exists employees;
create database employees;

drop table if exists city;
create table city (
	id int not null auto_increment,
    name varchar(50),
    primary key (id)
);

drop table if exists employees;
create table employees (
	id int auto_increment not null,
    name varchar(50),
    salary int,
    city_id int,
    manager_id int,
    primary key (id),
    foreign key (city_id) references city(id),
    foreign key (manager_id) references employees(id)
);

insert into city values 
(1, 'Ha Noi'),
(2, 'HCM'),
(3, 'Thai Binh'),
(4, 'Bac Giang'),
(5, 'Bac Ninh');


insert into employees values 
(1, 'Nguyen Van A', 3000, 1, null),
(2, 'Nguyen Van B', 4000, 3, 1),
(3, 'Nguyen Van C', 5000, 2, 1),
(4, 'Nguyen Van D', 3000, 3, 2),
(5, 'Nguyen Van E', 2000, 4, 1);
insert into employees values (default, 'b', 2000, null, 1);

select * from employees;
select * from city;
-- find employee by id
drop procedure if exists find_employees_by_id;
delimiter //
create procedure find_employees_by_id (
	in id int
)
begin
	select * from employees e where e.id = id;
end //
delimiter ;
-- check salary
drop procedure if exists check_salary_status_by_id;
delimiter //
create procedure check_salary_status_by_id (
	in id int, out salary_status varchar(50)
)
begin
	declare avg_salary int;
    set avg_salary = 2000;
	select salary into @salary from employees e where e.id = id;
    if @salary > avg_salary then set salary_status = "HIGH";
    elseif @salary = avg_salary then set salary_status = "MEDIUM";
    elseif @salary < avg_salary then set salary_status = "LOW";
    end if; 
end //
delimiter ;

-- get total employees by city id

drop function if exists get_total_employees_by_city;
delimiter //
create function get_total_employees_by_city (
	city_id int
)
returns int
deterministic
begin
	declare total int;
    select count(id)
    into total
    from employees e
    where e.city_id = city_id;
    return (total);
end //
delimiter ;
select * from employees where id = 2;


-- join
select e.id, e.name, e.salary, c.name as city, e2.name as manager from employees e
left join city c on e.city_id = c.id
left join employees e2 on e.manager_id = e2.id;

