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
import restaurant.service.employee.EmployeeService;
import restaurant.service.employee.EmployeeServiceImpl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class EmployeeServiceImplTest {

    @Mock
    OrderrRepository orderrRepository;

    @InjectMocks
    EmployeeService employeeService = new EmployeeServiceImpl(orderrRepository);


    Orderr testOrderr;
    User testclient;

    @Before
    public void init() {
        testclient = new UserBuilder()
                .setName("testClient@gm.com")
                .setPassword("dali123*")
                .setRole("client")
                .build();
        Map dishList = new HashMap<>();
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
                .setProcessed(true)
                .setDistance(0.2)
                .setCar(1)
                .build();
    }

    @Test
    public void setCarToOrder() {
        Assert.assertTrue(employeeService.setCarToOrder(1, testOrderr).getResult());
    }

    @Test
    public void getWaitingTime() {
        List<Orderr>orderrs=new ArrayList<>();
        orderrs.add(testOrderr);
        when(orderrRepository.findByCar(1)).thenReturn(orderrs);
        Assert.assertEquals("0:20",employeeService.calcWaitingTime(testOrderr));
    }
}
