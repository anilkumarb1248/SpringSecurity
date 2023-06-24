
insert into users(username, password, enabled) values('ammi','ammi',true);
insert into users(username, password, enabled)values('ikshi','ikshi',true);

insert into authorities(username,authority) values('ammi','ROLE_USER');
insert into authorities(username,authority) values('ikshi','ROLE_USER');
insert into authorities(username,authority) values('ikshi','ROLE_ADMIN');