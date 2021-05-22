package com.szw.me.mmd.mapper;

import com.szw.me.mmd.domain.RoleAccess;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleAccessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAccess record);

    int insertSelective(RoleAccess record);

    RoleAccess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAccess record);

    int updateByPrimaryKey(RoleAccess record);
}