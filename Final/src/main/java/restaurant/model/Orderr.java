package restaurant.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "orderr_dishes",
            joinColumns = @JoinColumn(name = "orderr_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id")
    )
    private List<Dish> dishes;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private float receit;
    private boolean online;

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public List<Dish> getDishes() {
//        return dishes;
//    }
//
//    public void setDishes(List<Dish> dishes) {
//        this.dishes = dishes;
//    }

    public float getReceit() {
        return receit;
    }

    public void setReceit(float receit) {
        this.receit = receit;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
