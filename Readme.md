npm install
cd ios
pod install
cd ..

npx react-native run-ios

npx react-native run-android

**iOS:**

Change ios/RCTOcrModule.m to have valid value for below items:

[create ocrEngineWithApiKey:@"YOUR_IOS_API_KEY" licenseKey:@"YOUR_IOS_LICENSE_KEY" path:filePath success:^(NSString * result) {
    callback(@[result]);
  }];

**Android**

*Please note: Android version is not functional as of now, we are working on a fix*

Change android/app/src/main/java/com/conpds/ocrenginedemo/java/ocrenginedemoapp/OcrModule.java to have valid value for below items:

String API_KEY = "YOUR_ANDROID_API_KEY";
String LICENSE_KEY = "YOUR_ANDROID_LICENSE_KEY";