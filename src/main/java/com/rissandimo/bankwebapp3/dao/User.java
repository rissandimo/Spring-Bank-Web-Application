package com.rissandimo.bankwebapp3.dao;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Data
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min=3)
    private String firstName;

    @NotNull
    @Size(min=1)
    private String lastName;
    private String username;
    private String password;
    private String roles;
    private int active;

    private double currentBalance;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList = new ArrayList<>();

    public User(String firstName, String lastName, String username, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = "USER";
        this.active = 1;
        this.currentBalance = 0.0;
    }

    public void addTransaction(Transaction transaction)
    {
        this.transactionList.add(transaction);
    }

    public double getCurrentBalance()
    {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public boolean getActive()
    {
        return active == 1;
    }

    public User()
    {
    }

    public String getFullName()
    {
       return StringUtils.capitalize(firstName) + " " + StringUtils.capitalize(lastName);
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", roles='" + roles + '\'' + ", active=" + active + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword());
    }
}
