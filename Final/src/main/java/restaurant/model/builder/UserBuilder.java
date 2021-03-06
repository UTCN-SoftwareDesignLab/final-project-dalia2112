package restaurant.model.builder;


import restaurant.model.Orderr;
import restaurant.model.User;

import java.util.List;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder setName(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(String role) {
        user.setRole(role);
        return this;
    }

    public UserBuilder setOrderrs(List<Orderr> orderrs) {
        user.setOrderrs(orderrs);
        return this;
    }

    public User build() {
        return user;
    }
}
