drop procedure if exists sp_tbl_PostMaster;
create procedure sp_tbl_PostMaster()
begin
    declare currentSchema varchar(50) default '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_PostMaster'
        ) then
        begin
            create table tbl_PostMaster
            (
                id               int auto_increment primary key         not null,
                vendor_id        int                                    not null,
                first_name       varchar(255)                           not null,
                last_name        varchar(255)                           not null,
                post_description varchar(255) default null,
                image_url        varchar(255)                           not null,
                like_count       int          default 0,
                comment_count    int          default 0,
                is_active        tinyint      default 1                 not null,
                created_by       int                                    not null,
                created          timestamp    default current_timestamp not null,
                modified_by      int          default null,
                modified         timestamp    default null
            );
        end;
    end if;
end;
call sp_tbl_PostMaster();
drop procedure if exists sp_tbl_PostMaster;