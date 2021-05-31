# Fundamentals Capstone

## **Purpose**

The purpose of this app is to thoroughly prove my knowledge that was provided in the course Codelabs for Android Developer Fundamentals provided through developers.android.com. There was plenty of cross-over between this course and the college Intro to Android App Development course that I took in the Winter of 2021.  
I still find myself having trouble with a couple of these topics, including all the implementations for RecyclerView and Room/SQLite. I hope that putting in a strong effort to coordinate my knowledge into a fully fleshed-out app will help solidify at least the concepts where there is still a grey area and my brain has yet to connect the dots on what is actually happening.  
This app will also include the concepts that were covered in the phone call and SMS codelab that is also available on developers.android.com as I covered this in a similar time frame (to make a joke app that could send a message with as many iterations as desired).

## **About The App**

The main direction of the app has yet to be decided, but I think it will be a refined recipe app that I can keep with me. It will try to incorporate everything taught in the course if that is at all possible. It should also be a useful app that I can share with friends and family with hopes that a net benefit can be taken away from it. I enjoy cooking and I have a tendency to try one recipe repetitively until it reaches a level of satisfactory deliciousness, then I tend to forget all about it or lose the specific recipe as I move on with my life. Beginner dishes might include Colombian Arepas, vegan chicken wings, vegetarian pizza, tofu stir-fry, etc.

## **Index of Included Content**

Here I will include the list of all the topics covered in the course that I will want to add to the app. Later I may add in-depth analysis of the topics; however, my in-code documentation may be sufficient. The majority of this information was pulled from each summary section of the Codelab. I will refer back to these concepts eventually maybe with screen shots of UI or code to save the reader effort of searching the repository.

Unit 1 Get Started

Lesson 1:

- Ensuring that there are log statements
- View elements and View Group, TextEdit and attributes, onClick handlers
- Using the layout editor and design tab to customize UI
- Displaying Toast messages (possibly for every action?)
- TextView elements and ScrollView. Scrolling multiple elements
- Formatting with HTML and escape sequences
- Clickable web links

Lesson 2:

- More than one activity
- Using intents with extra information and/or perform an action (as URI)
- Implicit and Explicit intents
- Using android:visibility to set visibility of a view widget
- Demonstrate understanding and modify lifecycle methods, onCreate(), onStart(), onPause(), onRestart(), onResume(), onStop(), and onDestroy()
- Add landscape mode with understanding that rotation destroys and rebuilds the activity
- Some portions of an activity are saved automatically, but some are not.
- Save basic information with onSavedInstanceState() to save key/value pairs in a Bundle.
- Restore the information in onCreate() or in onRestoreInstanceState()
- Implicit intents with Open website, open location, and share (ShareCompat.IntentBuilder).
- Have the ability to receive an implicit intent including filters in AndroidManifest.xml

Lesson 3:

- Log info and view log info with logcat
- Demonstrate debugging and Gradle info... maybe in a YouTube video? Include info about the debugger pane and breakpoint
- Making and using Unit tests (JUnit)
* Unit tests will require extra attention
- Demonstrate knowledge of Android Support Library, SDK directives, and dependencies in Gradle

Unit 2 User Experience

Lesson 4:

- Adding drawables
- Defining an imageview and using android:onClick and handling this in the activity
- Display toast messages
- Use various android:inputType s to specialize the EditText (Can concatenate various input types using "|" pipe )
- Radio buttons and isChecked() of the checkable interface.
- Spinner and Array adapter and AdapterView.OnItemSelectedListener
- Use onItemSelected() and getItemAtPosition() to implement specific code per selection
- Can use Basic Activity to implement app bar, options menu and FAB button on start-up, but my version Basic Activity differs greatly from the text and I may avoid that
- Coordinator Layout and AppBarLayout
- Use Toolbar over ActionBar  to implement the app bar
- setSupportActionBar() to inflate the Toolbar
- onOptionsItemSelected() to execute code from user choices
- add specific icons for the app bar from the library or online at material.icons
- app:showAsAction to set visibility of app bar icons and the overflow menu
- Use an alert dialog  and AlertDialog.Builder to build a simple dialog
- Create and implement a dialog fragment class and instance
- Use FragmentManager and getSupportFragmentManager() to implement the dialog/fragment in the Activity
- Declaring parent activities and using the up arrow for navigation
- Use tabs for lateral navigation
- ViewPager and FragmentPagerAdapter and FragmentStatePagerAdapter
- RecyclerView and Creating a layout specific and using layout inflator
- LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
- View.onClickListener to react to clicks on RecyclerView

Lesson 5:

- Add a ShapeDrawable
- Use styles to reduce repetitous xml code in layouts
- A style applied to and activtity or app must be declared in AndroidManifest.xml
- Inherit a style by declaring a parent style
- Apply a theme with android:theme
- Use a CardView (probably with RecyclerView)
- Use Glide to easily manage loading images into imageview
- Use ItemTouchHelper to get information about gesture events including with cards in RcyclerView
- Using material design to create a palatable app
- Use different configurations to "localize the app" possibly in Spanish?

Lesson 6:

- Set up and implement UI testing with Espresso... (This is important and will take a bit to implement)
- Adding appropriate instrumentation, implementaion, and annotations to run successfully .
- Test code and test a Spinner

Unit 3 Working in the background

Lesson 7:

- Create an Asyntask to handle intensive processing on a thread separate from the one that runs the UI
- Use onPreExectute(), doInBackground(), onPostExecute(), and onProgressUpdate()
- Rotating an app will disconnect the activity from the asynctask and must be handled appropriately
- Use google API's with Asyntask or AsyncTaskLoader to generate data.
- Use AyncTaskLoader to manage more aspects of background work
- Set up a Broadcast Receiver
- A broadcast intent is completely different than an activity intent
- Use LocalBroadcastManager to register and send broadcasts within an app in a secure and efficient away

Lesson 8:

- Implement Notifications
- Use NotificationCompat.Builder to specify UI and actions of the notifications
- To update or cancel a notification, there must be a notification ID
- Utilize setSmallIcon(), setContentTitle(), setContentText()
- Use AlarmManager to schedule tasks based on real-time clock or on the elapsed time since boot.
- Alarms do not fire in doze mode.
- Use setAndAllowWhileIdle() or setExactAndAllowWhileIdle() to complete tasks while idle.
- Use inexact time to reduce load of many actions happening at the same onOptionsItemSelected
- Use Pending intents to perform operations
- Use JobScheduler to accomplish background services
- Use JobInfo to set conditions to trigger the JobScheduler
- Use JobService to specify the code to be executed

Unit 4 Saving User Data

Lesson 9:

- Utilize Shared Preferences to store small amounts of key/value pair information
- Shared Preferences persist over different user sessions
- Use a SharePreference.Editor object
- Use put, get, clear, and apply methods to alter data in the SharedPreferences
- Implement a settings activity
- Use a PreferenceFragment to display the app settings
- PreferenceFragment = getFragmentManager()
- PreferencFragmentCompat = getSupportFragmentManager()
- assign the preferences.xml with setPreferencesFromResource()
- set default values with PreferenceManager.setDefaultValues()
- add UI controls for settings
- read settings changes in onCreate(). Save settings as simple information in SharedPreferences

Lesson 10:

- Implement Room, LiveData, and ViewModel (This is the most complicated section to complete)
- Include a Repository as well
- Write database code and utilize ItemTouchHelper

*Refer to DESIGNPLAN.md for the potential implementation of these concepts and to follow my design process as it unfolds.


## **Contributors**

Myself standing on the shoulders of giants.


## **License**
MIT license
