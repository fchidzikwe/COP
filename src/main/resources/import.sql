--
--INSERT VALUES INTO TABLE `ROLE`
--

INSERT INTO `role`(`role_id`, `role`) VALUES(1, "ADMIN");

--
--INSERT ADMIN VALUES INTO TABLE `USER`
--

INSERT INTO user(user_id, active, email, last_name, name, password, phoneNumber,role)
VALUES (1,1,'admin@gmail.com','Admin','Admin','$2a$10$57lovD4dmI7yoSLXVrXXx.FV16UIe/MvdYN/mxFwm6Wjy9D7crXBq','0774845608','ADMIN');






