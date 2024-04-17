package org.example.express_backend.service;

import org.example.express_backend.mapper.LogisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticService {
    @Autowired
    private LogisticMapper logisticMapper;

    /**
     * 获取网点的等级
     * @param id 网点id
     * @return 网点等级
     */
    public String getLogisticLevel(Long id){
        return logisticMapper.selectById(id).getLevel();
    }
}
