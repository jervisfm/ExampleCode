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

var smslist = [
  '+44770xxxxxx1',
  '+44770xxxxxx2',
  '+44770xxxxxx3',  
  '+44770xxxxxx4',  
  '+44770xxxxxx5'  
];

var msg = 'SMS Ahoy!';

var i = 0;
smslist.forEach(function (to) {
  phone.sendSms(to, msg, {}, function(sms) {
    sms.on('processed', function(req) {  
      i += 1;  
      console.log('Message to ' + req.To + ' processed with status: ' + req.SmsStatus);
      if (i === smslist.length) {process.exit();}

    });
  });
});

