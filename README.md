# kmp-app-template
Contains basic classes for project based on KotlinMultiplatform

#Firebase Crashlytics
=====================
Step 1: Create a Firebase Project
* In the Firebase console click "Add Project", then enter "Project name".
=====================
Step 2: Register your apps with Firebase.
* After you have a Firebase project, you can add your Android and iOS apps to it.
* In the center of the project overview page click the Android/IOS icon to launch the setup workflow.
* Enter your app's package name in the Android package name field.(Android) / Enter your app's bundle ID in the iOS bundle ID field.(IOS)
* (Optional) Enter other app information: App nickname and Debug signing certificate SHA-1.(Android) / Enter other app information: App nickname and App Store ID.(IOS)
* Register app
=====================
Step 3: Add a configuration file to the project
* Add the Firebase Android configuration file to your app:
  * Click Download google-services.json to obtain your Firebase Android config file (google-services.json).
  * Move your config file into the module (app-level) directory of your app.
* Add the Firebase IOS configuration file to your app:
  * Click Download GoogleService-Info.plist to obtain your Firebase iOS config file (GoogleService-Info.plist).
  * Move your config file into the root of your Xcode project. Select to add the config file to all targets.
