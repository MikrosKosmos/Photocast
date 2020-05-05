drop procedure if exists sp_OtpCreateCheck;
create procedure sp_OtpCreateCheck(parMobileNumber varchar(13), parOtp int, parValidity varchar(20), isCheck tinyint)
BEGIN
    # isCheck =1 for validating the OTP.
    IF isCheck = 1 then
        SET @isValid = 0;
        select id
        into @isValid
        from tbl_OtpMaster
        where phone_number = parMobileNumber
          and OTP = parOtp
          and validity > now()
          and is_active = 1;
        #select @isValid;
        if @isValid > 0 THEN
            delete from tbl_OtpMaster WHERE phone_number = parMobileNumber and OTP = parOtp;
            select 1 as id;
        else
            select -1 as id;
        end if;
    else
        SET @isExists = 0;
        select id into @isExists from tbl_OtpMaster where phone_number = parMobileNumber;
        if @isExists > 0 THEN
            UPDATE tbl_OtpMaster
            set OTP=parOtp,
                validity=parValidity,
                is_active=1,
                modified=now(),
                modified_by=1
            where phone_number = parMobileNumber;
        else
            insert into tbl_OtpMaster (phone_number, OTP, validity, created_by)
            values (parMobileNumber, parOtp, parValidity, 1);
        end if;
        select 1 as id;
    end if;
end;