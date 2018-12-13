# Data Listener
Listens to a queue where the device data is pushed to.  This is a 1st come 1st serve queue so multiple clients running simulataneously, will ensure that any single client will not receive all messages pointed to the queue. It is meant to be used as a verification tool that the data is getting from the EdgeClient to the Edgestream Platform.


## Install NodeJS.

https://nodejs.org/en/

## Get latest source Code
```
git clone https://github.com/edgespace-io/Edgestream.git
```

## Install Dependencies
npm install

## Run Listener
node index.js