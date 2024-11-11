package com.phms.service;

import com.phms.pojo.PetDaily;

public interface PetDailyService {
    void update(PetDaily po);

    void add(PetDaily po);

    void deleteById(Long id);

    Object getAllByLimit(PetDaily po);
}
