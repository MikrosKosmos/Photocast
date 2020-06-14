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
               p.comment_count,
               l.id         as LikeId,
               l.post_id,
               l.user_id    as likeUserId,
               l.first_name as likeUserFirstName,
               l.last_name  as likeUserLastName,
               l.role,
               c.id,
               c.post_id,
               c.first_name as commentUserFirstName,
               c.last_name  as commentUserLastName,
               c.user_id    as commentUserId,
               c.role       as commentUserRole,
               comment
        from tbl_PostMaster p
                 left join tbl_LikeMaster l
                           on l.post_id = p.id
                 left join tbl_CommentMaster c
                           on c.post_id = p.id
        where p.is_active = 1
          and p.vendor_id = parVendorId
          and c.is_active = 1
          and l.is_active = 1
        limit parInitialValue,parLimit;
    else
        select p.id,
               p.vendor_id,
               p.first_name,
               p.last_name,
               p.post_description,
               p.image_url,
               p.like_count,
               p.comment_count,
               l.id         as LikeId,
               l.post_id,
               l.user_id    as likeUserId,
               l.first_name as likeUserFirstName,
               l.last_name  as likeUserLastName,
               l.role,
               c.id,
               c.post_id,
               c.first_name as commentUserFirstName,
               c.last_name  as commentUserLastName,
               c.user_id    as commentUserId,
               c.role       as commentUserRole,
               comment
        from tbl_PostMaster p
                 left join tbl_LikeMaster l
                           on l.post_id = p.id
                 left join tbl_CommentMaster c
                           on c.post_id = p.id
        where p.is_active = 1
          and c.is_active = 1
          and l.is_active = 1
        limit parInitialValue,parLimit;
    end if;
end;