import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import restaurant.model.User;
import restaurant.model.builder.UserBuilder;
import restaurant.repository.UserRepository;
import restaurant.service.user.UserService;
import restaurant.service.user.UserServiceImpl;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class UserServiceImplTest {


    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService = new UserServiceImpl(userRepository);


    User testUser;

    @Before
    public void init() {
        testUser = new UserBuilder()
                .setName("dal@gm.com")
                .setPassword("dali123*")
                .setRole("admin")
                .build();
    }

    @Test
    public void addUser() {
        Assert.assertTrue(userService.registerUser(testUser.getName(), testUser.getPassword(), testUser.getRole()).getResult());
    }

    @Test
    public void updateUser() {
        long id = 1;
        when(userRepository.findById(id)).thenReturn(testUser);
        Assert.assertTrue(userService.updateUser(id, "dali@gm.com", testUser.getPassword(), testUser.getRole()).getResult());
    }

    @Test
    public void deleteUser() {
        long id = 1;
        when(userRepository.findById(id)).thenReturn(testUser);
        Assert.assertTrue(userService.deleteUser(id).getResult());
    }

    @Test
    public void findById() {
        long id = 1;
        when(userRepository.findById(id)).thenReturn(testUser);
        testUser.setId(id);
        Assert.assertEquals(id, userService.findById(id).getId());
    }

}