
/*
 * GET home page.
 */

var profiles = require('../profiles.js');

exports.index = function(req, res){
  res.render('index', { title: 'Profiles', profiles: profiles })
};
