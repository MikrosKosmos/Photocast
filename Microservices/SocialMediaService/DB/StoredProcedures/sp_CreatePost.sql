drop procedure if exists sp_CreatePost;
create procedure sp_CreatePost(parVendorId int, parPostDesc text, parImageUrl varchar(255), parFirstName varchar(255),
                               parLastName varchar(255))
begin
    if length(parImageUrl) > 0 then
        insert into tbl_PostMaster(vendor_id, post_description, image_url, first_name, last_name,
                                   created_by)
            value (parVendorId, parPostDesc, parImageUrl, parFirstName, parLastName, parVendorId);
        select last_insert_id() as id;
    else
        select -1 as id;
    end if;
end;