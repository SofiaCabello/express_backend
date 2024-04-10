package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.example.express_backend.dto.CreateBatchDTO;
import org.example.express_backend.dto.UpdateBatchStatusDTO;
import org.example.express_backend.entity.Batch;
import org.example.express_backend.mapper.BatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {
    @Autowired
    private BatchMapper batchMapper;

    /**
     * 生成批次Id
     * @param origin 出发地
     * @param destination 目的地
     * @return 生成的批次Id
     */
    private String generateBatchId(Long origin, Long destination) {
        String time = String.valueOf(System.currentTimeMillis()).substring(7);
        return String.format("%d%d%s", origin, destination, time);
    }

    /**
     * 创建批次
     * @param DTO 创建批次的信息
     * @return 是否创建成功
     */
    public boolean createBatch(CreateBatchDTO DTO) {
        String batchId = generateBatchId(DTO.getOrigin(), DTO.getDestination());
        Batch batch = Batch.builder()
                .id(Long.parseLong(batchId))
                .origin(DTO.getOrigin())
                .destination(DTO.getDestination())
                .responsible(DTO.getResponsible())
                .status(Batch.statusEnum.IN_TRANS.getStatus())
                .build();
        try {
            batchMapper.insert(batch);
        } catch (MybatisPlusException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新批次状态
     * @param DTO 更新批次状态的信息
     * @return 是否更新成功
     */
    public boolean updateBatchStatus(UpdateBatchStatusDTO DTO) {
        Batch batch = batchMapper.selectById(DTO.getBatchId());
        if (batch == null) {
            return false;
        }
        batch.setStatus(DTO.getStatus());
        try {
            batchMapper.updateById(batch);
        } catch (MybatisPlusException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
