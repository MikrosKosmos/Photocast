drop procedure if exists sp_UpdateLoginDetails;
create procedure sp_UpdateLoginDetails(parPhone varchar(15), parEmail varchar(255), parPassword varchar(255),
                                       parRole varchar(30), parUserId int, OUT isUpdated int)
begin
    #This SP can be used to check for the validity of the login details.
    set @loginId = 0;
    #Checking the validity of the login details.
    select id into @loginId from tbl_LoginMaster where user_id = parUserId and role = parRole and is_active = 1;
    if @loginId > 0 then
        #Updating the email or phone of the user.
        if length(parEmail) > 0 and length(parPassword) > 0 then
            update tbl_LoginMaster
            set email_id=parEmail,  password = parPassword,
                modified_by=parUserId , modified = now()
            where user_id = parUserId
              and role = parRole
              and id = @loginId
              and is_active = 1;
        elseif length(parPhone) > 0 then
            update tbl_LoginMaster
            set phone_number = parPhone,
                modified_by=parUserId , modified = now()
            where user_id = parUserId
              and role = parRole
              and id = @loginId
              and is_active = 1;
        end if;
        set isUpdated = 1;
    else
        #The login details is invalid.
        set isUpdated = -1;
    end if;
end;

