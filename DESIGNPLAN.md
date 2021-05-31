# Design Plan

**May 29th / 2021**

*Here, I will be laying out some of the ideas that I would like to implement into my app. I will be using a journal format. I will be writing down design ideas as they occur to me and all of this file will remain unedited (if I can) and only updated.*

*To start, I will try and address each concept that was listed in the README.md file. I will then try to generate a solution for each concept to include in the app.*

*At this point, I think that a thorough recipe app would be a great idea. This is something I can get a bit passionate about and something that I can easily share with friends and family.*

*This would be an ideal situation to use Markdown's table feature.*

*Out the gate here, I think that RecyclerView, Cards, and the Room database features will be the bedrock of the app, so conceptually this is where I have started... In the end maybe, during the app summary, I can include a play-by-play for my chronological actions, but here I will be brain storming solutions for the concepts as they appear linearly in the text.*

**Concept**|**Design Solution**
-----------|-------------------
Lesson 1
Log Statements| I will include Log statements in all method blocks to simply communicate where the code has been executed.
View Elements| There will be many view elements embedded in the app as these are essential components of the UI.
Layout Editor| This will be tough to prove in code, so I will make a short (less than 5 min) YouTube video to demonstrate my knowhow
Toast Messages| I will add Toast messages for any app action where the user may not have a visually confirmation like changing settings (which might only update upon returning to the Main Activity).
TextView & ScrollView| Here I will add the in-depth description/ingredients/instructions to be read. After the user clicks the recipe card, a detail activity will open displaying the lengthy information in a TextView/ScrollView combination
HTML Formatting| I will format the TextView in a pleasant enough manner
Web Links| I will embed links within the TextView (maybe to my YouTube channel for recipes that I have already demonstrated)
Lesson 2
Multiple Activities| Self explanatory
Explicit Intents| I will use explicit intents to open and start other activities that belong to the apply
Implicit Intents| Maybe I will open an implicit intent for the user to be able to e-mail me with any bugs that they might find.
Android:Visibility| I can add possibly a section within the recipe details activity that could maybe also show the measurements in metric. I would be able to change the visibility in settings.
Lifecycle Methods| I will override all lifecycle methods (onCreate(), onStart(), onPause(), onRestart(), onResume(), onStop(), and onDestroy()) and include extra code and add log messages everytime method is called.
Landscape Mode| Add a integrated landscape mode with save-state to retain the information on the screen when the user rotates it.
Automatic Restoration| Document properly which portions of the activity are saved automatically onRestart()
onSavedInstanceState()| I will utilize the key/value pairs of onSavedInstanceState() to create a save state for the landscape mode.
Open Location, Open Website, Open Share| Using implicit intent to open up the user's current location, or maybe my website, and maybe a share option (which can send the APK?)
Receive an Implicit Intent| The app will be able to receive an intent of the View type in the default category... I am not yet sure how I can implement this.
Lesson 3
Debugging| I will make a YouTube video demonstrating my debugging ability. I should address this actively... I will do a debuggin session on a real bug
Unit Tests (JUnit)| I will write a full and impressive Unit Test for operations that I will won't to demonstrate functionality.
Android Support Library, SDK directives, and Dependencies| I will make another short YouTube video demonstrating this ability.
Lesson 4
Drawables| I will add custom drawables from the android library or the a site like https://materialdesignicons.com/
ImageView and android:onClick| The images of the recipe will be clickable in the detail activity and if the user would like, they can upload their own picture (using and implicit intent to take a picture).
android:inputType| In the portion of the text where the user can add the recipe, there will be several editTexts to add the information. They will need to specify the input type for ease for use for the user.
Radio Buttons| Radio button selection will be added to the settings acticity.
Spinner and Array Adapter| Can also be added to the settings activity (The array adapter is neccesary to run the Spinner).
onItemSelected() & getItemAtPosition()| I will use these methods to identify selection and execute specific code.
Basic Activity| I will start my main activity with a Basic Activity as it will give me access to the FAB button (to add recipes) and it comes with a sepeartion of the acitivity_main.xml and content.xml
AppBarLayout| I will implement the app bar layout to delegate possible actions for the user.
ToolBar| A ToolBar will be used over an ActionBar as the ToolBar is newer and provides more levels of customization.
setSupportActionBar()| I will call this to inflate the Toolbar.
onOptionsItemSelected()| This is needed and will be used to react to the action items that will be selected in the Toolbar.
app:showAsAction| I will use this to set the visibility and location of the Toolbar actions.
AlertDialog and AlertDialogBuilder| I will use the AlertDialogBuilder to create an alert dialog... I can use this dialog as a welcome dialog or maybe it can be the dialog for creating recipe card.
DialogFragment| I will also create a custom dialog extending the DialogFragment class. This is probably better suited as the dialog for creating the recipe.
FragmentManager and getSupportFragmentManager| I will need to use this object and method to implement the DialogFragments
Parent Activities| I will decalre the parent activities in the AndroidManifest.xml to dictate navigation and display an up arrow for the user to return to the parent activity.
Tabs| Maybe I can use tabs to display the recipes in different organizations (newer, older, less time consuming, and possibly by country(alphabetically)). Tabs are also a bit complicated and this may take some time to implement.
ViewPager, FragmentPagerAdapter, FragmentStatePagerAdapter| These objects are necessary for the tabs layout to work. I will need to implement these in-code.
RecyclerView| This sits at the core of the app and a solid understanding of this topic is necessary. I will provide thorough documentation and potentially some other form of understanding like a visual representation in a poster or something like that.
LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager| One of these will be needed in assoiciation with the RecyclerView.  I will probably use GridLayoutManager with the columns set to match the device that it is displayed on such as the example that was given in the text.
View.onClickListener| This will be needed to react the user clicks on the RecyclerView items.
Lesson 5
ShapeDrawable| I will create my own drawable for the add recipe button as it should be quite simple. I will stack a circle and a cross I think somehow. It will not be unique, but it will demonstrate my understanding of the concept.
Styles| I will use styles for buttons and textviews (Maybe even just the font) for standardization and ease across the app. A style applied to an activity or app (like the default one) will be declared in AndroidManifest.xml
Inheriting Styles| I will ensure that at least one style inherits from a parent style that is available in the library.
Themes| I will apply a theme for the activities with android:theme. (Styles are usually reserved for a single view and Themes are intended for entire apps or activities).
CardView| Another crucial part of the app displays a the RecyclerView items in a digestable way. It also provides me the developer with plenty of routes of customization. I will dispaly my list with cards and I will try to incorporate some elements that I am not quite familiar with, like dispalying the picture and a title as well as maybe a transtion for when the user scrolls through the list (like maybe the card will expand as the user scrolls thru, providing more information)
Glide| I will use this to import the pictures into the app for display it is a lot less intensive for processing and space wise.
ItemTouchHelper| I will need to implement this to react to the different gesture events that user might do, but as of righ now, I can think of only clicking to open the detail activity... I could also longpress for a context menu to delete???
Material Design| I will prefer to material design for the theme and for some ideas to borrow for the layout most likely. Like best practice for spacing and location of certain actions.
Configurations| I will add a separate configuration to load the strings.xml in Spanish (but I will just copy and paste from Google to hasten this process).
Lesson 6
Espresso| I will set up UI testing using the Espresso API. There needs to appropraite instrumentation, implementaion, and annotations to run successfully. I will ensure that a spinner gets tested. There should be an option using a Spinner in the Settings Activity.
Lesson 7
AsyncTask| I am not sure what actions I would need to take care of in the background? I can have an updated list of recipe books pulled from Googles API. Or I could have a calender setting with an alarm in the background to notify the user on which days they will be cooking which things.
onPreExectute(), doInBackground(), onPostExecute(), and onProgressUpdate()| I need to use these methods to implement the AsyncTask.
Broadcast Receiver| I will receive certain broadcasts like when the user is connected to wifi, at which point the list of recipe books from the Google API can be updated.
Custom Broadcast| The app will be able to send (and possible receive the same) custom broadcast. I can use a local broadcast if this is the case.
LocalBroadcastManager| This will be needed to manager the local custom broadcast.
Lesson 8
Notifications| I will implement notifications on a timer that reminds the user that they should start looking at the recipes at 5pm (near supper time). This will be able to be adjusted in the settings menu.
NotificationCompat.Builder| This will be used to builder the visual and UI aspects of the notification.
Cancel Notification| I will add the a notification ID to be able to update and cancel the notification.
setSmallIcon(), setContentTitle(), setContentText()| These will be used to further customize the notification.
AlarmManager| This will be used in tandem with the notification to alert the user that they should start considering what they will be having for supper.
setAndAllowWhileIdle() or setExactAndAllowWhileIdle()| These can be used to complete tasks while idle, possibly update the Recipe book list from Google's API and designated intervals.
InexactTime| I will use this with the scheduled AsyncTask as not to overload the thread with multiple requests.
Pending Intents| I will use Pending Intents to perform the operations as this allows the receiving app to exectute the operation as if the receiving app was the app that issued the operation.
JobScheduler| I will use the JobScheduler to check for new versions of the app??? This may just be an empty task that does not actually search for new versions but simply returns a message saying "This is the newest version of the app".
JobInfo| I will use this to define the conditions for acting out the job.
JobService| This will need to be defined. This is the code to be executed once the conditions have been met.
Lesson 9
Shared Preferences| I will use this to save data of medium complexity... Maybe this can hold user information if they decide to provide it in the settings acitivity.
SharedPreferences.Editor| I will use this alongside put, get, clear, and apply methods to manage the user information.
Settings Activity| There will be a settings activity. I have referenced it many times already.
PreferenceFragment| Using the preference fragment will allow me to compartmentalize the setting types as well as provide a better user experience on larger screen devices.
PreferencFragmentCompat = getSupportFragmentManager()| I will need to use getSupportFragmentManager() as I will most likely be using AppActiviityCompat.
setPreferencesFromResource()| I will refer to my custom preferences layout with this method.
PreferenceManager.setDefaultValues()| I will use this to display what the defualt settings are.
Settings Control| I will add appropriate UI elements, like toggles, spinners, EditText boxes, etc for the user to interact with.
Settings & onCreate()| I will read the settings from SharedPreferences onCreate(). This will me implemented everytime the activity is started.
Lesson 10
Room, LiveData, and ViewModel| Another corner stone of the app. Difficult to implement, but crucial for understanding. All the RecyclerView items will have their information saved in and accesed from a Room (SQLite) database.
Repository| The repository adds another level of serparation between the model and the view. (Acts somewhat as the controller).
ItemTouchHelper| I will use the ItemTouchHelper to be able to move the recipe cards around, possibly in a tab labelled custom.

*This table was completed over three days, finishing on May 31st.*

**May 31st / 2021**

*I would like to import a similar table as the one listed above that references specific files for each component that I said I would add. I can include these different coordinating lists/tables in a separate file for ease of reading.*
