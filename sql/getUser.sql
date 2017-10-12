DROP PROCEDURE IF EXISTS `getUser`;

CREATE PROCEDURE `getUser`(IN user VARCHAR(50))
  BEGIN
    SELECT
      userName
      ,`password`

    FROM
      user

    WHERE
      userName = user;
  END;