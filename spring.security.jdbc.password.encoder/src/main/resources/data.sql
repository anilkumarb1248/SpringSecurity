
insert into users(username, password, enabled) values('anil','$2a$10$LjXHSif9ZrMgPRcJ/0Q5FOMR7ZTz2VkqfzI87j3XvQ0UTcMTLkGBi',true);
insert into users(username, password, enabled)values('bandari','$2a$10$tcFXBgUvdN47m8Pzdc0JbOoxCM9BsHtEpc/v9xSqRSNprgzESTlyi',true);

insert into authorities(username,authority) values('anil','ROLE_USER');
insert into authorities(username,authority) values('bandari','ROLE_USER');
insert into authorities(username,authority) values('bandari','ROLE_ADMIN');