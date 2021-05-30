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
View Elements| There will be many view elements embedded in the app as these are essentially components of the UI.
Layout Editor| This will be tough to prove in code, so I will make a short (less than 5 min) YouTube video to demonstrate my knowhow
Toast Messages| I will add Toast messages for any app action where the user may not have a visually confirmation like changing settings (which might only update upon returning to the Main Activity).
TextView & ScrollView| Here I will add the in-depth description/ingredients/instructions to be read. After the user clicks the recipe card, a detail activity will open displaying the lengthy information in a TextView/ScrollView combination
HTML Formatting| I will format the TextView in a pleasant enough manner
Web Links| I will embed links within the TextView (maybe to my YouTube channel for recipes that I have already demonstrated)
Lesson 2
Multiple Activities| Self explanatory
Explicit Intents| I will use explicit intents to open and start other activities that belong to the apply
Implicit Intents| Maybe I will open an implicit intent for the user to be able to e-mail me with any bugs that they might find.
