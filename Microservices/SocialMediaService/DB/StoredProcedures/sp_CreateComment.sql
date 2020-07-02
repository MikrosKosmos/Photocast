drop procedure if exists sp_CreateComment;
create procedure sp_CreateComment(parPostId int, parRole varchar(100), parUserId int, parFirstName varchar(255),
                                  parLastName varchar(255), parComment varchar(255))
begin
    set @isExists = 0;
    select id into @isExists from tbl_PostMaster where is_active = 1 and id = parPostId;
    if @isExists > 0 then
        insert into tbl_CommentMaster (post_id, first_name, last_name, user_id, role, comment, created_by)
            value (parPostId, parFirstName, parLastName, parUserId, parRole, parComment, parUserId);
        select last_insert_id() as id;
        update tbl_PostMaster set comment_count=(comment_count + 1) where id = parPostId and is_active = 1;
    else
        select -1 as id;
    end if;
end;