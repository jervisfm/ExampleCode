var mongo = require('mongodb');
var server = new mongo.Server("localhost", 27017);
var client = new mongo.Db('quotes', server);
var params = {id: process.argv[2]};
client.open(function (err, client) {
  if (err) { throw err; }

  var collection = new mongo.Collection(client, 'quotes');

  if (params.id) {
    collection.update({_id : new mongo.ObjectID(params.id)},
      {$inc: {votes: 1}}, {safe: true},
      function (err) {
        if (err) { throw err; }
        console.log('1 vote added to %s by %s', params.id);
        collection.find().sort({votes: -1}).limit(10).each(function (err, doc) {
          if (err) { throw err; }
          if (doc) {
            var votes = (doc.votes) || 0;
            console.log(doc.author, doc.quote, votes);
            return;
          }
          client.close();
        });
      });

    return;
  }

  collection.find().each(function (err, doc) {
    if (err) { throw err; }
    if (doc) { console.log(doc._id, doc.quote); return; }
    client.close();
  });


});
