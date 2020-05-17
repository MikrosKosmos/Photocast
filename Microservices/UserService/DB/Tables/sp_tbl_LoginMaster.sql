drop procedure if exists sp_tbl_LoginMaster;
create procedure sp_tbl_LoginMaster()
begin
    declare currentSchema varchar(100) DEFAULT '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_LoginMaster'
        ) then
        begin
            create table tbl_LoginMaster
            (
                id           int primary key auto_increment,
                email_id     varchar(255) UNIQUE DEFAULT null,
                password     varchar(255)        default null,
                phone_number varchar(15) UNIQUE                NOT NULL,
                role         varchar(30)                       not null,
                is_active    tinyint             default 1,
                created_by   int                               not null,
                created      timestamp           default now() not null,
                modified_by  int                 default null,
                modified     timestamp           default null
            );
        end;
    end if;
    if not exists(
            select 1
            from information_schema.COLUMNS
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_LoginMaster'
              and COLUMN_NAME = 'user_id'
        ) then
        begin
            alter table tbl_LoginMaster
                add column user_id int NOT NULL after role;
        end;
    end if;
end;
call sp_tbl_LoginMaster();
drop procedure if exists sp_tbl_LoginMaster;