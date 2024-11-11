package com.phms.service;

import com.phms.pojo.Standard;

public interface StandardService {
    void update(Standard po);

    void add(Standard po);

    void deleteById(Long id);

    Object getAllByLimit(Standard po);
}
