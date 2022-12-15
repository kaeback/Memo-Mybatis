create table member (
    member_id number primary key ,
    email varchar2(100) not null,
    password varchar2(30) not null,
    name varchar2(50) not null
);

create table memo (
    memo_id number primary key ,
    title varchar2(500) not null,
    contents varchar2(4000) not null,
    created_time date default sysdate
);

create table memo_record (
     id number primary key ,
     memo_id NUMBER REFERENCES memo(memo_id),
     sender_id number not null,
     receiver_id number not null,
     is_receiver_read varchar2(1) default 'N'
);

create sequence seq_memo;