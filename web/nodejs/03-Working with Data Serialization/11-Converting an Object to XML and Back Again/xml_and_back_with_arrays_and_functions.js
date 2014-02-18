var profiles = require('./profiles_with_arrays_and_functions');
var xml2js = new (require('xml2js')).Parser();

function buildXml(rootObj, rootName) {
  var xml = "<?xml version='1.0' encoding='UTF-8'?>\n";
  rootName = rootName || 'xml';
  xml += "<" + rootName + ">\n";
  (function traverse(obj) {
    Object.keys(obj).forEach(function (key) {

      var open = "<" + key + ">",
        close = "</" + key + ">\n",
        nonObj = (obj[key] 
          && {}.toString.call(obj[key]) !== "[object Object]"),
        isArray = Array.isArray(obj[key]),
        isFunc = (typeof obj[key] === "function");


      if (isArray) {
          var childNode = {};
        obj[key].forEach(function (xmlNode) {
          childNode[key] = xmlNode;
          traverse(childNode);
        });
        return;
      }

      xml += open;
      
      if (nonObj) {
        xml += (isFunc) ? obj[key]() : obj[key];
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

profiles = buildXml(profiles, 'profiles');

//console.log(profiles); // <-- show me the XML!

xml2js.parseString(profiles, function (err, obj) {
  profiles = obj;
  console.log(profiles.ryan);
  console.log(profiles.bert);
});





