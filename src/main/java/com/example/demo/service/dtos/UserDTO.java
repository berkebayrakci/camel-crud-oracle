package com.example.demo.service.dtos;

import java.util.List;

public class UserDTO {
    private Long userId;
    private String userName;
    private String userStatus;
    private String userAddr;
    private List<InventoryDTO> inventories;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public List<InventoryDTO> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryDTO> inventories) {
        this.inventories = inventories;
    }
}