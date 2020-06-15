const constants = require('./constants');
const printer = require('./printer');
const validators = require('./validators');
const fetch = require('node-fetch');

class NetworkHelper {
   /**
    * _url
    * _method
    * _queryParams
    * _body
    * _headers
    * @param url
    * @param method
    * @param queryParams
    * @param body
    * @param headers
    */
   constructor(url, method, queryParams, body, headers) {
      this._url = validators.validateURL(url) ? url : false;
      this._method = validators.validateString(method) ? method : false;
      this._queryParams = validators.validateArray(queryParams) ? queryParams : false;
      this._body = validators.validateJSON(body) ? body : false;
      this._headers = validators.validateArray(headers) ? headers : false;
   }

   /**
    * Method to make a HTTP request.
    * @returns {Promise<JSON>}: The response or the error.
    */
   request() {
      return new Promise((resolve, reject) => {
         this._url = generator.generateQueryURL(this._url, this._queryParams);
         fetch(this._url, {
            method: this._method,
            headers: this._headers,
            body: JSON.stringify(this._body)
         }).then(response => {
            resolve(response.json());
         }).catch(err => {
            printer.printError(err);
            reject(err);
         });
      });
   }
}

/**
 * Exporting the class.
 * @type {NetworkHelper}
 */
module.exports = NetworkHelper;