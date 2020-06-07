drop procedure if exists sp_tbl_CountryMaster;
create procedure sp_tbl_CountryMaster()
begin
    declare currentSchema varchar(50) default '';
    select database() into currentSchema;
    if not exists(
            select 1
            from information_schema.TABLES
            where TABLE_SCHEMA = currentSchema
              and TABLE_NAME = 'tbl_CountryMaster'
        ) then
        begin
            CREATE TABLE `tbl_CountryMaster`
            (
                `id`             int(11) unsigned NOT NULL AUTO_INCREMENT,
                `country_name`   varchar(255)     NOT NULL,
                `country_code`   varchar(255)          DEFAULT NULL,
                `citizenship`    varchar(255)          DEFAULT NULL,
                `country_id_org` int(10) unsigned      DEFAULT NULL,
                `is_active`      tinyint(1) unsigned   DEFAULT '1',
                `created_by`     int(11)          NOT NULL,
                `created`        timestamp        NULL DEFAULT CURRENT_TIMESTAMP,
                `modified_by`    int(11)               DEFAULT NULL,
                `modified`       timestamp        NULL DEFAULT NULL,
                PRIMARY KEY (`id`)
            );
        end;
    end if;
end;
call sp_tbl_CountryMaster();
drop procedure if exists sp_tbl_CountryMaster;