var mongo = require('mongoskin'),
  client = mongo.db('localhost:27017/profiler'),
  profiles = require('./profiles'),
  users = [{name : 'dave', pwd: 'expressrocks'},
           {name : 'MrPage', pwd: 'hellomynamesmrpage'}
          ];

client.collection('users').insert(users);

Object.keys(profiles).forEach(function (key) {
  client.collection('profiles').insert(profiles[key]);
});

client.close();
