
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
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  
  // Express 2
  app.use(express.cookieParser());
  app.use(express.session({secret: 'kooBkooCedoN'}));
  /* 
  // Express 3
  app.use(express.cookieParser('kooBkooCedoN'));
  app.use(express.session());
  */
  
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true })); 
});

app.configure('production', function(){
  app.use(express.errorHandler({dumpExceptions: true})); 
});

// Routes
app.get('/',  routes.index);
app.post('/', routes.login, routes.index);
app.del('/', routes.logout, routes.index);



app.listen(3000, function(){
  console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
});  
