package com.example.UserManagement.service;

import com.example.UserManagement.model.ELocationType;
import com.example.UserManagement.model.Location;
import com.example.UserManagement.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepo;

    public List<Location> getAll() { return locationRepo.findAll(); }

    public List<Location> getByType(ELocationType type) { return locationRepo.findByType(type); }

    public Location getById(Long id) {
        return locationRepo.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
    }

    public Location create(Location location) {
        if (locationRepo.existsByCode(location.getCode())) {
            throw new RuntimeException("Location with code " + location.getCode() + " already exists");
        }
        resolveParent(location);
        return locationRepo.save(location);
    }

    public Location update(Long id, Location updated) {
        Location existing = getById(id);
        existing.setName(updated.getName());
        existing.setCode(updated.getCode());
        existing.setType(updated.getType());
        resolveParent(updated);
        existing.setParent(updated.getParent());
        return locationRepo.save(existing);
    }

    public void delete(Long id) { locationRepo.deleteById(id); }

    // If parent has only a code set (from frontend), resolve it to a full entity
    private void resolveParent(Location location) {
        if (location.getParent() != null && location.getParent().getCode() != null && location.getParent().getId() == 0) {
            Location parent = locationRepo.findByCode(location.getParent().getCode())
                    .orElseThrow(() -> new RuntimeException("Parent location not found: " + location.getParent().getCode()));
            location.setParent(parent);
        }
    }
}
