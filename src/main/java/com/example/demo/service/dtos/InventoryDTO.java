package com.example.demo.service.dtos;

public class InventoryDTO {
    private Long invId;
    private String invName;
    private String invSpec;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}