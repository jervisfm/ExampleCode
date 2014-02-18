var express = require('express');
var crypto = require('crypto');
var util = require('util');
var userStore = {},
  app = express.createServer().listen(8080);

app.use(express.bodyParser());

app.get('/', function (req, res) {
  res.sendfile('regform.html');
});

app.post('/', function (req, res) {
  if (req.body && req.body.user && req.body.pass) {  
    var hash = crypto
        .createHmac("md5",'SuperSecretKey')
        .update(req.body.pass)
        .digest('hex');


    userStore[req.body.user] = hash;
    res.send('Thanks for registering ' + req.body.user);
    console.log(userStore);
  }
});
