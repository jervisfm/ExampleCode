var express = require('express');
var crypto = require('crypto');
var fs = require('fs'); //added fs module

var userStore = {},
  app = express.createServer().listen(8080);


app.use(express.bodyParser());

app.get('/', function (req, res) {
  res.sendfile('regform.html');
});

app.post('/', function (req, res) {
  if (req.body && req.body.user && req.body.pass) {  
    crypto.randomBytes(128, function (err, salt) {
      if (err) { throw err; }
      salt = new Buffer(salt).toString('hex');
      crypto.pbkdf2(req.body.pass, salt, 7000, 256, function (err, hash) {
        if (err) { throw err; }
        userStore[req.body.user] = {salt : salt, 
          hash : (new Buffer(hash).toString('hex')) };
        res.send('Thanks for registering ' + req.body.user);
        console.log(userStore);
      });
    });
  }
});

