drop table coursemanager.studentcourses;
drop table coursemanager.student;
drop table coursemanager.course;

create table student (student_id Number(10),firstName Varchar2(50), lastName Varchar2(50), email Varchar2(100), CONSTRAINT student_pk PRIMARY KEY (student_id)); 
drop sequence coursemanager.studentid_seq;
create sequence coursemanager.studentid_seq start with 20 increment by 1;

create table course (course_id Number(10), courseName Varchar2(50), location Varchar2(50), credit number(2), CONSTRAINT course_pk PRIMARY KEY (course_id));
drop sequence coursemanager.courseid_seq;
create sequence coursemanager.courseid_seq start with 10 increment by 1;

create table coursemanager.studentcourses(student_id Number(10), course_id Number (10), 
 CONSTRAINT student_fk Foreign Key  (student_id) references coursemanager.student (student_id), 
 CONSTRAINT course_fk Foreign Key  (course_id) references coursemanager.course (course_id));

