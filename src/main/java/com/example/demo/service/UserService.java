package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.service.dtos.InventoryDTO;
import com.example.demo.utils.exceptions.UserAlreadyExistsException;
import com.example.demo.utils.exceptions.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            throw new UserAlreadyExistsException("User with id " + user.getUserId() + " already exists.");
        }
        return userRepository.save(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        userRepository.deleteById(id);
    }
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUserName(userDTO.getUserName());
                    user.setUserStatus(userDTO.getUserStatus());
                    user.setUserAddr(userDTO.getUserAddr());
                    userRepository.save(user);
                    return convertToUserDTO(user);
                })
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserStatus(user.getUserStatus());
        userDTO.setUserAddr(user.getUserAddr());
        userDTO.setInventories(user.getInventories().stream()
                .map(inventory -> {
                    InventoryDTO inventoryDTO = new InventoryDTO();
                    inventoryDTO.setInvId(inventory.getInvId());
                    inventoryDTO.setInvName(inventory.getInvName());
                    inventoryDTO.setInvSpec(inventory.getInvSpec());
                    inventoryDTO.setUserId(inventory.getUser().getUserId());
                    return inventoryDTO;
                })
                .collect(Collectors.toList()));
        return userDTO;
    }
}