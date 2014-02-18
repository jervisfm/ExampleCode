var mongo = require('mongoskin');
var client = mongo.db('localhost:27017/quotes');
var collection = client.collection('quotes');
var params = {author: process.argv[2], quote: process.argv[3]};
if (params.author && params.quote) {
  collection.insert({author: params.author, quote: params.quote});
}

if (params.author) {
  collection.findEach({author: params.author}, function (err, doc) {
    if (err) { throw err; }
    if (doc) { console.log('%s: %s \n', doc.author, doc.quote); return; }
    client.close();
  });
  return;
}

client.close();
