package restaurant.model.builder;


import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;

import java.util.Map;

public class OrderrBuilder {
    private Orderr orderr;

    public OrderrBuilder() {
        orderr = new Orderr();
    }

    public OrderrBuilder setId(long id) {
        orderr.setId(id);
        return this;
    }

    public OrderrBuilder setDishes(Map<Dish, Integer> dishes) {
        orderr.setDishes(dishes);
        return this;
    }

    public OrderrBuilder setClient(User client) {
        orderr.setClient(client);
        return this;
    }

    public OrderrBuilder setReceit(float receit) {
        orderr.setReceit(receit);
        return this;
    }

//    public OrderrBuilder setAddress(String address) {
//        orderr.setAddress(address);
//        return this;
//    }
//
//    public OrderrBuilder setCity(String city) {
//        orderr.setCity(city);
//        return this;
//    }
//
//    public OrderrBuilder setState(String state) {
//        orderr.setState(state);
//        return this;
//    }
//
//    public OrderrBuilder setZip(int zip) {
//        orderr.setZip(zip);
//        return this;
//    }

    public OrderrBuilder setProcessed(boolean processed) {
        orderr.setProcessed(processed);
        return this;
    }

    public OrderrBuilder setRating(int rating) {
        orderr.setRating(rating);
        return this;
    }
    public OrderrBuilder setCar(int car) {
        orderr.setCar(car);
        return this;
    }


    public Orderr build() {
        return orderr;
    }


}
