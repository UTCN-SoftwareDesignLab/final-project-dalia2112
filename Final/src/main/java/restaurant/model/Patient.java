package restaurant.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private long id_card_nr;
    private long pers_num_code;
    private LocalDate birthday;
    private String address;


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

    public long getId_card_nr() {
        return id_card_nr;
    }

    public void setId_card_nr(long id_card_nr) {
        this.id_card_nr = id_card_nr;
    }

    public long getPers_num_code() {
        return pers_num_code;
    }

    public void setPers_num_code(long pers_num_code) {
        this.pers_num_code = pers_num_code;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + " " + id_card_nr + " " + pers_num_code + " " + birthday + " " + address;
    }
}
