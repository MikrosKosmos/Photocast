const constants = {};
constants.SYSTEM_USER_ID = 1;
/**
 * Color Constants.
 * @type {string}
 */
constants.COLOR_RED = "\x1b[31m";
constants.COLOR_GREEN = "\x1b[32m";

/**
 * Logging File Details.
 */
constants.LOG_FILE_NAME = 'cirro-logs.logs';

/**
 * Date Format.
 */
constants.DATE_FORMAT = "YYYY-MM-DD";
constants.DATE_TIME_FORMAT = "YYYY-MM-DD HH:mm:ss";
constants.TIME_ZONE = "Asia/Kolkata";

/**
 * Validation strings.
 */
constants.EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
constants.PASSWORD_REGEX = "(?=^.{6,16}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])" +
   "(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?&gt;.&lt;,])(?!.*\\s).*$";
constants.PHONE_NUMBER_PREFIX = "+91";
constants.DATE_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

/**
 * Header Data.
 */
constants.HEADERS = {
   'Access-Control-Allow-Origin': '*',
   'Access-Control-Allow-Methods': 'OPTIONS, POST, GET, PUT, DELETE',
   'Access-Control-Max-Age': 2592000,
   'Access-Control-Allow-Headers': 'Content-Type,access-control-allow-origin,X-Requested-With,key,jw_token'
};
constants.CONTENT_TYPE_TEXT = "Content-Type";
constants.CONTENT_TYPE_JSON = "application/json";

/**
 * Constants for Role Details.
 */
constants.ROLE_ADMIN = "Admin";
constants.ROLE_EMPLOYEE = "Employee";
constants.ROLE_CUSTOMER = "tbl_CustomerMaster";
constants.ROLE_VENDOR = "tbl_VendorMaster";

/**
 * Request Keys.
 */
constants.API_TOKEN_KEY = "key";
constants.API_REQUEST_KEY = "request_Key";
constants.REQUEST_TYPE = "type";
/**
 * Status Values
 */
constants.STATUS_PENDING = 1;
constants.STATUS_CONFIRM = 2;
constants.STATUS_CANCEL = 3;
constants.STATUS_REJECTED = 4;
constants.STATUS_COMPLETED = 5;
constants.STATUS_PROCESSING = 6;
constants.STATUS_ERROR = 7;
constants.STATUS_AUTHORIZED = 8;
constants.STATUS_CAPTURED = 9;
constants.STAUTS_REFUNDED = 10;
constants.STATUS_VERIFIED = 11;

/**
 * API Methods.
 */
constants.HTTP_POST = "post";
constants.HTTP_GET = "get";
constants.HTTP_PUT = "put";
constants.HTTP_OPTIONS = "options";

/**
 * Response Codes and messages.
 */
constants.BAD_REQUEST_CODE = 400;
constants.HTTP_NOT_FOUND_CODE = 404;
constants.FORBIDDEN_REQUEST_CODE = 403;
constants.INTERNAL_SERVER_ERROR_CODE = 500;
constants.HTTP_SUCCESS = 200;
constants.HTTP_ACCEPTED_OKAY = 201;
constants.HTTP_UNAUTHORIZED_CODE = 401;

constants.RESPONSE_KEY = "res";
constants.ERROR_MESSAGE = "Error";
constants.USER_DATA = "user_data";
constants.JW_TOKEN = "jw_token";
constants.BAD_REQUEST_MESSAGE = "Incorrect Request";
constants.FORBIDDEN_MESSAGE = "Incorrect Token or token expired.";
constants.INSUFFICIENT_DATA_MESSAGE = "Insufficient Data";
constants.INVALID_METHOD_MESSAGE = "Invalid Method";
constants.INVALID_PATH = "Invalid Path";
constants.INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
constants.RESPONSE_KEY_ERROR = "error";
constants.RESPONSE_SUCESS_LEVEL_1 = "1";
constants.RESPONSE_SUCCESS_LEVEL_2 = "2";
constants.ERROR_LEVEL_KEY = "error_level";
constants.ERROR_LEVEL_1 = "1";
constants.ERROR_LEVEL_2 = "2";
constants.ERROR_LEVEL_3 = "3";
constants.ERROR_LEVEL_4 = "4";

