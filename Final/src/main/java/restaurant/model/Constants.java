package restaurant.model;

import restaurant.model.builder.IngredientBuilder;

import java.util.ArrayList;
import java.util.List;

import static restaurant.model.Constants.Ingredients.*;

public class Constants {
    public static class Ingredients {

        //VEGAN
        public static final String BREAD = "bread";
        public static final String FLOUR = "flour";
        public static final String SALT = "salt";
        public static final String HONEY = "honey";
        public static final String SUGAR = "sugar";
        public static final String POTATO = "potato";
        public static final String PEPPER = "pepper";
        public static final String CHILLY = "chilly";
        public static final String ONION = "onion";
        public static final String GARLIC = "garlic";
        public static final String SALAD = "salad";
        public static final String TOMATOE = "tomatoe";
        public static final String COCOA = "cocoa";
        public static final String VANILLA = "vanilla";
        public static final String FRUITS = "fruits";
        public static final String DOUGH = "dough";


        public static final String MILK = "milk";
        public static final String CHEESE = "cheese";
        public static final String HAM = "ham";
        public static final String CHICKEN = "chicken";
        public static final String BEAF = "beaf";
        public static final String PORK = "pork";


        public static final String[] VEGAN = new String[]{BREAD, FLOUR, SALT,
                HONEY, SUGAR, POTATO, PEPPER, CHILLY, ONION, GARLIC, SALAD, TOMATOE, COCOA, VANILLA, FRUITS, DOUGH};

        public static final String[] NONVEGAN = new String[]{MILK, CHEESE, HAM, CHICKEN, BEAF,
                PORK};

        public static final String[] ALL = new String[]{BREAD, FLOUR, SALT,
                HONEY, SUGAR, POTATO, PEPPER, CHILLY, ONION, GARLIC, SALAD, TOMATOE, COCOA, VANILLA, FRUITS, DOUGH,
                MILK, CHEESE, HAM, CHICKEN, BEAF,
                PORK
        };
    }

    public static class Dishes {
        public static final String[] NAMES=new String[]{"Pizza","Apple Pie","Pork Chops"};

        private static final String[] INGREDIENTS1=new String[]{DOUGH,ONION,TOMATOE,HAM,CHEESE};
        private static final String[] INGREDIENTS2=new String[]{DOUGH,VANILLA,FRUITS};
        private static final String[] INGREDIENTS3=new String[]{PORK,POTATO,CHILLY};
        private static final String[][] ALLINGREDIENTS={INGREDIENTS1,INGREDIENTS2,INGREDIENTS3};
        public static final List<List<Ingredient>> getIngredients(){
            List<List<Ingredient>> list=new ArrayList<>();
            for(String[] ingr:ALLINGREDIENTS){
                List<Ingredient> ingredients=new ArrayList<>();
                for(String ingredient:ingr){
                    Ingredient ingredient1=new IngredientBuilder()
                            .setName(ingredient)
                            .setVegan(false)
                            .build();
                    ingredients.add(ingredient1);
                }
                list.add(ingredients);
            }
            return list;
        }


    }
}
