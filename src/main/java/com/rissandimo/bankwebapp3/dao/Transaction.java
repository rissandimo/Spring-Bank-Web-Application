package com.rissandimo.bankwebapp3.dao;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private double balance;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date transactionDate = new Date();
    private String description;

    @ManyToOne
    private User user;

    private Type type;

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Transaction()
    {
    }

    public Transaction(double amount, String description, Type type)
    {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public void setBalance(double amount, Type type) {
        switch (type) {
            case DEPOSIT: {
                user.setCurrentBalance(user.getCurrentBalance() + amount);
                break;
            }
            case WITHDRAWAL: {
                user.setCurrentBalance(user.getCurrentBalance() - this.getAmount());
                break;
            }
            case PURCHASE: {
                user.setCurrentBalance(user.getCurrentBalance() - this.getAmount());
                break;
            }

        }
    }
}


