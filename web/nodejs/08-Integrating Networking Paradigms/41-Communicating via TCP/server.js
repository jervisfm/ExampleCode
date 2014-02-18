var net = require('net'),
  stats = new (require('events').EventEmitter),
  filter = 'User-Agent';

var fauxHttp = net.createServer(function(socket) {
  socket.write('Hello, this is TCP\n');
  socket.end();    
  
  socket.on('data', function (data) {
    console.log(data.toString());
    stats.emit('stats', data.toString());
  });
   
}).listen(8080);


var monitorInterface = net.createServer(function(socket) {
  
  stats.on('stats', function (stats) {
    var header = stats.match(filter + ':') || stats.match('');
    header = header.input.substr(header.index).split('\r\n')[0];
    socket.write(header);
  });


  socket.write('Specify a filter [e.g. User-Agent]');
  socket.on('data', function(data) {
    filter = data.toString().replace('\n','');
    
    socket.write('Attempting to filter by: ' + filter);
  });

}).listen(8081);
