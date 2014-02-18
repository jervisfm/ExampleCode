
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
/* 
// Express 3
  , flash = require('connect-flash') 
*/

var app = module.exports = express.createServer();

// Configuration
app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  
  app.use(express.cookieParser());
  app.use(express.session({secret: 'kooBkooCedoN'}));
  /* 
  // Express 3
  app.use(express.cookieParser('kooBkooCedoN'));
  app.use(express.session());
  app.use(flash());
  */
  
  app.use(require('stylus').middleware({
    src: __dirname + '/views',
    dest: __dirname + '/public'
  }));  
  
  app.use(require('./login'));
  
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true })); 
});

app.configure('production', function(){
  app.use(express.errorHandler()); 
});

app.mounted(function (parent) {
  this.helpers({masterviews: parent._locals.settings.views + '/'});
});

// Express 2
app.dynamicHelpers({
  user: function (req, res) {
    return req.session.user;
  },
  flash: function(req, res) {
    return req.flash();
  }
});

/* 
// Express 3
app.locals.use( function (req, res) {
  res.locals.user = req.session.user;
  res.locals.flash = req.flash();
});
*/

// Routes
app.get('/:pagenum([0-9]+)?', routes.index);
app.post('/:pagenum([0-9]+)?', routes.index);
app.del('/:pagenum([0-9]+)?', routes.index);


app.get('/del', routes.delprof);
app.post('/add', routes.addprof, routes.index);




if (!module.parent) {
  app.listen(3000);
  console.log("Express server listening on port %d in %s mode",
    app.address().port, app.settings.env);
}
