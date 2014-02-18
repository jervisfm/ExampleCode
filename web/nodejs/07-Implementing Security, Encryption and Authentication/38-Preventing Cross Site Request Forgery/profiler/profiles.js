var mongo = require('mongoskin'),
    db = mongo.db('localhost:27017/profiler'),
    profs = db.collection('profiles');
    

exports.pull = function pull(page, cb) {
  var p = {},
    //rowsPer = 10, //realisted rowsPer
    rowsPer = 2, //debugging rowsPer
    skip;
        
  page = page || 1;
  skip = (page - 1) * rowsPer;
  
  profs.findEach({}, {limit: rowsPer, skip: skip}, function (err, doc) {
  
    if (err) { 
      console.log(err);
      cb(err); 
      return; 
    }
    
    if (doc) {
      p[doc._id] = doc;
      delete p[doc._id]._id;
      return;
    }
    
    cb(null, p);
  });
}


exports.del = function del(id, cb) {
  profs.removeById(id, cb);
}

exports.add = function del(profile, cb) {
  profs.insert(profile.profile, cb);
}
