
/*
 * GET home page.
 */

var profiles = require('../models/profiles');


function patchMixins(req, mixins) {
  if (!req.session || !req.session.user) {
    var noop = function(){},
      dummies = {};
    mixins.forEach(function (mixin) {
      dummies[mixin + '_mixin'] = noop;
    });  
    req.app.helpers(dummies);
  }
}

exports.index = function (req, res) {
  
  profiles.pull(req.params.pagenum, function (err, profiles) {
    if (err) { console.log(err); }
    //output no-ops to avoid choking on non-existant mixins when not logged in
    patchMixins(req, ['add','del','adminScript']);
    
    res.render('index', { title: 'Profiler', profiles: profiles,
      page: req.params.pagenum });
  });

};
