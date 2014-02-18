
/*
 * GET home page.
 */

var profiles = require('../profiles');

exports.index = function (req, res) {

  profiles.pull(req.params.pagenum, function (err, profiles) {
    if (err) { console.log(err); }

    res.render('index', { title: 'Profiler', profiles: profiles,
      page: req.params.pagenum });
  });

};
