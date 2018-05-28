import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.builder.DishBuilder;
import restaurant.model.builder.IngredientBuilder;
import restaurant.repository.DishRepository;
import restaurant.service.dish.DishService;
import restaurant.service.dish.DishServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class DishServiceImplTest {

    @Mock
    DishRepository dishRepository;

    @InjectMocks
    DishService dishService = new DishServiceImpl(dishRepository);


    Dish testDish;

    @Before
    public void init() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient i = new IngredientBuilder()
                .setName("onion")
                .setVegan(true)
                .build();
        ingredients.add(i);
        testDish = new DishBuilder()
                .setIngredients(ingredients)
                .setMoney(10)
                .setName("Onion rings")
                .build();
    }

    @Test
    public void addDish() {
        Ingredient i = new IngredientBuilder()
                .setName("onion")
                .setVegan(true)
                .build();
        Assert.assertTrue(dishService.addDish("onion rings", i, 10).getResult());
    }

    @Test
    public void deleteDish() {
        long id = 1;
        when(dishRepository.findById(id)).thenReturn(testDish);
        Assert.assertTrue(dishService.deleteDish(id).getResult());
    }

}
