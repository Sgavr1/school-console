CREATE TABLE courses(
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR NOT NULL,
    course_description VARCHAR(500)
);

CREATE TABLE groups(
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(5) NOT NULL
);

CREATE TABLE students(
    student_id SERIAL PRIMARY KEY,
    group_id INTEGER REFERENCES groups(group_id),
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL
);

CREATE TABLE student_course(
    student_id INTEGER REFERENCES students(student_id),
    course_id INTEGER REFERENCES courses(course_id),
    PRIMARY KEY (student_id, course_id)
);

INSERT INTO groups(group_name) VALUES('AV-01');
INSERT INTO groups(group_name) VALUES('AB-04');

INSERT INTO courses(course_name, course_description) VALUES('Вступ до програмування','Основи програмування для початківців.');
INSERT INTO courses(course_name, course_description) VALUES('Інтернет-маркетинг','Основи маркетингу в Інтернеті та просування продуктів онлайн.');
INSERT INTO courses(course_name, course_description) VALUES('Математика для інформатики','Основи математики для студентів інформатичних спеціальностей.');

INSERT INTO students(first_name, last_name, group_id) VALUES ('Людмила', 'Козлов', 1);
INSERT INTO students(first_name, last_name, group_id) VALUES ('Виктория', 'Мельник', 1);

INSERT INTO student_course(student_id, course_id) VALUES (1, 1);
INSERT INTO student_course(student_id, course_id) VALUES (1, 2);
INSERT INTO student_course(student_id, course_id) VALUES (2, 2);