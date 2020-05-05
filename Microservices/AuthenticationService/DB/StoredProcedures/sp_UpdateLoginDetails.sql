drop procedure if exists sp_UpdateLoginDetails;
create procedure sp_UpdateLoginDetails(parEmail varchar(255), parPhone varchar(15), parPassword varchar(255),
                                       parUserId int)
begin
    set @setClaus = ' set ';
    if length(parEmail) > 0 then
        set @setClaus = concat(@setClaus, ' email_id = ''', parEmail, ''',');
    end if;
    if length(parPhone) > 0 then
        set @setClaus = concat(@setClaus, ' phone_number =''', parPhone, ''',');
    end if;
    if length(parPassword) > 0 then
        set @setClaus = concat(@setClaus, ' password = ''', parPassword, ''',');
    end if;
    set @setClaus = concat(@setClaus, ' modified_by = 1 and modified = now()');
    select concat('update tbl_LoginMaster ', @setClaus, ' WHERE user_id = ', parUserId) into @stmtSQL;
    #select @stmtSQL;
    prepare stmtExec from @stmtSQL;
    execute stmtExec;
    deallocate prepare stmtExec;
end;