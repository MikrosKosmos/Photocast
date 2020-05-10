drop procedure if exists sp_GetCustomerDetails;
create procedure sp_GetCustomerDetails(parUserId int, parEmail varchar(255), parPhone varchar(255))
begin
    set @whereClaus = '';
    if length(parEmail) > 0 then
        set @whereClaus = concat(@whereClaus, ' email = ''', parEmail, ''' and ');
    end if;
    if length(parPhone) > 0 then
        set @whereClaus = concat(@whereClaus, ' phone_number = ''', parPhone, ''' and ');
    end if;
    if parUserId > 0 then
        set @whereClaus = concat(@whereClaus, ' id = ', parUserId, ' and ');
    end if;
    set @whereClaus = concat(@whereClaus, ' c.is_active = 1');
    select concat('select c.id,
           first_name,
           last_name,
           email,
           phone_number,
           gender,
           used_referral_code,
           referral_code,
           status_id,
           s.status_name
    from tbl_CustomerMaster c
             left join tbl_StatusMaster s
                       on s.id = c.status_id where ', @whereClaus)
    into @stmtSQL;
    #select @stmtSQL;
    prepare stmtExec from @stmtSQL;
    execute stmtExec;
    deallocate prepare stmtExec;
end;