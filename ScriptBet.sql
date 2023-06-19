CREATE TABLE tbl_log (
  id INT AUTO_INCREMENT,
  api VARCHAR(250),
  data_log DateTime,
  game VARCHAR(100),
  user_id INT,
  odd FLOAT,
  bet_type VARCHAR(20),
  bet VARCHAR(200),
  PRIMARY KEY (id)
);

CREATE TABLE tbl_properties(
  id INT AUTO_INCREMENT,
  create_by VARCHAR(50),
  create_at DateTime,
  updated_by VARCHAR(50),
  updated_at DateTime,
  propertie_key VARCHAR(100),
  propertie_value VARCHAR(20),
  PRIMARY KEY (id)
);


CREATE TABLE tbl_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50) UNIQUE,
    user_name VARCHAR(50),
    user_password VARCHAR(100),
    create_at DateTime NOT NULL,
    updated_at DateTime,
    user_enabled TINYINT
);

CREATE TABLE tbl_role (
    id INT,
    type_name VARCHAR(50),
    role_description VARCHAR(50),
    create_at DateTime,
    updated_at DateTime,
    user_enabled TINYINT,
    create_by VARCHAR(50),
    updated_by VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE tbl_subscription (
    id INT AUTO_INCREMENT,
    create_by VARCHAR(50),
    create_at DateTime,
    updated_by VARCHAR(50),
    updated_at DateTime,
    price  FLOAT,
    expires_at DateTime,
    role_id INT,
    user_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES tbl_role(id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);

CREATE TABLE tbl_currentPrice (
    id INT,
    current_price FLOAT,
    role_id INT,
    PRIMARY KEY (id)
);

INSERT INTO tbl_user (id, first_name, last_name, email, user_name,
                      user_password, create_at, updated_at, user_enabled) 
VALUES (0, 'João','Fulano' ,'joao@example.com', 'jao', 'e2fc714c4727ee9395f324cd2e7f331f', "1998-11-10 13:30:35", null, 1);

SELECT * FROM tbl_user where user_name = 'jao'