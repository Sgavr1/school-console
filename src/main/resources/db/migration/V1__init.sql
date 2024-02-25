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