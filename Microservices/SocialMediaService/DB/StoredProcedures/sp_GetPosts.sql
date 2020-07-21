drop procedure if exists sp_GetPosts;
create procedure sp_GetPosts(parInitialValue int, parLimit int, parIsSelf tinyint, parVendorId int)
begin
    if parIsSelf = 1 then
        select p.id,
               p.vendor_id,
               p.first_name,
               p.last_name,
               p.post_description,
               p.image_url,
               p.like_count,
               p.comment_count
        from tbl_PostMaster p
        where p.is_active = 1
          and p.vendor_id = parVendorId

        limit parInitialValue,parLimit;
    else
        select p.id,
               p.vendor_id,
               p.first_name,
               p.last_name,
               p.post_description,
               p.image_url,
               p.like_count,
               p.comment_count
        from tbl_PostMaster p
        where p.is_active = 1
        limit parInitialValue,parLimit;
    end if;
end;