drop procedure if exists sp_LikeUnlikePost;
create procedure sp_LikeUnlikePost(parPostId int, parUserId int, parFirstName varchar(255), parLastName varchar(255),
                                   parRole varchar(100))
begin
    set @isExists = 0;
    set @activeValue = 0;
    #checking whether exists or not.
    select id
    into @isExists
    from tbl_LikeMaster
    where post_id = parPostId
      and user_id = parUserId
      and role = parRole;
    if @isExists > 0 then
        #If it exists, liking or unliking the post.
        update tbl_LikeMaster
        set modified=now(),
            modified_by=parUserId,
            is_active =
                case
                    when is_active = 1
                        then 0
                    when is_active = 0 then 1
                    end
        where post_id = parPostId
          and user_id = parUserId
          and role = parRole;
        select @isExists as id;
    else
        #Creating new like data.
        insert into tbl_LikeMaster(post_id, user_id, first_name, last_name, role, is_active, created_by)
            value (parPostId, parUserId, parFirstName, parLastName, parRole, 1, parUserId);
        select last_insert_id() as id;
    end if;
end;