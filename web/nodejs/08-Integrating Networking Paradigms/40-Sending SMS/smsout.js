var twilio = require('twilio');

var settings =  {
  sid : 'Ad054bz5be4se5dd211295c38446da2ffd', 
  token: '3e0345293rhebt45r6erta89xc89v103',
  hostname : 'dummyhost',
  phonenumber: '+14155992671' //sandbox number
};

var restClient = new (twilio.RestClient)(settings.sid,settings.token);

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


var msg = 'SMS Ahoy!', i = 0;
smslist.forEach(function (to) {
  phone.sendSms(to, msg, {}, function (sms) {
    function checkStatus(smsInstance) {  
      restClient.getSmsInstance(smsInstance.sid, function (presentSms) {


        if (presentSms.status === 'sent') {  
          console.log('Sent to ' + presentSms.to);
        } else {
        //if it's not a number (like 404, 401), it's not an error
        //so we wait one second and retry
          if (isNaN(presentSms.status)) { 
            setTimeout(checkStatus, 1000, presentSms);
            return;
          }
        //it seems to be a number, let's notify, 
        //but carry on with other numbers
          console.log('API error: ', presentSms.message);
        }
        i += 1;
        if (i === smslist.length) { process.exit(); }
         
      });
    };
    
    checkStatus(sms.smsDetails);
    
  });
});
