package com.phms.service.impl;

import com.phms.mapper.PetDailyMapper;
import com.phms.mapper.PetMapper;
import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.Pet;
import com.phms.pojo.PetDaily;
import com.phms.service.PetDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PetDailyServiceImpl implements PetDailyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    PetDailyMapper petDailyMapper;
    @Resource
    PetMapper petMapper;
    @Override
    public Object getAllByLimit(PetDaily po) {
        int size = 0;
        // 计算分页
        Integer begin = (po.getPage() - 1) * po.getLimit();
        po.setPage(begin);

        List<PetDaily> rows = new ArrayList<>();
        try {
            rows = petDailyMapper.getAllByLimit(po);
            for (PetDaily p : rows){
                Pet pet = petMapper.selectByPrimaryKey(p.getPetId());
                p.setName(pet.getName());
            }
            size = petDailyMapper.countAllByLimit(po);
            if (po.getName()!=null && ""!=po.getName()){
                Iterator<PetDaily> iterator = rows.iterator();
                while (iterator.hasNext()){
                    PetDaily p = iterator.next();
                    if (!p.getName().contains(po.getName())){
                        iterator.remove();
                    }
                }
                size = rows.size();
            }
        } catch (Exception e) {
            logger.error("根据条件查询异常", e);
        }
        MMGridPageVoBean<PetDaily> vo = new MMGridPageVoBean<>();
        vo.setTotal(size);
        vo.setRows(rows);

        return vo;
    }

    @Override
    public void deleteById(Long id) {
        petDailyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(PetDaily po) {
        petDailyMapper.insert(po);
    }

    @Override
    public void update(PetDaily po) {
        petDailyMapper.updateByPrimaryKeySelective(po);
    }
}
