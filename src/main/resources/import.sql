/* Populate tabla clients */

INSERT INTO regions (id, name) VALUES (1, 'Sudamérica');
INSERT INTO regions (id, name) VALUES (2, 'Centroamérica');
INSERT INTO regions (id, name) VALUES (3, 'Norteamérica');
INSERT INTO regions (id, name) VALUES (4, 'Europa');
INSERT INTO regions (id, name) VALUES (5, 'Asia');
INSERT INTO regions (id, name) VALUES (6, 'Africa');
INSERT INTO regions (id, name) VALUES (7, 'Oceanía');
INSERT INTO regions (id, name) VALUES (8, 'Antártida');

INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01', 1);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02', 1);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03', 2);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04', 3);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01', 4);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10', 5);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18', 6);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28', 7);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03', 8);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04', 7);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05', 6);
INSERT INTO clients (name, last_name, email, created_at, region_id) VALUES('Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06', 5);

INSERT INTO users (username, password, enabled, name, last_name, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',TRUE, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO users (username, password, enabled, name, last_name, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',TRUE, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);