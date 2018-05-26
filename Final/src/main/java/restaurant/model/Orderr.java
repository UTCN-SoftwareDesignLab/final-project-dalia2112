package restaurant.model;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ElementCollection
    private Map<Dish, Integer> dishes;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private float receit;
    private boolean processed;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employees;
    private int car;
    private double distance;
    private int waitingTime;


    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
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


    public float getReceit() {
        return receit;
    }

    public void setReceit(float receit) {
        this.receit = receit;
    }


    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getEmployees() {
        return employees;
    }

    public void setEmployees(User employees) {
        this.employees = employees;
    }

    public String getClientName() {
        return client.getName();
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
