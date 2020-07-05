drop procedure if exists sp_GetComments;
create procedure sp_GetComments(parPostId int)
begin
    set @isExists = 0;
    select id into @isExists from tbl_PostMaster where id = parPostId and is_active = 1;
    if @isExists > 0 then
        select id, post_id, first_name, last_name, user_id, role, comment
        from tbl_CommentMaster
        where post_id = parPostId
          and is_active = 1;
    else
        select -1 as id;
    end if;
end;