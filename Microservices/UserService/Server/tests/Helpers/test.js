const Cache = require('./../../src/Helpers/cacheHelper');
const constants = require('./../../src/Helpers/constants');
require('dotenv').config();
const cache = new Cache();

cache.storeData("testKey", "testValue1").then(isStored => {
   console.log(isStored);
}).catch(err => {
   console.error(err);
});
cache.getData("testKey").then(data => {
   console.log("DATA RECEIVED: " + data);
}).catch(err => {
   console.error(err);
});
