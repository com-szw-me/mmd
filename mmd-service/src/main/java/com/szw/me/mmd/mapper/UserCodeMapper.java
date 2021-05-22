package com.szw.me.mmd.mapper;

import com.szw.me.mmd.domain.UserCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCode record);

    int insertSelective(UserCode record);

    UserCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCode record);

    int updateByPrimaryKey(UserCode record);

    UserCode selectByPrefix(String prefix);
}