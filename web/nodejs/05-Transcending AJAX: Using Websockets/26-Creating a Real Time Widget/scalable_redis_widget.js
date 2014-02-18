var io = require('socket.io').listen(8081);
var sioclient = require('socket.io/node_modules/socket.io-client');
var widgetScript = require('fs').readFileSync('widget_client.js');
var url = require('url');
var totals = require('redis').createClient();

io.configure(function () {
  io.set('resource', '/loc');
  io.enable('browser client gzip');
});


sioclient.builder(io.transports(), function (err, siojs) {
  if (!err) {
    io.static.add('/widget.js', function (path, callback) {
      callback(null, new Buffer(siojs + widgetScript));
    });
  }
});

io.sockets.on('connection', function (socket) {

  var origin = (socket.handshake.xdomain)
    ? url.parse(socket.handshake.headers.origin).hostname
    : 'local';
    
  socket.join(origin);
  
  totals.incr(origin, function (err, total) {
    io.sockets.to(origin).emit('total', total);  
  });
  

  socket.on('disconnect', function () {
    totals.decr(origin, function (err, total) {
      io.sockets.to(origin).emit('total', total);  
    });
  });
});

