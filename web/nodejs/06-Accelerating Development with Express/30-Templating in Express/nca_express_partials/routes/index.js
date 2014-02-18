
/*
 * GET home page.
 */

var profiles = require('../profiles.js');

exports.index = function(req, res){
  if (req.xhr) { res.partial('row', {collection: profiles.ryan}); }
  res.render('index', { title: 'Profiles', profiles: profiles});
};
