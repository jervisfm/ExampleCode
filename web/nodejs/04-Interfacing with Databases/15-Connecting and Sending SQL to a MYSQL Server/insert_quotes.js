var mysql = require('mysql');
var client = mysql.createClient({
  user: 'root',
  password: 'psalm33',
  //debug: true  
});

var ignore = [mysql.ERROR_DB_CREATE_EXISTS,
              mysql.ERROR_TABLE_EXISTS_ERROR];

var params = {author: process.argv[2] , quote: process.argv[3]};              

client.on('error', function (err) {
  if (ignore.indexOf(err.number) + 1) { return; }
  throw err;
});

client.query('CREATE DATABASE quotes');
client.useDatabase('quotes');
client.query('CREATE TABLE quotes.quotes (' +
             'id INT NOT NULL AUTO_INCREMENT, ' +
             'author VARCHAR( 128 ) NOT NULL, ' +
             'quote TEXT NOT NULL, PRIMARY KEY (  id )' +
             ')');
             
if (params.author && params.quote) {
  client.query('INSERT INTO  quotes.quotes (' +
                'author, quote) ' +
                'VALUES (?, ?);', [ params.author, params.quote ]);
}

client.end();
