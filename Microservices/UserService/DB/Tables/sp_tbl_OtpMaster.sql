drop procedure if exists sp_tbl_OtpMaster;
create procedure sp_tbl_OtpMaster()
begin
    declare currentSchema varchar(50) default '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_OtpMaster'
        ) then
        begin
            create table tbl_OtpMaster
            (
                id           int primary key auto_increment,
                phone_number varchar(15) NOT NULL,
                otp          int         not null,
                validity     timestamp default NULL,
                is_active    tinyint   default 1,
                created_by   int         NOT NULL,
                created      timestamp default now(),
                modified_by  int       default null,
                modified     timestamp default null
            );
        end;
    end if;
end;
call sp_tbl_OtpMaster();
drop procedure if exists sp_tbl_OtpMaster;