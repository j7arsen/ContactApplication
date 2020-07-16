# kmp-app-template
Contains basic classes for project based on KotlinMultiplatform

#Firebase Crashlytics
===============
Step 1: Create a Firebase Project
* In the Firebase console click "Add Project", then enter "Project name".
---------------
Step 2: Register your apps with Firebase.
* After you have a Firebase project, you can add your Android and iOS apps to it.
* In the center of the project overview page click the Android/IOS icon to launch the setup workflow.
* Enter your app's package name in the Android package name field.(Android) / Enter your app's bundle ID in the iOS bundle ID field.(IOS)
* (Optional) Enter other app information: App nickname and Debug signing certificate SHA-1.(Android) / Enter other app information: App nickname and App Store ID.(IOS)
* Register app
--------------
Step 3: Add a configuration file to the project
* Add the Firebase Android configuration file to your app:
  * Click Download google-services.json to obtain your Firebase Android config file (google-services.json).
  * Move your config file into the module (app-level) directory of your app.
* Add the Firebase IOS configuration file to your app:
  * Click Download GoogleService-Info.plist to obtain your Firebase iOS config file (GoogleService-Info.plist).
  * Move your config file into the root of your Xcode project. Select to add the config file to all targets.

#Navigation
===============
Screens are navigated in PresentationModel class. The NavigationRouter class used for navigation.
Each screen should be extended from NavigationScreen class and must be initialized in NavigationScreens class.
Supported Navigation Messages:
* AddTabs - add tab screens
* AttachTab - add tab
* NavigateTo - open new screen
* Exit - return to the previous screen
* NewChain - open several screens inside single transaction
* NewChainRoot - clear screen stack and open several screens inside single transaction
* FinishChain - remove all screens from the chain
* NewRootScreen - clear all screens and open new one as root
* ReplaceScreen - replace current screen
* BackTo - return to the needed screen
* StartFlow - open new flow screen
* NewRootFlow -  clear all screens and open new flow screen as root
* FinishFlow - remove flow screen from the chain and return to the previous screen
Handling navigation messages
* For processing navigation messages need to implement NavigationMessageHandler interface in Activity/Fragment/Controller
Cases for Android
* Application - Single Activity - Navigator(AppNavigator class) in activity is the navigator in the application
* Each flow screen has its own navigator(AppNavigator class)
* When to do local navigation(for example, in tab)
 * Extend flow screen
 * Override function from NavigationMessageHandler interface, where by NavigationScreen makes navigation at the application or local level



