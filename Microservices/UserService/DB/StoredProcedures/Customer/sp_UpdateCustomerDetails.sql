drop procedure if exists sp_UpdateCustomerDetails;
create procedure sp_UpdateCustomerDetails(parFirstName varchar(255), parLastName varchar(255), parEmail varchar(255),
                                          parPhone varchar(15), parPassword varchar(255), parUserId int)
begin
    set @setClaus = '';
    set @isAuthChanged = 0;
    if length(parFirstName) > 0 then
        set @setClaus = concat(@setClaus, ' first_name = ''', parFirstName, ''',');
    end if;
    if length(parLastName) > 0 then
        set @setClaus = concat(@setClaus, ' last_name =''', parLastName, ''',');
    end if;
    if length(parEmail) > 0 then
        set @setClaus = concat(@setClaus, ' email = ''', parEmail, ''',');
        set @isAuthChanged = 1;
    end if;
    if length(parPhone) > 0 then
        set @setClaus = concat(@setClaus, ' phone_number = ''', parPhone, ''',');
        set @isAuthChanged = 1;
    end if;
    select concat('update tbl_CustomerMaster set ', @setClaus, ' modified_by = ', parUserId,
                  ' , modified =now() where id =', parUserId)
    into @stmtSQL;
    #select @stmtSQL;
    prepare stmtExec from @stmtSQL;
    execute stmtExec;
    deallocate prepare stmtExec;
    if @isAuthChanged = 1 then
        set @isUpdated = 0;
        CALL sp_UpdateLoginDetails(parPhone, parEmail, parPassword,
                                   'tbl_CustomerMaster', parUserId, @isUpdated);
        if @isUpdated > 0 then
            select 1 as id;
        else
            select -1 as id;
        end if;
    else
        select 1 as id;
    end if;
end;