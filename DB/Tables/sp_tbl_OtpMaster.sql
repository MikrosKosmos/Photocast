create or replace procedure sp_tbl_OtpMaster()
    language plpgsql
as
$$
declare
    currentSchema varchar(255) := '';
begin
    select current_database() into currentSchema;
    if not exists(
            select 1
            from information_schema.tables
            where table_schema = currentSchema
              and table_name = 'tbl_OtpMaster'
        ) then
        begin
            create table tbl_otpMaster
            (
                id           serial,
                phone_number varchar(15) NOT NULL,
                otp          int         not null,
                validity     timestamp default NULL,
                is_active    smallint  default 1,
                created_by   int         NOT NULL,
                created      timestamp default now(),
                modified_by  int       default null,
                modified     timestamp default null
            );
        end;
    end if;
end ;
$$;
alter procedure sp_tbl_OtpMaster() owner to photocast;
call sp_tbl_OtpMaster();
drop procedure if exists sp_tbl_OtpMaster();