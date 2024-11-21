package com.javatunes.domain;


import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Service")
public class ServiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name, description;
    private int duration;

    @Column(name = "cost")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ServiceCategory category;

    public ServiceItem(){}

    public ServiceItem(Long id) {
        this.id = id;
    }

    public ServiceItem(String name, String description,
                       int duration, BigDecimal price,
                       ServiceCategory category) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.category = category;
    }

    public ServiceItem(Long id, String name,
                       String description,
                       Integer duration, BigDecimal price,
                       ServiceCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ServiceItem [id=" + id + ", name=" + name + ", description="
                + description + ", duration (min)=" + duration + ", price=" + price
                + ", serviceCategory=" + category + "]";

    }
}


