-- #1 Create the databse
CREATE DATABASE RPG;
GO

-- #2 Switch to RPG database
USE RPG;
GO

-- #3 Create Schema
CREATE SCHEMA game_data;
GO

-- #4 Drop tables if they already exist
IF OBJECT_ID('game_data.inventories', 'U') IS NOT NULL DROP TABLE game_data.inventories;
IF OBJECT_ID('game_data.skills', 'U') IS NOT NULL DROP TABLE game_data.skills;
IF OBJECT_ID('game_data.players', 'U') IS NOT NULL DROP TABLE game_data.players;
IF OBJECT_ID('game_data.classes', 'U') IS NOT NULL DROP TABLE game_data.classes;
IF OBJECT_ID('game_data.items', 'U') IS NOT NULL DROP TABLE game_data.items;
GO

-- #5 Create classes table
CREATE TABLE game_data.classes (
  class_id integer IDENTITY(1,1) PRIMARY KEY,
  class_name varchar(30),
  class_description text
);

-- #6 Create players table
CREATE TABLE game_data.players (
  player_id integer IDENTITY(1,1)  PRIMARY KEY,
  username varchar(30) NOT NULL,
  player_level integer DEFAULT (0),
  class_id integer NOT NULL,
  CONSTRAINT fk_class_id
  FOREIGN KEY (class_id)
  REFERENCES game_data.classes(class_id)
);

-- #7 Create items table
CREATE TABLE game_data.items (
  item_id integer IDENTITY(1,1) PRIMARY KEY,
  item_name varchar(30) NOT NULL,
  item_type varchar(30),
  value decimal
);

-- #8 Create inventories table
CREATE TABLE game_data.inventories (
  player_id integer NOT NULL,
  item_id integer NOT NULL,
  item_quantity integer DEFAULT(1),
  CONSTRAINT fk_player_id
  FOREIGN KEY (player_id)
  REFERENCES game_data.players(player_id),
  CONSTRAINT fk_item_id
  FOREIGN KEY(item_id) 
  REFERENCES game_data.items(item_id),
  PRIMARY KEY (player_id, item_id)
);

-- #9 Create skills table
CREATE TABLE game_data.skills (
  skill_id integer IDENTITY(1,1) PRIMARY KEY,
  skill_name varchar(30) NOT NULL,
  skill_type varchar(30),
  skill_description text,
  class_id integer NOT NULL,
  CONSTRAINT fk_class_id_2
  FOREIGN KEY (class_id)
  REFERENCES game_data.classes(class_id)
);
GO

-- #10 Insert sample data into classes
INSERT INTO game_data.classes (class_name, class_description)
VALUES
('Rogue', 'Specializes in stealth and precision'),
('Mage', 'Specializes in magic'),
('Archer', 'Specializes in ranged attacks');


-- #11 Insert sample data into players
INSERT INTO game_data.players (username, player_level, class_id)
VALUES
('CheeseDip', 11, 1),
('LarpMaster', 15, 2),
('Ryuu', 8, 3);

-- #12 Insert sample data into items
INSERT INTO game_data.items (item_name, item_type, value)
VALUES
('Rusty Dagger', 'Weapon', 30.00),
('Lapis Staff', 'Weapon', 75.00),
('Lesser Healing Potion', 'Consumable', 10.00);


-- #13 Insert sample data into inventories
INSERT INTO game_data.inventories (player_id, item_id, item_quantity)
VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 6);

-- #14 Insert sample data into skills
INSERT INTO game_data.skills (skill_name, skill_type, skill_description, class_id)
VALUES
('Backstab', 'Attack', 'Stealth attack from behind', 1),
('Fireball', 'Magic', 'Summons and throws a ball of fire at an enemy', 2),
('Rain of Arrows', 'Attack', 'A barrage of arrows shot in the air towards the enemy', 3);