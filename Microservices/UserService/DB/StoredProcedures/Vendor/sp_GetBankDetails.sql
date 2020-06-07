drop procedure if exists sp_GetBankDetails;
create procedure sp_GetBankDetails(parVendorId int)
begin
    set @isExists = 0;
    select id into @isExists from tbl_VendorMaster where id = parVendorId and is_active = 1;
    if @isExists > 0 then
        select id,
               holder_id,
               holder_type,
               holder_name,
               account_number,
               bank_name,
               ifsc_code,
               contact_number,
               payment_gateway_account_id
        from tbl_BankDetailsMaster
        where holder_id = parVendorId
          and holder_type = 'tbl_VendorMaster'
          and is_active = 1;
    else
        select -1 as id;
    end if;
end;