package restaurant.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float sum;
    private int expMonth;
    private int expYear;
    private int cVV;  //nr de pe spatele cardului

//    private LocalDate issueDate;
//    private LocalDate expiryDate;
    private String accountNumber;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;
    private boolean credit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

//    public LocalDate getIssueDate() {
//        return issueDate;
//    }
//
//    public void setIssueDate(LocalDate issueDate) {
//        this.issueDate = issueDate;
//    }
//
//    public LocalDate getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(LocalDate expiryDate) {
//        this.expiryDate = expiryDate;
//    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User user) {
        this.client = user;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public int getcVV() {
        return cVV;
    }

    public void setcVV(int cVV) {
        this.cVV = cVV;
    }
}
