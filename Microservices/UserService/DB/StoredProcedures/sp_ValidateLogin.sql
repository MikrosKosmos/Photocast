drop procedure if exists sp_ValidateLogin;
create procedure sp_ValidateLogin(parPhone varchar(15), parOTP int, parEmail varchar(255), parPassword varchar(255))
begin
    set @isValid = 0;
    if length(parPhone) > 0 and length(parOTP) > 0 then
        select fn_ValidateOTP(parPhone, parOTP) into @isValid;
    else
        select id
        into @isValid
        from tbl_LoginMaster
        where email_id = parEmail
          and password = parPassword
          and is_active = 1;
    end if;
    if @isValid > 0 then
        #TODO: get the user details.
        select 1 as id;
    else
        select -1 as id;
    end if;
end;