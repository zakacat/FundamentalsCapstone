package com.example.android.fundamentalscapstone;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
//The Recipe Database is an abstract class that builds the database from the table.
//Instance and singleton is used to avoid multiple versions of the database exisitng
//at one time, which is not good.
@Database(entities = Recipe.class, version = 4, exportSchema = false)//Version numbers don't need to be changed as the user has creative control of Recipes at this point.
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

    private static RecipeRoomDatabase INSTANCE; //Singleton

    public static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { //check to see if there is a database
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create the database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeRoomDatabase.class, "recipe_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            // I am not quite sure how this will affect my app.
                            .fallbackToDestructiveMigration() //this can prevent updating the low-level information as there is no proper migration strategy.
                            .addCallback(sRoomDatabaseCallback) //calls everytime.
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    //This private class acts when the database is opened. onOpen (if the INSTANCE is null) this will be called and
    //directed to the startdata call which is an asynctask.
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {

        private final RecipeDao mDao;
        //Constructor to migrate the DAO.
        PopulateDBAsync(RecipeRoomDatabase db) {
            mDao = db.recipeDao();
        }

        //Only if there is no recipe to retrieve from the database
        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyRecipe().length < 1) { //I am not sure what returns with the getAnyWord() call but it is definitely not null... must be an empty string cause a check for null didn't work. Nevermind, I am dumb, it returns an array of Recipes, and if the array is less than 1, yada yada yada.
                mDao.insert(new Recipe("Vegan Buffalo Wings", "Give these delicious vegan wings a try-- I promise they will taste JUST like the meat version. The seitan chicken has taken me roughly 2 years to perfect. It is moist, and perfectly spiced. For the \"bones\" you can use either sugar cane or popsicle sticks; or you can simply have \"boneless\" wings!", "Chicken “Meat”\n" +
                        "\n" +
                        "    1 cup vital wheat gluten\n" +
                        "    2 tbsp nutritional yeast\n" +
                        "    1 tsp onion powder\n" +
                        "    ½ tsp salt\n" +
                        "    ½ tsp poultry seasoning\n" +
                        "    ¾ cup vegetable broth, I made mine with bouillon\n" +
                        "    2 tbsp tahini\n" +
                        "    2-3 cups of vegetable broth, I made mine with bouillon\n" +
                        "\n" +
                        "Buffalo Wing Batter\n" +
                        "\n" +
                        "    2 eggs worth of egg replacer\n" +
                        "    1-2 tablespoons Buffalo hot sauce\n" +
                        "    ¼ tsp salt\n" +
                        "    ½ tsp black pepper\n" +
                        "    ¼ tsp cayenne pepper\n" +
                        "    1 cup all-purpose unbleached flour or flour of choice", "Vital Wheat Gluten\n" + "Nutritional Yeast\n" + "Onion Powder\n" + "Salt\n" + "Poultry Seasoning\n" + "Vegetable Broth\n" +
                        "Tahini\n" + "Egg Replacer\n" + "Buffalo Hot Sauce\n" + "Black Pepper\n" + "Cayenne Pepper\n" + "Flour", "Chicken \"Meat\"\n" +
                        "\n" +
                        "  1.   In a mixing bowl, mix together wheat gluten, salt, nutritional yeast, onion powder and poultry seasoning.\n" +
                        "  2.   In a larger bowl, combine ¾ cup broth and tahini and whisk until smooth.\n" +
                        "  3.   Mix the dry ingredients with the wet and stir until well combined.\n" +
                        "  4.   Knead the dough until it is elastic but not dry. Sprinkle some additional gluten flour if you find you have made your dough too sticky.\n" +
                        "  5.   Divide the dough into small little disks, it’s fun to try and shape them into wing-like shapes. Keep in mind they will grow to about twice their size, so make then smaller then you would normally. Place in a casserole dish, covering them with 2-3 cups vegetable broth.\n" +
                        "  6.   Bake wings in broth for 1 hour at 350 degrees, flipping at 45 minutes. Allow to cool.\n" +
                        "  7.   Once cooled skewer your “wing” with a sugar cane “bone” or popsicle stick.\n" +
                        "\n" +
                        "Spicy Batter\n" +
                        "\n" +
                        "  1.   Whisk together the egg replacer, hot sauce, red pepper flakes, and cayenne pepper into a medium size bowl.\n" +
                        "  2.   Put the flour and black pepper into a large zip lock bag.\n" +
                        "  3.   Dredge chicken wings into the egg mixture then toss into the Ziplock bag of flour.\n" +
                        "  4.   Deep fry in a large pot of oil or deep fryer at 350° for 5-8 minutes or until light brown and crispy. Remove wings to a paper towel lined plate or tray.\n" +
                        "  5.   Toss the wings in remaining buffalo wing sauce and enjoy. Dip onto vegan ranch sauce and follow with celery and carrot sticks.\n", 0, 2, R.drawable.vegan_buffalo_wings));
                mDao.insert(new Recipe("Flour Tortillas", "These homemade flour tortillas are tender, soft and flavorful. Because they keep well, they can be made in advance and warmed in the microwave or on the stovetop, just before serving.", " 3 cups all-purpose flour\n" +
                        "1 teaspoon salt\n" +
                        "1 teaspoon baking powder\n" +
                        "⅓ cup extra virgin olive oil, vegetable oil or other fairly neutral flavored oil\n" +
                        "1 cup warm water ", "Flour\n" + "Salt\n" + "Baking Powder\n" + "Olive Oil",
                        "  1.  Combine flour, salt and baking powder in a medium-size bowl. Using a sturdy silicone spatuala or a sturdy wooden spoon, mix dry ingredients until well combined.\n" +
                        "\n" +
                        "  2.   Make a well in the center of the dry ingredients and add the oil and water. Stir well from the bottom up, until all dry ingredients are incorporated and the dough begins to come together and form a shaggy ball.\n" +
                        "\n" +
                        "  3.   Turn dough out onto a lightly floured work surface and knead for 1-2 minutes until the dough is nice and smooth.\n" +
                                "  4.   Transfer dough to a lightly floured work surface. Divide into 16 equal portions. Turn each piece to coat with flour. Form each piece into a ball and flatten with the palm of your hand. Cover flattened balls of dough with a clean kitchen towel and allow to rest for at least 15 minutes (or as much as 2 hours) before proceeding.\n" +
                                "\n" +
                                "  5.   After the rest period, heat a large pan over medium heat. Roll each dough piece into a rough circle, about 6-7 inches in diameter, keep work surface and rolling pin lightly floured. Don’t stack uncooked tortillas on top of each other or they will stick together. (I like to separate my tortillas with parchement paper.)\n" +
                                "\n" +
                                "  6.   When the pan is hot, place one dough circle into the pan and allow to cook 45 seconds to 1 minute or until the bottom surface has a few pale brown spots and the uncooked surface is bubbly. If browning too fast, reduce the heat a bit. If it’s taking longer than a minute to see a few pale golden brown spots on the underside of tortillas, increase the heat a bit. Flip to other side and cook for 15-20 seconds. The tortillas should be nice and soft but have a few small brown spots on the surface. \n" +
                                "\n" +
                                "  7.   Remove from pan with tongs and stack in a covered container or zippered bag to keep the tortillas soft. \n" +
                                "\n" +
                                "  8.   Serve warm or allow to cool for later use. When ready to use, place a slightly damp paper towel in the bottom of a microwave-safe container (with a cover) that will hold the stacked tortillas. Microwave uncovered for 15-30 seconds (start with 15) or until warm, then keep covered to hold heat while serving.\n" +
                                "\n" +
                                "  9.   Store in an airtight container or zippered bag at room temperature for 24 hours or refrigerate for up to 1 week. To freeze, separate tortillas with parchment paper or waxed paper and place in a zippered bag before placing in the freezer.\n", 1, 3, R.drawable.flour_tortillas));
                mDao.insert(new Recipe("Pierogi", "Pierogi, dumplings stuffed with a filling, make for a wonderful change-of-pace side dish.", "5 cups all-purpose flour\n" +
                        "1 teaspoon salt\n" +
                        "1 cup water\n" +
                        "3 large eggs\n" +
                        "1/2 cup butter, softened\n" + "2 tablespoons butter\n" +
                        "5 ounces cream cheese, softened\n" +
                        "1/2 teaspoon salt\n" +
                        "1/2 teaspoon pepper\n" + "4 medium potatoes, peeled and cubed\n" +
                        "2 medium onions, chopped", "Flour\n" + "Salt\n" + "Eggs\n" + "Butter\n" + "Pepper\n" + "Potatoes\n" + "Onions\n", "  1.   In a food processor, combine flour and salt; cover and pulse to blend. Add water, eggs and butter; cover and pulse until dough forms a ball, adding an additional 1 to 2 tablespoons of water or flour if needed. Let rest, covered, 15 to 30 minutes.\n" +
                        "  2.   Place potatoes in a large saucepan and cover with water. Bring to a boil over high heat. Reduce heat; cover and simmer until tender, 10-15 minutes. Meanwhile, in a large skillet over medium-high heat, saute onions in butter until tender; set aside.\n" +
                        "  3.   Drain potatoes. Over very low heat, stir potatoes until steam has evaporated, 1-2 minutes. Press through a potato ricer or strainer into a large bowl. Stir in cream cheese, salt, pepper and onion mixture; set aside.\n" +
                        "  4.   Divide dough into 4 parts. On a lightly floured surface, roll 1 portion of dough to 1/8-in. thickness; cut with a floured 3-in. biscuit cutter. Place 2 teaspoons of filling in center of each circle. Moisten edges with water; fold in half and press edges to seal. Repeat with remaining dough and filling.\n" +
                        "  5.   Bring a Dutch oven of water to a boil over high heat; add pierogi in batches. Reduce heat to a gentle simmer; cook until pierogi float to the top and are tender, 1-2 minutes. Remove with a slotted spoon. In a large skillet, saute 4 pierogi and onion in butter until pierogi are lightly browned and heated through; sprinkle with parsley. Repeat with remaining pierogi.\n" +
                        "  6.   Freeze option: Place cooled pierogi on waxed paper-lined 15x10x1-in. baking pans; freeze until firm. Transfer to an airtight freezer container; freeze up to 3 months. To use, for each serving, in a large skillet, saute 4 pierogi and 1/4 cup chopped onion in 1 tablespoon butter until pierogi are lightly browned and heated through; sprinkle with minced fresh parsley.", 4, 2, R.drawable.pierogi));
                mDao.insert(new Recipe("Cheese-Stuffed Arepas", "A staple in Colombian and Venezuelan cooking, these corn cakes take no time to make, melt in your mouth, and are infinitely adaptable. Experiment with toppings like fried eggs, avocado, beans and cheese.", "Kosher salt\n" +
                        "\n" +
                        "2 cups pre-cooked white corn meal, such as P.A.N.   \n" +
                        "\n" +
                        "4 ounces part-skim mozzarella, cut into 8 cubes\n" +
                        "\n" +
                        "1/4 cup vegetable oil, or as needed\n", "Kosher Salt\n" + "White Corn Meal\n" + "Part-Skim Mozzarella\n" + "Vegetable Oil",
                        "  1.   Position a rack in the center of the oven and preheat to 350 degrees F.\n" +
                        "  2.   Stir together 2 1/2 cups lukewarm water and 1 1/2 teaspoons salt in a large bowl. Gradually add the corn meal into the water, using your fingers to stir and combine, until a soft and moist dough forms. \n" +
                        "  3.   Divide the dough into 8 golf ball-size balls and pat each into a patty about 5-inches wide and 1/4-inch-thick. \n" +
                        "  4.   Put a cube of mozzarella in the center of each patty. Fold the dough over the cheese, making sure the cheese is completely covered and sealed. Pat it back down until 3-inches wide and 1/2-inch-thick (see Cook's Note).\n" +
                        "  5.  Heat the oil in a large skillet over medium-low heat until shimmering. Working in batches, cook the corn patties, until golden brown, about 5 minutes per side (making sure the oil is hot for each batch). Transfer to a cooling rack set on a rimmed baking sheet and bake until the edges are crisp and golden, about 10 minutes.", 2, 1, R.drawable.cheesestuffed_arepas));
                mDao.insert(new Recipe("Pakora", "These are very yummy, and if any of you have ever had Japanese tempura coated veggies, you'll love this recipe. It's similar, yet it is unique in it's taste. Serve with a chili sauce, mint yogurt sauce, or sweet and sour sauce. Try other vegetables for dipping, such as sweet potatoes, broccoli and asparagus. ", "1 cup chickpea flour\n" +
                        "½ teaspoon ground coriander\n" +
                        "1 teaspoon salt\n" +
                        "½ teaspoon ground turmeric\n" +
                        "½ teaspoon chili powder\n" +
                        "½ teaspoon garam masala\n" +
                        "2 cloves garlic, crushed\n" +
                        "¾ cup water\n" + "1 quart oil for deep frying\n" +
                        "½ head cauliflower florets\n" +
                        "2 onions, sliced into rings", "Chickpea Flour\n" + "Coriander\n" + "Turmeric\n" + "Chili Powder\n" + "Garam Masala\n" + "Garlic\n" + "Cooking Oil\n" + "Cauliflower\n" + "Onions", " Step 1\n" +
                        "\n" +
                        "Sift the chickpea flour into a medium bowl. Mix in the coriander, salt, turmeric, chili powder, garam masala and garlic.\n" +
                        "Step 2\n" +
                        "\n" +
                        "Make a well in the center of the flower. Gradually pour the water into the well and mix to form a thick, smooth batter.\n" +
                        "Step 3\n" +
                        "\n" +
                        "Over medium high heat in a large, heavy saucepan, heat the oil to 375 degrees F (190 degrees C).\n" +
                        "Step 4\n" +
                        "\n" +
                        "Coat the cauliflower and onions in the batter and fry them in small batches until golden brown, about 4 to 5 minutes. Drain on paper towels before serving.\n", 6, 3, R.drawable.pakora));
                mDao.insert(new Recipe("Vegetable Lasagna", "Tender vegetables, a light tomato sauce, and lots of cheese make this the best vegetable lasagna recipe, ever. We really do not miss the meat. Add your favorite vegetables to this.", "14 lasagna noodles (2 extra for filling in holes)\n" +
                        "2 tablespoons extra-virgin olive oil\n" +
                        "1 cup (140 grams) chopped onion\n" +
                        "1 tablespoon minced garlic, (3 cloves)\n" +
                        "1/8 teaspoon crushed red pepper flakes, or more to taste\n" +
                        "2 medium zucchini, cut into 1/2-inch pieces\n" +
                        "2 medium yellow squash, cut into 1/2-inch pieces\n" +
                        "One (12-ounce) jar roasted red peppers, drained and cut into 1/2-inch pieces, 1 heaping cup\n" +
                        "1 (28-ounce) can crushed tomatoes\n" +
                        "Generous handful fresh basil leaves, chopped\n" +
                        "One (15-ounce) container ricotta cheese or cottage cheese\n" +
                        "2 large eggs\n" +
                        "2 ounces (60 grams) parmesan cheese, grated, about 1 cup\n" +
                        "8 ounces (230 grams) low-moisture mozzarella cheese, shredded\n" +
                        "Salt and fresh ground black pepper, to taste\n", "Lasagna Noodles\n" + "Olive Oil\n" + "Onion\n" + "Red Pepper Flakes\n" + "Zucchini\n" + "Yellow Squash\n" + "Roasted Red Peppers\n" +
                        "Crushed Tomatoes\n" +  "Basil Leaves\n" + "Ricotta Cheese\n" + "Eggs\n" + "Parmesan Cheese\n" + "Mozzarella Cheese\n" + "Salt\n" + "Pepper\n", "    Cook Noodles\n" +
                        "\n" +
                        "  1.   Bring a large pot of salted water to the boil then cook lasagna noodles according to package directions. (We add a couple teaspoons of olive oil to the water so the noodles do not stick together). Drain then lay flat on a sheet of aluminum foil.\n" +
                        "        Make Vegetable Sauce\n" +
                        "\n" +
                        "  1.   Heat the oven to 350 degrees Fahrenheit. Lightly oil a 13-inch by 9-inch baking dish or spray with non-stick cooking spray.\n" +
                        "\n" +
                        "  2.   Heat the olive oil in a wide skillet with sides over medium heat. Add onion and cook, stirring occasionally until translucent, about 5 minutes. Add the garlic, red pepper flakes, zucchini, squash, and a pinch of salt then cook, stirring occasionally until softened but still with some crunch, another 5 to 8 minutes.\n" +
                        "\n" +
                        "  3.   Stir in the roasted red peppers and crushed tomatoes. Bring to a low simmer and cook until the liquid has thickened and reduced by half, about 5 minutes. Add the basil and season to taste with additional salt and pepper.\n" +
                        "            Make Cheese Filling\n" +
                        "\n" +
                        "  1.   While the sauce cooks, stir the ricotta cheese, eggs, and a 1/2 teaspoon of salt in a medium bowl until blended.\n" +
                        "                Assemble Lasagna\n" +
                        "\n" +
                        "  1.   Spoon just enough vegetable mixture into the baking dish to lightly cover the bottom (about 1 cup). Arrange four noodles lengthwise and side by side to cover the bottom. (If the noodles are short on one end, you may need to cut an extra noodle and place into dish to cover where the other noodles have not).\n" +
                        "\n" +
                        "  2.   Spread about half of the ricotta cheese mixture over the noodles. Sprinkle with a third of the parmesan cheese and a third of the mozzarella cheese. Top with a third of the vegetable mixture.\n" +
                        "\n" +
                        "  3.   Add another layer of four noodles then repeat with remaining cheese and vegetables. Finish with a final layer of noodles, vegetables, parmesan cheese and mozzarella cheese.\n" +
                        "\n" +
                        "  4.   Cover loosely with aluminum foil and bake 20 minutes, uncover then bake 15 minutes until cheese is crusty around the edges. To make the cheese golden brown on top, slide under the broiler for 1 to 2 minutes. Let rest 10 to 15 minutes before serving.\n", 3, 2, R.drawable.vegetable_lasgana));
                mDao.insert(new Recipe("Vegetarian Spring Rolls", "Although you'll find these popular fried appetizers in many Asian cuisines, the Chinese version of spring rolls are usually filled with cabbage, maybe carrots or a few other vegetables. We made our rolls extra bright, green and springy by incorporating snow peas, minced green onions and cilantro. Shiitake mushrooms are an added umami bonus.", "For the Dipping Sauce:\n" +
                        "⅓ cup soy sauce\n" +
                        "1 tablespoon rice vinegar\n" +
                        "¼ teaspoon sesame oil\n" +
                        "2 teaspoons honey\n" +
                        "2 teaspoons chili oil\n" +
                        "½ teaspoon finely grated ginger\n" +
                        "For the Spring Rolls:\n" +
                        "1 tablespoon canola oil\n" +
                        "1 teaspoon minced garlic\n" +
                        "2 teaspoons finely grated ginger\n" +
                        "3 green onions, minced\n" +
                        "12 shiitake mushroom caps, julienned\n" +
                        "½ small green cabbage, shredded (about 2 cups)\n" +
                        "1 cup bamboo shoots, drained and julienned\n" +
                        "Kosher salt, to taste\n" +
                        "1 cup julienned snow peas\n" +
                        "1½ tablespoons soy sauce\n" +
                        "1 teaspoon sesame oil\n" +
                        "2 tablespoons finely chopped cilantro\n" +
                        "Spring roll wrappers, defrosted\n" +
                        "1 egg white, lightly beaten\n" +
                        "Canola oil, for frying", "Soy Sauce\n" + "Rice Vinegar\n" + "Sesame Oil\n" + "Honey\n" + "Chili Oil\n" + "Ginger\n" +
                        "Canola Oil\n" + "Garlic\n" + "Onions\n" + "Shiitake Mushrooms\n" + "Green Cabbage\n" + "Bamboo Shoots\n" + "Kosher Salt\n" + "Snow Peas\n" + "Cilantro\n" + "Spring Roll Wrappers\n" + "Eggs", "1. Make the dipping sauce: In a small mixing bowl, combine all the ingredients and set aside until ready to serve.\n" +
                        "\n" +
                        "2. Make the spring rolls: In a large skillet or wok over medium-high heat, add the oil. When the oil begins to shimmer, add the garlic, ginger and green onions, and cook, stirring often, until fragrant, 30 to 40 seconds. Add the mushrooms, cabbage and bamboo shoots. Season with salt. Sauté, shaking the pan often until the vegetables start to soften, 3 minutes. Increase the heat to high, then add the snow peas, soy sauce and sesame oil, and season with salt. Sauté for 1 more minute until the vegetables are crisp-tender. Stir in the cilantro.\n" + "3. Drain the filling to remove any excess moisture, then spread the vegetables on a rimmed baking sheet and let them cool to room temperature for about 10 minutes.\n" +
                        "Begin assembling the spring rolls (see the story for a slideshow): Working with one wrapper, place the wrapper so that a corner is facing you, then spoon 2 tablespoons of the cooled filling onto the bottom third of the wrapper that is closest to you.\n" + "4. Lift the bottom of the wrapper and fold over the filling, flattening the wrapper around the filling so that no air bubbles form. Tuck it back. Begin folding the wrapper away from you. Fold in the sides to form an envelope-like shape. Continue to cinch and fold. Brush the top inside corner lightly with the egg white. The spring roll should measure 1-by-3½ inches. Place the spring roll on a parchment-lined sheet tray; cover with a damp paper towel. Repeat with the remaining wrappers and filling. Make ahead: You can freeze the spring rolls in a single layer on a parchment-lined baking sheet, making sure they do not touch. Once frozen, place the spring rolls in a plastic bag and store them for up to 1 month. Add 1 additional minute to the frying time for frozen spring rolls.\n" +
                        "5. Fill a wok or a medium heavy-bottomed pot with 2 inches of oil and heat to 350°. Working in batches of 4, fry the spring rolls, turning occasionally, until golden brown and crisp, 1½ to 2 minutes. Remove using a slotted spoon and transfer to a wire rack to cool.\n" +
                        "\n" +
                        "6. Serve the spring rolls with the dipping sauce.",7, 1, R.drawable.vegetarian_spring_rolls));
                mDao.insert(new Recipe("Ultimate Veggie Pizza", "Make the BEST veggie pizza at home! This mouthwatering vegetarian pizza recipe features fresh cherry tomatoes, bell peppers, artichoke, spinach and more. Recipe yields two 11-inch pizzas or 1 large (about 6 to 8 servings).", "1 batch easy whole wheat pizza dough or 1 pound store-bought pizza dough\n" +
                        "1 cup pizza sauce or marinara\n" +
                        "2 cups baby spinach\n" +
                        "2 to 3 cups (8 to 12 ounces) shredded low-moisture part-skim mozzarella cheese\n" +
                        "½ cup jarred or canned artichoke, cut into 1” pieces\n" +
                        "½ cup fresh red or orange bell pepper, cut into narrow 2″ strips\n" +
                        "½ cup red onion, cut into thin wedges\n" +
                        "½ cup halved cherry tomatoes\n" +
                        "½ cup pitted Kalamata olives, halved lengthwise\n" +
                        "½ cup sliced almonds (optional)\n" +
                        "Optional garnishes: Fresh basil (small leaves or torn), red pepper flakes and/or finely grated Parmesan cheese", "Whole Wheat Pizza Dough\n" +
                        "Pizza Sauce\n" + "Baby Spinach\n" + "Part-Skim Mozzarella\n" + "Artichoke\n" + "Bell Pepper\n" + "Red Onion\n" + "Cherry Tomatoes\n" + "Olives\n" + "Almonds\n" + "Basil", "\n" +
                        "  1.   Preheat the oven to 500 degrees Fahrenheit with a rack in the upper third of the oven. If you’re using a baking stone or baking steel, place it on the upper rack. Prepare dough through step 5.\n" +
                        "  2.   Spread pizza sauce evenly over the two pizzas, leaving about 1 inch bare around the edges. Evenly distribute the spinach on top of the sauce, followed by the cheese (use all three cups if you want a cheesy pizza as shown here).\n" +
                        "  3.   Top the pizzas with artichoke, bell pepper, red onion, tomatoes, olives and almonds (if using).\n" +
                        "  4.   Bake pizzas individually on the top rack until the crust is golden and the cheese is golden and bubbly, about 10 to 12 minutes (or significantly less, if you’re using a baking stone/steel—keep an eye on it).\n" +
                        "  5.   Transfer pizzas to a cutting board and sprinkle with with fresh basil, red pepper flakes and Parmesan, if using. Slice and serve! Leftover pizza keeps well in the refrigerator for 4 days, or for several months in the freezer.\n", 3, 2, R.drawable.ultimate_vegetarian_pizza));
                mDao.insert(new Recipe("Chili and Grilled Corn", "Enjoy this winter warmer! This dish is packed with fresh flavour and vegetarian protein.", "For the chilli:\n" +
                        "2 x 400g cans black beans, drained and rinsed (we love Chantal Organics)\n" +
                        "1 x 400g can chickpeas, drained and rinsed (we love Chantal Organics)\n" +
                        "1 x 400g can chopped tomatoes (we love Chantal Organics)\n" +
                        "1 tsp smoked paprika\n" +
                        "1 tsp cumin powder\n" +
                        "1 tsp garlic powder\n" +
                        "1 tsp salt and pepper, to taste\n" +
                        "1 onion, diced\n" +
                        "2 cloves garlic, crushed and finely chopped\n" +
                        "2 tbsp avocado oil with chilli (we love Olivado)\n" +
                        "-\n" +
                        "For the grilled corn:\n" +
                        "1 tbsp avocado oil with chilli (we love Olivado)\n" +
                        "1 cup corn kernels, fresh or canned\n" +
                        "1 clove garlic, crushed and finely chopped\n" +
                        "1/2 tsp smoked paprika", "Black Beans\n" + "ChickPeas\n" + "Tomatoes\n" + "Smoked Paprika\n" + "Cumin Powder\n" + "Garlic Powder\n" + "Salt\n" + "Pepper\n" + "Onion\n" + "Garlic\n" +
                        "Avocado Oil\n" + "Corn\n", "Make the chilli. Fry onions in a deep frying pan with a drizzle of oil until golden for about 5 minutes. Add garlic, smoked paprika, cumin and garlic powder and continue to cook for 2 minutes. Add the tomatoes, black beans and chickpeas. Season with salt and pepper. Simmer for 10 minutes, stirring every couple of minutes.\n" +
                        "Grill the corn: Drain, rinse and pat dry the corn if using canned. Heat a good drizzle of avocado oil in a frying pan over medium high heat. Add the corn, garlic and smoked paprika. Fry for 5 minutes or until corn kernels are starting to char.", 8, 0, R.drawable.chili_and_grilled_corn));
            }
            return null;
        }

    }

}
