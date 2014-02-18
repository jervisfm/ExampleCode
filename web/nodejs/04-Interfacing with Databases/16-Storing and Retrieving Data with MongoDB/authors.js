var mongo = require('mongodb');
var server = new mongo.Server("localhost", 27017);
var client = new mongo.Db('quotes', server);

client.open(function (err, client) {
  if (err) { throw err; }

  var collection = new mongo.Collection(client, 'quotes');  
  
  collection.ensureIndex('author', {safe: true}, function (err) {
    if (err) { throw err; } 
    collection.distinct('author', function (err, result) {
        if (err) { throw err; }
        console.log(result.join('\n')); 
        client.close();
      });  
    });
    
 
});
