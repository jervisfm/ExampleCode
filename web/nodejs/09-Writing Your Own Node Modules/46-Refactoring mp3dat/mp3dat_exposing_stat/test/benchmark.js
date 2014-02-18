//uses node-bench to compare benchmark tests for
//standard bufferSize with looping through data chunks
//and 2 byte buffersizes with no looping neccessary
//for tests to be successful file limits must be changed in 
//
//  /etc/security/limits.conf
//  root            hard    nofile            100000
//  root            soft    nofile            100000
var fs = require('fs'),
  bitrates = { 1 : 32000, 2 : 40000, 3 : 48000, 4 : 56000, 5 : 64000,
    6 : 80000, 7 : 96000, 8 : 112000, 9 : 128000, A : 160000, B : 192000,
    C : 224000, D : 256000, E : 320000 };

var testFile = 'test.mp3';
exports.compare = {
  "stat" : function () {
   fs.createReadStream(testFile)
    .on('data', function (data) {
      var i = 0, bitrate;
       for (i; i < data.length; i += 2) {
        if (data.readUInt16LE(i) === 64511) {
          bitrate = bitrates[data.toString('hex', i + 2, i + 3)[0]];
          break;
        };
    }
    //need to backport this into earlier example code
    if (bitrate) {  
      this.destroy();
      console.log(bitrate);
    }
  });


  },
  "smallbuffer" : function () {
   fs.createReadStream(testFile, {bufferSize:4})
    .on('data', function (data) {

        if (data.readUInt16LE(0) === 64511) {
          bitrate = bitrates[data.toString('hex', 2, 3)[0]];
          this.destroy();
          console.log(bitrate);
        };

  });

  }
};
require("bench").runMain()
