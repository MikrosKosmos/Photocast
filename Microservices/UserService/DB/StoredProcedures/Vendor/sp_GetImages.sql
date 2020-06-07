drop procedure if exists sp_GetImages;
create procedure sp_GetImages(parVendorId int, parImageType enum ('DP','DOCUMENT'))
begin
    set @isExists = 0;
    select id into @isExists from tbl_VendorMaster where id = parVendorId and is_active = 1;
    if @isExists > 0 then
        if parImageType = 'DP' then
            select id, profile_image from tbl_VendorMaster where id = parVendorId and is_active = 1;
        elseif parImageType = 'DOCUMENT' then
            select id, vendor_id, image_type, image_key, base_url, position
            from tbl_VendorImages
            where vendor_id = parVendorId
              and is_active = 1;
        else
            select -1 as id;
        end if;
    else
        select -1 as id;
    end if;
end;