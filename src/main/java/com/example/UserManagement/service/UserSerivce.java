package com.example.UserManagement.service;

import com.example.UserManagement.model.User;
import com.example.UserManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserSerivce {

    private final UserRepository userrepo;

    public List<User> getAll() {
        return userrepo.findAll();
    }

    public User getById(UUID id) {
        return userrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User register(User user) {
        if (userrepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        return userrepo.save(user);
    }

    public User login(String email, String password) {
        return userrepo.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    public User update(UUID id, User updated) {
        User existing = getById(id);
        existing.setFullname(updated.getFullname());
        existing.setEmail(updated.getEmail());
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(updated.getPassword());
        }
        return userrepo.save(existing);
    }

    public void delete(UUID id) {
        userrepo.deleteById(id);
    }
}
