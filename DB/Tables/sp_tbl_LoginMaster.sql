create
    or
    replace procedure sp_tbl_LoginMaster()
    language plpgsql
as
$$
declare
    currentSchema varchar(100) := '';
begin
    select current_database()
    into currentSchema;
    if not exists(
            select 1
            from information_schema.tables
            where table_schema = currentSchema
              and table_name = 'tbl_LoginMaster'
        ) then
        begin
            create table tbl_LoginMaster
            (
                id           serial,
                email_id     varchar(255) DEFAULT NULL,
                password     varchar(255) DEFAULT NULL,
                phone_number varchar(15) NOT NULL,
                user_id      int         NOT NULL,
                role         varchar(30) not null,
                is_active    smallint     default 1,
                created_by   int         NOT NULL,
                created      timestamp    default now(),
                modified_by  int          default null,
                modified     timestamp    default null
            );
        end;
    end if;
end;
$$;
alter procedure sp_tbl_LoginMaster() owner to photocast;
call sp_tbl_LoginMaster();
drop procedure if exists sp_tbl_LoginMaster();