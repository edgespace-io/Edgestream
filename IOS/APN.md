
#Configuring APNs

Cloud Messaging APNs interface uses the Apple Push Notification service (APNs) to send messages up to 4KB in size to your iOS app, including when it is in the background.

To enable sending Push Notifications through APNs, you need:

- An Apple Push Notification Authentication Key for your Apple Developer account. Firebase Cloud Messaging uses this token to send Push Notifications to the application identified by the App ID.
- A provisioning profile for that App ID.

You create both in the Apple Developer Member Center.

##Create the authentication key