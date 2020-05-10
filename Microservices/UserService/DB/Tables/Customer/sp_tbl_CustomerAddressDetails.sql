DROP PROCEDURE IF EXISTS sp_tbl_CustomerAddressDetails;
DELIMITER $$
CREATE PROCEDURE sp_tbl_CustomerAddressDetails()
BEGIN
    DECLARE currentSchema varchar(100);
    SELECT database() into currentSchema;
    IF NOT EXISTS(
            SELECT 1
            FROM information_schema.TABLES
            WHERE TABLE_SCHEMA = currentSchema
              AND TABLE_NAME = 'tbl_CustomerAddressDetails'
        ) THEN
        create table tbl_CustomerAddressDetails
        (
            id          int primary key auto_increment,
            customer_id int            default null,
            address1    varchar(100)   default null,
            address2    varchar(100)   default null,
            city_id     int            default null,
            pincode     int            default null,
            gps_lat     decimal(30, 8) default null,
            gps_long    decimal(30, 8) default null,
            is_default  bool           default true,
            is_active   bool           default 1,
            created_by  int            default null,
            created     datetime       default current_timestamp(),
            modified_by int            default null,
            modified    datetime       default null
        );
    end if;
end$$

DELIMITER ;
CALL sp_tbl_CustomerAddressDetails();
DROP PROCEDURE IF EXISTS sp_tbl_CustomerAddressDetails;