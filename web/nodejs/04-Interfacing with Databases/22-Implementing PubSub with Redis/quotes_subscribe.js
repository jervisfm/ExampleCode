var redis = require('redis');
var client = redis.createClient();

process.argv.forEach(function (authorChannel, i) {
  if (i > 1) {
    client.subscribe(authorChannel, function () {
      console.log('Subscribing to ' + authorChannel + ' channel');
    });
  }
});

client.on('message', function (channel, msg) {

  console.log("\n%s: %s", channel, msg);

});
