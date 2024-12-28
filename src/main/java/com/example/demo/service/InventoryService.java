package com.example.demo.service;

import com.example.demo.entity.Inventory;
import com.example.demo.entity.User;
import com.example.demo.utils.exceptions.InventoryAlreadyExistsException;
import com.example.demo.utils.exceptions.InventoryNotFoundException;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dtos.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Inventory addInventory(InventoryDTO inventoryDTO) {
        if (inventoryRepository.existsById(inventoryDTO.getInvId())) {
            throw new InventoryAlreadyExistsException("Inventory with id " + inventoryDTO.getInvId() + " already exists.");
        }
        Inventory inventory = new Inventory();
        inventory.setInvId(inventoryDTO.getInvId());
        inventory.setInvName(inventoryDTO.getInvName());
        inventory.setInvSpec(inventoryDTO.getInvSpec());
        User user = userRepository.findById(inventoryDTO.getUserId())
                .orElseThrow(() -> new InventoryNotFoundException("User with id " + inventoryDTO.getUserId() + " not found."));
        inventory.setUser(user);

        return inventoryRepository.save(inventory);
    }

    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> {
                    InventoryDTO inventoryDTO = new InventoryDTO();
                    inventoryDTO.setInvId(inventory.getInvId());
                    inventoryDTO.setInvName(inventory.getInvName());
                    inventoryDTO.setInvSpec(inventory.getInvSpec());
                    inventoryDTO.setUserId(inventory.getUser().getUserId());
                    return inventoryDTO;
                })
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    InventoryDTO inventoryDTO = new InventoryDTO();
                    inventoryDTO.setInvId(inventory.getInvId());
                    inventoryDTO.setInvName(inventory.getInvName());
                    inventoryDTO.setInvSpec(inventory.getInvSpec());
                    inventoryDTO.setUserId(inventory.getUser().getUserId());
                    return inventoryDTO;
                })
                .orElseThrow(() -> new InventoryNotFoundException("Inventory with id " + id + " not found."));
    }

    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new InventoryNotFoundException("Inventory with id " + id + " not found.");
        }
        inventoryRepository.deleteById(id);
    }

    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventory.setInvId(inventoryDTO.getInvId());
                    inventory.setInvName(inventoryDTO.getInvName());
                    inventory.setInvSpec(inventoryDTO.getInvSpec());
                    User user = userRepository.findById(inventoryDTO.getUserId())
                            .orElseThrow(() -> new InventoryNotFoundException("User with id " + inventoryDTO.getUserId() + " not found."));
                    inventory.setUser(user);
                    inventoryRepository.save(inventory);
                    return inventoryDTO;
                })
                .orElseThrow(() -> new InventoryNotFoundException("Inventory with id " + id + " not found."));
    }
}