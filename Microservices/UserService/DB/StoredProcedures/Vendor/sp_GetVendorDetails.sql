drop procedure if exists sp_GetVendorDetails;
create procedure sp_GetVendorDetails(parVendorId int, parEmail varchar(255), parPhone varchar(15))
begin
    set @whereClaus = '';
    if parVendorId > 0 then
        set @whereClaus = concat(@whereClaus, ' v.id = ', parVendorId, ' and ');
    end if;
    if length(parEmail) > 0 then
        set @whereClaus = concat(@whereClaus, ' email_id = ''', parEmail, ''' and ');
    end if;
    if length(parPhone) > 0 then
        set @whereClaus = concat(@whereClaus, ' phone_number =''', parPhone, ''' and ');
    end if;
    if length(@whereClaus) > 0 then
        set @whereClaus = concat(@whereClaus, ' v.is_active = 1 ');
        select concat('select v.id,
               first_name,
               last_name,
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
               status_name,
               used_referral_code,
               referral_code
        from tbl_VendorMaster v
                 left join tbl_StatusMaster s
                           on s.id = v.status_id where ', @whereClaus)
        into @stmtSQL;
        #select @stmtSQL;
        prepare stmtExec from @stmtSQL;
        execute stmtExec;
        deallocate prepare stmtExec;
    else
        select -1 as id;
    end if;
end;
