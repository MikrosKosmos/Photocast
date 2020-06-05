drop procedure if exists sp_UpdateImages;
create procedure sp_UpdateImages(parImageType enum ('DP','DOCUMENT'), parImageKey varchar(255),
                                 parBaseUrl varchar(255), parVendorId int)
begin
    set @isExists = 0;
    #Checking if the vendor exists or not.
    select id into @isExists from tbl_VendorMaster where id = parVendorId and is_active = 1;
    if @isExists > 0 then
        #Updating the DP of the vendor.
        if parImageType = 'DP' then
            update tbl_VendorMaster
            set profile_image =parBaseUrl,
                modified_by=parVendorId,
                modified=now()
            where id = parVendorId
              and is_active = 1;
        elseif parImageType = 'DOCUMENT' then
            #Inserting the vendor images.
            insert into tbl_VendorImages(vendor_id, image_type, image_key, base_url, created_by)
                value (parVendorId, parImageType, parImageKey, parBaseUrl, parVendorId);
            select last_insert_id() as id;
        end if;
    else
        select -1 as id;
    end if;
end;
