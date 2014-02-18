var users = {'dave' : 'expressrocks'}; //pretend this a remote database

function validate(user, cb) {
  var valid = Object.keys(users).some(function (name) { 
    return (user.name === name && user.pwd === users[name]); 
  });

  cb((!valid && {msg: 'Login details invalid!'} ));
}


module.exports = function (req, res, next) {
  var method = req.method.toLowerCase(), //cache the method
    user = req.body.user,
    logout = (method === 'delete'),
    login = (method === 'post' && user),
    routes = req.app.routes.routes[method];
  
  if (!routes) { next(); return; }
    
  if (login || logout) {    
    routes.forEach(function (route) {
      if (!(req.url.match(route.regexp))) {
        req.method = 'GET';
      }
    });
  }


  if (logout) {
    delete req.session.user;
  }
  
  if (login) {
    validate(user, function (err) {
      if (err) { req.flash('error', err.msg); return; }
        req.session.user = {
          name: user.name,
          pwd: user.pwd
        };
    });   
  }
  
  if (!req.session.user) { req.url = '/'; }  
  
  next();
};
