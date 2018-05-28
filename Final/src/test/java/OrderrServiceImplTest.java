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
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.builder.DishBuilder;
import restaurant.model.builder.IngredientBuilder;
import restaurant.model.builder.OrderrBuilder;
import restaurant.model.builder.UserBuilder;
import restaurant.repository.OrderrRepository;
import restaurant.repository.UserRepository;
import restaurant.service.orderr.OrderrService;
import restaurant.service.orderr.OrderrServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration
public class OrderrServiceImplTest {


    @Mock
    OrderrRepository orderrRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    OrderrService orderrService = new OrderrServiceImpl(orderrRepository);


    Orderr testOrderr;
    User testclient;
    Map<Dish, Integer> dishList;

    @Before
    public void init() {
        testclient = new UserBuilder()
                .setName("testClient@gm.com")
                .setPassword("dali123*")
                .setRole("client")
                .build();
        dishList = new HashMap<>();
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient i = new IngredientBuilder()
                .setName("onion")
                .setVegan(true)
                .build();
        ingredients.add(i);
        Dish dish = new DishBuilder()
                .setIngredients(ingredients)
                .setMoney(10)
                .setName("Onion rings")
                .build();
        dishList.put(dish, 2);
        testOrderr = new OrderrBuilder()
                .setClient(testclient)
                .setDishes(dishList)
                .setReceit(20)
                .build();
    }

    @Test
    public void addOrderr() {
        Assert.assertTrue(orderrService.addOrderr(dishList, testclient).getResult());
    }

    @Test
    public void setRating() {
        long id = 1;
        List<Orderr> orderrs = new ArrayList<>();
        testOrderr.setProcessed(true);
        orderrs.add(testOrderr);
        when(userRepository.findById(id)).thenReturn(testclient);
        when(orderrRepository.findByClientId(id)).thenReturn(orderrs);
        Assert.assertTrue(orderrService.setRating("star5", id).getResult());
    }
}
