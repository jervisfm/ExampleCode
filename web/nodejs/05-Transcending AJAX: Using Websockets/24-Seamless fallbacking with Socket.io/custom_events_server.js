var http = require('http');
var clientHtml = require('fs').readFileSync('client.html');
var plainHttpServer = http.createServer(function (request, response) {
    response.writeHead(200, {'Content-type' : 'text/html'});
    response.end(clientHtml);
  }).listen(8080);


var io = require('socket.io').listen(plainHttpServer);

io.set('origins', ['localhost:8080', '127.0.0.1:8080']);

io.sockets.on('connection', function (socket) {
  socket.emit('hello', 'Socket.io!');
  socket.on('hollaback', function (from) {
    console.log('Received a hollaback ' + from);
  });
});

