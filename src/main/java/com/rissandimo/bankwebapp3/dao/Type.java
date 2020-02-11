package com.rissandimo.bankwebapp3.dao;
public enum Type
{
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL"),
    PURCHASE("PURCHASE");


    Type(String type)
    {
        this.transactionType = type;
    }

    public String transactionType;

    @Override
    public String toString()
    {
        return transactionType;
    }

    public void setTransactionType(String type)
    {
        this.transactionType = type;
    }
}
