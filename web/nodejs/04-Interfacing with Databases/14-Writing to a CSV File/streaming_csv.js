var csv = require('ya-csv');
var http = require('http');

http.createServer(function (request, response) {
     response.write('[');
      csv.createCsvFileReader('big_data.csv')
      .on('data', function(data) { 
        response.write(((this.parsingStatus.rows > 0) ? ',' : '') + JSON.stringify(data));
      }).on('end', function() {
        response.end(']');
      });
}).listen(8080);
