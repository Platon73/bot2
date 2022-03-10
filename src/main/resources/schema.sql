drop table IF EXISTS completed_questionnaire_qa cascade;
drop table IF EXISTS question cascade;
drop table IF EXISTS questionnaire_qa cascade;
drop table IF EXISTS questionnaire cascade;
drop table IF EXISTS user_tb_list_completed_questionnaire cascade;
drop table IF EXISTS completed_questionnaire cascade;
drop table IF EXISTS user_tb cascade;
drop table IF EXISTS answer cascade;


CREATE TABLE customers
(
    Id SERIAL PRIMARY KEY,
    FirstName CHARACTER VARYING(30),
    LastName CHARACTER VARYING(30),
    Email CHARACTER VARYING(30),
    Age INTEGER
);