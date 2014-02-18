var express = require('express'),
  mappings = require('./mappings'),
  app = express.createServer();
  
Object.keys(mappings.sites).forEach(function (domain) {
  app.use(express.vhost(domain, mappings.sites[domain]));
});

Object.keys(mappings.aliases).forEach(function (domain) {
  app.use(express.vhost(domain, mappings.sites[mappings.aliases[domain]]));
});

app.listen(8080);



