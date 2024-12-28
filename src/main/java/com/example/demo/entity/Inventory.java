package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    private Long invId;
    private String invName;
    private String invSpec;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getInvSpec() {
        return invSpec;
    }

    public void setInvSpec(String invSpec) {
        this.invSpec = invSpec;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}