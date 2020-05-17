drop procedure if exists sp_CreateCustomer;
create procedure sp_CreateCustomer(parFirstName varchar(255), parLastName varchar(255), parEmail varchar(255),
                                   parPassword varchar(255),
                                   parPhone varchar(15), parGender varchar(1), parUsedReferralCode varchar(50),
                                   parReferralCode varchar(50))
begin
    set @isValidEmail = 0;
    set @isValidPhone = 0;
    #Checking for existing customer.
    select c.id
    into @isValidEmail
    from tbl_CustomerMaster c
             inner join tbl_LoginMaster l
                        on l.email_id = c.email
    where c.email = parEmail;
    select c.id
    into @isValidPhone
    from tbl_CustomerMaster c
             inner join tbl_LoginMaster l
                        on l.phone_number = c.phone_number
    where c.phone_number = parPhone;
    if length(parEmail) < 1 then
        set parEmail = null;
    end if;
    if @isValidPhone = 0 and @isValidEmail = 0 then
        set @userId = 0;
        #Creating the customer details.
        insert into tbl_CustomerMaster(first_name, last_name, email, phone_number, gender, used_referral_code,
                                       referral_code,
                                       status_id, created_by)
            VALUE (parFirstName, parLastName, parEmail, parPhone, parGender, parUsedReferralCode, parReferralCode, 2,
                   1);
        select last_insert_id() into @userId;
        #Creating the login details for the customer.
        insert into tbl_LoginMaster (email_id, password, phone_number, role, user_id, created_by)
            VALUE (parEmail, parPassword, parPhone, 'tbl_CustomerMaster', @userId, @userId);
        select @userId as id;
    else
        #Email or phone already exits.
        select -1 as id;
    end if;
end;