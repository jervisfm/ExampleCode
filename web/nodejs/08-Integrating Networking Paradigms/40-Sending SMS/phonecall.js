var twilio = require('twilio');

var settings =  {
  sid : 'Ad054bz5be4se5dd211295c38446da2ffd', 
  token: '3e0345293rhebt45r6erta89xc89v103',
  hostname : 'nodecookbook.com',
  phonenumber: '+14155992671' //sandbox number
};

var client = new (twilio.Client)(settings.sid,
                                  settings.token, 
                                  settings.hostname);


var phone = client.getPhoneNumber(settings.phonenumber);

//don't forget to replace intended number with verified number
//(unless account is upgraded then it can be any number)
phone.makeCall('+44770xxxxxx1', {}, function(call) {
  call.on('answered', function (req, res) {
    console.log('answered');
    res.append(new (twilio.Twiml).Say('Meet us in the abandoned factory'));

    res.append(new (twilio.Twiml).Say('Come alone', {voice: 'woman'}));
    res.send();
  }).on('ended', function (req) {
    console.log('ended', req);
    process.exit();
  })
});
