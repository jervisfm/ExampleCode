var https = require('https');
var fs = require('fs');

var port = 8080, //change to 443 for straight HTTPS port, 
                 //must be root to run on privaledged ports, 
                //use with caution.
  mappings = require('./mappings');

var server = https.createServer({}, function (req, res) {
  var domain = req.headers.host.replace(new RegExp(':' + port + '$'), ''),
    site = mappings.sites[domain] || mappings.sites[mappings.aliases[domain]];

  if (site) { site.content.serve(req, res); return; }
  
  res.writeHead(404);
  res.end('Not Found\n');

}).listen(port);


Object.keys(mappings.sites).forEach(function (hostname) {
  server.addContext(hostname, {key: mappings.sites[hostname].key, 
    cert: mappings.sites[hostname].cert});    
});
