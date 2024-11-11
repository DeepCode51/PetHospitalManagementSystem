package com.phms.service.impl;

import com.phms.mapper.AppointmentMapper;
import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.Appointment;
import com.phms.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    AppointmentMapper appointmentMapper;
    @Override
    public Object getAllByLimit(Appointment appointment) {
        int size = 0;
        // 计算分页
        Integer begin = (appointment.getPage() - 1) * appointment.getLimit();
        appointment.setPage(begin);

        List<Appointment> rows = new ArrayList<>();
        try {
            rows = appointmentMapper.getAllByLimit(appointment);
            size = appointmentMapper.countAllByLimit(appointment);
        } catch (Exception e) {
            logger.error("根据条件查询异常", e);
        }
        MMGridPageVoBean<Appointment> vo = new MMGridPageVoBean<>();
        vo.setTotal(size);
        vo.setRows(rows);

        return vo;
    }

    @Override
    public void deleteById(Long id) {
        appointmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Appointment categorization) {
        appointmentMapper.insert(categorization);
    }

    @Override
    public void update(Appointment appointment) {
        appointmentMapper.updateByPrimaryKeySelective(appointment);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getFreeTimeById(Long docId, String s) {
        return appointmentMapper.getFreeTimeById(docId, s);
    }
}
