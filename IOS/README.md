# IOS SDK for Edgestream

![Current State: Preview Release](https://img.shields.io/badge/Current_State-Preview_Release-brightgreen.svg) 

# EdgestreamSDK-Release

This is the binary release of the Edgestream SDK library as a framework package that can be added to your own application. refer to the release notes in the [EdgestreamSDK-Release](EdgestreamSDK-Release/) folder.

# MinClient

The MinClient directory contains a minimal IOS client application to test the SDK and provide an example of how to use the SDK. The Application can be opened from XCode.  Tested with Android XCode 10.1.

# Configure SDK

Before making calls to SendData, you'll need to configure the IOS client with the Edgestream platform by registering your device and providing the returned token to the SDK where it is stored for future use

# Usage

| Resource                                              | Action                                                |
| ----------------------------------------------------- | ----------------------------------------------------- |
| **[Edgestream Client](#edgestream-client)**           | [Create](#create)                                     |
| **[Configuration](#configuration)**                   | [Token](#token)                                       |
| **[Connection](#connection)**                         | [Connect](#connect)                                   |
| **[Connection](#connection)**                         | [Disconnect](#disconnect)                             |
| **[Data](#data)**                                     | [Send](#send)                                         |



## Edgestream Client

#### Create
```java
// create new EdgestreamClient object passing in the application context as a parameter 
// this should be performed early in startup of application
// returns a valid object on success
_client = EdgestreamClient()
	
```

## Configuration

Configuration data is required only one time and will be stored by the SDK, if the device is to be re-purposed the token can be set again

#### Token
```java
// set the token via the client using the key and message received from add device 
// returns true on successful configuration
_client.setToken(key: key, deviceToken: token)

```

## Connection

A connection to the platform must be established prior to sending data to the platform or receiving cloud to device messages

### Connect

Establishes the connection from the device to the Edgestream platform.

```java
// connect to platform prior to sending data returns true on successful connection
// returns true on successful connection
_client.iotConnect()

```

### Disconnect

Removes the connection from the device to the Edgestream platform.


```java
// disconnect from the platform prior to exiting your application
// returns true on successful disconnect
_client.iotDisconnect()

```

## Data

Manage data from device to the cloud and from the cloud to the device.

### Send

Send telemetry data from device to Edgestream platform.

```java
// send data to the Edgestream platform for storage and processing
// returns true on successful send command
_client.sendData(dictString: data)

```

Send telemetry data tagged with location data from the device to the Edgestream platform.

```java
// send data tagged with location data to the Edgestream platform for storage and processing
// returns true on successful send command
_client.sendData(dictString: data, location:CLLocation)

```
