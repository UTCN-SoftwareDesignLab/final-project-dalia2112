package restaurant.model.builder;


import restaurant.model.Client;
import restaurant.model.Dish;
import restaurant.model.Orderr;

import java.util.List;

public class OrderrBuilder {
    private Orderr orderr;

    public OrderrBuilder() {
        orderr = new Orderr();
    }

    public OrderrBuilder setId(long id) {
        orderr.setId(id);
        return this;
    }

    public OrderrBuilder setDishes(List<Dish> dishes) {
        orderr.setDishes(dishes);
        return this;
    }

    public OrderrBuilder setClient(Client client) {
        orderr.setClient(client);
        return this;
    }

    public OrderrBuilder setReceit(float receit) {
        orderr.setReceit(receit);
        return this;
    }

    public OrderrBuilder setOnline(boolean online) {
        orderr.setOnline(online);
        return this;
    }

    public Orderr build() {
        return orderr;
    }


}
