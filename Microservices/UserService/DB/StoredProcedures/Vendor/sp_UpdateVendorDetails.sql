drop procedure if exists sp_UpdateVendorDetails;
create procedure sp_UpdateVendorDetails(parVendorId int, parEmail varchar(255), parPassword varchar(255),
                                        parPhoneNumber varchar(15),
                                        parBrandName varchar(255),
                                        parAddress1 varchar(255), parAddress2 varchar(255), parPincode int, parCity int,
                                        parGPSLat decimal(30, 8), parGPSLong decimal(30, 8))
begin
    set @setClaus = '';
    set @isAuthChanged = 0;
    if length(parEmail) > 0 then
        set @setClaus = concat(@setClaus, ' email_id = ''', parEmail, ''', ');
        set @isAuthChanged = 1;
    end if;
    if length(parPhoneNumber) > 0 then
        set @setClaus = concat(@setClaus, ' phone_number = ''', parPhoneNumber, ''', ');
        set @isAuthChanged = 1;
    end if;
    if length(parBrandName) > 0 then
        set @setClaus = concat(@setClaus, ' company_brand_name = ''', parBrandName, ''', ');
    end if;
    if length(parAddress1) > 0 then
        set @setClaus = concat(@setClaus, ' address_1 = ''', parAddress1, ''', ');
    end if;
    if length(parAddress2) > 0 then
        set @setClaus = concat(@setClaus, ' address_2 =''', parAddress2, ''', ');
    end if;
    if parCity > 0 then
        set @setClaus = concat(@setClaus, ' city_id = ', parCity, ', ');
    end if;
    if parPincode > 0 then
        set @setClaus = concat(@setClaus, ' pincode = ', parPincode, ', ');
    end if;
    if parGPSLat > 0 then
        set @setClaus = concat(@setClaus, ' gps_lat = ', parGPSLat, ', ');
    end if;
    if parGPSLong > 0 then
        set @setClaus = concat(@setClaus, ' gps_long = ', parGPSLong, ', ');
    end if;
    set @setClaus = concat(@setClaus, ' modified_by = ', parVendorId, ' ,modified = now()');
    select concat('update tbl_VendorMaster set ', @setClaus, ' where id = ', parVendorId, ' and is_active = 1')
    into @stmtSQL;
    if @isAuthChanged = 1 then
        set @authSet = '';
        if length(parEmail) > 0 then
            set @authSet = concat(@authSet, ' email_id =''', parEmail, ''',');
        end if;
        if length(parPassword) > 0 then
            set @authSet = concat(@authSet, ' password = ''', parPassword, ''',');
        end if;
        if length(parPhoneNumber) > 0 then
            set @authSet = concat(@authSet, ' phone_number = ''', parPhoneNumber, ''',');
        end if;
        select concat('update tbl_LoginMaster set ', @authSet, ' where user_id = ', parVendorId) into @stmtSQL1;
        #select @stmtSQL1;
        prepare stmtExec from @stmtSQL1;
        execute stmtExec;
        deallocate prepare stmtExec;
    end if;
    #select @stmtSQL;
    prepare stmtExec from @stmtSQL;
    execute stmtExec;
    deallocate prepare stmtExec;
end;
