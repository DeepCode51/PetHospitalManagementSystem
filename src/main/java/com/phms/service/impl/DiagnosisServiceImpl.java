package com.phms.service.impl;

import com.phms.mapper.DiagnosisMapper;
import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.Diagnosis;
import com.phms.pojo.Pet;
import com.phms.pojo.User;
import com.phms.service.DiagnosisService;
import com.phms.service.PetService;
import com.phms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    DiagnosisMapper diagnosisMapper;
    @Resource
    UserService userService;
    @Resource
    PetService petService;


    @Override
    public Object getAllByLimit(Diagnosis diagnosis) {
        int size = 0;
        // 计算分页
        Integer begin = (diagnosis.getPage() - 1) * diagnosis.getLimit();
        diagnosis.setPage(begin);

        List<Diagnosis> rows = new ArrayList<>();
        List<Diagnosis> resule = new ArrayList<>();
        try {
            rows = diagnosisMapper.getAllByLimit(diagnosis);
            size = diagnosisMapper.countAllByLimit(diagnosis);
            for (Diagnosis d: rows){
                if (d.getPetId()!=null){
                    Pet my = petService.selectByPrimaryKey(d.getPetId());
                    if (my !=null){
                        d.setName(my.getName());
                    }

                }
                if (d.getUserId()!=null){
                    User my = userService.selectByPrimaryKey(d.getUserId());
                    if (my != null) {
                        d.setUserName(my.getName());
                    }
                }

                if (d.getDoctorId()!=null){
                    User dt = userService.selectByPrimaryKey(d.getDoctorId());
                    if (dt != null) {
                        d.setDoctorName(dt.getName());
                    }
                }
                resule.add(d);
            }
            if (diagnosis.getName()!=null && ""!=diagnosis.getName()){
                Iterator<Diagnosis> iterator = resule.iterator();
                while (iterator.hasNext()){
                    Diagnosis p = iterator.next();
                    if (!p.getName().contains(diagnosis.getName())){
                        iterator.remove();
                    }
                }
                size = rows.size();
            }
        } catch (Exception e) {
            logger.error("根据条件查询异常", e);
        }
        MMGridPageVoBean<Diagnosis> vo = new MMGridPageVoBean<>();
        vo.setTotal(size);
        vo.setRows(resule);

        return vo;
    }

    @Override
    public void deleteById(Long id) {
        diagnosisMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Diagnosis diagnosis) {
        diagnosisMapper.insert(diagnosis);
    }

    @Override
    public void update(Diagnosis diagnosis) {
        diagnosisMapper.updateByPrimaryKeySelective(diagnosis);
    }
}
