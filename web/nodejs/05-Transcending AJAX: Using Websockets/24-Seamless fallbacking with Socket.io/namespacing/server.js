var http = require('http');
var fs = require('fs');
var profiles = require('./profiles');
var buildXml = require('./buildXml');
var index = fs.readFileSync('index.html');
var io = require('socket.io').listen(
    http.createServer(function (request, response) {
      response.end(index);
    }).listen(8080)
  );

io.of('/json').on('connection', function (socket) {
  socket.emit('profiles', Object.keys(profiles));

  socket.on('profile', function (profile) {
    socket.emit('profile', profiles[profile]);
  });
});

io.of('/xml').on('connection', function (socket) {

  socket.on('profile', function (profile) {
    socket.emit('profile', buildXml(profiles[profile]));
  });

});