/**
 * Messages.
 */
constants.WELCOME_MESSAGE = "Welcome to USER MICROSERVICE.";
constants.OTP_MESSAGE = "Your Photocast OTP is :";

/**
 * General Keys
 */
constants.RAZOR_PAY_ID = "key_id";
constants.RAZOR_PAY_SECRET = "key_secret";
constants.AWS_KEY_ID = "accessKeyId";
constants.AWS_SECRET_KEY = "secretAccessKey";
constants.TWO_FACTOR_KEY = "2F";
constants.S3_BUCKET_KEY = "Bucket";
constants.S3_KEY_KEY = "Key";
constants.S3_BODY_KEY = "Body";
constants.ENV_DEVELOPMENT = "DEVELOPMENT";
constants.ENV_PRODUCTION = "PRODUCTION";
constants.ENV_KEY = "ENV";
constants.ENCRYPTION_KEY_KEY = "ENCRYPTED_KEY";
constants.DB_HOST_KEY = "DB_HOST";
constants.DB_PASSWORD_KEY = "DB_PASSWORD";
constants.REDIS_PORT = "redis_port";
constants.REDIS_HOST = "redis_host";
constants.AWS_DOCUMENTS_BUCKET = "photocast-documents";
constants.AWS_IMAGES_BUCKET = "photocast-images";
constants.IMAGES_BASE_URL = "https://photocast-images.s3.ap-south-1.amazonaws.com/";
constants.DOCUMENTS_BASE_URL = "https://photocast-documents.s3.ap-south-1.amazonaws.com/";

/**
 * SP Names.
 */
constants.SP_CHECK_API_TOKEN = "sp_CheckApiToken";
constants.SP_LOG_API_STATUS = "sp_LogApiStatus";
constants.SP_VALIDATE_LOGIN = "sp_ValidateLogin";
constants.SP_CREATE_CUSTOMER = "sp_CreateCustomer";
constants.SP_GET_CUSTOMER = "sp_GetCustomerDetails";
constants.SP_UPDATE_CUSTOMER_ADDRESS = "sp_UpdateCustomerAddress";
constants.SP_UPDATE_CUSTOMER_DETAILS = "sp_UpdateCustomerDetails";
constants.SP_OTP_CREATE_CHECK = "sp_OtpCreateCheck";
constants.SP_GET_CUSTOMER_ADDRESS = "sp_GetCustomerAddress";
constants.SP_CREATE_VENDOR = "sp_CreateVendor";
constants.SP_GET_VENDOR = "sp_GetVendorDetails";
constants.SP_UPDATE_VENDOR = "sp_UpdateVendorDetails";
constants.SP_BANK_DETAILS = "sp_BankDetailsUpdateInsert";
constants.SP_VENDOR_UPDATE_IMAGE = "sp_UpdateImages";
constants.SP_GET_BANK_DETAILS = "sp_GetBankDetails";
constants.SP_GET_VENDOR_IMAGES = "sp_GetImages";
constants.SP_GET_STATE = "sp_GetState";
constants.SP_GET_CITIES = "sp_GetCities";

/**
 * Column Names
 */
constants.COLUMN_CREATED_BY = "created_by";
constants.COLUMN_CREATED = "created";
constants.COLUMN_MODIFIED_BY = "modified_by";
constants.COLUMN_MODIFIED = "modified";
constants.EMPLOYEE_ID = "employee_id";
constants.ID = "id";

