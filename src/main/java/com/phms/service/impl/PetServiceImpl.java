package com.phms.service.impl;

import com.phms.mapper.PetMapper;
import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.Pet;
import com.phms.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    PetMapper petMapper;
    @Override
    public Object getAllByLimit(Pet po) {
        int size = 0;
        // 计算分页
        Integer begin = (po.getPage() - 1) * po.getLimit();
        po.setPage(begin);

        List<Pet> rows = new ArrayList<>();
        try {
            rows = petMapper.getAllByLimit(po);
            size = petMapper.countAllByLimit(po);
        } catch (Exception e) {
            logger.error("根据条件查询异常", e);
        }
        MMGridPageVoBean<Pet> vo = new MMGridPageVoBean<>();
        vo.setTotal(size);
        vo.setRows(rows);

        return vo;
    }

    @Override
    public Pet selectByPrimaryKey(Long petId) {
        return petMapper.selectByPrimaryKey(petId);
    }

    @Override
    public void deleteById(Long id) {
        petMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Pet po) {
        petMapper.insert(po);
    }

    @Override
    public void update(Pet po) {
        petMapper.updateByPrimaryKeySelective(po);
    }
}
