var profiles = require('./profiles');
var xml2js = new (require('xml2js')).Parser();

function buildXml(rootObj, rootName) {
  var xml = "<?xml version='1.0' encoding='UTF-8'?>\n";
  rootName = rootName || 'xml';
  xml += "<" + rootName + ">\n";
  (function traverse(obj) {
    Object.keys(obj).forEach(function (key) {

      var open = "<" + key + ">",
        close = "</" + key + ">\n",
        isTxt = (obj[key] 
          && {}.toString.call(obj[key]) !== "[object Object]");

      xml += open;

      if (isTxt) {
        xml += obj[key];
        xml += close;
        return;
      }

      xml += "\n";
      traverse(obj[key]);
      xml += close;
    });
  }(rootObj));

  xml += "</" + rootName + ">";
  return xml;
}

profiles = buildXml(profiles, 'profiles').replace(/name/g, 'fullname');

//console.log(profiles); // <-- show me the XML!

xml2js.parseString(profiles, function (err, obj) {
  profiles = obj;
  profiles.felix.fullname = "Felix Geisendörfer";
  console.log(profiles.felix);
});





