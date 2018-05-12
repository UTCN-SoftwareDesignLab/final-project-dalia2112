package restaurant.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.builder.DishBuilder;
import restaurant.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    //    @Override
//    public Dish findByName(String name){
//        return dishRepository.findByName(name);
//    }
    @Override
    public void addDish(String name, Ingredient ingredient, float price, boolean availableOnline) {

        Dish dish = dishRepository.findByName(name);
        List<Ingredient> ingredients;
        if (dish == null) {
            ingredients = new ArrayList<>();
            ingredients.add(ingredient);
            System.out.println("era null");
            Dish dish1 = new DishBuilder()
                    .setName(name)
                    .setIngredients(ingredients)
                    .setMoney(price)
                    .setOnline(availableOnline)
                    .build();
            dishRepository.save(dish1);
        } else {
            ingredients = dish.getIngredients();
            ingredients.add(ingredient);
            dish.setIngredients(ingredients);
            System.out.println("NU era null " + ingredients.get(0).getName());
            dishRepository.save(dish);
        }

    }

    public void updateDish(long id,String name,float price,boolean online){
        Dish dish=dishRepository.findById(id);
        if(dish!=null){
            dish.setName(name);
            dish.setPrice(price);
            dish.setAvailableOnline(online);
            dishRepository.save(dish);
        }
    }

    public void deleteDish(long id){
        dishRepository.deleteById(id);
    }
}
