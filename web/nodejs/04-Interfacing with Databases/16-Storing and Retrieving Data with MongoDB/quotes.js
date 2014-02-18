var mongo = require('mongodb');
var server = new mongo.Server("localhost", 27017);
var client = new mongo.Db('quotes', server);
var params = {author: process.argv[2], quote: process.argv[3]};
client.open(function (err, client) {
  if (err) { throw err; }

  var collection = new mongo.Collection(client, 'quotes');

  if (params.author && params.quote) {
    collection.insert({author: params.author, quote: params.quote});
  }

  if (params.author) {
    collection.find({author: params.author}).each(function (err, doc) {
      if (err) { throw err; }
      if (doc) { console.log('%s: %s \n', doc.author, doc.quote); return; }
      client.close();
    });
    return;
  }

  client.close();
});
