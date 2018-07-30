package com.budget.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    private int id;
    private String name;
    private String description;
    private boolean credit;
    private BigDecimal balance;
    private BigDecimal creditLimit;

    public Account(int id, String name, String description, BigDecimal balance, boolean credit, BigDecimal creditLimit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.balance = balance;
        this.credit = credit;
        this.creditLimit = credit ? creditLimit : BigDecimal.valueOf(0.0);
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCredit() {
        return credit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
