
# Configuring APNs

[//]: # (Image References)

[image1]: ./docs/019fb11-api1.png "Developer Center"
[image2]: ./docs/a402056-api2.png "Certificates, Identifiers & Profiles"
[image3]: ./docs/08011a6-api3.png "Add key Name and Select APNs"
[image4]: ./docs/d707f9d-api4.png "Hit Continue"
[image5]: ./docs/b3a6b0e-api5.png "Download Auth Key File"
[image6]: ./docs/0d2a17a-api6.png "Locate Team ID"

Cloud Messaging APNs interface uses the Apple Push Notification service (APNs) to send messages up to 4KB in size to your iOS app, including when it is in the background.

To enable sending Push Notifications through APNs We recommend that you create and upload an APNs Auth Key for the following reasons:

- No need to re-generate the push certificate every year
- One auth key can be used for all your apps – this avoids the complication of maintaining different certificates

When sending push notifications using an APNs Auth Key, we require the following information about your app:

- Auth Key file
- Team ID
- Your app’s bundle ID

You create both in the Apple Developer Member Center.

## To create an APNs auth key, follow the steps below.

Visit the [Apple Developer Member Center](https://developer.apple.com).

![alt text][image1]

Click on “Certificates, Identifiers & Profiles”.

Go to Keys from the Left Menu.

Create a new Auth Key by clicking on the “+” button in the top right corner.

![alt text][image2]

On the following page, add a Key Name, and select APNs

![alt text][image3]

Hit the Continue button.

![alt text][image4]

On this page, you will be able to download your auth key file. Please do not rename this file, and upload it as it is to our dashboard, as shown later below.

![alt text][image5]

Locate and copy your Team ID – click on your name/company name at the top right corner, then select “View Account”.

![alt text][image6]

Send the APNs Auth Key file which you downloaded earlier, Team ID and your app’s bundle ID to [george.vigelette@edgespace.com](mailto:george.vigelette@edgespace.com) to enable cloud messaging. Your app’s bundle ID can be found in Xcode.