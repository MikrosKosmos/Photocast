drop procedure if exists sp_ValidateLogin;
create procedure sp_ValidateLogin(parPhone varchar(15), parOTP int, parEmail varchar(255), parPassword varchar(255))
begin
    set @isValid = 0;
    set @userId = 0;
    set @table = '';
    if length(parPhone) > 0 and length(parOTP) > 0 then
        select fn_ValidateOTP(parPhone, parOTP) into @isValid;
        select user_id, role
        into @userId,@table
        from tbl_LoginMaster
        where phone_number = parPhone
          and is_active = 1
        limit 1;
    else
        select user_id, role
        into @userId,@table
        from tbl_LoginMaster
        where email_id = parEmail
          and password = parPassword
          and is_active = 1;
        if @userId > 0 then
            set @isValid = 1;
        end if;
    end if;
    if @isValid > 0 then
        if @table = 'tbl_VendorMaster' then
            select v.id,
                   first_name,
                   last_name,
                   'tbl_VendorMaster' as role,
                   email_id,
                   phone_number,
                   gender,
                   vendor_type,
                   company_brand_name,
                   address_1,
                   address_2,
                   city_id,
                   pincode,
                   gps_lat,
                   gps_long,
                   status_id,
                   s.status_name,
                   used_referral_code,
                   referral_code
            from tbl_VendorMaster v
                     left join tbl_StatusMaster s
                               on v.status_id = s.id
            where v.id = @userId
              and v.is_active = 1;
        else
            select c.id,
                   first_name,
                   last_name,
                   'tbl_CustomerMaster' as role,
                   email,
                   phone_number,
                   gender,
                   used_referral_code,
                   referral_code,
                   status_id,
                   s.status_name
            from tbl_CustomerMaster c
                     left join tbl_StatusMaster s
                               on c.status_id = s.id
            where c.id = @userId
              and c.is_active = 1;
        end if;
    else
        select -1 as id;
    end if;
end;