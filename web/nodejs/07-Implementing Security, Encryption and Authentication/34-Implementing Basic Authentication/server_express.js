var express = require('express');

var username = 'dave',
  password = 'ILikeBrie_33',
  realm = "Node Cookbook";

var app = express.createServer();

app.use(express.basicAuth(function (user, pass) {
  return username === user && password === pass;
}, realm));

app.get('/:route?', function (req, res) {
  res.end('Somebody likes soft cheese!');
});

app.listen(8080);

