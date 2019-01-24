
var azure = require('azure');
var bRun = true;
var queue = 'queue-1';

var connectionString = "Endpoint=sb://edgestream.servicebus.windows.net/;SharedAccessKeyName=testlistener;SharedAccessKey=eoD33vvfkf65KyZn8D5A4LwLKoE4LiLFOh2FExN0qiE=";

var serviceBusClient = azure.createServiceBusService(connectionString);


serviceBusClient.receiveQueueMessage(queue, function (error, message1, response) {
    if (error) {
        console.log(error);        
    } else {
        // Message received
        console.log(message1.body);
    }
});
