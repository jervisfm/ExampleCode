var csv = require('ya-csv');
var writer = csv.createCsvFileWriter('data.csv');

var arr = [['a','b','c','d','e','f','g'], ['h','i','j','k','l','m','n']];

arr.forEach(function(rec) {
  writer.writeRecord(rec);
});






