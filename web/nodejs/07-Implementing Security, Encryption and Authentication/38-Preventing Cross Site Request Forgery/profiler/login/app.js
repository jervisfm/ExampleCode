
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
  
  app.use(express.cookieParser());
  app.use(express.session({secret: 'kooBkooCedoN'}));
  
  app.use(express.csrf());

  
  app.use(function (req) {
    console.log(req.session._csrf);
    req.next()
  });
  
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


app.dynamicHelpers({
  user: function (req, res) {
    return req.session.user;
  },
  flash: function (req, res) {
    return req.flash();
  },
  _csrf: function (req) {
    return req.session._csrf;
  }
});


// Routes
app.get('/:pagenum([0-9]+)?', routes.index);
app.post('/:pagenum([0-9]+)?', routes.index);
app.del('/:pagenum([0-9]+)?', routes.index);


app.get('/del', routes.delprof);
app.post('/add', routes.addprof, routes.index);



if (!module.parent) {
  app.listen(3000);
  console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
}

