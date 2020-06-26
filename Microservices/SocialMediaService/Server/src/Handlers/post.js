const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const postHandler = {};
const Post = require('./../Entity/post');
/**
 * Method to handle the requests for the post.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}: The response object and the response code.
 */
postHandler.post = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_POST) {
         const token = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         const postDesc = validator.validateString(dataObject.postData[constants.POST_POST_DESCRIPTION]) ?
            dataObject.postData[constants.POST_POST_DESCRIPTION] : false;
         const imageData = validator.validateUndefined(dataObject.postData[constants.POST_IMAGE_DATA]) ?
            dataObject.postData[constants.POST_IMAGE_DATA] : false;
         const fileExtension = validator.validateString(dataObject.postData[constants.FILE_EXTENSION]) ?
            dataObject.postData[constants.FILE_EXTENSION] : false;
         if (token && postDesc && imageData && fileExtension) {
            const post = new Post(false, false, false, false, token);
            post.createPost(postDesc, imageData, fileExtension).then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else {
         reject(responseGenerator.generateErrorResponse(constants.INVALID_METHOD_MESSAGE, constants.ERROR_LEVEL_1));
      }
   });
};

/**
 * Exporting the post handler.
 */
module.exports = postHandler;