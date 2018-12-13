
var azure = require('azure');
var bRun = true;
var queue = 'queue-b3426101f42b';

var connectionString = "Endpoint=sb://edgestream.servicebus.windows.net/;SharedAccessKeyName=listenonly;SharedAccessKey=VKw9EJK7jxOO4cOt/wJElLSifTH2Odji+h+I/rpfqlE=";
var serviceBusClient = azure.createServiceBusService(connectionString);


serviceBusClient.receiveQueueMessage(queue, function (error, message1, response) {
    if (error) {
        console.log(error);        
    } else {
        // Message received
        console.log(message1.body);
    }
});
