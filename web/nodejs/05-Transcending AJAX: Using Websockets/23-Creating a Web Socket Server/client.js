new (require('websocket').client)()
  .on('connect', function (connection) {
    var msg = 'Hello';

    connection.send(msg);
    console.log('Sent: ' + msg);

    connection.on('message', function (msg) {
      console.log("Received: " + msg.utf8Data);
    }).on('close', function (code, desc) {
      console.log('Disconnected: ' + code + ' - ' + desc);
    }).on('error', function (error) {
      console.log("Error: " + error.toString());
    });


  })
  .on('connectFailed', function (error) {
    console.log('Connect Error: ' + error.toString());
  })
  .connect('ws://localhost:8080/', null, 'http://localhost:8080');
