//Don't forget, we need mongoDB for this to work!
//sudo mongod --dbpath [a folder for the database]
var mongo = require('mongoskin'),
  db = mongo.db('localhost:27017/profiler'),
  profiles = require('./profiles'),
  users = [{name : 'dave', pwd: 'expressrocks'},
           {name : 'MrPage', pwd: 'hellomynamesmrpage'}
          ];


db.collection('users').remove({});
db.collection('profiles').remove({});
db.collection('users').insert(users);


Object.keys(profiles).forEach(function (key) {
  db.collection('profiles').insert(profiles[key]);
});

db.close();
