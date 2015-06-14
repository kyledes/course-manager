CREATE USER coursemanager IDENTIFIED BY coursemanager 
DEFAULT TABLESPACE "USERS"
TEMPORARY TABLESPACE "TEMP";

grant unlimited tablespace to coursemanager;
grant create session to coursemanager;
grant create table to coursemanager;
grant create sequence to coursemanager;
