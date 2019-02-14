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
| **[Cloud2Device](#Cloud2Device)**                     | [edgeEventCallback](#event)                           |
| **[Cloud2Device](#Cloud2Device)**                     | [sendMessageReceiptConfirmations](#confirm)           |
| **[Notifications](#Notifications)**                   | [registerWithNotificationHubs](#registerNotifications)|            |



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

## Cloud2Device

Manage Messages received by the EdgestreamSDK client for Cloud to Device Messages and Receipt of Device to Cloud Messaging.
Developer must implement the EdgestreamMessageEventCallback interface to receive messages from the Edgestream platform. Cloud
to device messages are only received when the client is running.

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

## Notifications

Manage Platform Notifications to be sent to the device running the EdgestreamSDK client.  Developer must register for Notifications.
Notifications will be sent to the device once registered whether or not the device is active

### registerNotifications
To receive notifiations you must add a few configuration items to your project and register your package name with the platform

Register your package name by 


After registering your application you will receive json configuration data that you must put in a file called google-services.json
and the file must reside in your app directory

```java

app/google-services.json

```

Modify your Application manifest file to receive notificaitons.  

Add Tools to manifest tag

```java

    xmlns:tools="http://schemas.android.com/tools"

```

Add icon replace with your icon in Manifest in Application Tag

```java

    tools:replace="android:icon"

```

Add Services and Receivers to Application tag after your activity

```java

        <service android:name="io.edgestream.edgesdk.EdgestreamInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT"/>
            </intent-filter>
        </service>

        <service
            android:name="io.edgestream.edgesdk.RegistrationIntentService"
            android:exported="false">
        </service>

        <receiver android:name="com.microsoft.windowsazure.notifications.NotificationsBroadcastReceiver"
            android:permission="com.google.android.c2dm.permission.SEND">
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <category android:name="com.edgestream.notifications.getstartednh" />
            </intent-filter>
        </receiver>

        <uses-library android:name="org.apache.http.legacy" android:required="false"/>

```

Add libraries to your App build.gradle file

```java

    implementation 'com.google.firebase:firebase-messaging:17.3.4'
    implementation 'com.microsoft.azure:notification-hubs-android-sdk:0.4@aar'
    implementation 'com.microsoft.azure:azure-notifications-handler:1.0.1@aar'
    implementation 'com.google.android.gms:play-services-gcm:16.0.0'

```

Add plugin to your App build.gradle after the dependencies section

```java

    apply plugin: 'com.google.gms.google-services'

```


Add classpath to dependencies of project build.gradle

```java

    classpath 'com.google.gms:google-services:4.2.0'

```

In the onCreate or onStart method Register the EdgestreamHandler with the notification manager adn use the _client to register for 
notifications with the platform notification HUB as shown below.

```java

    // Register for Notifications

    NotificationsManager.handleNotifications(this, EdgestreamNotificationSettings.SenderId, EdgestreamHandler.class);
    _client.registerWithNotificationHubs(this);

```
