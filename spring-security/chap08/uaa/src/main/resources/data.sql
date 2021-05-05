delete from wzb_roles_permissions;
delete from wzb_users_roles;
delete from wzb_permissions;
delete from wzb_users;
delete from wzb_roles;
insert into wzb_permissions(id, permission_name, display_name)
values (1, 'USER_READ', '查询用户信息'),
       (2, 'USER_CREATE', '新建用户'),
       (3, 'USER_UPDATE', '编辑用户信息'),
       (4, 'USER_ADMIN', '用户管理');
insert into wzb_users(id, username, `name`, mobile, password_hash, enabled, account_non_expired, account_non_locked, credentials_non_expired, using_mfa, mfa_key, email)
values (1, 'user', 'Zhang San', '13012341234', '{bcrypt}$2a$10$jhS817qUHgOR4uQSoEBRxO58.rZ1dBCmCTjG8PeuQAX4eISf.zowm', 1, 1, 1, 1, true, '8Uy+OZUaZur9WwcP0z+YxNy+QdsWbtfqA70GQMxMfLeisTd8Na6C7DkjhJWLrGyEyBsnEmmkza6iorytQRh7OQ==', 'zhangsan@local.dev'),
       (2, 'old_user', 'Li Si', '13812341234', '{SHA-1}7ce0359f12857f2a90c7de465f40a95f01cb5da9', 1, 1, 1, 1, false, '8Uy+OZUaZur9WwcP0z+YxNy+QdsWbtfqA70GQMxMfLeisTd8Na6C7DkjhJWLrGyEyBsnEmmkza6iorytQRh7OQ==', 'lisi@local.dev');
insert into wzb_roles(id, role_name, display_name, built_in)
values (1, 'ROLE_USER', '客户端用户', true),
       (2, 'ROLE_ADMIN', '超级管理员', true),
       (3, 'ROLE_STAFF', '管理后台用户', true);
insert into wzb_users_roles(user_id, role_id) values (1, 1), (1, 2), (1, 3), (2, 1);
insert into wzb_roles_permissions(role_id, permission_id) values (1, 1), (2, 1), (2, 2), (2, 3), (2, 4);
INSERT INTO oauth_client_details (client_id, client_name, client_secret, scope, authorized_grant_types, web_server_redirect_uri, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('web-client', '第三方前端', '{noop}secret', 'todo.read,todo.write', 'authorization_code,password,refresh_token,client_credentials', 'http://localhost:8081/login/oauth2/code/web-client-auth-code', '900', '31536000', '{}', null),
       ('admin-client', '后台管理客户端', '{noop}secret', 'user.admin,client.admin', 'authorization_code,password,refresh_token,client_credentials', 'http://localhost:4001', '60', '31536000', '{}', true),
       ('ios-client', 'iOS 客户端', '{noop}secret', 'todo.read,todo.write', 'authorization_code,password,refresh_token,client_credentials', 'com.example.app://action', '900', '31536000', '{}', null),
       ('android-client', 'Android 客户端', '{noop}secret', 'todo.read,todo.write', 'authorization_code,password,refresh_token,client_credentials', 'com.example.app://action', '900', '31536000', '{}', null),
       ('todos-service', '微服务', '{noop}secret', 'todo.read,todo.write', 'authorization_code,refresh_token,client_credentials', 'http://localhost:8082/authorized', '900', '31536000', '{}', true );
