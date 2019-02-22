# IOS SDK for Edgestream

![Current State: Preview Release](https://img.shields.io/badge/Release-v1-green.svg) 

# EdgestreamSDK-Release

This is the binary release of the Edgestream SDK library as a framework package that can be added to your own application. refer to the release notes in the [EdgestreamSDK-Release](EdgestreamSDK-Release/) folder. Version 1 Release brings notifications from the EdgestremSDK client.  Currently supported notifications are Cloud to Device Messages.  Implementation of Cloud to Device Messaging has been included in the Sample Device.

# Sample-Device

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
| **[Receive Data](#receive)**                          | [Receive](#receivedata)                               |
| **[Notifications](#Notifications)**                   | [AppConfiguration](#setup)                            |            



## Edgestream Client

#### Create
```swift
// create new EdgestreamClient object passing in the application context as a parameter 
// this should be performed early in startup of application
// returns a valid object on success
_client = EdgestreamClient()
	
```

## Configuration

Configuration data is required only one time and will be stored by the SDK, if the device is to be re-purposed the token can be set again

#### Token
```swift
// set the token via the client using the key and message received from add device 
// returns true on successful configuration
_client.setToken(key: key, deviceToken: token)

```

## Connection

A connection to the platform must be established prior to sending data to the platform or receiving cloud to device messages

### Connect

Establishes the connection from the device to the Edgestream platform.

```swift
// connect to platform prior to sending data returns true on successful connection
// returns true on successful connection
_client.iotConnect()

```

### Disconnect

Removes the connection from the device to the Edgestream platform.


```swift
// disconnect from the platform prior to exiting your application
// returns true on successful disconnect
_client.iotDisconnect()

```

## Data

Manage data from device to the cloud and from the cloud to the device.

### Send

Send telemetry data from device to Edgestream platform.

```swift
// send data to the Edgestream platform for storage and processing
// returns true on successful send command
_client.sendData(dictString: data)

```

Send telemetry data tagged with location data from the device to the Edgestream platform.

```swift
// send data tagged with location data to the Edgestream platform for storage and processing
// returns true on successful send command
_client.sendData(dictString: data, location:CLLocation)

```

## Receive

Manage data from device to the cloud and from the cloud to the device.

### Receive Data

Register for the notification requires adding the notification name, creating the notificationCenter and adding an observer

```swift
// define the notification name that we will be listening for
extension Notification.Name{
    static let messageDataReceived = Notification.Name("messageDataReceived")
}

// declare the notificationCenter and use default 
private let notificationCenter:NotificationCenter = .default

// handler function to handle the notification and the data
@objc func onMessageDataReceived(_ notification: Notification){
        
    print("onMessageDataReceived")
    if let data = notification.userInfo as? [String: String]
    {
        for(tag, msg) in data
        {
            if(tag == "msgData")
            {
                // print received data
                print(msg)
                
            }
        }
    }
        
}

// add the observer
notificationCenter.addObserver(self,
        selector: #selector(onMessageDataReceived(_:)),
        name: .messageDataReceived,
        object: nil
    )

```

## Notifications

Manage Platform Notifications to be sent to the device running the EdgestreamSDK client.  Developer must configure their
their application to receive notifications.  Notifications will be sent to the device once the application has been 
configured whether or not the application is running or in the background. Please reference the [sample-device](sample-device/) applicaation
for how to configure your application.


### configuration
To receive notifiations you must add a few configuration items to your project and register your package name with the platform

NOTE: Application Platform Registration is migrating to an API but is currently a manual process Register your IOS Bundle ID with 
the platform by sending your IOS Bundle ID to [george.vigelette@edgespace.com](mailto:george.vigelette@edgespace.com) along with your Application Nickname and App Store ID which are both optional.  Once the applicaiton is registered with the platform you will receive a configuration file [GoogleService-Info.plist](sample-device/sample-device/GoogleService-Info.plist).  

NOTE: IOS Applications will not receive any notifications until cloud messaging is also enabled on the platform. In order to enable Cloud Messaging you must provide an APNs authentication key or APNs certificate to connect with APNs. These are generated from your Apple development portal [Configuring APNs](APN.md).