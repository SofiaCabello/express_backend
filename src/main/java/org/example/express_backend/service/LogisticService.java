package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.entity.Logistic;
import org.example.express_backend.mapper.LogisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogisticService extends ServiceImpl<LogisticMapper, Logistic> implements IService<Logistic> {
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

    public List<Long> getTransferRoute(Long originId, Long destinationId){
        List<Long> routeOrigin = new ArrayList<>();
        List<Long> routeDestination = new ArrayList<>();

        QueryWrapper<Logistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", originId);
        Long originParentId = logisticMapper.selectOne(queryWrapper).getParentId();
        queryWrapper.clear();
        queryWrapper.eq("id", destinationId);
        Long destinationParentId = logisticMapper.selectOne(queryWrapper).getParentId();
        while(originParentId != null){
            routeOrigin.add(originParentId);
            queryWrapper.clear();
            queryWrapper.eq("id", originParentId);
            originParentId = logisticMapper.selectOne(queryWrapper).getParentId();
        }
        while(destinationParentId != null){
            routeDestination.add(destinationParentId);
            queryWrapper.clear();
            queryWrapper.eq("id", destinationParentId);
            destinationParentId = logisticMapper.selectOne(queryWrapper).getParentId();
        }

        // 合并两个路径，逆序输入routeDestination
        List<Long> transferRoute = new ArrayList<>();
        transferRoute.add(originId);
        transferRoute.addAll(routeOrigin);
        for(int i = routeDestination.size() - 1; i >= 0; i--){
            transferRoute.add(routeDestination.get(i));
        }
        transferRoute.add(destinationId);
        System.out.println("transferRoute: " + transferRoute);
        return transferRoute;
    }

    public Map<String, int[]> getCountsByLevel(String level) {
        Map<String, int[]> countsMap = new HashMap<>();

        QueryWrapper<Logistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", level);

        List<Logistic> logistics = logisticMapper.selectList(queryWrapper);

        // 根据省份分类统计网点数量
        Map<String, Integer> provinceCounts = new HashMap<>();
        for (int i = 0; i < logistics.size(); i++) {
            Logistic logistic = logistics.get(i);
            String province = logistic.getProvince();
            provinceCounts.put(province, provinceCounts.getOrDefault(province, 0) + 1);
        }

        // 将统计结果放入结果集
        provinceCounts.forEach((province, count) -> {
            countsMap.put(province, new int[]{count});
        });

        return countsMap;
    }
}
