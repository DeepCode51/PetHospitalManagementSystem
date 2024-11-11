package com.phms.service;

import com.phms.pojo.Pet;

public interface PetService {
    void update(Pet po);

    void add(Pet po);

    void deleteById(Long id);

    Object getAllByLimit(Pet po);

    Pet selectByPrimaryKey(Long petId);
}
