
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')

var app = module.exports = express.createServer();

// Configuration

app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');

//  app.use(express.logger());  //default output 
//  app.use(express.logger('dev'));  //developer output
 // app.use(express.logger(':date -- URL::url Referrer::referrer '));  //referral information
  
  //custom token
  express.logger.token('external-referrer',function (req, res) {
    var ref = req.header('Referrer'),
      host = req.headers.host;
      
      if (ref && ref.replace(/http[s]?:\/\//, '').split('/')[0] !== host) {
        return ref;
      } else {
        return '#local#';
      }
  });
//  app.use(express.logger(':date -- URL::url Referrer::external-referrer'));  //external sites referrers with custom token
  
  app.use(express.logger( //writing external sites to log file
    {format: ':date -- URL::url Referrer::external-referrer', 
    stream: require('fs').createWriteStream('external-referrers.log')   
  }));
  
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  

  
  app.use(require('stylus').middleware({
    src: __dirname + '/views',
    dest: __dirname + '/public'
  }));
  
  app.use(express.favicon());
  
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
  
  app.use('/admin', require('./login/app'));
  

});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true })); 
});

app.configure('production', function(){
  app.use(express.errorHandler({dumpExceptions: true})); 
});


// Routes

app.get('/:pagenum([0-9]+)?', routes.index);

app.listen(3000, function(){
  console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
});
