var csv = require('ya-csv');

var reader = csv.createCsvFileReader('data.csv');

//reading custom CSV elements:
/* var reader = csv.createCsvFileReader('custom_data.csv', {
    'separator': '~',
    'quote': '|',
    'escape': '|'
}); */

var arr = [];

reader.on('data', function(data) {
  arr.push(data);
}).on('end', function() {
  console.log(arr);
});




