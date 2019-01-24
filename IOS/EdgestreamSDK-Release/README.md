# EdgestreamSDK Binary Release

![Current State: Preview Release](https://img.shields.io/badge/Release-v1-green.svg) 

---


[//]: # (Image References)

[image1]: ./docs/embedded_framework.png "Embedded Framework"
[image2]: ./docs/configured_project.png "Configured Project"
[image3]: ./docs/added_client.png "Edgestream Client"
[image4]: ./docs/build_project.png "Clean and Build"

This is currently the preview release for the EdgestreamSDK Binary Release. 

## Getting Started Guide
Prior to starting development using the Edgestream SDK please ensure that you have the Xcode IDE installed on your local development machine.  The Latest Xcode release can be found [here](https://developer.apple.com/xcode/).

### Add SDK to a newly created project or existing project in Xcode as embedded framework
1. [Download](https://github.com/edgespace-io/Edgestream/blob/master/IOS/EdgestreamSDK-Release/edgestreamSDK-Release-v1.tar) edgestreamSDK-Release.tar for IOS and extract the Edgestream framework to the desktop using the tar command 
```bash
tar xvf edgestreamSDK-Release-v1.tar ~/Desktop/
```

2. [Open or Create](https://developer.apple.com/library/archive/referencelibrary/GettingStarted/DevelopiOSAppsSwift/BuildABasicUI.html#//apple_ref/doc/uid/TP40015214-CH5-SW3) a new project in Xcode

2. Once in Xcode add framework as an embedded binary, by navigating to your Application Embedded Binaries settings clicking the plus sign and selecting the extracted framework edgesdk.framework on your desktop or location that you extracted the framework to.

![alt text][image1]

3. Once the library is added close the Xcode project and navigate to your project directory on the command line. Generate a Pod file by using the following command
```bash
pod init
```

4. Add the following dependencies to your Podfile
```
  pod 'AzureIoTHubClient', '~>1.2.11'
  pod 'AzureIoTUtility', '~>1.1'
  pod 'AzureIoTuMqtt', '~>1.1'
  pod 'AzureIoTuAmqp', '~>1.2'
  pod 'SwiftyRSA', '~>1.5'
  pod 'CryptoSwift', '~>0.12'
```

5. Install dependencies and create workspace by running the pod install command
```bash
pod install
```

6. Open Workspace project in Xcode

![alt text][image2]

7. Import edgesdk and add EdgestreamClient to your project. We demonstrate doing this in teh ViewController

![alt text][image3]

8. Clean and Build Project

![alt text][image4]
