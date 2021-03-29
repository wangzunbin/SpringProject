Insert into wzb_users(username, password, enabled) values
('user', '{bcrypt}$2a$10$MoqrtIgYjzcNU2RZtknG9ORnI7OaOX3LwJH8MxegMJLedKLWo/8uO', 1),
('old_user', '{bcrypt}$2a$10$RegzWWF8XjnCzvTDMGB08uaRv/0//2SYjorThVVIK4TRFfEnjt1p6', 1);
insert into wzb_authorities(username, authority) values
('old_user', 'ROLE_USER'),
('user', 'ROLE_ADMIN'),
('user', 'ROLE_USER');