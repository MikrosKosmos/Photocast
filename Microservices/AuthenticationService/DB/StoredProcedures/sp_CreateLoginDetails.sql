drop procedure if exists sp_CreateLoginDetails;
create procedure sp_CreateLoginDetails(parPhone varchar(15), parEmail varchar(255), parPassword varchar(255),
                                       parRole varchar(30))
begin
    #Inserting into the login details.
    insert into tbl_LoginMaster (email_id, password, phone_number, role, created_by)
        VALUE (parEmail, parPassword, parPhone, parRole, 1);
    select last_insert_id() as id;
end;

