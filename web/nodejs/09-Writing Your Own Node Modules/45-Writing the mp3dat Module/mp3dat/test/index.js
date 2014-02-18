var should = require('should');

var mp3dat = require('../index.js');

var testFile = 'test/test.mp3';

should.exist(mp3dat);
mp3dat.should.have.property('stat');
mp3dat.stat.should.be.an.instanceof(Function);

mp3dat.stat(testFile, function (err, stats) {
  should.ifError(err);
  
  //expected properties
  stats.should.have.property('duration');
  stats.should.have.property('bitrate');
  stats.should.have.property('filesize');    
  stats.should.have.property('timestamp');
  stats.should.have.property('timesig');  
  
  
  //expected types
  stats.duration.should.be.an.instanceof(Object);
  stats.bitrate.should.be.a('number');
  stats.filesize.should.be.a('number');
  stats.timestamp.should.be.a('number');  
  
  stats.timesig.should.match(/^\d+:\d+:\d+$/);
    
    
  //expected duration properties
  stats.duration.should.have.keys('hours', 'minutes', 'seconds', 'milliseconds');
  
  //expected duration types and constraints
  stats.duration.hours.should.be.a('number');
  stats.duration.minutes.should.be.a('number').and.be.below(60);
  stats.duration.seconds.should.be.a('number').and.be.below(60);
  stats.duration.milliseconds.should.be.a('number').and.be.below(1000);  
  
  console.log('All tests passed');
    
});




