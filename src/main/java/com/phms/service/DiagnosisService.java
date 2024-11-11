package com.phms.service;

import com.phms.pojo.Diagnosis;

public interface DiagnosisService {
    void update(Diagnosis diagnosis);

    void add(Diagnosis diagnosis);

    void deleteById(Long id);

    Object getAllByLimit(Diagnosis diagnosis);
}
