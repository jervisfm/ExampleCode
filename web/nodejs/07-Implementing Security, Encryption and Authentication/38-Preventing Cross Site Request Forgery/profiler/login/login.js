var mongo = require('mongoskin'),
  db = mongo.db('localhost:27017/profiler'),
  users = db.collection('users');

function validate(user, cb) {

  users.findOne({name: user.name, pwd: user.pwd}, function (err, user) {
    if (err) { throw err; }
    if (!user) {
      cb({msg: 'Invalid login details!'});
      return;
    }

    cb();

  });

}


module.exports = function (req, res, next) {
  var method = req.method.toLowerCase(), //cache the method
    logout = (method === 'delete'),
    login = (method === 'post' && req.body.user);

  if (login && logout) {
    req.app.routes.routes[method]
      .forEach(function (route) {
        if (!(req.url.match(route.regexp))) {
          req.method = 'GET';
        }
      });
  }


  if (logout) {
    delete req.session.user;
  }


  if (login) {
    validate(req.body.user, function (err) {
      if (err) { req.flash('error', err.msg); next(); return; }
      req.session.user = {
        name: req.body.user.name,
        pwd: req.body.user.pwd
      };

      next();
    });
    return;
  }
  
  if (!req.session.user) {req.url = '/'; }  

  next();

};
