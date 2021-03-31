delete from wzb_users_roles;
delete from wzb_users;
delete from wzb_roles;
insert into wzb_users(id, username, `name`,  mobile, password_hash, enabled, account_non_expired, account_non_locked, credentials_non_expired, email)
values (1, 'user',  'Zhang San', '13012341234', '{bcrypt}$2a$10$jhS817qUHgOR4uQSoEBRxO58.rZ1dBCmCTjG8PeuQAX4eISf.zowm', 1, 1, 1, 1,   'wpcfan@163.com'),
       (2, 'old_user', 'Li Si', '13012341235', '{SHA-1}7ce0359f12857f2a90c7de465f40a95f01cb5da9', 1, 1, 1, 1, 'lisi@local.dev');
insert into wzb_roles(id, role_name) values (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
insert into wzb_users_roles(user_id, role_id) values (1, 1), (1, 2), (2, 1);