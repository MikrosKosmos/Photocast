drop procedure if exists sp_OtpCreateCheck;
create procedure sp_OtpCreateCheck(parMobileNumber varchar(13), parOtp int, parValidity varchar(20), isCheck tinyint)
BEGIN
    # isCheck =1 for validating the OTP.
    IF isCheck = 1 then
        select fn_ValidateOTP(parMobileNumber, parOtp) as id;
    else
        SET @isExists = 0;
        set @isValid = 0;
        select id into @isValid from tbl_LoginMaster where phone_number = parMobileNumber and is_active = 1;
        if @isValid > 0 then
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
        else
            select -1 as id;
        end if;
    end if;
end;