var http = require('http');
var xml2js = new (require('xml2js')).Parser();
var colors = require('colors'); //for prettifying the console output
var trendingTopics = require('./twitter_trends')


var hotTopics = Object.create(trendingTopics, {trends: {value: {urlOpts: {
    host: 'www.google.com',
    path: '/trends/hottrends/atom/hourly',
    headers: {'User-Agent': 'Node Cookbook: Twitter Trends'}
  }
    }}});

hotTopics.xmlHandler = function (response, cb) {
  var hotTopicsfeed = '';
  response.on('data', function (chunk) {
    hotTopicsfeed += chunk;
  }).on('end', function () {
    xml2js.parseString(hotTopicsfeed, function (err, obj) {
      if (err) { throw (err.message); }
      xml2js.parseString(obj.entry.content['#'],
        function (err, obj) {
          if (err) { throw (err.message); }
          cb(encodeURIComponent(obj.li[0].span.a['#']));
        });
    });
  });
};

function makeCall(urlOpts, handler, cb) {
  http.get(urlOpts, function (response) { //make a call to the twitter api  
    handler(response, cb);
  }).on('error', function (e) {
    console.log("Connection Error: " + e.message);
  });
}

makeCall(hotTopics.trends.urlOpts, hotTopics.xmlHandler, function (query) {
  hotTopics.tweetPath(query);
  makeCall(hotTopics.tweets.urlOpts, hotTopics.jsonHandler, function (tweetsObj) {
    tweetsObj.results.forEach(function (tweet) {
      console.log("\n" + tweet.from_user.yellow.bold + ': ' + tweet.text);
    });
  });
});

