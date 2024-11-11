package com.phms.service.impl;

import com.phms.mapper.StandardMapper;
import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.Standard;
import com.phms.service.StandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StandardServiceImpl implements StandardService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    StandardMapper standardMapper;
    @Override
    public Object getAllByLimit(Standard po) {
        int size = 0;
        // 计算分页
        Integer begin = (po.getPage() - 1) * po.getLimit();
        po.setPage(begin);

        List<Standard> rows = new ArrayList<>();
        try {
            rows = standardMapper.getAllByLimit(po);
            size = standardMapper.countAllByLimit(po);
        } catch (Exception e) {
            logger.error("根据条件查询异常", e);
        }
        MMGridPageVoBean<Standard> vo = new MMGridPageVoBean<>();
        vo.setTotal(size);
        vo.setRows(rows);

        return vo;
    }

    @Override
    public void deleteById(Long id) {
        standardMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Standard po) {
        standardMapper.insert(po);
    }

    @Override
    public void update(Standard po) {
        standardMapper.updateByPrimaryKeySelective(po);
    }
}
