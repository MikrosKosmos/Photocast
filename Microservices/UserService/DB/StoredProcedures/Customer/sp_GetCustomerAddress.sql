drop procedure if exists sp_GetCustomerAddress;
create procedure sp_GetCustomerAddress(parCustomerId int)
begin
    set @isValid = 0;
    select id into @isValid from tbl_CustomerMaster where id = parCustomerId and is_active = 1;
    if @isValid > 0 then
        select id,
               customer_id,
               address1,
               address2,
               city_id,
               pincode,
               gps_lat,
               gps_long,
               is_default
        from tbl_CustomerAddressDetails
        where customer_id = parCustomerId
          and is_active = 1;
    else
        select -1 as id;
    end if;
end;