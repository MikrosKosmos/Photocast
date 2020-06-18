const constants = require('./constants');
const printer = require('./printer');
const generator = require('./../Services/generator');
const validators = require('./validators');
const http = require('http');
const StringDecoder = require('string_decoder').StringDecoder;

class NetworkHelper {
   /**
    * _hostName
    * _path
    * _method
    * _queryParams
    * _body
    * _headers
    * @param hostName
    * @param path
    * @param method
    * @param queryParams
    * @param body
    * @param headers
    */
   constructor(hostName, path, method, queryParams, body, headers) {
      this._hostName = validators.validateString(hostName) ? hostName : false;
      this._path = validators.validateString(path) ? path : false;
      this._method = validators.validateString(method) ? method : false;
      this._queryParams = validators.validateArray(queryParams) ? queryParams : false;
      this._body = validators.validateJSON(body) ? body : false;
      this._headers = validators.validateUndefined(headers) ? headers : false;
   }

   /**
    * Method to make a HTTP request.
    * @returns {Promise<JSON>}: The response or the error.
    */
   request() {
      const decoder = new StringDecoder('utf-8');
      return new Promise((resolve, reject) => {
         this._path = generator.generateQueryURL(this._path, this._queryParams);
         const requestOptions = {
            protocol: "http:",
            hostname: this._hostName,
            method: this._method,
            path: this._path,
            headers: this._headers
         };
         let responseBody = "";
         const request = http.request(requestOptions, res => {
            res.on('data', (data) => {
               responseBody += decoder.write(data);
            }).on('end', () => {
               responseBody += decoder.end();
               responseBody = generator.generateParsedJSON(responseBody);
               resolve(responseBody);
            });
         }).on("error", (err) => {
            printer.printError(err);
            reject(err);
         });
         if (this._method === constants.HTTP_POST || this._method === constants.HTTP_PUT) {
            request.write(generator.generateSerializedJSON(this._body));
         }
         request.end();
      });
   }
}

/**
 * Exporting the class.
 */
module.exports = NetworkHelper;
