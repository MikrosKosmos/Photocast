drop procedure if exists sp_CreateLoginDetails;
create procedure sp_CreateLoginDetails(parPhone varchar(15), parEmail varchar(255), parPassword varchar(255),
                                       parRole varchar(30), parUserId int)
begin
    set @isEmailValid = 0;
    set @isPhoneValid = 0;
    if length(parEmail) > 0 then
        select id into @isEmailValid from tbl_LoginMaster where email_id = parEmail limit 1;
    else
        select id into @isPhoneValid from tbl_LoginMaster where phone_number = parPhone limit 1;
    end if;
    if @isPhoneValid = 0 and @isEmailValid = 0 then
        #Inserting into the login details.
        insert into tbl_LoginMaster (email_id, password, phone_number, role, user_id, created_by)
            VALUE (parEmail, parPassword, parPhone, parRole, parUserId, 1);
        select last_insert_id() as id;
    else
        select -1 as id;
    end if;
end;

