INSERT INTO `pool_sensor_test`.`MyUser`
(`id`, `email`, `login`, `password`)
VALUES
(1, 'test-email', 'test-user', 'test-password');



INSERT INTO `pool_sensor_test`.`Device`
(`id`, `ipAddress`, `location`, `name`, `myUser_id`)
VALUES
(1, 'test-ipAddress', 'test-location', 'test-name', 1);



INSERT INTO `pool_sensor_test`.`Sensor`
(`id`, `name`, `value`, `device_id`)
VALUES
(1, 'test sensor', 'test value', 1);

INSERT INTO `pool_sensor_test`.`Sensor`
(`id`, `name`, `value`, `device_id`)
VALUES
(2, 'test', 'test value', 1);

INSERT INTO `pool_sensor_test`.`Sensor`
(`id`, `name`, `value`, `device_id`)
VALUES
(3, 'sensor_test', 'test value', 1);

