const config = {};
config.port = 6001;

/**
 * data base configurations.
 * @type {string}
 */
config.databasePort = 3306;
config.databaseUserName = "photocast";
config.databaseName = "user";

/**
 * Encryption constants
 */
config.ENCRYPT_ALGO = "aes256";
config.ENCRYPT_ENCODING = "hex";

/**
 * exporting the Config.
 */
module.exports = config;