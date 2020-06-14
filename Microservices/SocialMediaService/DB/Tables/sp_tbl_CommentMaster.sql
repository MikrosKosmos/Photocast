drop procedure if exists sp_tbl_CommentMaster;
create procedure sp_tbl_CommentMaster()
begin
    declare currentSchema varchar(50) default '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_CommentMaster'
        ) then
        begin
            create table tbl_CommentMaster
            (
                id          int primary key auto_increment      not null,
                post_id     int                                 not null,
                first_name  varchar(255)                        not null,
                last_name   varchar(255)                        not null,
                user_id     int                                 not null,
                role        varchar(100)                        not null,
                comment     text      default null,
                is_active   tinyint   default 1                 not null,
                created_by  int                                 not null,
                created     timestamp default current_timestamp not null,
                modified_by int       default null,
                modified    timestamp default null
            );
        end;
    end if;
end;
call sp_tbl_CommentMaster;
drop procedure if exists sp_tbl_CommentMaster;