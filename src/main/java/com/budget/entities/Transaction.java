package com.budget.entities;

import java.util.Currency;
import java.util.Date;

public class Transaction {
    private int id;
    private Date date;
    private String name;
    private String description;
    private Category category;
    private Currency currency;
}
