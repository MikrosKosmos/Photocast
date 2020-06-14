drop procedure if exists sp_tbl_LikeMaster;
create procedure sp_tbl_LikeMaster()
begin
    declare currentSchema varchar(50) default '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_LikeMaster'
        ) then
        begin
            create table tbl_LikeMaster
            (
                id          int primary key auto_increment      not null,
                post_id     int                                 not null,
                user_id     int                                 not null,
                first_name  varchar(255)                        not null,
                last_name   varchar(255)                        not null,
                role        varchar(100)                        not null,
                is_active   tinyint   default 1                 not null,
                created_by  int                                 not null,
                created     timestamp default current_timestamp not null,
                modified_by int       default null,
                modified    timestamp default null
            );
        end;
    end if;
end;
call sp_tbl_LikeMaster();
drop procedure if exists sp_tbl_LikeMaster;