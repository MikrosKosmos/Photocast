drop procedure if exists sp_CreateVendor;
create procedure sp_CreateVendor(parFirstName varchar(255), parLastName varchar(255), parEmailId varchar(255),
                                 parPhoneNumber varchar(15), parGender varchar(1),
                                 parVendorType ENUM ('PERSONAL','COMPANY'), parCompanyName varchar(255),
                                 parAddress1 varchar(255), parAddress2 varchar(255), parCityId int, parPincode int,
                                 parGpsLat decimal(30, 8), parGpsLong decimal(30, 8),
                                 parUsedReferralCode varchar(255), parReferralCode varchar(255),
                                 parDocumentType varchar(255),
                                 parDocumentID varchar(255))
begin
    if length(parEmailId) < 1 then
        set parEmailId = null;
    end if;
    if parVendorType = 'COMPANY' and parDocumentType <> 'PAN CARD' then
        select -1 as id;
    else
        set @isDocumentValid = 0;
        select id into @isDocumentValid from tbl_IdentificationDocumentMaster where document_id_number = parDocumentID;
        set @isLoginValid = 0;
        select id
        into @isLoginValid
        from tbl_LoginMaster
        where phone_number = parPhoneNumber
        limit 1;
        if @isDocumentValid > 0 then
            select -1 as id;
        elseif @isLoginValid > 0 then
            select -1 as id;
        else
            set @userId = 0;
            #Creating the user.
            insert into tbl_VendorMaster (first_name, last_name, email_id, phone_number, gender, vendor_type,
                                          company_brand_name, address_1, address_2, city_id, pincode,
                                          gps_lat, gps_long, status_id,
                                          used_referral_code, referral_code, created_by)
                VALUE (
                       parFirstName, parLastName,
                       parEmailId, parPhoneNumber,
                       parGender, parVendorType,
                       parCompanyName, parAddress1, parAddress2,
                       parCityId, parPincode, parGpsLat, parGpsLong, 1,
                       parUsedReferralCode, parReferralCode, 1
                );
            select last_insert_id() into @userId;
            #Creating the document data.
            insert into tbl_IdentificationDocumentMaster (document_holder_id, document_holder_type, document_type,
                                                          document_id_number, created_by)
                VALUE (@userId, 'tbl_VendorMaster', parDocumentType, parDocumentID, @userId);
            #Creating the login details for the vendor.
            insert into tbl_LoginMaster(email_id, password, phone_number, role, user_id, created_by)
                value (parEmailId, '', parPhoneNumber, 'tbl_VendorMaster', @userId, @userId);
            select @userId as id;
        end if;
    end if;
end;