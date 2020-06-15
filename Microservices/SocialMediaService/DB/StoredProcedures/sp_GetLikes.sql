drop procedure if exists sp_GetLikes;
create procedure sp_GetLikes(parPostId int)
begin
    set @isExists = 0;
    select id into @isExists from tbl_PostMaster where id = parPostId and is_active = 1;
    if @isExists > 0 then
        select id, post_id, user_id, first_name, last_name, role
        from tbl_LikeMaster
        where post_id = parPostId
          and is_active = 1;
    else
        select -1 as id;
    end if;
end;