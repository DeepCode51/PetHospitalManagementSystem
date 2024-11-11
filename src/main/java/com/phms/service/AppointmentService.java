package com.phms.service;

import com.phms.pojo.Appointment;

import java.util.List;
import java.util.Map;

public interface AppointmentService {
    Object getAllByLimit(Appointment appointment);

    void deleteById(Long id);

    void add(Appointment appointment);

    void update(Appointment appointment);

    Appointment getById(Long id);

    List<Map<String, Object>> getFreeTimeById(Long docId, String s);
}
