const NetworkHelper = require('./../../src/Helpers/networkHelper');
const constants = require('./../../src/Helpers/constants');
test("Make a successful API call", () => {
   const networkHelper = new NetworkHelper("www.petcoset.com", "/api/v1/city",
      constants.HTTP_GET, [{state_id: 29}], null, {"key": "5e31eb7b48506f1"});
   networkHelper.request().then(response => {
      console.log(response);
   }).catch(err => {
      console.error(err);
   });
});