var mysql = require('mysql');
var client = mysql.createClient({
  user: 'root',
  password: 'psalm33',
  //debug: true  
});

var ignore = [mysql.ERROR_DB_CREATE_EXISTS,
              mysql.ERROR_TABLE_EXISTS_ERROR];

client.on('error', function (err) {
  if (ignore.indexOf(err.number) + 1) { return; }
  throw err;
});

client.query('CREATE DATABASE quotes');
client.useDatabase('quotes');
client.query('CREATE TABLE quotes.quotes (' +
             'id INT NOT NULL AUTO_INCREMENT,' +
             'author VARCHAR( 128 ) NOT NULL,' +
             'quote TEXT NOT NULL, PRIMARY KEY (  id )' +
             ')');

client.query('INSERT INTO  quotes.quotes (' +
              'author, quote) ' +
              'VALUES ("Proof by analogy is fraud.", "Bjarne Stroustrup");');

client.end();
