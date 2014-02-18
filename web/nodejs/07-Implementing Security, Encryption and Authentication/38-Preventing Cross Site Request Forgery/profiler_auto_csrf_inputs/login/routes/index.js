
/*
 * GET home page.
 */
 
var profiles = require('../../profiles'); 
 
exports.index = function (req, res) {
  profiles.pull(req.params.pagenum, function (err, profiles) {
    if (err) {  console.log(err); }
    res.render('index', { title: 'Profiler Admin', profiles: profiles, page: req.params.pagenum });
  });
};


exports.delprof = function (req, res) {
  if (req.query._csrf !== req.session._csrf) {
    res.send(403);
    return;
  };
    profiles.del(req.query.id, function (err) {
      if (err) { console.log(err); }
        profiles.pull(req.query.p, function (err, profiles) {
          req.app.helpers({profiles: profiles});
          res.redirect(req.header('Referrer') || '/');
        });
    });

}

exports.addprof = function (req, res) {
  profiles.add(req.body, function (err) {
    if (err) { console.log(err); }
      res.redirect(req.header('Referrer') || '/');
  });


}
