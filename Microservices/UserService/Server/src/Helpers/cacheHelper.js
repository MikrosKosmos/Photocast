const redis = require('redis');
const encrypterDecrypter = require('./encrypterDecrypter');
const config = require('./config');
const constants = require('./constants');
const printer = require('./printer');

class Cache {
   /**
    * _client
    */
   constructor() {
      this._client = redis.createClient(config[constants.REDIS_PORT],
         encrypterDecrypter.decrypt(process.env[constants.REDIS_HOST]));
   }
   
   /**
    * Method to store the data.
    * @param key: The key by which data will be stored.
    * @param value: The value to be stored.
    * @returns {Promise<unknown>}
    */
   storeData(key, value) {
      return new Promise((resolve, reject) => {
         this._client.set(key, value, (err, reply) => {
            if (err) {
               printer.printError(err);
               reject(err);
            } else {
               printer.printHighlightedLog(reply);
               resolve(true);
            }
         });
      });
   }
   
   /**
    * Method to get the data from the cache.
    * @param key: The key by which data was saved.
    * @returns {Promise<unknown>}
    */
   getData(key) {
      return new Promise((resolve, reject) => {
         this._client = redis.createClient(config[constants.REDIS_PORT],
            encrypterDecrypter.decrypt(process.env[constants.REDIS_HOST]), {
               no_ready_check: true
            });
         this._client.on('connect', () => {
            this._client.get(key, (err, data) => {
               if (err) {
                  printer.printError(err);
                  reject(err);
               } else {
                  resolve(data);
               }
            });
         });
         this._client.on('error', (err) => {
            printer.printError(err);
         });
      });
   }
}

/**
 * Exporting the Cache
 * @type {Cache}
 */
module.exports = Cache;