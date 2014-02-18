
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
  var subpage = req.params[0],
    parentPage = subpage ? ' of the ' + req.params.page + ' page' : '';
    
  res.send('Welcome to the ' + (subpage || req.params.page) + ' page' + parentPage);
};
