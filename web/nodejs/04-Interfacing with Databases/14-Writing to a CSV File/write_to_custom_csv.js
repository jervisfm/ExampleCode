var csv = require('ya-csv');
var writer = csv.createCsvFileWriter('custom_data.csv', {
    'separator': '~',
    'quote': '|',
    'escape': '|'
});

var arr = [['a','b','c','d','e|','f','g'], ['h','i','j','k','l','m','n']];

arr.forEach(function(rec) {
  writer.writeRecord(rec);
});






