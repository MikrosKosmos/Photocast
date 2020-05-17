drop function if exists fn_ValidateOTP;
create function fn_ValidateOTP(parPhone varchar(15), parOTP int) returns int
begin
    set @isValid = -1;
    select id
    into @isValid
    from tbl_OtpMaster
    where phone_number = parPhone
      and OTP = parOtp
      and validity > now()
      and is_active = 1;
    if @isValid > 0 then
        delete from tbl_OtpMaster where phone_number = parPhone and otp = parOTP;
    end if;
    return @isValid;
end;