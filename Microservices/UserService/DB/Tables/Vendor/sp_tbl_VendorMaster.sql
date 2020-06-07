drop procedure if exists sp_tbl_VendorMaster;
create procedure sp_tbl_VendorMaster()
begin
    declare currentSchema varchar(50) default '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_VendorMaster'
        ) then
        begin
            create table tbl_VendorMaster
            (
                id                 int primary key auto_increment           not null,
                first_name         varchar(255)                             not null,
                last_name          varchar(255)                             not null,
                email_id           varchar(255),
                phone_number       varchar(15)                              not null,
                gender             enum ('M','F')                           not null,
                vendor_type        ENUM ('PERSONAL','COMPANY')              not null,
                company_brand_name varchar(255)   default null,
                address_1          varchar(255)   default null,
                address_2          varchar(255)   default null,
                city_id            int            default null,
                pincode            int            default null,
                gps_lat            decimal(30, 8) default null,
                gps_long           decimal(30, 8) default null,
                status_id          int            default null,
                used_referral_code varchar(100)   default null,
                referral_code      varchar(100)   default null,
                is_active          tinyint        default 1                 not null,
                created_by         int                                      not null,
                created            timestamp      default current_timestamp not null,
                modified_by        int            default null,
                modified           timestamp      default null
            );
        end;
    end if;
    if not exists(
            select 1
            from information_schema.COLUMNS
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_VendorMaster'
              and COLUMN_NAME = 'profile_image'
        ) then
        begin
            alter table tbl_VendorMaster
                add column profile_image varchar(255) default null after gps_long;
        end;
    end if;
end;
call sp_tbl_VendorMaster();
drop procedure if exists sp_tbl_VendorMaster;