var http = require('http');
var fs = require('fs');
var path = require('path');
var xml2js = new (require('xml2js')).Parser();
var profiles = require('./profiles');
var buildXml = require('./buildXml');

var index = fs.readFileSync('./add_profile_index.html');
var buildXmljs = buildXml.toString();
var routes, 
  mimes = {
   js: "application/javascript",
   json: "application/json",
   xml: "application/xml"
  };

routes = {
  'profiles': function (format) {
    return output(Object.keys(profiles), format);
  },
  '/profile': function (format, basename) {
    return output(profiles[basename], format, basename);
  },
  'buildXml' : function(ext) {
    if (ext === 'js') { return buildXmljs; }
  }
};

function output(content, format, rootNode) {
  if (!format || format === 'json') {
    return JSON.stringify(content);
  }
  if (format === 'xml') {
    return buildXml(content, rootNode);
  }
}

function addProfile(request,cb) {
  var newProf, profileName, pD = ''; //post data
  request
    .on('data', function (chunk) { pD += chunk; })
    .on('end',function() {
      var contentType = request.headers['content-type'];
      if (contentType === 'application/json') {
        newProf = JSON.parse(pD);
      }
      
      if (contentType === 'application/xml') {
        xml2js.parseString(pD, function(err,obj) {
          newProf = obj;  
        });
      }
      profileName = newProf.profileName;
      profiles[profileName] = newProf;    
      delete profiles[profileName].profileName;

      cb(output(profiles[profileName], 
        contentType.replace('application/', ''), profileName));
});
}





http.createServer(function (request, response) {
  var dirname = path.dirname(request.url),
    extname = path.extname(request.url),
    basename = path.basename(request.url, extname);
    extname = extname.replace('.',''); //remove period
  if (request.method === 'POST') {
    addProfile(request, function(output) {
      response.end(output);
    });
    return;
  }
  
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
