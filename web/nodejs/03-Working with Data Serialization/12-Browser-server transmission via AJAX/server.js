var http = require('http');
var fs = require('fs');
var path = require('path');
var profiles = require('./profiles');
var buildXml = require('./buildXml');

var index = fs.readFileSync('index.html'),
  routes,
  mimes = {xml: "application/xml", json: "application/json"};

function output(content, format, rootNode) {
  if (!format || format === 'json') {
    return JSON.stringify(content);
  }
  if (format === 'xml') {
    return buildXml(content, rootNode);
  }
}

routes = {
  'profiles': function (format) {
    return output(Object.keys(profiles), format);
  },
  '/profile': function (format, basename) {
    return output(profiles[basename], format, basename);
  }
};

http.createServer(function (request, response) {
  var reqUrl = decodeURI(request.url),
    dirname = path.dirname(reqUrl),
    extname = path.extname(reqUrl),
    basename = path.basename(reqUrl, extname);
  extname = extname.replace('.', '');

  response.setHeader("Content-Type", mimes[extname] || 'text/html');
  if (routes.hasOwnProperty(dirname)) {
    response.end(routes[dirname](extname, basename));
    return;
  }

  if (routes.hasOwnProperty(basename)) {
    response.end(routes[basename](extname));
    return;
  }

  response.end(index);

}).listen(8080);