constants.CUSTOMER_ID = "customer_id";
constants.CUSTOMER_FIRST_NAME = "first_name";
constants.CUSTOMER_LAST_NAME = "last_name";
constants.CUSTOMER_EMAIL = "email";
constants.CUSTOMER_PASSWORD = "password";
constants.CUSTOMER_PHONE_NUMBER = "phone_number";
constants.CUSTOMER_GENDER = "gender";
constants.CUSTOMER_ADDRESS_ID = "address_id";
constants.CUSTOMER_ADDRESS_1 = "address_1";
constants.CUSTOMER_ADDRESS_2 = "address_2";
constants.CUSTOMER_CITY = "city";
constants.CUSTOMER_STATE = "state";
constants.CUSTOMER_COUNTRY = "country";
constants.CUSTOMER_PINCODE = "pincode";
constants.CUSTOMER_ADDRESS_IS_DEFAULT = "is_default";
constants.CUSTOMER_GPS_LAT = "gps_lat";
constants.CUSTOMER_GPS_LONG = "gps_long";
constants.CUSTOMER_REFERAL_CODE = "referral_code";
constants.CUSTOMER_USED_REFERAL_CODE = "used_referral_code";
constants.IS_VALID = "isValid";
constants.OTP = "otp";

constants.VENDOR_ID = "vendor_id";
constants.VENDOR_FIRST_NAME = "first_name";
constants.VENDOR_LAST_NAME = "last_name";
constants.VENDOR_EMAIL = "email_id";
constants.VENDOR_PASSWORD = "password";
constants.VENDOR_PHONE_NUMBER = "phone_number";
constants.VENDOR_GENDER = "gender";
constants.VENDOR_ADDRESS_1 = "address_1";
constants.VENDOR_ADDRESS_2 = "address_2";
constants.VENDOR_CITY = "city";
constants.VENDOR_STATE = "state";
constants.VENDOR_COUNTRY = "country";
constants.VENDOR_PINCODE = "pincode";
constants.VENDOR_GPS_LAT = "gps_lat";
constants.VENDOR_GPS_LONG = "gps_long";
constants.VENDOR_TYPE = "vendor_type";
constants.VENDOR_BRAND_NAME = "company_brand_name";
constants.VENDOR_PROFILE_IMAGE = "profile_image";
constants.VENDOR_STATUS = "status_id";

constants.BANK_ACCOUNT_HOLDER_ID = "holder_id";
constants.BANK_ACCOUNT_HOLDER_TYPE = "holder_type";
constants.BANK_ACCOUNT_HOLDER_NAME = "holder_name";
constants.BANK_ACCOUNT_ACCOUNT_NUMBER = "account_number";
constants.BANK_ACCOUNT_BANK_NAME = "bank_name";
constants.BANK_ACCOUNT_IFSC_CODE = "ifsc_code";
constants.BANK_ACCOUNT_CONTACT_NUMBER = "contact_number";
constants.BANK_ACCOUNT_PAYMENT_GATEWAY_ID = "payment_gateway_account_id";
constants.BANK_ACCOUNT_IS_UPDATE = "is_update";

constants.VENDOR_IMAGES_VENDOR_ID = "vendor_id";
constants.VENDOR_IMAGES_IMAGE_TYPE = "image_type";
constants.VENDOR_IMAGE_DATA = "image_data";
constants.VENDOR_IMAGES_IMAGE_KEY = "image_key";
constants.VENDOR_IMAGES_BASE_URL = "base_url";
constants.VENDOR_IMAGE_POSITION = "position";
constants.IMAGE_TYPE_DP = "DP";
constants.IMAGE_TYPE_DOCUMENT = "DOCUMENT";
constants.IMAGE_TYPE_PET = "PET";
constants.FILE_EXTENSION = "file_extension";

constants.DOCUMENT_HOLDER_ID = "document_holder_id";
constants.DOCUMENT_HOLDER_TYPE = "document_holder_type";
constants.DOCUMENT_TYPE = "document_type";
constants.DOCUMENT_ID_NUMBER = "document_id_number";

constants.API_PATH = "path";
constants.API_LOGGER_TOKEN = "api_token";
constants.API_LOGGER_RESPONSE_CODE = "response_code";
constants.API_LOGGER_STATUS = "api_status";

constants.AUTH_EMAIL = "email_id";
constants.AUTH_PASSWORD = "password";
constants.AUTH_PHONE = "phone_number";
constants.AUTH_OTP = "otp";
constants.AUTH_ROLE = "role";

constants.CITY_NAME = "city_name";
constants.CITY_ID = "city_id";
constants.CITY_STATE_ID = "state_id";
/**
 * exporting the constants.
 */
module.exports = constants;
