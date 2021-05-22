package com.szw.me.mmd.service.impl;

import com.szw.me.mmd.domain.UserCode;
import com.szw.me.mmd.mapper.UserCodeMapper;
import com.szw.me.mmd.service.UserCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserCodeServiceImpl implements UserCodeService {

    @Autowired
    UserCodeMapper userCodeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getNextCode(String prefix) {
        UserCode userCode = userCodeMapper.selectByPrefix(prefix);
        if (userCode == null) {
            UserCode code = new UserCode();
            code.setPrefix(prefix);
            code.setIndex(1);
            userCodeMapper.insert(code);
        }
        int nextIndex = (userCode == null) ? 1 : userCode.getIndex() + 1;
        return prefix + String.format("%05d", nextIndex);
    }
}
