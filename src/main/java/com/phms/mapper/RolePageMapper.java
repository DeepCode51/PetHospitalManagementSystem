package com.phms.mapper;


import com.phms.pojo.RolePage;
import com.phms.pojo.RolePageExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePageMapper {
    long countByExample(RolePageExample example);

    int deleteByExample(RolePageExample example);

    int deleteByPrimaryKey(Integer rpId);

    int insert(RolePage record);

    int insertSelective(RolePage record);

    List<RolePage> selectByExample(RolePageExample example);

    RolePage selectByPrimaryKey(Integer rpId);

    int updateByExampleSelective(@Param("record") RolePage record, @Param("example") RolePageExample example);

    int updateByExample(@Param("record") RolePage record, @Param("example") RolePageExample example);

    int updateByPrimaryKeySelective(RolePage record);

    int updateByPrimaryKey(RolePage record);
}