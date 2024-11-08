CREATE TABLE students (
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  surname VARCHAR(255) NOT NULL,
  index_number VARCHAR(50) UNIQUE NOT NULL,
  espb TINYINT UNSIGNED DEFAULT 0,
  points_for_sc TINYINT UNSIGNED DEFAULT 0,
  points_for_ce TINYINT UNSIGNED DEFAULT 0,
  points_for_ml TINYINT UNSIGNED DEFAULT 0
);