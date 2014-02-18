
/*
 * GET home page.
 */

exports.index = function(req, res){
  res.render('index', { title: 'Express' })
};

exports.mrpage =  function (req, res) {
  res.send('Hello I am Mr Page');
};

exports.anypage = function (req, res) {
  res.send('Welcome to the ' + req.params.page + ' page');
};
