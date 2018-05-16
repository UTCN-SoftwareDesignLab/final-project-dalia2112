package restaurant.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String password;
    private String role;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "employee_orderrs",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "orderr_id", referencedColumnName = "id")
    )
    private List<Orderr> orderrs;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Orderr> getOrderrs() {
        return orderrs;
    }

    public void setOrderrs(List<Orderr> orderrs) {
        this.orderrs = orderrs;
    }
}
