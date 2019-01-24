# Android SDK for Edgestream

![Current State: Preview Release](https://img.shields.io/badge/Release-v1-green.svg) 

# EdgestreamSDK-Release

This is the binary v1 Release of the Edgestream SDK library for Android, which is distributed as an aar package that can be added to your own application. Refer to the release notes in the [EdgestreamSDK-Release](EdgestreamSDK-Release/) folder.

# Samples

The Samples directory contains a minimal Android client application to test the SDK and provide an example of how to use the SDK. The Application can be opened from Android Studio.  Tested with Android Studio 3.2.1.

# Configure SDK

Before making calls to SendData, you'll need to configure the Android client with the Edgestream platform by registering your device and providing the returned token to the SDK where it is stored for future use

# Usage

| Resource                                              | Action                                                |
| ----------------------------------------------------- | ----------------------------------------------------- |
| **[Edgestream Client](#edgestream-client)**           | [Create](#create)                                     |
| **[Configuration](#configuration)**                   | [Token](#token)                                       |
| **[Connection](#connection)**                         | [Connect](#connect)                                   |
| **[Connection](#connection)**                         | [Disconnect](#disconnect)                             |
| **[Data](#data)**                                     | [Send](#send)                                         |
| **[Notifications](#notifications)**                   | [edgeEventCallback](#event)                           |
| **[Notifications](#notifications)**                   | [sendMessageReceiptConfirmations](#confirm)           |



## Edgestream Client

#### Create
```java
// create new EdgestreamClient object passing in the application context as a parameter 
// this should be performed early in startup of application
// returns a valid object on success
EdgestreamClient _client = new EdgestreamClient(this.getApplicationContext());
	
```

## Configuration

Configuration data is required only one time and will be stored by the SDK, if the device is to be re-purposed the token can be set again

#### Token
```java
// set the token via the client using the key and message received from add device 
// returns true on successful configuration
_client.setToken(key, msg);

```

## Connection

A connection to the platform must be established prior to sending data to the platform or receiving cloud to device messages

### Connect

Establishes the connection from the device to the Edgestream platform.

```java
// connect to platform prior to sending data returns true on successful connection
// returns true on successful connection
_client.iotConnect();

```

### Disconnect

Removes the connection from the device to the Edgestream platform.


```java
// disconnect from the platform prior to exiting your application
// returns true on successful disconnect
_client.iotDisconnect();

```

## Data

Manage data from device to the cloud and from the cloud to the device.

### Send

Send telemetry data from device to Edgestream platform.

```java
// send data to the Edgestream platform for storage and processing
// returns true on successful send command
_client.sendData(json);

```

Send telemetry data tagged with location data from the device to the Edgestream platform.

```java
// send data tagged with location data to the Edgestream platform for storage and processing
// returns true on successful send command
_client.sendData(json, location);

```

## Notifications

Manage notifications from the EdgestreamSDK client for Cloud to Device Messages and Receipt of Device to Cloud Messaging.
Developer must implement the EdgestreamMessageEventCallback interface to receive notifications from the Edgestream Client.

In the sample application we implement the interface on our main activity class as below

```java

public class MainActivity extends AppCompatActivity implements EdgestreamMessageEventCallback {

```

### Event

Receive Cloud to Device messages occur in the edgeEventCallback funtion of the EdgestreamMessageEventCallback interface.
Data is received as a byte array string for processing by the application and is application specific.

```java
    // Edgestream Client Callbacks
    @Override
    public void edgeEventCallback(final byte[] message) {
        // callback sends a byte array string containing the data
    }

```

### Confirmation

Send message confirmations from the EdgestreamSDK client are received in the sendMessageReceiptConfirmations call of the
EdgestreamMessageEventCallback interface.  Count is the total number of message receipts acknowledged by the platform.

```java
    // Edgestream Client Callbacks
    @Override
    public void sendMessageReceiptConfirmations(final int count) {
        // callback sends the number of confirmations
    }

```

